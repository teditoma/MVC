package ro.teamnet.zth.controllers;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;

/**
 * Created by Theodor.Toma on 7/20/2017.
 */

@MyController(urlPath = "/locations")
public class LocationController {
    @MyRequestMethod(methodType = "GET", urlPath = "/all")
    public String getAllLocations() {
        return "allLocations";
    }

    @MyRequestMethod(methodType = "GET", urlPath = "/one")
    public String getOneLocation() { return "oneRandomJob";}
}

