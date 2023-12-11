package com.projetjava.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity {

    EditText txt_login, txt_password;
    ProgressBar loginProgressBar;
    private RecipeDatabaseHelper recipeDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        txt_login = findViewById(R.id.txt_login);
        txt_password = findViewById(R.id.txt_password);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        recipeDatabaseHelper = new RecipeDatabaseHelper(this);

        Intent intent = getIntent();
        intent.getExtras();

//        if (recipeDatabaseHelper.isUserLoggedIn()) {
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            finish();
//        }

        if(intent.hasExtra("warning"))
        {
            Snackbar. make(findViewById(R.id.txt_login),intent.getStringExtra("warning"),BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }

    public void btnLogin(final View view) {
        String email = txt_login.getText().toString().trim();
        String password = txt_password.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            txt_login.setError("Email is Required.");
            return;
        }

        if (TextUtils.isEmpty(password)){
            txt_password.setError("Password is Required.");
            return;
        }

        if (password.length() < 6){
            txt_password.setError("Password Must be >= 6 Characters");
            return;
        }

        loginProgressBar.setVisibility(View.VISIBLE);

        // Utilise RecipeDatabaseHelper pour authentifier l'utilisateur
        User user = recipeDatabaseHelper.getUserByEmailAndPassword(email, password);

        if (user != null) {
            // Authentification réussie
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else {
            // Authentification échouée
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            loginProgressBar.setVisibility(View.INVISIBLE);
        }
    }
//        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    finish();
//                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                }
//                else {
//                    Snackbar.make(view,"Error ! " + task.getException().getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
//                    loginProgressBar.setVisibility(View.INVISIBLE);
//                }
//            }
//        });


    public void btnRegister(View view) {
        startActivity(new Intent(this,RegisterActivity.class));
    }

    public void btnForgotPassword(final View view) {
        final EditText resetMail = new EditText(view.getContext());
        final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
        passwordResetDialog.setTitle("Reset Password");
        passwordResetDialog.setMessage("Enter Your Email to receive reset link.");
        passwordResetDialog.setView(resetMail);

        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String email = resetMail.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    resetMail.setError("Email is Required.");
                    return;
                }

                // Vérifier si l'e-mail existe dans la base de données
                if (recipeDatabaseHelper.isEmailExists(email)) {
                    // Envoyer le lien de réinitialisation si l'e-mail existe
                    // Ajoutez votre logique pour envoyer le lien de réinitialisation ici
                    Snackbar.make(view, "Reset Link Sent to your Email", BaseTransientBottomBar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(view, "Email does not exist", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        });

        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Ne rien faire ici si l'utilisateur clique sur "No"
            }
        });

        passwordResetDialog.create().show();
    }


    public void btnGuestLogin(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}