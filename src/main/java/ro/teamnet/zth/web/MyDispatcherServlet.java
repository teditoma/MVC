package ro.teamnet.zth.web;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.controllers.DepartmentController;
import ro.teamnet.zth.controllers.EmployeeController;
import ro.teamnet.zth.fmk.AnnotationScanUtils;
import ro.teamnet.zth.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Theodor.Toma on 7/20/2017.
 */
public class MyDispatcherServlet extends HttpServlet {

    private Map<String, MethodAttributes> urlMap;

    @Override
    public void init() throws ServletException {
        urlMap = new HashMap();
        try {
            ArrayList<Class> classes = (ArrayList<Class>) AnnotationScanUtils.getClasses("ro.teamnet.zth.controllers");
            for (Class clazz : classes) {
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(MyRequestMethod.class)) {
                        String url = "/app/mvc" + ((MyController) clazz.getAnnotation(MyController.class)).urlPath();
                        String methodRequest = ((MyRequestMethod) method.getAnnotation(MyRequestMethod.class)).methodType();
                        url += ((MyRequestMethod) method.getAnnotation(MyRequestMethod.class)).urlPath();
                        String key = url + ":" + methodRequest;
                        MethodAttributes methodAttributes = new MethodAttributes();
                        methodAttributes.setControllerClass(clazz.getName());
                        methodAttributes.setMethodName(method.getName());
                        methodAttributes.setMethodType(methodRequest);
                        urlMap.put(key, methodAttributes);
                        System.out.println(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void dispatchReplay(HttpServletRequest req, HttpServletResponse resp, String method) throws IOException {
        try {
            Object result = dispatch(req,method);
            reply(resp,result);
        }
        catch (Exception e) {
            e.printStackTrace();
            sendExeptionError(resp);
        }
    }

    private Object dispatch(HttpServletRequest req, String method) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
        String key = req.getRequestURI() + ":" + method;
        System.out.println(key);
        System.out.println(urlMap.containsKey(key));
        MethodAttributes methodAttributes = urlMap.get(key);
        System.out.println(methodAttributes.getControllerClass());
        System.out.println(methodAttributes.getMethodName());

        Class clazz = Class.forName(methodAttributes.getControllerClass());
        Object instance = clazz.newInstance();
        Method method1 = clazz.getDeclaredMethod(methodAttributes.getMethodName());
        Object res = method1.invoke(instance);

        System.out.println(res.toString());
        return res;
    }

    private void reply(HttpServletResponse resp, Object result) throws IOException {
        resp.getWriter().write(result.toString());
    }



    private void sendExeptionError(HttpServletResponse resp) throws IOException {
        resp.getWriter().write("Error!");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReplay(req,resp,"GET");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReplay(req,resp,"POST");
    }
}
