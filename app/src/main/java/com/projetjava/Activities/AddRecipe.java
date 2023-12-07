package com.projetjava.Activities;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.projetjava.DataBase.RecipeDatabaseHelper;
import com.projetjava.R;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

// c'est la classe qui permet de faire la logique d'ajout de la recipe au Data base
public class AddRecipe extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTextName, editTextDescription, editTextIngredients;
    private ImageView imageView;
    private Button btnChooseImage, btnSubmit;

    // ici j'ai créer ce variable juste pour préserver son état car avant d'envoyer la formualaire on doit afficher l'image et
    // par la suite on click sur envoyer pour ajouter cette recipe à la base de données.
    private byte[] imageBytes;
    private RecipeDatabaseHelper recipeDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        setTitle("Add Recipe");

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
            && imageInBytes!=null
            ){
                System.out.println("tous les champs sont correctes ");
              long result = recipeDatabaseHelper.addRecipe(nameRecipe,descriptionRecipe,ingredientsRecipe,imageBytes);
              if(result!=-1){
                  System.out.println("bien ajoute au db");
                  Toast.makeText(getBaseContext(),"Success : Recipe est bien ajouté",Toast.LENGTH_LONG).show();
                  // on fait revenir à l'activité MAin
                  Intent i = new Intent(getBaseContext(),MainActivity.class);
                  startActivity(i);
              }else{
                  System.out.println("n'a pas bien ajoute au db");
                  Toast.makeText(getBaseContext(),"Failure : Recipe n'est pas ajouté",Toast.LENGTH_LONG).show();
              }

            }else {
                System.out.println("les champs sont incorrect");
                Toast.makeText(getBaseContext(),"Veuillez vérifiez que vous avez remplis tous les champs",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(this, "Error: Image doit étre inférieur à 1MB", Toast.LENGTH_SHORT).show();
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
