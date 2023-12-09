package com.projetjava.Activities;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.projetjava.DataBase.RecipeDatabaseHelper;
import com.projetjava.R;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// c'est la classe qui permet de faire la logique d'ajout de la recipe au Data base
public class AddRecipe extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTextName, editTextDescription, editTextIngredients;
    private Spinner type,difficulty;
    private ImageView imageView;
    private Button btnChooseImage, btnSubmit;

    String typeChoose,difficultyChoose;

    // ici j'ai créer ce variable juste pour préserver son état car avant d'envoyer la formualaire on doit afficher l'image et
    // par la suite on click sur envoyer pour ajouter cette recipe à la base de données.
    private byte[] imageBytes;
    private RecipeDatabaseHelper recipeDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        setTitle("Add Recipe");
        type= findViewById(R.id.type);
        difficulty= findViewById(R.id.difficulty);

        List<String> types= new ArrayList<>(Arrays.asList("Breakfast","Lunch","Dinner","Snack"));
        List<String> difficulties= new ArrayList<>(Arrays.asList("Beginner","Intermediate","Advanced"));

        ArrayAdapter<String> typeAdapter= new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,types);
        ArrayAdapter<String> difficultyAdapter= new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,difficulties);

        type.setAdapter(typeAdapter);
        difficulty.setAdapter(difficultyAdapter);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeChoose=types.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        difficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                difficultyChoose=difficulties.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recipeDatabaseHelper = new RecipeDatabaseHelper(getBaseContext());
        loadViews();
        // intent implicite pour que on peut choisir une image
        btnChooseImage.setOnClickListener((View v)-> {
                chooseImage();
            });

        btnSubmit.setOnClickListener((v)->{
            String nameRecipe= editTextName.getText().toString();
            String descriptionRecipe = editTextDescription.getText().toString();
            String ingredientsRecipe= editTextIngredients.getText().toString();
            byte[] imageInBytes=imageBytes;
            System.out.println("btn submit clicked ");
            if(nameRecipe!=null && !nameRecipe.equals("") && descriptionRecipe!=null && !descriptionRecipe.equals("") && ingredientsRecipe!=null && !ingredientsRecipe.equals("")
            && imageInBytes!=null &&typeChoose!=null && difficultyChoose!=null
            ){
                System.out.println("tous les champs sont correctes ");
                System.out.println(typeChoose+ "  :  "+difficultyChoose);
              long result = recipeDatabaseHelper.addRecipe(nameRecipe,typeChoose.toString(),difficultyChoose.toString(),descriptionRecipe,ingredientsRecipe,imageBytes);
              if(result!=-1){
                  System.out.println("bien ajoute au db");
                  Toast.makeText(getBaseContext(),"Success : Recipe is added successfully!",Toast.LENGTH_LONG).show();
                  // on fait revenir à l'activité MAin
                  Intent i = new Intent(getBaseContext(),MainActivity.class);
                  startActivity(i);
              }else{
                  System.out.println("n'a pas bien ajoute au db");
                  Toast.makeText(getBaseContext(),"Failure : Recipe not added!",Toast.LENGTH_LONG).show();
              }

            }else {
                System.out.println("les champs sont incorrect");
                Toast.makeText(getBaseContext(),"Please fill all fields!",Toast.LENGTH_LONG).show();
            }

        });


    }

    // choisir une image
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
                int imageSizeInKB = imageSizeInBytes / 1024; // Convert to KB

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


    //// juste pour avoir les ids des view correspondent correctement au objets déclarés.
    private void loadViews() {
        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextIngredients = findViewById(R.id.editTextIngredients);
        imageView = findViewById(R.id.imageView);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnSubmit = findViewById(R.id.btnSubmit);
    }
}
