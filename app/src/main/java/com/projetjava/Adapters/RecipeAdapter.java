package com.projetjava.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

import com.projetjava.R;


// c'est une simple classe qui permet d'afficher le recipe image/titre/description/ingrédients
public class RecipeAdapter {
    private final Context context;
    private final String recipeName;
    private final String recipeType;
    private final String recipeDifficulty;
    private final String ingredients;
    private final String description;
    private final byte[] imageBytes;

    public RecipeAdapter(Context context, String recipeName,String recipeType,String recipeDifficulty, String ingredients, String description, byte[] imageBytes) {
        this.context = context;
        this.recipeName = recipeName;
        this.recipeType=recipeType;
        this.recipeDifficulty=recipeDifficulty;
        this.ingredients = ingredients;
        this.description = description;
        this.imageBytes = imageBytes;
    }


    // on fait donner les data qu'on va recevoir depuis l'intent a l'xml activity_recipe
    public void bindData() {
        TextView textViewRecipeName = ((Activity) context).findViewById(R.id.textViewRecipeName);
        TextView textViewRecipeType = ((Activity) context).findViewById(R.id.textViewType);
        TextView textViewRecipeDifficulty = ((Activity) context).findViewById(R.id.textViewDifficulty);
        TextView textViewIngredients = ((Activity) context).findViewById(R.id.textViewIngredients);
        TextView textViewDescription = ((Activity) context).findViewById(R.id.textViewDescription);
        ImageView imageViewRecipe = ((Activity) context).findViewById(R.id.imageViewRecipe);
        textViewRecipeName.setText(recipeName);
        textViewRecipeType.setText(recipeType);
        textViewRecipeDifficulty.setText(recipeDifficulty);
        textViewIngredients.setText(ingredients);
        textViewDescription.setText(description);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imageViewRecipe.setImageBitmap(bitmap);
    }
}