package com.projetjava.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.projetjava.Adapters.RecipeAdapter;
import com.projetjava.R;
public class RecipeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        setTitle("View Recipe");
        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("RecipeName");
        String recipeType = intent.getStringExtra("RecipeType");
        String recipeDifficulty = intent.getStringExtra("RecipeDifficulty");
        String ingredients = intent.getStringExtra("RecipeIngredients");
        String description = intent.getStringExtra("RecipeMethodTitle");
        byte[] imageBytes = intent.getByteArrayExtra("ImageBytes");
        RecipeAdapter recipeAdapter = new RecipeAdapter(this, recipeName,recipeType,recipeDifficulty, ingredients, description, imageBytes);
        recipeAdapter.bindData();
    }
}