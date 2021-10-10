package com.example.heath_together;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.heath_together.Adapter.SearchGAdapter;
import com.example.heath_together.Object.DTO.GroupListItem;
import com.example.heath_together.R;

import java.util.List;

public class GroupJoin extends Fragment {

    private String groupName,leaderName,groupTag;
    private TextView groupN, groupT,leaderN;

    public GroupJoin(String groupName,String leaderName,String groupTag){
        this.leaderName = leaderName;
        this.groupName = groupName;
        this.groupTag = groupTag;
    }



    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.group_join, container, false);

        groupN = view.findViewById(R.id.group_name);
        leaderN = view.findViewById(R.id.leader_name);
        groupT = view.findViewById(R.id.group_tag);

        groupN.setText(groupName);
        leaderN.setText(leaderName);
        groupT.setText(groupTag);


        return view;
    }

}
