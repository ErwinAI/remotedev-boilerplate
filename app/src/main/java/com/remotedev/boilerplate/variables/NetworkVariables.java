package com.remotedev.boilerplate.variables;

/**
 * NetworkVariables containing all backend API urls
 * <p>
 * Created by Erwin on 07/03/2018.
 */
public class NetworkVariables {

    // Sample url to test basic web call
    private static final String SAMPLE_URL = "https://jsonplaceholder.typicode.com/todos/1";

    public static String getSampleUrl() {
        return SAMPLE_URL;
    }
}