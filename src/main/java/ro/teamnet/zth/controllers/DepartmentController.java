package ro.teamnet.zth.controllers;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;

/**
 * Created by Theodor.Toma on 7/20/2017.
 */
@MyController(urlPath = "/departments")
public class DepartmentController {
    @MyRequestMethod(methodType = "GET",urlPath = "/all")
    public String getAllDepartments() {
        return "allDepartments";
    }
}
