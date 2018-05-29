package controllers;


import play.mvc.*;


public class ApplicationController extends Controller {

    public static Result index() throws Exception {
        throw new Exception("Test exception from Play 2 using Java");
    }

}

