package com.remotedev.boilerplate.enums;

/**
 * PreferenceEnum, containing all preference keys
 *
 * Created by Erwin on 18-2-2018.
 */
public enum PreferenceEnum {

    // preference key for onboarding screen
    ONBOARDING_VERSION_SEEN("onboarding_version_seen");

    public final String key;
    PreferenceEnum(String preferenceVariableKey) {
        this.key = preferenceVariableKey;
    }
}