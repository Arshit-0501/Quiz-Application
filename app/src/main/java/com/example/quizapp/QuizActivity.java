package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.quizme.databinding.ActivityQuizBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

//    ActivityQuizBinding binding;
    View view4;
    View view5;
    TextView timer1;
    TextView questionCounter;
//    TextView que;
    ImageView imageView4;
    ImageView imageView5;
    TextView ques;
    TextView option1;
    TextView option2;
    TextView option3;
    TextView option4;
    Button nextBtn;
    Button quizBtn;


    ArrayList<Question> questions;
    int index = 0;
    Question question;
    CountDownTimer timer;
    FirebaseFirestore database;
    int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_quiz);

        view4=findViewById(R.id.view4);
        view5=findViewById(R.id.view5);
        timer1=findViewById(R.id.timer);
        questionCounter=findViewById(R.id.questionCounter);
        ques=findViewById(R.id.question);
        imageView4=findViewById(R.id.imageView4);
        imageView5=findViewById(R.id.imageView5);
        option1=findViewById(R.id.option_1);
        option2 =findViewById(R.id.option_2);
        option3=findViewById(R.id.option_3);
        option4=findViewById(R.id.option_4);
        nextBtn=findViewById(R.id.nextBtn);
        quizBtn=findViewById(R.id.quizBtn);


        questions = new ArrayList<>();
        database = FirebaseFirestore.getInstance();

        final String catId = getIntent().getStringExtra("catId");

        Random random = new Random();
        final int rand = random.nextInt(12);

        database.collection("categories")
                .document(catId)
                .collection("questions")
                .whereGreaterThanOrEqualTo("index", rand)
                .orderBy("index")
                .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.getDocuments().size() < 5) {
                    database.collection("categories")
                            .document(catId)
                            .collection("questions")
                            .whereLessThanOrEqualTo("index", rand)
                            .orderBy("index")
                            .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                    Question question = snapshot.toObject(Question.class);
                                    questions.add(question);
                                }
                            setNextQuestion();
                        }
                    });
                } else {
                    for(DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        Question question = snapshot.toObject(Question.class);
                        questions.add(question);
                    }
                    setNextQuestion();
                }
            }
        });



        resetTimer();

    }

    void resetTimer() {
        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer1.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

            }
        };
    }

    void showAnswer() {
        if(question.getAnswer().equals(option1.getText().toString()))
            option1.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if(question.getAnswer().equals(option2.getText().toString()))
            option2.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if(question.getAnswer().equals(option3.getText().toString()))
            option3.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if(question.getAnswer().equals(option4.getText().toString()))
            option4.setBackground(getResources().getDrawable(R.drawable.option_right));
    }

    void setNextQuestion() {
        if(timer != null)
            timer.cancel();

        timer.start();
        if(index < questions.size()) {
            questionCounter.setText(String.format("%d/%d", (index+1), questions.size()));
            question = questions.get(index);
            ques.setText(question.getQuestion());
            option1.setText(question.getOption1());
            option2.setText(question.getOption2());
            option3.setText(question.getOption3());
            option4.setText(question.getOption4());
        }
    }

    void checkAnswer(TextView textView) {
        String selectedAnswer = textView.getText().toString();
        if(selectedAnswer.equals(question.getAnswer())) {
            correctAnswers++;
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));
        } else {
            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
        }
    }

    void reset() {
        option1.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        option2.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        option3.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        option4.setBackground(getResources().getDrawable(R.drawable.option_unselected));
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.option_1:
            case R.id.option_2:
            case R.id.option_3:
            case R.id.option_4:
                if(timer!=null)
                    timer.cancel();
                TextView selected = (TextView) view;
                checkAnswer(selected);

                break;
            case R.id.nextBtn:
                reset();
                if(index <questions.size()-1) {
                    index++;
                    setNextQuestion();
                } else {
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("correct", correctAnswers);
                    intent.putExtra("total", questions.size());
                    startActivity(intent);
                    //Toast.makeText(this, "Quiz Finished.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.quizBtn:
                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(intent);
                break;

        }
    }

}