package com.projetjava.DataBase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.projetjava.Activities.User;
import com.projetjava.Models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recipe_database";
    private static final int DATABASE_VERSION = 5;

    // Table name and columns
    private static final String TABLE_RECIPE = "recipes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name_recipe";
    private static final String COLUMN_TYPE = "type_recipe";
    private static final String COLUMN_DIFFICULTY = "difficulty_recipe";
    private static final String COLUMN_DESCRIPTION = "description_recipe";
    private static final String COLUMN_INGREDIENTS = "ingredients_recipe";
    private static final String COLUMN_IMAGE = "image_bytes";

    // Table name and columns for User
    private static final String TABLE_USER = "users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PHONE = "user_phone";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_IS_LOGGED_IN = "is_logged_in";

    // Create table query for recipes
    private static final String CREATE_TABLE_RECIPE = "CREATE TABLE " + TABLE_RECIPE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_TYPE + " TEXT,"
            + COLUMN_DIFFICULTY + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_INGREDIENTS + " TEXT,"
            + COLUMN_IMAGE + " BLOB"
            + ")";

    // Create table query for User
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT UNIQUE,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_PHONE + " TEXT,"
            + COLUMN_IS_LOGGED_IN + " INTEGER DEFAULT 0"
            + ")";
    public RecipeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RECIPE);
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    // CRUD Operations
    public long addRecipe(String name,String type,String difficulty, String description, String ingredients, byte[] imageBytes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_DIFFICULTY,difficulty );
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_INGREDIENTS, ingredients);
        values.put(COLUMN_IMAGE, imageBytes);
        long id = db.insert(TABLE_RECIPE, null, values);
        db.close();
        return id;
    }


    @SuppressLint("Range")
    public Recipe getRecipe(long recipeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME,COLUMN_TYPE,COLUMN_DIFFICULTY, COLUMN_DESCRIPTION, COLUMN_INGREDIENTS, COLUMN_IMAGE};
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(recipeId)};
        Cursor cursor = db.query(TABLE_RECIPE, columns, selection, selectionArgs, null, null, null);
        Recipe recipe = null;
        if (cursor != null && cursor.moveToFirst()) {
            recipe = new Recipe(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DIFFICULTY)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENTS)),
                    cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE))
            );
            cursor.close();
        }
        db.close();
        return recipe;
    }

    public int updateRecipe(long recipeId, String name,String type, String difficulty, String description, String ingredients, byte[] imageBytes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_DIFFICULTY,difficulty);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_INGREDIENTS, ingredients);
        values.put(COLUMN_IMAGE, imageBytes);
        String whereClause = COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(recipeId)};
        int rowsAffected = db.update(TABLE_RECIPE, values, whereClause, whereArgs);
        db.close();
        return rowsAffected;
    }

    public int deleteRecipe(long recipeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(recipeId)};
        int rowsAffected = db.delete(TABLE_RECIPE, whereClause, whereArgs);
        db.close();
        return rowsAffected;
    }


    @SuppressLint("Range")
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME,COLUMN_TYPE,COLUMN_DIFFICULTY, COLUMN_DESCRIPTION, COLUMN_INGREDIENTS, COLUMN_IMAGE};
        Cursor cursor = db.query(TABLE_RECIPE, columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
               Recipe recipe = new Recipe(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                       cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)),
                       cursor.getString(cursor.getColumnIndex(COLUMN_DIFFICULTY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENTS)),
                        cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE))
                );
                recipeList.add(recipe);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return recipeList;
    }

    @SuppressLint("Range")
    public long getItemIdByName(String itemName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ID};
        String selection = COLUMN_NAME + "=?";
        String[] selectionArgs = {itemName};
        Cursor cursor = db.query(TABLE_RECIPE, projection, selection, selectionArgs, null, null, null);
        long itemId = -1; // Default value if not found
        if (cursor != null && cursor.moveToFirst()) {
            itemId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            cursor.close();
        }
        db.close();
        return itemId;

    }
    // Méthode pour obtenir l'utilisateur actuellement connecté
    @SuppressLint("Range")
    public User getCurrentUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        User user = null;
        try {
            String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_IS_LOGGED_IN + " = 1";
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return user;
    }
    // Méthode pour récupérer un utilisateur par e-mail et mot de passe
    @SuppressLint("Range")
    public User getUserByEmailAndPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        User user = null;
        try {
            String query = "SELECT * FROM " + TABLE_USER +
                    " WHERE " + COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
            cursor = db.rawQuery(query, new String[]{email, password});

            if (cursor != null && cursor.moveToFirst()) {
                user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return user;
    }
    // add user
    public long addUser(String name, String email,String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_PHONE, phone);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);
        long userId = db.insert(TABLE_USER, null, values);
        db.close();
        return userId;
    }

    // Reset password
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + " = ?";
            cursor = db.rawQuery(query, new String[]{email});
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

}

