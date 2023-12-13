package com.projetjava.DataBase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.projetjava.Models.User;
import com.projetjava.Models.Recipe;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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

    private Context context;
    public RecipeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RECIPE);
        db.execSQL(CREATE_TABLE_USER);
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, "Moroccan Tagine");
        values.put(COLUMN_TYPE, "Dinner");
        values.put(COLUMN_DIFFICULTY,"Intermediate" );
        values.put(COLUMN_DESCRIPTION, "A tagine is a slow-cooked North African dish, typically featuring meat, vegetables, and spices. Named after the earthenware pot it's cooked in, tagines are known for rich flavors and tender textures.");
        values.put(COLUMN_INGREDIENTS, "1.5 lbs Chicken pieces (drumsticks, thighs, or a whole chicken, cut into pieces)\n2 Onions (finely chopped)\n3 cloves Garlic (minced)\n2 tablespoons Olive Oil\n1 teaspoon Ground Cumin\n1 teaspoon Ground Paprika\n1 teaspoon Ground Turmeric\n1 teaspoon Ground Ginger\n1 teaspoon Ground Cinnamon\n1/2 teaspoon Ground Coriander\n1/4 teaspoon Cayenne Pepper (optional, for heat)\nSalt (to taste)\nBlack Pepper (to taste)\n1 cup Chicken Broth\n1 cup Water\n1 Lemon (sliced)\n1/2 cup Green Olives (optional)\n1/4 cup Fresh Cilantro (chopped, for garnish)\n1/4 cup Fresh Parsley (chopped, for garnish)\n");
        int imageResId = context.getResources().getIdentifier("tagine", "drawable", context.getPackageName());
        byte[] imageBytes = convertImageResourceToBytes(imageResId);
        values.put(COLUMN_IMAGE, imageBytes);
        long id = db.insert(TABLE_RECIPE, null, values);

        ContentValues values1 = new ContentValues();
        values1.put(COLUMN_NAME, "Moroccan Couscous");
        values1.put(COLUMN_TYPE, "Dinner");
        values1.put(COLUMN_DIFFICULTY,"Intermediate" );
        values1.put(COLUMN_DESCRIPTION, "A versatile grain dish popular in many cuisines. Quick-cooking and light, it serves as a neutral base for various flavors and can be paired with diverse ingredients.");
        values1.put(COLUMN_INGREDIENTS, "1 cup Couscous\n1.5 cups Water or Vegetable Broth\n1 tablespoon Olive Oil\n1/2 teaspoon Salt (or to taste)\n1/4 teaspoon Black Pepper (or to taste)\n1/4 cup Raisins\n1/4 cup Chopped Almonds (optional)\n1/4 cup Fresh Parsley (chopped)\n1 Lemon (for zest and juice)\n");
        int imageResId1 = context.getResources().getIdentifier("couscous", "drawable", context.getPackageName());
        byte[] imageBytes1 = convertImageResourceToBytes(imageResId1);
        values1.put(COLUMN_IMAGE, imageBytes1);
        long id2 = db.insert(TABLE_RECIPE, null, values1);

        ContentValues values2 = new ContentValues();
        values2.put(COLUMN_NAME, "Moroccan Djaj Mhmer");
        values2.put(COLUMN_TYPE, "Lunch");
        values2.put(COLUMN_DIFFICULTY,"Intermediate" );
        values2.put(COLUMN_DESCRIPTION, "Versatile and mild-flavored meat enjoyed globally. Prepared through roasting, grilling, or frying, it serves as a versatile protein base adaptable to various seasonings and cuisines.");
        values2.put(COLUMN_INGREDIENTS, "1 Whole Chicken (about 3-4 lbs)\n2 tablespoons Olive Oil\n2 teaspoons Salt\n1 teaspoon Black Pepper\n1 teaspoon Paprika\n1 teaspoon Garlic Powder\n1 teaspoon Onion Powder\n1 teaspoon Dried Thyme\n1 teaspoon Dried Rosemary\n1 teaspoon Dried Oregano\n1 Lemon (sliced)\n4 Garlic Cloves (whole)\nFresh Herbs (optional, e.g., parsley, thyme, rosemary)\n");
        int imageResId2 = context.getResources().getIdentifier("djaj", "drawable", context.getPackageName());
        byte[] imageBytes2 = convertImageResourceToBytes(imageResId2);
        values2.put(COLUMN_IMAGE, imageBytes2);
        db.insert(TABLE_RECIPE, null, values2);

        ContentValues values3 = new ContentValues();
        values3.put(COLUMN_NAME, "Moroccan Harira");
        values3.put(COLUMN_TYPE, "Lunch");
        values3.put(COLUMN_DIFFICULTY,"Intermediate" );
        values3.put(COLUMN_DESCRIPTION, "Harira is a traditional Moroccan soup known for its hearty and flavorful qualities. It often includes ingredients like lentils, chickpeas, tomatoes, and various spices such as ginger, cinnamon, and turmeric. Harira is frequently enjoyed during Ramadan and other special occasions. The soup reflects a blend of diverse flavors, making it a staple in Moroccan cuisine.");
        values3.put(COLUMN_INGREDIENTS, "1 cup Dried Chickpeas (soaked overnight)\n1/2 cup Lentils\n1/2 cup Rice\n2 tablespoons Olive Oil\n1 Onion (finely chopped)\n2 stalks Celery (chopped)\n1 Carrot (chopped)\n3 cloves Garlic (minced)\n1 can (14 oz) Tomatoes (diced)\n1/4 cup Tomato Paste\n1 teaspoon Ground Ginger\n1 teaspoon Ground Cinnamon\n1 teaspoon Ground Turmeric\n1 teaspoon Ground Paprika\n1/2 teaspoon Ground Cumin\n1/4 teaspoon Cayenne Pepper (optional, for heat)\nSalt (to taste)\nBlack Pepper (to taste)\n8 cups Vegetable Broth\n1/2 cup Fresh Cilantro (chopped, for garnish)\n1/4 cup Fresh Parsley (chopped, for garnish)\n1 Lemon (cut into wedges)\n");
        int imageResId3 = context.getResources().getIdentifier("harira", "drawable", context.getPackageName());
        byte[] imageBytes3 = convertImageResourceToBytes(imageResId3);
        values3.put(COLUMN_IMAGE, imageBytes3);
        db.insert(TABLE_RECIPE, null, values3);

        ContentValues values4 = new ContentValues();
        values4.put(COLUMN_NAME, "Burger");
        values4.put(COLUMN_TYPE, "Lunch");
        values4.put(COLUMN_DIFFICULTY,"Intermediate" );
        values4.put(COLUMN_DESCRIPTION, "A burger is a sandwich typically made with a ground meat patty, usually beef, placed between two slices of bread or in a bun. It offers versatility with various toppings and condiments and is commonly grilled or fried. Burgers are a popular and widely enjoyed food item globally.");
        values4.put(COLUMN_INGREDIENTS, "1 Bun\n1 Meat Patty\n1 tablespoon Ketchup\n1 teaspoon Mustard\n1 tablespoon Mayonnaise\nA handful of Lettuce\n2 Tomato slices\nA few Onion slices\n2 Pickles\n1 slice American Cheese\n2 strips Bacon (optional)\n1/2 Avocado (sliced, optional)\n1/4 cup Mushrooms (sautéed, optional)\n1 Fried Egg (optional)\nSalt (to taste)\nPepper (to taste)\n");
        int imageResId4 = context.getResources().getIdentifier("burger", "drawable", context.getPackageName());
        byte[] imageBytes4 = convertImageResourceToBytes(imageResId4);
        values4.put(COLUMN_IMAGE, imageBytes4);
        db.insert(TABLE_RECIPE, null, values4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
        System.out.println("on Upgrad called");


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

    private byte[] convertImageResourceToBytes(int imageResId) {
     /*   Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageResId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        //bitmap.recycle();
        return byteArray;*/

        try (InputStream inputStream = context.getResources().openRawResource(imageResId)) {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            return bytes;
            // Now 'bytes' contains the image data in byte format
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  new byte[]{};

    }





}

