package com.projetjava.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.projetjava.DataBase.RecipeDatabaseHelper;
import com.projetjava.R;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    EditText mName, mPhone , mEmail, mPassword;
    TextView mLogin;
    ProgressBar mProgressBar;
    RecipeDatabaseHelper recipeDatabaseHelper;
    String TAG = "TAG";
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mName = findViewById(R.id.txt_name);
        mPhone = findViewById(R.id.txt_phone);
        mEmail = findViewById(R.id.txt_email);
        mPassword = findViewById(R.id.txt_password);
        mLogin = findViewById(R.id.txt_login);
        mProgressBar = findViewById(R.id.registerProgressBar);
        recipeDatabaseHelper = new RecipeDatabaseHelper(this);
        if (recipeDatabaseHelper.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

    }
    public void register(final View view) {
        final String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        final String fullName = mName.getText().toString().trim();
        final String phone = mPhone.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            mEmail.setError("Email is Required.");
            return;
        }
        if (TextUtils.isEmpty(password)){
            mPassword.setError("Password is Required.");
            return;
        }
        if (password.length() < 6){
            mPassword.setError("Password Must be >= 6 Characters");
            return;
        }
        mProgressBar.setVisibility(View.VISIBLE);
        // Ajoutez le code pour enregistrer l'utilisateur à l'aide de RecipeDatabaseHelper
        long userId = recipeDatabaseHelper.addUser(fullName, email,phone, password);
        if (userId != -1) {
            // L'utilisateur a été ajouté avec succès dans la base de données locale
            Snackbar.make(view,"User Created", BaseTransientBottomBar.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        } else {
            // Une erreur s'est produite lors de l'ajout de l'utilisateur
            Snackbar.make(view,"Error creating user", BaseTransientBottomBar.LENGTH_LONG).show();
        }

        mProgressBar.setVisibility(View.INVISIBLE);
    }
    public void login(View view) {
        startActivity(new Intent(this,LoginActivity.class));
    }
}
