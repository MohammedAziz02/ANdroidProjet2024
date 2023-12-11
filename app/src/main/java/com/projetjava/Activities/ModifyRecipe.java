package com.projetjava.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.projetjava.DataBase.RecipeDatabaseHelper;
import com.projetjava.Models.Recipe;
import com.projetjava.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModifyRecipe extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTextName, editTextDescription, editTextIngredients;
    private Spinner type,difficulty;
    private ImageView imageView;
    private Button btnChooseImage, btnSubmit;
    private RecipeDatabaseHelper recipeDatabaseHelper;

    String typeChoose,difficultyChoose;

    private byte[] imageBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_recipe);
        setTitle("Modify Recipe");
        recipeDatabaseHelper = new RecipeDatabaseHelper(getBaseContext());
        loadViews();
        Intent intent = getIntent();
        long recipeId=intent.getLongExtra("RecipeId",-1);
       // Toast.makeText(getBaseContext(),"THis is Id "+ recipeId,Toast.LENGTH_SHORT).show();
        Recipe r = recipeDatabaseHelper.getRecipe(recipeId);
        //Toast.makeText(getBaseContext(),"THis is " + r,Toast.LENGTH_SHORT).show();
        editTextName.setText(r.getName_recipe());
        editTextDescription.setText(r.getDescription_recipe());
        editTextIngredients.setText(r.getIngredients_recipe());
        List<String> types= new ArrayList<>(Arrays.asList("Breakfast","Lunch","Dinner","Snack"));
        List<String> difficulties= new ArrayList<>(Arrays.asList("Beginner","Intermediate","Advanced"));
        ArrayAdapter<String> typeAdapter= new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,types);
        ArrayAdapter<String> difficultyAdapter= new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,difficulties);
        type.setAdapter(typeAdapter);
        difficulty.setAdapter(difficultyAdapter);
        String selectedType = r.getType();
        int typePosition = typeAdapter.getPosition(selectedType);
        type.setSelection(typePosition);
        String selectedDifficulty = r.getDifficulty();
        int difficultyPosition = difficultyAdapter.getPosition(selectedDifficulty);
        difficulty.setSelection(difficultyPosition);
        if(r.getImageBytes()!=null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(r.getImageBytes(), 0, r.getImageBytes().length);
            imageView.setImageBitmap(bitmap);
            imageBytes=convertImageToByteArray(bitmap);
        }
        btnChooseImage.setOnClickListener((View v)-> {
            chooseImage();
        });

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeChoose=types.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typeChoose=r.getType();
            }
        });

        difficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                difficultyChoose=difficulties.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                difficultyChoose=r.getDifficulty();
            }
        });


        btnSubmit.setOnClickListener((v)->{
            String nameRecipe= editTextName.getText().toString();
            String descriptionRecipe = editTextDescription.getText().toString();
            String ingredientsRecipe= editTextIngredients.getText().toString();
            byte[] imageInBytes=imageBytes;
            System.out.println("btn submit clicked  " + nameRecipe + " "+ descriptionRecipe + " "+ ingredientsRecipe + " "+ " ");
            if(nameRecipe!=null && !nameRecipe.equals("") && descriptionRecipe!=null && !descriptionRecipe.equals("") && ingredientsRecipe!=null && !ingredientsRecipe.equals("")
            ){
                System.out.println("tous les champs sont correctes ");
                System.out.println(typeChoose.toString()+ "  :  "+difficultyChoose.toString());
                //long result = recipeDatabaseHelper.addRecipe(nameRecipe,typeChoose.toString(),difficultyChoose.toString(),descriptionRecipe,ingredientsRecipe,imageBytes);
                long result = recipeDatabaseHelper.updateRecipe(recipeId,nameRecipe,typeChoose.toString(),difficultyChoose.toString(),descriptionRecipe,ingredientsRecipe,imageInBytes);
                if(result!=-1){
                    Toast.makeText(getBaseContext(),"Success : Recipe is successfully Modified!",Toast.LENGTH_LONG).show();
                    // on fait revenir à l'activité MAin
                    Intent i = new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);
                }else{
                    System.out.println("n'a pas bien Modifié");
                    Toast.makeText(getBaseContext(),"Failure : Recipe not Modified!",Toast.LENGTH_LONG).show();
                }

            }else {
                System.out.println("les champs sont incorrect");
                Toast.makeText(getBaseContext(),"Please fill all fields!",Toast.LENGTH_LONG).show();
            }

        });
    }

    private void loadViews() {
        editTextName = findViewById(R.id.editTextNameModify);
        editTextDescription = findViewById(R.id.editTextDescriptionModify);
        editTextIngredients = findViewById(R.id.editTextIngredientsModify);
        imageView = findViewById(R.id.imageViewModify);
        btnChooseImage = findViewById(R.id.btnChooseImageModify);
        btnSubmit = findViewById(R.id.btnSubmitModify);
        type= findViewById(R.id.typeModify);
        difficulty= findViewById(R.id.difficultyModify);
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // si on a bien choisi l'image et après on affiche cette image dans un Image View
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                //  assurer que la taille d'image ne dépasse pas 1MB pour éviter les problèmes
                int imageSizeInBytes = inputStream.available();
                int imageSizeInKB = imageSizeInBytes / 1024; // Convertir en KB

                if (imageSizeInKB > 1024) { // Check if the image size is greater than 1MB
                    Toast.makeText(this, "Error: Image's size must be less than 1MB", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                imageBytes = convertImageToByteArray(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // cette méthode nous aide a convertir l'image choisi en byte pour que on peut le stocker dans le DB
    private byte[] convertImageToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

}