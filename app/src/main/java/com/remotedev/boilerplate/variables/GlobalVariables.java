package com.remotedev.boilerplate.variables;

import com.remotedev.boilerplate.BuildConfig;
import com.remotedev.boilerplate.enums.BuildEnum;

/**
 * GlobalVariables defining static final variables to be used anywhere.
 *
 * Created by Erwin on 18/02/2018.
 */

public class GlobalVariables {

    /**
     * Build variable (SET FROM GRADLE FILE)
     */
    public static final BuildEnum CURRENT_BUILD = BuildConfig.BUILD;
}
