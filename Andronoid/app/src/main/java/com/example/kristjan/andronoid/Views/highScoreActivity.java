package com.example.kristjan.andronoid.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kristjan.andronoid.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Kristjan on 4.05.2017.
 */

public class highScoreActivity extends Activity {

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    String name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        name = getIntent().getExtras().getString("name");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score);

        ImageView myImage2 = (ImageView) findViewById(R.id.back);
        myImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), scoreActivity.class);
                i.putExtra("name", name);
                startActivity(i);
            }
        });
        ImageView myImage1 = (ImageView) findViewById(R.id.delete);
        myImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
            }
        });
        showAllScores();
    }

    private void showAllScores (){
        Map<String,?> allPrefs = sharedPreferences.getAll();


        ArrayList<String> result = new ArrayList<String>();

        List<TextView> scoreLayots = new ArrayList<>(4);
        scoreLayots.add((TextView)findViewById(R.id.position1));
        scoreLayots.add((TextView)findViewById(R.id.position2));
        scoreLayots.add((TextView)findViewById(R.id.position3));
        scoreLayots.add((TextView)findViewById(R.id.position4));

            for(Map.Entry<String,?> entry : allPrefs.entrySet()) {
               // for (int i = 0; i < 3; i++){
               // position1.setText(entry.getKey() + ": " +
                       // entry.getValue().toString());

            //}
                result.add(entry.getValue().toString() + ": " +
                        entry.getKey());
        }
        Collections.sort(result);
        for (int i = 0 ; i < 4; i++){
            scoreLayots.get(i).setText(result.get(i));
        }
    }
}
