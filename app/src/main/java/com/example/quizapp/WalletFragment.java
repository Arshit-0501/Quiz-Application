package com.example.quizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.quizme.databinding.FragmentWalletBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class WalletFragment extends Fragment {

    public WalletFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    FragmentWalletBinding binding;
    FirebaseFirestore database;
    View view6;
    View view7;
    TextView textView9;
    TextView textView15;
    TextView currentCoins;
    EditText emailBox;
    Button sendRequest;
    User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        binding = FragmentWalletBinding.inflate(inflater, container, false);
        View view =inflater.inflate(R.layout.fragment_wallet, container, false);

        view6= view.findViewById(R.id.view6);
        view7= view.findViewById(R.id.view7);
        textView9=view.findViewById(R.id.textView9);
        textView15=view.findViewById(R.id.textView15);
        currentCoins=view.findViewById(R.id.currentCoins);
        emailBox=view.findViewById(R.id.emailBox);
        sendRequest=view.findViewById(R.id.sendRequest);
        database = FirebaseFirestore.getInstance();

        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                currentCoins.setText(String.valueOf(user.getCoins()));

                //binding.currentCoins.setText(user.getCoins() + "");

            }
        });

        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getCoins() > 50000) {
                    String uid = FirebaseAuth.getInstance().getUid();
                    String payPal = emailBox.getText().toString();
                    WithdrawRequest request = new WithdrawRequest(uid, payPal, user.getName());
                    database
                            .collection("withdraws")
                            .document(uid)
                            .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Request sent successfully.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "You need more coins to get withdraw.", Toast.LENGTH_SHORT).show();
                }
            }
        });




        return view;
    }
}