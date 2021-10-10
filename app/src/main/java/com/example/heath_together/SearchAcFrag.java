package com.example.heath_together;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

//GroupScActivitiy
public class SearchAcFrag extends Fragment {

    private String result;
    private View view;
    private Button moveButton;

    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main3_2, container, false);

        moveButton = view.findViewById(R.id.changeGpButton);

        recyclerView = view.findViewById(R.id.account_list);//have to find another list


        moveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Main3 searchGpFrag= new Main3();
                transaction.replace(R.id.main_frame, searchGpFrag);
                transaction.commit();

            }
        });



        return view;
    }
}
