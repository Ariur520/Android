package tarkvaratehnika.ttu.lapseaed.selectionGame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

import tarkvaratehnika.ttu.lapseaed.GameMenuActivity;
import tarkvaratehnika.ttu.lapseaed.R;

public class SelectionGameActivity extends Activity {

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    private List<List<Integer>> tasks = new ArrayList<>(6);
    private TextView conversation, score;
    final ImageView[] answers = new ImageView[4];
    private int[] indexes = new int[answers.length];
    private ImageView wrong = null;
    private String name = null;
    public static int scoreTotal = 0;
    public static boolean isRight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_game);

        for (int i = 0; i < 6; i++) {
            tasks.add(new ArrayList<Integer>());
        }

        tasks.get(0).add(R.drawable.elevant);
        tasks.get(0).add(R.drawable.joehobu);
        tasks.get(0).add(R.drawable.panda);
        tasks.get(1).add(R.drawable.lill);
        tasks.get(1).add(R.drawable.paevalill);
        tasks.get(1).add(R.drawable.roos);
        tasks.get(2).add(R.drawable.apple);
        tasks.get(2).add(R.drawable.banana);
        tasks.get(2).add(R.drawable.strawberry);
        tasks.get(3).add(R.drawable.plant1);
        tasks.get(3).add(R.drawable.plant2);
        tasks.get(3).add(R.drawable.plant3);
        tasks.get(4).add(R.drawable.vehicle1);
        tasks.get(4).add(R.drawable.vehicle2);
        tasks.get(4).add(R.drawable.vehicle3);
        tasks.get(5).add(R.drawable.kass);
        tasks.get(5).add(R.drawable.lehm);
        tasks.get(5).add(R.drawable.siga);

        score = (TextView) findViewById(R.id.score);
        score.setText("" + scoreTotal);
        answers[0] = (ImageView) findViewById(R.id.pic1);
        answers[1] = (ImageView) findViewById(R.id.pic2);
        answers[2] = (ImageView) findViewById(R.id.pic3);
        answers[3] = (ImageView) findViewById(R.id.pic4);

        Collections.shuffle(tasks);

        int wrongLayoutIndex = (int) Math.floor(Math.random() * answers.length);
        int wrongListIndex = (int) Math.floor(Math.random() * tasks.size());

        wrong = answers[wrongLayoutIndex];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        indexes[wrongLayoutIndex] = -1;
        answers[wrongLayoutIndex].setImageResource(
                tasks.get(wrongListIndex).get((int) Math.floor(Math.random()) * tasks.get(wrongListIndex).size()));
        tasks.remove(wrongListIndex);
        int rightListIndex = (int) Math.floor(Math.random() * (tasks.size() - 1));
        int counter = 0;
        for (int i = 0; i < indexes.length; i++) {

            if (indexes[i] == -1) {
                continue;
            }
            answers[i].setImageResource(tasks.get(rightListIndex).get(counter));
            counter++;
        }

        conversation = (TextView) findViewById(R.id.text);

    }

    public void onCheckAnswer(View view) {
        ImageView image = (ImageView) view;
        if (image.getDrawable() == wrong.getDrawable()) {
            conversation.setText("Õige vastus! ");
            tasks.clear();
            scoreTotal++;
            isRight = true;
            Log.e("tasks", String.valueOf(tasks.size()));
            wrong = null;
            Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    startActivity(getIntent());
                }
            }, 1000);
        } else {
            conversation.setText("Vale! Proovi veel!");
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
            highScore = sharedPreferences.getInt(name + "SelectionEasy", 0);
        }

        if (score > highScore) {
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(name + "SelectionEasy", score);
            editor.apply();
        }
    }

    public void onClickExit(View v) {
        saveHighScore(scoreTotal);
        Intent i = new Intent(v.getContext(), GameMenuActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("name", name);
        startActivity(i);
    }

}
