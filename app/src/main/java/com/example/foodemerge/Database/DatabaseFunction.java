//資料庫的功能

package com.example.foodemerge.Database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DatabaseFunction {
    private volatile static DatabaseFunction instance = null;
    private ArrayList<DatabaseForm> database = new ArrayList<>();

    String DATABASE_FOOD_INFO = "database_food_info";  //分類

    //功能模組，多一個分類就要再複製一次
    public ArrayList<DatabaseForm> getDatabase() {
        return this.database;
    }

    public void setDatabase(ArrayList<DatabaseForm> database) {
        this.database = database;
    }

    public void addDatabase(DatabaseForm database) {
        if (this.database == null){
            this.database = new ArrayList<>();
        }
        this.database.add(database);
    }

    public void deleteDatabase() {
        MainApplication.clearSettings(DATABASE_FOOD_INFO);
        this.database = null;
    }

    public void readDatabase() {
        String json = MainApplication.readSetting(DATABASE_FOOD_INFO);

        try {
            if (json != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<DatabaseForm>>() {
                }.getType();

                this.database = new ArrayList<>();
                this.database = gson.fromJson(json, type);
            }
        }catch (Exception e) {
            MainApplication.clearSettings(DATABASE_FOOD_INFO);
            this.database = null;
        }
    }

    public void saveDatabase() {
        saveObjectSetting(DATABASE_FOOD_INFO, this.database);
    }

    public void removeDatabase(String name) {
        for( int i = 0 ; i < this.database.size() ; i++)
        {
            /*
            if( database.get(i).food_neme.contains(name))  //包含
            {
                this.database.remove(i);
            }
            */
            if( database.get(i).food_neme.compareTo(name) == 0)  //如果字串一樣就刪除
            {
                this.database.remove(i);
            }
        }

    }
    //功能模組，多一個分類就要再複製一次



    //建構子，不要碰
    private DatabaseFunction() {
        initialize();
    }

    public static DatabaseFunction getInstance() {
        if (instance == null) {
            synchronized (DatabaseFunction.class) {
                if (instance == null) {
                    instance = new DatabaseFunction();
                }
            }
        }
        return instance;
    }

    private void initialize() {
        readDatabase();
    }

    private <T> T readObjectSetting(String type_string, Class<T> classOfT) {
        Gson gson = new Gson();
        String json = MainApplication.readSetting(type_string);

        if (json != null && json.length() > 0) {
            return gson.fromJson(json, classOfT);
        }
        return null;
    }

    private void saveObjectSetting(String type_string, Object object) {
        MainApplication.clearSettings(type_string);
        Gson gson = new Gson();
        String json = gson.toJson(object);
        MainApplication.writeSetting(type_string, json);
    }
    //建構子，不要碰

}


















