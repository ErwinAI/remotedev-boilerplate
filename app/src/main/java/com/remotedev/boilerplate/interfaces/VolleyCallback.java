package com.remotedev.boilerplate.interfaces;

import com.android.volley.VolleyError;

/**
 * VolleyCallback interface
 *
 * Created by Erwin on 07/03/2018.
 */
public interface VolleyCallback<T> {

    /**
     * Callback for when result is in from the volley request
     * @param response response that is returned from the call
     * @param error error in case there is any, else it is null
     * @param errorMessage errorMessage to indicate what went wrong, if anything did
     */
    void getResult(T response, VolleyError error, String errorMessage);
}

