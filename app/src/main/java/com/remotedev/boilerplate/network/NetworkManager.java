package com.remotedev.boilerplate.network;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.remotedev.boilerplate.R;
import com.remotedev.boilerplate.enums.BuildEnum;
import com.remotedev.boilerplate.enums.PreferenceEnum;
import com.remotedev.boilerplate.interfaces.VolleyCallback;
import com.remotedev.boilerplate.variables.GlobalVariables;
import com.remotedev.boilerplate.variables.PreferenceVariables;


/**
 * NetworkManager containing methods to do Volley calls with
 *
 * Created by Erwin on 07/03/2018.
 */
public class NetworkManager {

    private final String TAG = getClass().getSimpleName();
    private static NetworkManager instance = null;
    private Context context;

    private ConnectivityManager connectivityManager;
    private Resources resources;

    // for Volley API
    public RequestQueue requestQueue;

    /**
     * Constructor that makes requestQueue
     *
     * @param context context
     */
    private NetworkManager(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        resources = context.getResources();
        this.context = context;
    }

    /**
     * getInstance method to return static synchronized instance
     *
     * @param context context
     * @return NetworkManager instance
     */
    public static synchronized NetworkManager getInstance(Context context) {
        if (null == instance)
            instance = new NetworkManager(context);
        return instance;
    }

    /**
     * Regular getResponse method to obtain a JSON response from the backend.
     * getCall method to obtain a JSON response from the backend.
     *
     * @param url         backend url to make the call to
     * @param jsonPayload JSON payload for the request. Is an optional parameter, may be null.
     * @param listener    listener that is activated when the call is finished and a response is known
     */
    public void getCall(String url, String jsonPayload, final VolleyCallback<String> listener) {
        doRequest(url, jsonPayload, Request.Method.GET, listener);
    }

    /**
     * postCall method to obtain a JSON response from the backend.
     *
     * @param url         backend url to make the call to
     * @param jsonPayload JSON payload for the request. Is an optional parameter, may be null.
     * @param listener    listener that is activated when the call is finished and a response is known
     */
    public void postCall(String url, String jsonPayload, final VolleyCallback<String> listener) {
        doRequest(url, jsonPayload, Request.Method.POST, listener);
    }

    /**
     * putCall method to obtain a JSON response from the backend.
     *
     * @param url         backend url to make the call to
     * @param jsonPayload JSON payload for the request. Is an optional parameter, may be null.
     * @param listener    listener that is activated when the call is finished and a response is known
     */
    public void putCall(String url, String jsonPayload, final VolleyCallback<String> listener) {
        doRequest(url, jsonPayload, Request.Method.PUT, listener);
    }

    /**
     * deleteCall method to delete a item.
     *
     * @param url         backend url to make the call to
     * @param jsonPayload JSON payload for the request. Is an optional parameter, may be null.
     * @param listener    listener that is activated when the call is finished and a response is known
     */
    public void deleteCall(String url, String jsonPayload, final VolleyCallback<String> listener) {
        doRequest(url, jsonPayload, Request.Method.DELETE, listener);
    }

    private void doRequest(final String url, String jsonPayload, int method, final VolleyCallback<String> listener) {

        // check if connected to internet
        if(!hasConnectivity()) {
            listener.getResult(null, new VolleyError("NetworkError"), resources.getString(R.string.network_error));
        }
        else {
            Log.d(TAG, "Call to server with url: " + url + " and method: " + method + " and payload: " + jsonPayload);

            MetaStringRequest request = new MetaStringRequest(method, url, jsonPayload,
                    new Response.Listener<MetaStringRequest.MetaResponse>() {

                        /**
                         * What to do when a response is known
                         * @param response string response containing json
                         */
                        @Override
                        public void onResponse(MetaStringRequest.MetaResponse response) {
                            if (response != null) {
                                // send the result
                                listener.getResult(response.getResponse(), null, null);

                                // log the response
                                logResponse(response);
                            } else {
                                Log.d(TAG, "Response is null");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        /**
                         * What to do when an error occurred
                         * @param error error that occurred
                         */
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String message = "";
                            String logMessage = "null";
                            int statusCode = -1;
                            String errorData = "null";

                            if (error.networkResponse != null) {
                                statusCode = error.networkResponse.statusCode;

                                if (error.networkResponse.data != null) {
                                    errorData = new String(error.networkResponse.data);
                                }
                            }

                            if (error instanceof ServerError) {
                                message = resources.getString(R.string.server_error);
                                logMessage = "ServerError";
                            } else if (error instanceof NetworkError) {
                                message = resources.getString(R.string.network_error);
                                logMessage = "NetworkError";
                            } else if (error instanceof AuthFailureError) {
                                message = resources.getString(R.string.authfailure_error);
                                logMessage = "AuthFailureError";
                            } else if (error instanceof ParseError) {
                                message = resources.getString(R.string.parse_error);
                                logMessage = "ParseError";
                            } else if (error instanceof TimeoutError) {
                                message = resources.getString(R.string.timeout_error);
                                logMessage = "TimeoutError";
                            }

                            // log to console
                            Log.d(TAG, logMessage + " [" + statusCode + "] from url: " + url);
                            Log.d(TAG, "Data: " + errorData);
                            Log.e(TAG, "onErrorResponse: ", error);

                            // return the callback
                            listener.getResult(null, error, message);
                        }
                    }
            );

            //add request to queue
            requestQueue.add(request);
        }
    }

    /**
     * Log the data when there is a response and when the build is not in release mode
     * @param response response
     */
    private void logResponse(MetaStringRequest.MetaResponse response) {
        // only do this when not in release mode and if response not empty
        if(GlobalVariables.CURRENT_BUILD != BuildEnum.RELEASE && !TextUtils.isEmpty(response.getResponse())) {

            // get the mapper
            ObjectMapper mapper = new ObjectMapper();

            // try to map for a pretty log message
            try {
                Object jsonObject = mapper.readValue(response.getResponse(), Object.class);
                String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
                Log.d(TAG, "Response from call to backend was obtained: \n" + prettyJson);
            }
            // no pretty log message. output the error and the original
            catch (IOException e) {
                Log.d(TAG, "Response cannot be parsed. Exception is:");
                Log.e(TAG, e.toString());
                Log.d(TAG, "Response was:\n" + response.getResponse());
            }
        }
    }

    /**
     * Returns a boolean true if the device is connected to internet
     * @return whether or not the device has connectivity
     */
    private boolean hasConnectivity() {
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}