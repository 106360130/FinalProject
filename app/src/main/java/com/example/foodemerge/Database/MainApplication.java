package com.example.foodemerge.Database;

import android.app.Application;
import android.content.SharedPreferences;

public class MainApplication extends Application {
    private static MainApplication singleton;
    private static String SETTING_NAME = "database";

    public static MainApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        DatabaseFunction.getInstance();
    }

    public static String readSetting(String settingName) {
        SharedPreferences settings = singleton.getSharedPreferences(
                SETTING_NAME, MODE_PRIVATE);
        return settings.getString(settingName, "");
    }

    public static void writeSetting(String settingName, String value) {
        SharedPreferences settings = singleton.getSharedPreferences(
                SETTING_NAME, MODE_PRIVATE);
        settings.edit().putString(settingName, value).apply();
    }

    public static void clearSettings(String settingName) {
        SharedPreferences settings = singleton.getSharedPreferences(
                SETTING_NAME, MODE_PRIVATE);
        settings.edit().remove(settingName).apply();
    }
}

