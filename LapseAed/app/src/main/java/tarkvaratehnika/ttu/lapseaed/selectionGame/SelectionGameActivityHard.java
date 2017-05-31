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

import tarkvaratehnika.ttu.lapseaed.R;
import tarkvaratehnika.ttu.lapseaed.highScores.SelectionGameHighScoreActivity;

public class SelectionGameActivityHard extends Activity {

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    private Map<Integer, List<Integer>> tasks = new HashMap<>(6);
    private TextView conversation, score;
    final ImageView[] answers = new ImageView[6];
    private int[] indexes = new int[answers.length];
    private ImageView wrong = null;
    private String name = null;
    public static int scoreTotal = 0;
    public static boolean isRight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_game_hard);

        tasks.put(R.drawable.orav, new ArrayList<Integer>(5));
        tasks.put(R.drawable.korall, new ArrayList<Integer>(5));
        tasks.put(R.drawable.sibul, new ArrayList<Integer>(5));
        tasks.put(R.drawable.muru, new ArrayList<Integer>(5));
        tasks.put(R.drawable.paat, new ArrayList<Integer>(5));
        tasks.put(R.drawable.kana, new ArrayList<Integer>(5));
        tasks.get(R.drawable.orav).add(R.drawable.elevant);
        tasks.get(R.drawable.orav).add(R.drawable.joehobu);
        tasks.get(R.drawable.orav).add(R.drawable.panda);
        tasks.get(R.drawable.orav).add(R.drawable.ahv);
        tasks.get(R.drawable.orav).add(R.drawable.ninasarvik);
        tasks.get(R.drawable.korall).add(R.drawable.lill);
        tasks.get(R.drawable.korall).add(R.drawable.paevalill);
        tasks.get(R.drawable.korall).add(R.drawable.roos);
        tasks.get(R.drawable.korall).add(R.drawable.tulp);
        tasks.get(R.drawable.korall).add(R.drawable.sininelill);
        tasks.get(R.drawable.sibul).add(R.drawable.apple);
        tasks.get(R.drawable.sibul).add(R.drawable.banana);
        tasks.get(R.drawable.sibul).add(R.drawable.strawberry);
        tasks.get(R.drawable.sibul).add(R.drawable.arbuus);
        tasks.get(R.drawable.sibul).add(R.drawable.ananass);
        tasks.get(R.drawable.muru).add(R.drawable.plant1);
        tasks.get(R.drawable.muru).add(R.drawable.plant2);
        tasks.get(R.drawable.muru).add(R.drawable.plant3);
        tasks.get(R.drawable.muru).add(R.drawable.palm);
        tasks.get(R.drawable.muru).add(R.drawable.sonajalg);
        tasks.get(R.drawable.paat).add(R.drawable.vehicle1);
        tasks.get(R.drawable.paat).add(R.drawable.vehicle2);
        tasks.get(R.drawable.paat).add(R.drawable.vehicle3);
        tasks.get(R.drawable.paat).add(R.drawable.vehicle4);
        tasks.get(R.drawable.paat).add(R.drawable.vehicle5);
        tasks.get(R.drawable.kana).add(R.drawable.kass);
        tasks.get(R.drawable.kana).add(R.drawable.lehm);
        tasks.get(R.drawable.kana).add(R.drawable.siga);
        tasks.get(R.drawable.kana).add(R.drawable.lammas);
        tasks.get(R.drawable.kana).add(R.drawable.koer);
        List<Integer> bigIndexes = new ArrayList<>();
        bigIndexes.add(R.drawable.orav);
        bigIndexes.add(R.drawable.korall);
        bigIndexes.add(R.drawable.sibul);
        bigIndexes.add(R.drawable.muru);
        bigIndexes.add(R.drawable.paat);
        bigIndexes.add(R.drawable.kana);

        Collections.shuffle(bigIndexes);

        score = (TextView) findViewById(R.id.score);
        score.setText("" + scoreTotal);

        answers[0] = (ImageView) findViewById(R.id.pic1);
        answers[1] = (ImageView) findViewById(R.id.pic2);
        answers[2] = (ImageView) findViewById(R.id.pic3);
        answers[3] = (ImageView) findViewById(R.id.pic4);
        answers[4] = (ImageView) findViewById(R.id.pic5);
        answers[5] = (ImageView) findViewById(R.id.pic6);

        for (List list : tasks.values()) {
            Collections.shuffle(list);
        }
        int wrongLayoutIndex = (int) Math.floor(Math.random() * answers.length);
        //int wrongListIndex = (int) Math.floor(Math.random() * tasks.size());

        wrong = answers[wrongLayoutIndex];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        indexes[wrongLayoutIndex] = -1;

        int rightListIndex = bigIndexes.get((int) Math.floor(Math.random() * bigIndexes.size()));
        int counter = 0;
        for (int i = 0; i < indexes.length; i++) {

            if (indexes[i] == -1) {
                continue;
            }
            answers[i].setImageResource(tasks.get(rightListIndex).get(counter));
            counter++;
        }
        answers[wrongLayoutIndex].setImageResource(rightListIndex);

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
            highScore = sharedPreferences.getInt(name + "SelectionHard", 0);
        }

        if (score > highScore) {
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(name + "SelectionHard", score);
            editor.apply();
        }
    }

    public void onClickExit(View v) {
        saveHighScore(scoreTotal);
        Intent i = new Intent(v.getContext(), SelectionGameHighScoreActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("name", name);
        scoreTotal = 0;
        startActivity(i);
    }

}
