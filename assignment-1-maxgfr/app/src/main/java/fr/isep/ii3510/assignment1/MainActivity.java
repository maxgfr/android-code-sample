package fr.isep.ii3510.assignment1;

import android.os.Bundle;

import com.gc.materialdesign.views.Slider;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorListener;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private EditText editText;
    private ColorPickerView colorPickerView;
    private Slider sliderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String content = editText.getText().toString();
                textView.setText(content);
            }
        });

        colorPickerView.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(int color, boolean fromUser) {
                textView.setTextColor(color);
            }
        });

        sliderView.setOnValueChangedListener(new Slider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                textView.setTextSize(value);
            }
        });

    }

    private void initializeView () {
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        colorPickerView = findViewById(R.id.colorPickerView);
        sliderView = findViewById(R.id.slider);
    }

}
