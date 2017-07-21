package ro.teamnet.zth;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import ro.teamnet.zth.fmk.MethodAttributes;
import ro.teamnet.zth.fmk.domain.HttpMethod;
import ro.teamnet.zth.utils.BeanDeserializator;
import ro.teamnet.zth.utils.ComponentScanner;
import ro.teamnet.zth.utils.ControllerScanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Z2HDispatcherServlet extends HttpServlet {

    private ComponentScanner controllerScanner;

    @Override
    public void init() throws ServletException {
        controllerScanner = new ControllerScanner("ro.teamnet.zth.appl.controller");
        controllerScanner.scan();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply(req, resp, HttpMethod.GET);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply(req, resp, HttpMethod.POST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply(req, resp, HttpMethod.DELETE);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply(req, resp, HttpMethod.PUT);
    }

    private void dispatchReply(HttpServletRequest req, HttpServletResponse resp, HttpMethod methodType) {
        try {
            Object resultToDisplay = dispatch(req, methodType);
            reply(resp, resultToDisplay);
        } catch (Exception e) {
            try {
                sendExceptionError(e, resp);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private void sendExceptionError(Exception e, HttpServletResponse resp) throws IOException {
        //resp.getWriter().print(e.getMessage());
        e.printStackTrace();
    }

    private void reply(HttpServletResponse resp, Object resultToDisplay) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObject = objectMapper.writeValueAsString(resultToDisplay);
        resp.getWriter().write(jsonObject);
    }

    private Object dispatch(HttpServletRequest req, HttpMethod methodType) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        String uri = req.getRequestURI();
        uri = uri.substring(8);
        MethodAttributes methodAttributes= controllerScanner.getMetaData(uri,methodType);
        Class aClass = methodAttributes.getControllerClass();
        Object instance = aClass.newInstance();
        Method method = methodAttributes.getMethod();

        List<Object> params = BeanDeserializator.getMethodParams(method,req);
        System.out.println(method.getName() + " " + aClass.getName() + params.toString());
        Object res = method.invoke(instance,params.toArray());

        return res;
    }


}
