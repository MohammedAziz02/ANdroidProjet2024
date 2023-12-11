package com.projetjava.DataBase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.projetjava.Models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recipe_database";
    private static final int DATABASE_VERSION = 1;

    // Table name and columns
    private static final String TABLE_RECIPE = "recipes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name_recipe";
    private static final String COLUMN_TYPE = "type_recipe";
    private static final String COLUMN_DIFFICULTY = "difficulty_recipe";
    private static final String COLUMN_DESCRIPTION = "description_recipe";
    private static final String COLUMN_INGREDIENTS = "ingredients_recipe";
    private static final String COLUMN_IMAGE = "image_bytes";

    // Create table query
    private static final String CREATE_TABLE_RECIPE = "CREATE TABLE " + TABLE_RECIPE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_TYPE + " TEXT,"
            + COLUMN_DIFFICULTY + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_INGREDIENTS + " TEXT,"
            + COLUMN_IMAGE + " BLOB"
            + ")";

    public RecipeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RECIPE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
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

}

