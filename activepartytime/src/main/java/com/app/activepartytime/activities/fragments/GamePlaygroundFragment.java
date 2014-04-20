package com.app.activepartytime.activities.fragments;



import android.graphics.Color;
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
import com.app.activepartytime.core.game.Playground;
import com.app.activepartytime.core.game.Team;
import com.app.activepartytime.core.game.tasks.TaskType;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class GamePlaygroundFragment extends Fragment {





    private Playground playground;
    private Team[] teams;
    private View v;

    public GamePlaygroundFragment(Playground p, Team[] t) {
        // Required empty public constructor
        playground = p;
        teams = t;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_game_playground, container, false);

        RelativeLayout layoutPlayground = (RelativeLayout) v.findViewById(R.id.containerPlayground);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(140, 200);


        createPlayground();
        createTeamList();
        /*
        figure test
         */

            ImageView image = new ImageView(getActivity());
            // image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            image.setImageResource(R.drawable.figure_red);
            image.setId(50);
            image.setMaxHeight(50);
            layoutParams = new RelativeLayout.LayoutParams(140, 200);
            layoutParams.setMargins(24,0,24,0);
        layoutParams.addRule(RelativeLayout.RIGHT_OF, 3);
            image.setLayoutParams(layoutParams);

            layoutPlayground.addView(image);







        return v;
    }


    private void createPlayground(){

        RelativeLayout layoutPlayground = (RelativeLayout) v.findViewById(R.id.containerPlayground);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(140, 200);

        int imagesID = 1;

        for (int i = 0; i < playground.getPlaygroundLength(); i++) {
            ImageView image = new ImageView(getActivity());
            // image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            TaskType type = playground.getTaskType(i);
            switch (type){
                case DRAWING: image.setImageResource(R.drawable.drawing);break;
                case SPEAKING: image.setImageResource(R.drawable.speaking);break;
                case PANTOMIME: image.setImageResource(R.drawable.pantomime);break;
            }
            image.setId(imagesID);

            layoutParams = new RelativeLayout.LayoutParams(140, 200);
            layoutParams.setMargins(24,0,24,0);
            if(imagesID != 1){
                layoutParams.addRule(RelativeLayout.RIGHT_OF, imagesID -1);
            }

            imagesID++;
            image.setLayoutParams(layoutParams);

            layoutPlayground.addView(image);

        }



    }


    private void createTeamList(){

        LinearLayout layoutTeamList = (LinearLayout) v.findViewById(R.id.containerInfoList);
        RelativeLayout.LayoutParams layoutParams;
        for (int i = 0; i < teams.length; i++) {
            RelativeLayout row = new RelativeLayout(getActivity());
            row.setId("row".hashCode() + i);
            row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            /*
            * figure image
             */
            ImageView image = new ImageView(getActivity());
            switch (i){
                case 0: image.setImageResource(R.drawable.figure_blue);break;
                case 1: image.setImageResource(R.drawable.figure_yellow);break;
                case 2: image.setImageResource(R.drawable.figure_red);break;
                case 3: image.setImageResource(R.drawable.figure_green);break;
            }
            image.setId("teamImage".hashCode()+i);
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.height = 80;
            layoutParams.width = 80;
            image.setLayoutParams(layoutParams);
            row.addView(image);


            /*
             * team name
             */
            TextView teamName = new TextView(getActivity());
            teamName.setText(teams[i].getName());
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.RIGHT_OF,"teamImage".hashCode()+i);
            teamName.setId("teamPosition".hashCode()+i);
            teamName.setLayoutParams(layoutParams);
            teamName.setTextColor(Color.WHITE);
            row.addView(teamName);

            /*
             * position
             */
            TextView teamPositionTextView = new TextView(getActivity());
            StringBuffer text = new StringBuffer();
            text.append("Position: ");
            int teamPosition = teams[i].getPlaygroundPosition();
            text.append(teamPosition);
            text.append('/');
            text.append(playground.getPlaygroundLength());
            text.append("  ");
            switch (playground.getTaskType(teamPosition)){
                case DRAWING: text.append("Drawing");break;
                case SPEAKING: text.append("Speaking");break;
                case PANTOMIME: text.append("Pantomime");break;
            }
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.RIGHT_OF,"teamImage".hashCode()+i);
            layoutParams.addRule(RelativeLayout.BELOW,"teamPosition".hashCode()+i);
            teamPositionTextView.setText(text.toString());
            teamPositionTextView.setLayoutParams(layoutParams);
            teamPositionTextView.setTextColor(Color.WHITE);
            row.addView(teamPositionTextView);


            layoutTeamList.addView(row);
        }
        setActiveTeam(0);
    }


    private void setActiveTeam(int team){
        RelativeLayout row = (RelativeLayout)v.findViewById("row".hashCode()+team);
        RelativeLayout.LayoutParams layoutParams;
        /*
            * button image
             */
        ImageView image = new ImageView(getActivity());
        image.setId(R.string.play_button_active_team);
        image.setImageResource(R.drawable.play_button);
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = 80;
        layoutParams.width = 80;
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, "teamPosition".hashCode()+team);
        layoutParams.rightMargin = 20;
        image.setLayoutParams(layoutParams);
        row.addView(image);


    }

}
