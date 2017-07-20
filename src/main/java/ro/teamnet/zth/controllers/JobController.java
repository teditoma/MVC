package ro.teamnet.zth.controllers;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;

/**
 * Created by Theodor.Toma on 7/20/2017.
 */

@MyController(urlPath = "/jobs")
public class JobController {
    @MyRequestMethod(methodType = "GET", urlPath = "/all")
    public String getAllJobs() {
        return "allJobs";
    }

    @MyRequestMethod(methodType = "GET", urlPath = "/one")
    public String getOneJob() { return "oneRandomJob";}
}
