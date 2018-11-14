package com.remotedev.boilerplate.enums;

/**
 * BuildEnum, containing all settings for each type of build
 *
 * Created by Erwin on 18-2-2018.
 */
public enum BuildEnum {

    DEBUG("debug", true),
    ALPHA("alpha", false),
    BETA("beta", false),
    RELEASE("release", false);

    public final String buildType;
    public final boolean erasePreferences;

    BuildEnum(String buildType, boolean erasePreferences) {
        this.buildType = buildType;
        this.erasePreferences = erasePreferences;
    }
}