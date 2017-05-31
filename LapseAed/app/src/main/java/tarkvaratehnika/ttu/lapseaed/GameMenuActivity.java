package tarkvaratehnika.ttu.lapseaed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import tarkvaratehnika.ttu.lapseaed.highScores.CalculationGameHighScoreActivity;
import tarkvaratehnika.ttu.lapseaed.highScores.PicGameHighScoreActivity;
import tarkvaratehnika.ttu.lapseaed.highScores.SelectionGameHighScoreActivity;

public class GameMenuActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    String name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_menu);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        name = getIntent().getExtras().getString("name");
        TextView conversation = (TextView) findViewById(R.id.chooseGame);
        conversation.setText(name + ",\nvali mäng!");
    }

    public void onClickPicGame(View v) {
        Intent i = new Intent(v.getContext(), PicGameHighScoreActivity.class);
        i.putExtra("name", name);
        startActivity(i);
    }

    public void onClickCalcGame(View v) {
        Intent i = new Intent(v.getContext(), CalculationGameHighScoreActivity.class);
        i.putExtra("name", name);
        startActivity(i);
    }

    public void onClickSelectionGame(View v) {
        Intent i = new Intent(v.getContext(), SelectionGameHighScoreActivity.class);
        i.putExtra("name", name);
        startActivity(i);
    }
}
