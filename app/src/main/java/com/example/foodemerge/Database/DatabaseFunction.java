//資料庫的功能

package com.example.foodemerge.Database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class DatabaseFunction {
    private volatile static DatabaseFunction instance = null;

    //功能模組，多一個分類就要再複製一次
    //"DATABASE_FOOD_INFO"的功能模組
    private ArrayList<DatabaseForm> database = new ArrayList<>();
    String DATABASE_FOOD_INFO = "database_food_info";  //分類
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
            if( database.get(i).food_name.compareTo(name) == 0)  //如果字串一樣就刪除
            {
                this.database.remove(i);
            }
        }

    }
    //"DATABASE_FOOD_INFO"的功能模組
    //功能模組，多一個分類就要再複製一次



    //"DATABASE_SHOPPING_LIST"的功能模組
    private ArrayList<DatabaseForm> databaseShoppingList = new ArrayList<>();
    String DATABASE_SHOPPING_LIST = "database_shopping_list";  //分類
    public ArrayList<DatabaseForm> getDatabaseShoppingList() {
        return this.databaseShoppingList;
    }

    public void setDatabaseShoppingList(ArrayList<DatabaseForm> databaseShoppingList) {
        this.databaseShoppingList = databaseShoppingList;
    }

    public void addDatabaseShoppingList(DatabaseForm databaseShoppingList) {
        if (this.databaseShoppingList == null){
            this.databaseShoppingList = new ArrayList<>();
        }
        this.databaseShoppingList.add(databaseShoppingList);
    }

    public void deleteDatabaseShoppingList() {
        MainApplication.clearSettings(DATABASE_SHOPPING_LIST);
        this.databaseShoppingList = null;
    }

    public void readDatabaseShoppingList() {
        String json = MainApplication.readSetting(DATABASE_SHOPPING_LIST);

        try {
            if (json != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<DatabaseForm>>() {
                }.getType();

                this.databaseShoppingList = new ArrayList<>();
                this.databaseShoppingList = gson.fromJson(json, type);
            }
        }catch (Exception e) {
            MainApplication.clearSettings(DATABASE_SHOPPING_LIST);
            this.databaseShoppingList = null;
        }
    }

    public void saveDatabaseShoppingList() {
        saveObjectSetting(DATABASE_SHOPPING_LIST, this.databaseShoppingList);
    }

    public void removeDatabaseShoppingList(String name) {
        String want_delet_food = name;

        for( int i = 0 ; i < this.databaseShoppingList.size() ; i++)
        {

            /*
            if( database.get(i).food_neme.contains(name))  //包含
            {
                this.database.remove(i);
            }
            */
            if( databaseShoppingList.get(i).food_name.compareTo(want_delet_food) == 0)  //如果字串一樣就刪除
            {
                this.databaseShoppingList.remove(i);
            }
        }

    }
    //"DATABASE_SHOPPING_LIST"的功能模組
    //功能模組，多一個分類就要再複製一次



    //"DATABASE_HOME_FOOD"的功能模組
    private ArrayList<DatabaseForm> databaseHomeFood = new ArrayList<>();
    String DATABASE_HOME_FOOD = "database_home_food";  //分類
    public ArrayList<DatabaseForm> getDatabaseHomeFood() {
        return this.databaseHomeFood;
    }

    public void setDatabaseHomeFood(ArrayList<DatabaseForm> databaseHomeFood) {
        this.databaseHomeFood = databaseShoppingList;
    }

    public void addDatabaseHomeFood(DatabaseForm databaseHomeFood) {
        if (this.databaseHomeFood == null){
            this.databaseHomeFood = new ArrayList<>();
        }
        this.databaseHomeFood.add(databaseHomeFood);
    }

    public void deleteDatabaseHomeFood() {
        MainApplication.clearSettings(DATABASE_HOME_FOOD);
        this.databaseHomeFood = null;
    }

    public void readDatabaseHomeFood() {
        String json = MainApplication.readSetting(DATABASE_HOME_FOOD);

        try {
            if (json != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<DatabaseForm>>() {
                }.getType();

                this.databaseHomeFood = new ArrayList<>();
                this.databaseHomeFood = gson.fromJson(json, type);
            }
        }catch (Exception e) {
            MainApplication.clearSettings(DATABASE_HOME_FOOD);
            this.databaseHomeFood = null;
        }
    }

    public void saveDatabaseHomeFood() {
        saveObjectSetting(DATABASE_HOME_FOOD, this.databaseHomeFood);
    }

    public void removeDatabaseHomeFood(String name) {
        for( int i = 0 ; i < this.databaseHomeFood.size() ; i++)
        {
            /*
            if( database.get(i).food_neme.contains(name))  //包含
            {
                this.database.remove(i);
            }
            */
            if( databaseHomeFood.get(i).food_name.compareTo(name) == 0)  //如果字串一樣就刪除
            {
                this.databaseHomeFood.remove(i);
            }
        }

    }
    //"DATABASE_HOME_FOOD"的功能模組
    //功能模組，多一個分類就要再複製一次


    //"DATABASE_MONEY"的功能模組
    private ArrayList<DatabaseForm> databaseMoney = new ArrayList<>();
    String DATABASE_MONEY = "database_money";  //分類
    public ArrayList<DatabaseForm> getDatabaseMoney() {
        return this.databaseMoney;
    }

    public void setDatabaseMoney(ArrayList<DatabaseForm> databaseMoney) {
        this.databaseMoney = databaseMoney;
    }

    public void addDatabaseMoney(DatabaseForm databaseMoney) {
        if (this.databaseMoney == null){
            this.databaseMoney = new ArrayList<>();
        }
        this.databaseMoney.add(databaseMoney);
    }

    public void deleteDatabaseMoney() {
        MainApplication.clearSettings(DATABASE_MONEY);
        this.databaseMoney = null;
    }

    public void readDatabaseMoney() {
        String json = MainApplication.readSetting(DATABASE_MONEY);

        try {
            if (json != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<DatabaseForm>>() {
                }.getType();

                this.databaseMoney = new ArrayList<>();
                this.databaseMoney = gson.fromJson(json, type);
            }
        }catch (Exception e) {
            MainApplication.clearSettings(DATABASE_MONEY);
            this.databaseMoney = null;
        }
    }

    public void saveDatabaseMoney() {
        saveObjectSetting(DATABASE_MONEY, this.databaseMoney);
    }

    public void removeDatabaseMoney(String name) {
        for( int i = 0 ; i < this.databaseMoney.size() ; i++)
        {
            /*
            if( database.get(i).food_neme.contains(name))  //包含
            {
                this.database.remove(i);
            }
            */
            if( databaseMoney.get(i).food_name.compareTo(name) == 0)  //如果字串一樣就刪除
            {
                this.databaseMoney.remove(i);
            }
        }

    }
    //"DATABASE_MONEY"的功能模組
    //功能模組，多一個分類就要再複製一次
    //DATABASE_FOOD_INFO模組


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

    //每個database都要初始化
    private void initialize() {
        readDatabase();
        readDatabaseShoppingList();
        readDatabaseHomeFood();
        readDatabaseMoney();
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


















