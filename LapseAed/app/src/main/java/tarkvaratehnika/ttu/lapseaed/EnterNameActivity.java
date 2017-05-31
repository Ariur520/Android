package tarkvaratehnika.ttu.lapseaed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class EnterNameActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    private EditText myText = null;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_name);
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
                    editor.putInt(name + "PicEasy", 0);
                    editor.putInt(name + "PicMed", 0);
                    editor.putInt(name + "PicHard", 0);
                    editor.putInt(name + "CalcEasy", 0);
                    editor.putInt(name + "CalcMed", 0);
                    editor.putInt(name + "CalcHard", 0);
                    editor.putInt(name + "SelectionEasy", 0);
                    editor.putInt(name + "SelectionMed", 0);
                    editor.putInt(name + "SelectionHard", 0);
                    editor.commit();
                }

                Intent i = new Intent(v.getContext(), GameMenuActivity.class);
                i.putExtra("name", name);
                startActivity(i);
            }
        });

    }
}
