package fr.isep.ii3510.assignment2;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private Integer nbLife = 0;
    private String randomWord = "";
    private ArrayList<String> listFind = null;
    private View.OnClickListener onClickLetterListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button button = (Button) view;
            if(randomWord.contains(button.getText())) {
                listFind.add(button.getText().toString());
                button.setBackgroundColor(Color.GREEN);
                button.setEnabled(false);
                updateRes();
            } else {
                button.setBackgroundColor(Color.RED);
                button.setEnabled(false);
                TextView tView = findViewById(R.id.life);
                nbLife--;
                tView.setText(nbLife.toString());
            }
            if(nbLife < 6) {
                Button b = findViewById(R.id.restartButton);
                b.setVisibility(View.VISIBLE);
            }
            if(nbLife == 0) {
                Toast.makeText(getApplicationContext(),"You loose", Toast.LENGTH_LONG).show();
                initializeGame();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeGame();
    }

    private void updateRes() {
        LinearLayout lin = findViewById(R.id.myLinear);
        lin.removeAllViews();
        int iteration = 0;
        for (int i = 0; i < randomWord.length() ; i++) {
            TextView myText = new TextView(getApplicationContext());
            String r = String.valueOf(randomWord.charAt(i));
            if(listFind.contains(r)) {
                myText.setTextSize(25);
                myText.setText(r);
                iteration++;
            } else {
                myText.setTextSize(40);
                myText.setText("-");
            }
            lin.addView(myText);
        }
        if(randomWord.length() == iteration) {
            initializeGame();
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("You win")
                    .setMessage("Thanks for playing")
                    .show();
        }
    }


    private void initializeGame() {
        listFind = new ArrayList<>();
        nbLife = 10;
        ReadJson readJson = ReadJson.getInstance();
        String data = readJson.loadJSONFromAsset(getApplicationContext(),"words.json");
        ArrayList<String> allWords = readJson.getData(data);
        randomWord = readJson.selectRandomWords(allWords);
        LinearLayout lin = findViewById(R.id.myLinear);
        lin.removeAllViews();
        for (int i = 0; i < randomWord.length() ; i++) {
            TextView myText = new TextView(this);
            myText.setTextSize(40);
            myText.setText("-");
            lin.addView(myText);
        }
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        GridLayout gridLayout = findViewById(R.id.grid);
        gridLayout.removeAllViews();
        for (int i = 0; i < 26 ; i++) {
            Button button = new Button(this);
            button.setText(String.valueOf(alphabet.charAt(i)));
            button.setOnClickListener(onClickLetterListener);
            gridLayout.addView(button);
        }
        TextView textView = findViewById(R.id.life);
        textView.setText(nbLife.toString());
        Button b = findViewById(R.id.restartButton);
        b.setVisibility(View.INVISIBLE);
    }

    public void onRestart(View view) {
        initializeGame();
    }
}
