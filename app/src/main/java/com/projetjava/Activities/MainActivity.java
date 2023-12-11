package com.projetjava.Activities;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.projetjava.Adapters.RecipeBaseAdapter;
import com.projetjava.DataBase.RecipeDatabaseHelper;
import com.projetjava.Models.Recipe;
import com.projetjava.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button addRecipeButton;
    private ListView listView;
    private RecipeDatabaseHelper recipeDatabaseHelper;
    private List<Recipe> listofRecipes;
    private  RecipeBaseAdapter recipeBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("On create Main Activity executed");
        setContentView(R.layout.activity_main);
        setTitle("Recipe Application");
        // c'est la classe qui nous aide a faire CRUD au DB
        recipeDatabaseHelper = new RecipeDatabaseHelper(getBaseContext());
        // just pour affecter a chaque objet la partie xml qui le convient
        loadViews();
        // lorsque on clique sur la button addrecipe il doit nous affiche la formulaire de l'ajout
        goToAddRecipeView();
        // il fait revenir les recipes qui existe dans la base de données et les affiché dans LitView a l'aide de RecipeBaseAdapter
        loadItemsIntoListView();





    }



    private void goToAddRecipeView(){
        addRecipeButton.setOnClickListener((v) -> {
            Intent i = new Intent(getBaseContext(), AddRecipe.class);
            startActivity(i);
        });
    }
    protected void loadViews(){
        addRecipeButton = findViewById(R.id.addRecipeButton);
        listView = findViewById(R.id.listofrecipes);
    }

    private void loadItemsIntoListView(){
        listofRecipes= recipeDatabaseHelper.getAllRecipes();
        recipeBaseAdapter = new RecipeBaseAdapter(getBaseContext(),listofRecipes);
        listView.setAdapter(recipeBaseAdapter);
    }
    // pourquoi cette méthode car lorsque on est dans une nouvelle activité par exemple de l'ajout lorsque on veut revenir on doit
    // appellez la DB pour avoir les nouveaux lignes ajoutés dans la DB.
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("On start executed");
       loadItemsIntoListView();
    }
}