package com.app.activepartytime.activities.fragments;



import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.activepartytime.R;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class GamePlaygroundFragment extends Fragment {

    private CountDownTimer timer;
    private TextView timerDisplay;
    private boolean timerIsRunning;

    private Button startStopButton;

    private static final long MAX_TIME_IN_MS = 2 * 60 * 1000;
    private static final long COUNTDOWN_INTERVAL_IN_MS = 1000;

    public GamePlaygroundFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_game_playground, container, false);

        RelativeLayout layoutPlayground = (RelativeLayout) v.findViewById(R.id.containerPlayground);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(140, 200);

        int imagesID = 1;
        for (int i = 1; i <= 10; i++) {
            ImageView image = new ImageView(getActivity());
            // image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            image.setImageResource(R.drawable.drawing);
            image.setId(imagesID);
            image.setMaxHeight(50);
            image.setMaxHeight(50);
            layoutParams = new RelativeLayout.LayoutParams(140, 200);
            layoutParams.setMargins(24,0,24,0);
            if(imagesID != 1){
                layoutParams.addRule(RelativeLayout.RIGHT_OF, imagesID -1);
            }

            imagesID++;
            image.setLayoutParams(layoutParams);

            layoutPlayground.addView(image);

        }

        for (int i = 1; i <= 10; i++) {
            ImageView image = new ImageView(getActivity());
          //image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            image.setImageResource(R.drawable.speaking);
            image.setId(imagesID);
            image.setMaxHeight(50);
            image.setMaxHeight(50);
            layoutParams = new RelativeLayout.LayoutParams(140, 200);
            layoutParams.setMargins(24,0,24,0);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, imagesID -1);
            imagesID++;
            image.setLayoutParams(layoutParams);
            layoutPlayground.addView(image);

        }
        for (int i = 1; i <= 10; i++) {
            ImageView image = new ImageView(getActivity());
            //image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            image.setImageResource(R.drawable.pantomime);
            image.setId(imagesID);
            image.setMaxHeight(50);
            image.setMaxHeight(50);
            layoutParams = new RelativeLayout.LayoutParams(140, 200);
            layoutParams.setMargins(24,0,24,0);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, imagesID -1);
            imagesID++;
            image.setLayoutParams(layoutParams);
            layoutPlayground.addView(image);

        }

        /*
        figure test
         */

            ImageView image = new ImageView(getActivity());
            // image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            image.setImageResource(R.drawable.figure_red);
            image.setId(imagesID);
            image.setMaxHeight(50);
            image.setMaxHeight(50);
            layoutParams = new RelativeLayout.LayoutParams(140, 200);
            layoutParams.setMargins(24,0,24,0);
            if(imagesID != 1){
                layoutParams.addRule(RelativeLayout.RIGHT_OF, 2);
            }

            image.setLayoutParams(layoutParams);

            layoutPlayground.addView(image);



        LinearLayout layoutTeamList = (LinearLayout) v.findViewById(R.id.containerInfoList);
        for (int i = 0; i < 6; i++) {
            LinearLayout row = new LinearLayout(getActivity());
            row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < 3; j++) {
                Button btnTag = new Button(getActivity());
                btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                btnTag.setText("Button " + (j + 1 + (i * 4 )));
                btnTag.setId(j + 1 + (i * 4));
                row.addView(btnTag);
            }

            layoutTeamList.addView(row);
        }



        return v;
    }


}
