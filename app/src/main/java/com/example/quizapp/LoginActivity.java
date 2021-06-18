package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

//    ActivityLoginBinding binding;
    //variables..
    FirebaseAuth auth;
    ProgressDialog dialog;
    ImageView logo;
    TextView text2;
    TextView text3;
    EditText emailBox;
    EditText password;
    Button submitBtn;
    Button createNewBtn;
    View view;
    CardView cardView;
    FirebaseFirestore database;
    User user;
    String pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        logo = findViewById(R.id.imageView2);
        text2 = findViewById(R.id.textView2);
        text3 = findViewById(R.id.textView3);
        emailBox = findViewById(R.id.emailBox);
        password = findViewById((R.id.passwordBox));
        submitBtn = findViewById(R.id.submitBtn);
        createNewBtn = findViewById(R.id.createNewBtn);
        view = findViewById(R.id.view);
        cardView = findViewById(R.id.cardView);
        database = FirebaseFirestore.getInstance();


        dialog = new ProgressDialog(this);
        dialog.setMessage("Logging in...");

        if(auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }


//        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

//        database.collection("users").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                user = documentSnapshot.toObject(User.class);
//                pass=user.getPass();
//            }
//        });

//        text3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                alertDialogBuilder.setMessage("Your password is "+pass);
//                alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                });
//
//                AlertDialog alertDialog = alertDialogBuilder.create();
//                alertDialogBuilder.show();
//            }
//        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass;
                email = emailBox.getText().toString();
                pass = password.getText().toString();


                dialog.show();

                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if(task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        createNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });


    }
}
