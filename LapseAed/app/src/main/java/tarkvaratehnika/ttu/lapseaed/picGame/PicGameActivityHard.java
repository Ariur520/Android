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

public class PicGameActivityHard extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    private List<List<Integer>> pics = new ArrayList<>(6);
    //public static int[] pics = {R.drawable.janes, R.drawable.koer, R.drawable.karu, R.drawable.kass, R.drawable.lehm, R.drawable.siga, R.drawable.elevant, R.drawable.panda, R.drawable.joehobu, R.drawable.tulp, R.drawable.roos, R.drawable.karikakar, R.drawable.paevalill, R.drawable.taks, R.drawable.dalmaatslane, R.drawable.mops, R.drawable.puudel, R.drawable.jaakaru, R.drawable.pesukaru, R.drawable.pingviin, R.drawable.tuvi, R.drawable.flamingo, R.drawable.kurg};
    final String[] answers = new String[4];
    private int[] indexes = new int[answers.length];
    private String right = null;
    private String name = null;
    private TextView conversation, score;
    public static int scoreTotal = 0;
    public static boolean isRight = false;
    public static boolean isPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_game_hard);
        isPressed = false;
        score = (TextView) findViewById(R.id.score);
        score.setText("" + scoreTotal);
        ImageView image = (ImageView) findViewById(R.id.imageToGuess);

        for (int i = 0; i < 4; i++) {
            pics.add(new ArrayList<Integer>());
        }
        pics.get(0).add(R.drawable.taks);
        pics.get(0).add(R.drawable.dalmaatslane);
        pics.get(0).add(R.drawable.mops);
        pics.get(0).add(R.drawable.puudel);
        pics.get(1).add(R.drawable.paevalill);
        pics.get(1).add(R.drawable.tulp);
        pics.get(1).add(R.drawable.roos);
        pics.get(1).add(R.drawable.karikakar);
        pics.get(2).add(R.drawable.flamingo);
        pics.get(2).add(R.drawable.tuvi);
        pics.get(2).add(R.drawable.kurg);
        pics.get(2).add(R.drawable.pingviin);
        pics.get(3).add(R.drawable.karu);
        pics.get(3).add(R.drawable.pesukaru);
        pics.get(3).add(R.drawable.jaakaru);
        pics.get(3).add(R.drawable.panda);

        int rightLayoutIndex = (int) Math.floor(Math.random() * answers.length);
        int rightListIndex = (int) Math.floor(Math.random() * pics.get(0).size());
        int rightIndex = (int) Math.floor(Math.random() * pics.get(rightListIndex).size());
        image.setImageResource(pics.get(rightListIndex).get(rightIndex));
        //System.out.println(rightListIndex + " + " + rightLayoutIndex);
        for (int i = 0; i < pics.get(rightListIndex).size(); i++) {
            indexes[i] = i;
            answers[i] = "";
        }

        indexes[rightIndex] = -1;
        answers[rightIndex] = image.getResources().getResourceEntryName(pics.get(rightListIndex).get(rightIndex));
        right = answers[rightIndex];
        System.out.println(rightIndex);
        System.out.println(answers[rightIndex]);
        System.out.println(right);

        int counter = 0;
        for (int i = 0; i < 4; i++) {
            //System.out.println(indexes[i]);
            if (indexes[i] == -1) {
                answers[i] = right;
                counter++;
                continue;
            }
            answers[i] = image.getResources().getResourceEntryName(pics.get(rightListIndex).get(counter++));
            System.out.println(i + " " + answers[i]);
        }

        List<Button> buttons = new ArrayList<>(4);
        buttons.add((Button) findViewById(R.id.option1));
        buttons.add((Button) findViewById(R.id.option2));
        buttons.add((Button) findViewById(R.id.option3));
        buttons.add((Button) findViewById(R.id.option4));
        Collections.shuffle(buttons);

        for (int j = 0; j < 4; j++) {
            buttons.get(j).setText(answers[j]);
        }
        conversation = (TextView) findViewById(R.id.text);
    }

    public void onCheckAnswer(View view) {
        if (isPressed) return;
        Button button = (Button) view;
        if (button.getText() == right) {
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
        } else {
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
        if (getIntent().hasExtra("name")) {
            name = getIntent().getExtras().getString("name");
            highScore = sharedPreferences.getInt(name + "PicHard", 0);
        }

        if (score > highScore) {
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(name + "PicHard", score);
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
