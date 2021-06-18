package com.example.quizapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FirebaseFirestore database;
    CircleImageView profileImage;
    EditText emailBox;
    EditText nameBox;
    EditText passBox;
    Button updateBtn;
    User user;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_profile, container, false);

        database = FirebaseFirestore.getInstance();
        profileImage=view.findViewById(R.id.profileImage);
        emailBox=view.findViewById(R.id.emailBox);
        nameBox=view.findViewById(R.id.nameBox);
        passBox=view.findViewById(R.id.passBox);
        updateBtn=view.findViewById(R.id.updateBtn);

        database.collection("users").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);

                emailBox.setText(user.getEmail());
                nameBox.setText(user.getName());
                passBox.setText(user.getPass());

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= nameBox.getText().toString();
                String email= emailBox.getText().toString();
                String pass= passBox.getText().toString();
                User user1= new User(name,email,pass,user.getReferCode());
                database
                        .collection("users")
                        .document(FirebaseAuth.getInstance().getUid())
                        .set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Profile updated successfully.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}