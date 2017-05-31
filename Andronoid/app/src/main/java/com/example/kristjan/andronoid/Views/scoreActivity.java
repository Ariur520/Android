package com.example.kristjan.andronoid.Views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kristjan.andronoid.Game.Game;
import com.example.kristjan.andronoid.Game.demo;
import com.example.kristjan.andronoid.R;

/**
 * Created by Kristjan on 17.04.2017.
 */

public class scoreActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    String name = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        name = getIntent().getExtras().getString("name");
        TextView conversation = (TextView) findViewById(R.id.showname);
        //TextView showscore = (TextView) findViewById(R.id.score);
        conversation.setText(name);

        ImageView myImage = (ImageView) findViewById(R.id.play);
        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), demo.class);
                i.putExtra("name", name);
                startActivity(i);
            }
        });
        ImageView myImage2 = (ImageView) findViewById(R.id.back);
        myImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), enterNameActivity.class);
                i.putExtra("name", name);
                startActivity(i);
            }
        });
        ImageView myImage3 = (ImageView) findViewById(R.id.cup);
        myImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), highScoreActivity.class);
                i.putExtra("name", name);
                startActivity(i);
            }
        });
        showHighScore();
    }
    private void showHighScore() {
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int score = 0;
        if (getIntent().hasExtra("name")) {
            name = getIntent().getExtras().getString("name");

        }


                score = sharedPreferences.getInt(name + "score", 0);



        TextView highScore = (TextView) findViewById(R.id.score);
        highScore.setText("" + score);
    }

}
