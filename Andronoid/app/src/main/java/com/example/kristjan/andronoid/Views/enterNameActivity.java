package com.example.kristjan.andronoid.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kristjan.andronoid.Game.Game;
import com.example.kristjan.andronoid.R;


/**
 * Created by Kristjan on 17.04.2017.
 */

public class enterNameActivity extends Activity {

    public static final String MyPREFERENCES = "MyPrefs";
    private EditText myText = null;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_name_activity);
        ImageView img = (ImageView) findViewById(R.id.go);
        myText = (EditText) findViewById(R.id.name);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myText.getText().toString().equals("Sinu nimi") || myText.getText().toString().equals("")) {
                    Toast.makeText(v.getContext(), "Palun sisesta enne mängimist oma nimi!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = myText.getText().toString();

                if (!sharedPreferences.contains("name")) {
                    editor.putString("name", name);
                    editor.putInt(name + "score", 0);
                    editor.commit();
                }

                Intent i = new Intent(v.getContext(), scoreActivity.class);
                i.putExtra("name", name);
                startActivity(i);
            }
        });

    }
}
