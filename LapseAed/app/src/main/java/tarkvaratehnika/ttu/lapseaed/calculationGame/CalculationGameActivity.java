package tarkvaratehnika.ttu.lapseaed.calculationGame;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tarkvaratehnika.ttu.lapseaed.GameMenuActivity;
import tarkvaratehnika.ttu.lapseaed.R;

public class CalculationGameActivity extends Activity {

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    private ImageView bug1 = null;
    private ImageView bug2 = null;
    private TextView ans1 = null;
    private TextView ans2 = null;
    private TextView ans3 = null;
    private TextView ans4 = null;
    private TextView correctAns = null;
    private TextView conversation = null;
    private TextView score;
    private String name = null;
    public static int scoreTotal = 0;
    public static boolean isRight = false;
    private static final String[] WRONG_MESSAGES = {
            "Vale vastus!\nProovi uuesti!",
            "Vale vastus!\n Proovi veel!",
            "Vale vastus!\n Arva uuesti!",
            "Vale vastus!\n Arva veel!"
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_game);

        score = (TextView) findViewById(R.id.score);
        score.setText("" + scoreTotal);
        bug1 = (ImageView) findViewById(R.id.number1);
        bug2 = (ImageView) findViewById(R.id.number2);
        ans1 = (TextView) findViewById(R.id.answer1);
        ans2 = (TextView) findViewById(R.id.answer2);
        ans3 = (TextView) findViewById(R.id.answer3);
        ans4 = (TextView) findViewById(R.id.answer4);
        conversation = (TextView) findViewById(R.id.taskCalcGame);

        int num1 = (int) (Math.floor(Math.random() * 6) + 1);
        int num2 = (int) (Math.floor(Math.random() * 6) + 1);
        int correct = num1 + num2;

        setImg(bug1, num1);
        setImg(bug2, num2);

        TextView[] t = new TextView[4];

        t[0] = ans1;
        t[1] = ans2;
        t[2] = ans3;
        t[3] = ans4;
        List<Integer> answers = new ArrayList<Integer>();
        for (int i = 0; i < t.length; i++) {
            int ran = (int) Math.floor(Math.random() * 4);
            while (t[ran] == null && (t[0] != null || t[1] != null || t[2] != null || t[3] != null)) {
                ran = (int) Math.floor(Math.random() * 4);
            }
            if (i == 0) {
                t[ran].setText(String.valueOf(correct));
                correctAns = t[ran];
                t[ran] = null;
            } else {
                int ans = (int) Math.floor(Math.random() * 12) + 1;
                while (ans == correct || answers.contains(ans)) {
                    ans = (int) Math.floor(Math.random() * 12) + 1;
                }
                answers.add(ans);
                //Log.e("length", String.valueOf(answers.size()));
                t[ran].setText(String.valueOf(ans));
                t[ran] = null;
            }
        }
        //Log.e("values", ans1.getText() + " " + ans2.getText()+ " " + ans3.getText()+ " " + ans4.getText());
    }


    public void onCheckAnswer(View button) {
        if (button == correctAns) {
            conversation.setText("Õige vastus!");
            prepareNextRound(ans1, ans2, ans3, ans4, bug1, bug2);
            scoreTotal++;
            isRight = true;
            Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Bundle tempBundle = new Bundle();
                    onCreate(tempBundle);
                }
            }, 1000);
        } else {
            int randomIndex = new Random().nextInt(WRONG_MESSAGES.length);
            conversation.setText(WRONG_MESSAGES[randomIndex]);
            scoreTotal = 0;
            isRight = false;
        }
        saveHighScore(scoreTotal);
        score.setText("" + scoreTotal);
    }

    public void saveHighScore(int score) {
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        name = getIntent().getExtras().getString("name");
        int highScore = sharedPreferences.getInt(name + "CalcEasy", 0);

        if (score > highScore) {
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(name + "CalcEasy", score);
            editor.apply();
        }
    }

    public void setImg(ImageView a, int num) {
        switch (num) {
            case 1:
                a.setImageResource(R.drawable.ladybug1);
                break;
            case 2:
                a.setImageResource(R.drawable.ladybug2);
                break;
            case 3:
                a.setImageResource(R.drawable.ladybug3);
                break;
            case 4:
                a.setImageResource(R.drawable.ladybug4);
                break;
            case 5:
                a.setImageResource(R.drawable.ladybug5);
                break;
            case 6:
                a.setImageResource(R.drawable.ladybug6);
                break;
        }
    }

    public void prepareNextRound(TextView a, TextView b, TextView c, TextView d, ImageView img1, ImageView img2) {
        a.setText("");
        b.setText("");
        c.setText("");
        d.setText("");
        img1.setImageResource(R.drawable.ladybug);
        img2.setImageResource(R.drawable.ladybug);
    }

    public void onClickExit(View v) {
        saveHighScore(scoreTotal);
        Intent i = new Intent(v.getContext(), GameMenuActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("name", name);
        startActivity(i);
    }
}
