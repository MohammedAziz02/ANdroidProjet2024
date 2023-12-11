package com.projetjava.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.projetjava.Activities.MainActivity;
import com.projetjava.Activities.ModifyRecipe;
import com.projetjava.DataBase.RecipeDatabaseHelper;
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

    RecipeDatabaseHelper recipeDatabaseHelper;


    public RecipeAdapter(Context context, String recipeName,String recipeType,String recipeDifficulty, String ingredients, String description, byte[] imageBytes) {
        this.context = context;
        this.recipeName = recipeName;
        this.recipeType=recipeType;
        this.recipeDifficulty=recipeDifficulty;
        this.ingredients = ingredients;
        this.description = description;
        this.imageBytes = imageBytes;
        recipeDatabaseHelper=new RecipeDatabaseHelper(context);
    }


    // on fait donner les data qu'on va recevoir depuis l'intent a l'xml activity_recipe
    public void bindData() {
        TextView textViewRecipeName = ((Activity) context).findViewById(R.id.textViewRecipeName);
        TextView textViewRecipeType = ((Activity) context).findViewById(R.id.textViewType);
        TextView textViewRecipeDifficulty = ((Activity) context).findViewById(R.id.textViewDifficulty);
        TextView textViewIngredients = ((Activity) context).findViewById(R.id.textViewIngredients);
        TextView textViewDescription = ((Activity) context).findViewById(R.id.textViewDescription);
        ImageView imageViewRecipe = ((Activity) context).findViewById(R.id.imageViewRecipe);
        Button deleteButton=((Activity) context).findViewById(R.id.deleteButton);
        Button modifyButton=((Activity) context).findViewById(R.id.modifyButton);
        textViewRecipeName.setText(recipeName);
        textViewRecipeType.setText(recipeType);
        textViewRecipeDifficulty.setText(recipeDifficulty);
        textViewIngredients.setText(ingredients);
        textViewDescription.setText(description);
        if(imageBytes!=null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageViewRecipe.setImageBitmap(bitmap);
        }
        deleteButton.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            // Set the title and message for the dialog
            builder.setTitle("Confirm")
                    .setMessage("are you sure that you want to delete this recipe?");
            // Add a positive button and its click listener
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    long recipeId=-2;
                    recipeId=recipeDatabaseHelper.getItemIdByName(recipeName);
                    if(recipeId!=-1) {
                        recipeDatabaseHelper.deleteRecipe(recipeId);
                        Intent intent1 = new Intent(context, MainActivity.class);
                        context.startActivity(intent1);
                        Toast.makeText(context, "Recipe deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(context, "recipeId is null", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss(); // Close the dialog
                }
            });

            // Add a negative button and its click listener
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Do something when the negative button is clicked
                    dialogInterface.dismiss(); // Close the dialog
                }
            });
            // Create and show the AlertDialog
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        });
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long recipeId=-2;
                recipeId=recipeDatabaseHelper.getItemIdByName(recipeName);
               // Toast.makeText(context,"inside button click Recipe id is " + recipeId,Toast.LENGTH_LONG).show();
                if(recipeId!=-1) {
                    Intent intent1 = new Intent(context, ModifyRecipe.class);
                    intent1.putExtra("RecipeId",recipeId);
                    context.startActivity(intent1);
                }
                else Toast.makeText(context, "recipeId is null", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
