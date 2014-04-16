package com.app.activepartytime.activities.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.activepartytime.R;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class GameInfoFragment extends Fragment {

    private Button generateButton;
    private LinearLayout taskInformation;

    public GameInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_game_info, container, false);
        generateButton = (Button)v.findViewById(R.id.generate);
        taskInformation = (LinearLayout)v.findViewById(R.id.taskInformation);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateButton.setVisibility(RelativeLayout.GONE);
                generateButton.setEnabled(false);
                taskInformation.setVisibility(RelativeLayout.VISIBLE);
            }
        });

        return inflater.inflate(R.layout.fragment_game_info, container, false);
    }


}
