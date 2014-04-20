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
import com.app.activepartytime.activities.GameMoveActivity;
import com.app.activepartytime.core.game.Game;
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
    private int currentTeam;
    private ImageView[] figures;
    private TextView[] teamPositions;
    private GameMoveActivity activity;

    public GamePlaygroundFragment(Game g, GameMoveActivity ac) {
        // Required empty public constructor
        playground = g.getPlayground();
        teams = g.getTeams();
        currentTeam = 0;
        activity = ac;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_game_playground, container, false);




        createPlayground();
        createTeamList();
        createFigures();
        ImageView teamPlayButton = (ImageView)v.findViewById("teamPlayButton".hashCode()+currentTeam);
        teamPlayButton.setVisibility(View.VISIBLE);



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
        teamPositions = new TextView[teams.length];
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
            teamPositionTextView.setId("teamPositionText".hashCode() + i);
            row.addView(teamPositionTextView);


            layoutTeamList.addView(row);
            teamPositions[i] = teamPositionTextView;

            /*
            * button image
             */
            ImageView imagePlay = new ImageView(getActivity());
            imagePlay.setImageResource(R.drawable.play_button);
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.height = 80;
            layoutParams.width = 80;
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, "teamPosition".hashCode()+i);
            layoutParams.rightMargin = 20;
            imagePlay.setId("teamPlayButton".hashCode() + i);
            imagePlay.setLayoutParams(layoutParams);
            imagePlay.setVisibility(View.GONE);
            imagePlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changePage();
                }
            });
            row.addView(imagePlay);
        }

    }

    private void changePage(){
        activity.mPager.setCurrentItem(0);

    }

    private void createFigures(){
        RelativeLayout layoutPlayground = (RelativeLayout) v.findViewById(R.id.containerPlayground);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(140, 200);
        figures = new ImageView[teams.length];

        for(int i=0 ; i < teams.length; i++){
            ImageView image = new ImageView(getActivity());
            switch (i){
                case 0: image.setImageResource(R.drawable.figure_blue);break;
                case 1: image.setImageResource(R.drawable.figure_yellow);break;
                case 2: image.setImageResource(R.drawable.figure_red);break;
                case 3: image.setImageResource(R.drawable.figure_green);break;
            }
            image.setId("teamFigure".hashCode()+i);
            image.setMaxHeight(50);
            layoutParams = new RelativeLayout.LayoutParams(140, 200);
            layoutParams.setMargins(24,0,24,0);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, 0);
            image.setLayoutParams(layoutParams);

            layoutPlayground.addView(image);
            figures[i] = image;

        }
    }


    public void updateState(){
        ImageView teamPlayButton = (ImageView)v.findViewById("teamPlayButton".hashCode()+currentTeam);
        teamPlayButton.setVisibility(View.GONE);
        currentTeam = (currentTeam +1)%teams.length;
        teamPlayButton = (ImageView)v.findViewById("teamPlayButton".hashCode()+currentTeam);
        teamPlayButton.setVisibility(View.VISIBLE);

        /*
         * update figures
         */
        RelativeLayout.LayoutParams layoutParams;
        for (int i=0; i <teams.length; i++){
            ImageView image = figures[i];//(ImageView)v.findViewById("teamFigure".hashCode()+i);
            layoutParams = new RelativeLayout.LayoutParams(140, 200);
            layoutParams.setMargins(24,0,24,0);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, teams[i].getPlaygroundPosition());
            image.setLayoutParams(layoutParams);
            image.refreshDrawableState();
        }
        /*
         * update teamList
         */
        for (int i=0; i <teams.length; i++){
            TextView teamPositionTextView = teamPositions[i];//(TextView)v.findViewById("teamPositionText".hashCode() + i);
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
            teamPositionTextView.setText(text.toString());
            teamPositionTextView.refreshDrawableState();
        }





    }

}
