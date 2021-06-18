package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


//import com.example.quizme.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultActivity extends AppCompatActivity {

//    ActivityResultBinding binding;
    ImageView imageView6;
    TextView textView;
    TextView textView8;
    TextView textView13;
    TextView score;
    TextView earnedCoins;
    Button restartBtn;
    Button shareBtn;
    int POINTS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_result);
        imageView6=findViewById(R.id.imageView6);
        textView=findViewById(R.id.textView);
        textView8=findViewById(R.id.textView8);
        textView13=findViewById(R.id.textView13);
        score=findViewById(R.id.score);
        earnedCoins=findViewById(R.id.earnedCoins);
        restartBtn=findViewById(R.id.restartBtn);
        shareBtn=findViewById(R.id.shareBtn);

        int correctAnswers = getIntent().getIntExtra("correct", 0);
        int totalQuestions = getIntent().getIntExtra("total", 0);

        long points = correctAnswers * POINTS;

        score.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        earnedCoins.setText(String.valueOf(points));

        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(points));

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, MainActivity.class));
                finishAffinity();
            }
        });


    }
}