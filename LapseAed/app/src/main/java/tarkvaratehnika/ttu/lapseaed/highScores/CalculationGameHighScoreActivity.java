package tarkvaratehnika.ttu.lapseaed.highScores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tarkvaratehnika.ttu.lapseaed.GameMenuActivity;
import tarkvaratehnika.ttu.lapseaed.R;
import tarkvaratehnika.ttu.lapseaed.calculationGame.CalculationGameActivityEasy;
import tarkvaratehnika.ttu.lapseaed.calculationGame.CalculationGameActivityHard;
import tarkvaratehnika.ttu.lapseaed.calculationGame.CalculationGameActivityMedium;

public class CalculationGameHighScoreActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    private String name = null;
    private Integer selected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score_calc_game);
        showHighScore();
    }

    public void onClickPlayButtonCalc(View v) {
        switch (selected) {

            case 0:
                Toast.makeText(getApplicationContext(), "Vali raskusaste!", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Intent i = new Intent(v.getContext(), CalculationGameActivityEasy.class);
                name = getIntent().getExtras().getString("name");
                i.putExtra("name", name);
                startActivity(i);
                break;
            case 2:
                Intent j = new Intent(v.getContext(), CalculationGameActivityMedium.class);
                name = getIntent().getExtras().getString("name");
                j.putExtra("name", name);
                startActivity(j);
                break;
            case 3:
                Intent k = new Intent(v.getContext(), CalculationGameActivityHard.class);
                name = getIntent().getExtras().getString("name");
                k.putExtra("name", name);
                startActivity(k);
                break;
        }


    }

    public void onClickCalcEasy(View v) {
        selected = 1;
        setBackground(selected);
        showHighScore();
    }

    public void onClickCalcMed(View v) {
        selected = 2;
        setBackground(selected);
        showHighScore();
    }

    public void onClickCalcHard(View v) {
        selected = 3;
        setBackground(selected);
        showHighScore();
    }

    public void setBackground(int selected) {
        switch (selected) {
            case 1:
                ImageView selectedImg1 = (ImageView) findViewById(R.id.easy);
                ImageView selectedImg2 = (ImageView) findViewById(R.id.medium);
                ImageView selectedImg3 = (ImageView) findViewById(R.id.hard);
                selectedImg1.setBackgroundResource(R.drawable.gradient);
                selectedImg2.setBackgroundDrawable(null);
                selectedImg3.setBackgroundDrawable(null);
                break;
            case 2:
                selectedImg1 = (ImageView) findViewById(R.id.easy);
                selectedImg2 = (ImageView) findViewById(R.id.medium);
                selectedImg3 = (ImageView) findViewById(R.id.hard);
                selectedImg2.setBackgroundResource(R.drawable.gradient);
                selectedImg1.setBackgroundDrawable(null);
                selectedImg3.setBackgroundDrawable(null);
                break;
            case 3:
                selectedImg1 = (ImageView) findViewById(R.id.easy);
                selectedImg2 = (ImageView) findViewById(R.id.medium);
                selectedImg3 = (ImageView) findViewById(R.id.hard);
                selectedImg3.setBackgroundResource(R.drawable.gradient);
                selectedImg2.setBackgroundDrawable(null);
                selectedImg1.setBackgroundDrawable(null);
                break;
        }
    }

    protected void onResume() {
        super.onResume();
        showHighScore();
    }

    private void showHighScore() {
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int score = 0;
        if (getIntent().hasExtra("name")) {
            name = getIntent().getExtras().getString("name");

        }
        switch (selected) {
            case 1:
                score = sharedPreferences.getInt(name + "CalcEasy", 0);
                break;
            case 2:
                score = sharedPreferences.getInt(name + "CalcMed", 0);
                break;
            case 3:
                score = sharedPreferences.getInt(name + "CalcHard", 0);
                break;
        }
        TextView highScore = (TextView) findViewById(R.id.score);
        highScore.setText("" + score);
    }

    public void onClickBack(View v) {
        Intent i = new Intent(v.getContext(), GameMenuActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("name", name);
        startActivity(i);
    }
}
