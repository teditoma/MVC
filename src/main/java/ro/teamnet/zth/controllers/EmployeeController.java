package ro.teamnet.zth.controllers;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;

/**
 * Created by Theodor.Toma on 7/20/2017.
 */

@MyController(urlPath = "/employees")
public class EmployeeController {
    @MyRequestMethod(methodType = "GET", urlPath = "/all")
    public String getAllEmployees() {
        return "allEmployees";
    }

    @MyRequestMethod(methodType = "GET", urlPath = "/one")
    public String getOneEmployee() { return "oneRandomEmployee";}
}
