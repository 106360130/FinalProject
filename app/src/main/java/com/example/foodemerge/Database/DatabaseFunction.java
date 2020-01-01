//資料庫的功能

package com.example.foodemerge.Database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class DatabaseFunction {
    private volatile static DatabaseFunction instance = null;

    //DATABASE_FOOD_INFO模組
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
    //功能模組，多一個分類就要再複製一次
    //DATABASE_FOOD_INFO模組

    //DATABASE_SHOPPING_LIST模組
    private ArrayList<DatabaseForm> databaseShoppingList = new ArrayList<>();
    String DATABASE_SHOPPING_LIST = "database_food_info";  //分類

    //功能模組，多一個分類就要再複製一次
    public ArrayList<DatabaseForm> getDatabaseShoppingList() {
        return this.databaseShoppingList;
    }

    //增加
    public void setDatabaseShoppingList(ArrayList<DatabaseForm> databaseShoppingList) {
        this.databaseShoppingList = databaseShoppingList;
    }

    //修改
    public void addDatabaseShoppingList(DatabaseForm database) {
        if (this.databaseShoppingList == null){
            this.databaseShoppingList = new ArrayList<>();
        }
        this.databaseShoppingList.add(database);
    }

    public void deleteDatabaseShoppingList() {
        MainApplication.clearSettings(DATABASE_FOOD_INFO);
        this.databaseShoppingList = null;
    }

    public void readDatabaseShoppingList() {
        String json = MainApplication.readSetting(DATABASE_FOOD_INFO);

        try {
            if (json != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<DatabaseForm>>() {
                }.getType();

                this.databaseShoppingList = new ArrayList<>();
                this.databaseShoppingList = gson.fromJson(json, type);
            }
        }catch (Exception e) {
            MainApplication.clearSettings(DATABASE_FOOD_INFO);
            this.databaseShoppingList = null;
        }
    }

    public void saveDatabaseShoppingList() {
        saveObjectSetting(DATABASE_FOOD_INFO, this.databaseShoppingList);
    }
    //功能模組，多一個分類就要再複製一次
    //DATABASE_SHOPPING_LIST模組



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


















