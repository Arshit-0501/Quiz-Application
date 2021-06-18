package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.quizapp.databinding.FragmentHomeBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

//    FragmentHomeBinding binding;
    private View view1;
    private View view2;
    private ImageView logo;
    private TextView text4;
    private TextView text5;
    private TextView text6;
    private TextView text7;
    private TextView text10;
    private TextView text12;
    private TextView spinwheel;
    private RecyclerView recyclerView;

    FirebaseFirestore database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_home, container, false);

        database = FirebaseFirestore.getInstance();

        view1=view.findViewById(R.id.view2);
        view2=view.findViewById(R.id.view3);
        logo=view.findViewById(R.id.imageView3);
        text4=view.findViewById(R.id.textView4);
        text5=view.findViewById(R.id.textView5);
        text6=view.findViewById(R.id.textView6);
        text7=view.findViewById(R.id.textView7);
        text10=view.findViewById(R.id.textView10);
        text12=view.findViewById(R.id.textView12);
        spinwheel=view.findViewById(R.id.spinwheel);

        recyclerView=view.findViewById(R.id.categoryList);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        final ArrayList<CategoryModel> categories = new ArrayList<>();
        final CategoryAdapter adapter = new CategoryAdapter(getContext(), categories);
        recyclerView.setAdapter(adapter);


        database.collection("categories")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        categories.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            CategoryModel model = snapshot.toObject(CategoryModel.class);
                            model.setCategoryId(snapshot.getId());
                            categories.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });


//        binding.categoryList.setLayoutManager(new GridLayoutManager(getContext(),2));
//        binding.categoryList.setAdapter(adapter);

//        spinwheel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), SpinnerActivity.class));
//            }
//        });


        // Inflate the layout for this fragment
        return view;
    }
}