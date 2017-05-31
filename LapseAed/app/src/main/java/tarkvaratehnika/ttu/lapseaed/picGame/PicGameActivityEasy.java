package tarkvaratehnika.ttu.lapseaed.picGame;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

import tarkvaratehnika.ttu.lapseaed.GameMenuActivity;
import tarkvaratehnika.ttu.lapseaed.R;
import tarkvaratehnika.ttu.lapseaed.highScores.PicGameHighScoreActivity;

public class PicGameActivityEasy extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;
    public static int[] pics={R.drawable.janes,R.drawable.koer,R.drawable.karu,R.drawable.kass,
            R.drawable.lehm,R.drawable.siga,R.drawable.elevant,R.drawable.panda,R.drawable.joehobu,
            R.drawable.jaakaru,R.drawable.pingviin,R.drawable.tuvi,R.drawable.flamingo,R.drawable.kana, R.drawable.orav};
    String[] ans= new String[pics.length];
    private String trans = null;
    private String name = null;
    private TextView conversation, score;
    public static int scoreTotal = 0;
    public static boolean isRight = false;
    public static boolean isPressed = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_game_easy);
        isPressed = false;
        score = (TextView) findViewById(R.id.score);
        score.setText("" + scoreTotal);
        ImageView image = (ImageView) findViewById(R.id.imageToGuess);
        int correctIndex = randomize(0,pics.length-1);
        image.setImageResource(pics[correctIndex]);
        String right = image.getResources().getResourceEntryName(pics[correctIndex]);
        trans = right;

        List<Integer> chosenPics = new ArrayList<>();
        chosenPics.add(correctIndex);
        int wrong1Index,wrong2Index;
        do {
            wrong1Index = randomize(0, pics.length - 1);
            wrong2Index = randomize(0, pics.length - 1);
        }
        while (chosenPics.contains(wrong1Index) || chosenPics.contains(wrong2Index) || wrong1Index == wrong2Index);
        String wrong1 = image.getResources().getResourceEntryName(pics[wrong1Index]);
        String wrong2 = image.getResources().getResourceEntryName(pics[wrong2Index]);


        ans[0] = right;
        ans[1] = String.valueOf(wrong1);
        ans[2] = String.valueOf(wrong2);

        List<Button> btns = new ArrayList<>(3);

        btns.add((Button)findViewById(R.id.option1));
        btns.add((Button)findViewById(R.id.option2));
        btns.add((Button)findViewById(R.id.option3));
        Collections.shuffle(btns);
        for (int j = 0; j < 3; j++) {
            btns.get(j).setText(ans[j]);
        }
        conversation = (TextView) findViewById(R.id.text);


    }
    public void onCheckAnswer(View view) {
        if (isPressed) return;
        Button button = (Button) view;
        if(button.getText() == trans){
            conversation.setText("Õige \nvastus! ");
            scoreTotal++;
            isRight = true;
            isPressed = true;
            new Handler().postDelayed(new TimerTask() {
                @Override
                public void run() {
                    finish();
                    startActivity(getIntent());
                }
            }, 1000);
        }else{
            conversation.setText("Vale! \nProovi veel!");
            scoreTotal = 0;
            isRight = false;
        }
        saveHighScore(scoreTotal);
        score.setText("" + scoreTotal);
    }
    public void saveHighScore(int score) {
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int highScore = 0;
        if(getIntent().hasExtra("name")){
            name = getIntent().getExtras().getString("name");
            highScore = sharedPreferences.getInt(name + "PicEasy", 0);
        }

        if (score > highScore) {
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(name + "PicEasy", score);
            editor.apply();
        }
    }

    public int randomize(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    public void onClickExit(View v) {
        saveHighScore(scoreTotal);
        scoreTotal = 0;
        Intent i = new Intent(v.getContext(), PicGameHighScoreActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("name", name);
        startActivity(i);
    }
}
