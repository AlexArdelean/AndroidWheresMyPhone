package com.example.alexssd.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.content.res.Resources;
import android.util.TypedValue;
import android.content.Intent;

public class GenerateCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);

        RelativeLayout generateLayout = new RelativeLayout(this);

        //Middle button used to relatively layout
        Button middle = new Button(this);
        middle.setVisibility(View.INVISIBLE);

        Button generateButton = new Button(this);
        generateButton.setBackgroundColor(Color.CYAN);
        generateButton.setText("Generate Code");
        generateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent startNewActivity = new Intent(GenerateCode.this, MainActivity.class);
                startActivity(startNewActivity);
            }
        });

        middle.setId(1);
        generateButton.setId(2);

        RelativeLayout.LayoutParams middleDeets = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams generateButtonDeets = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        //middle button deets
        middleDeets.addRule(RelativeLayout.CENTER_HORIZONTAL);
        middleDeets.addRule(RelativeLayout.CENTER_VERTICAL);
        //middle button deets
        generateButtonDeets.addRule(RelativeLayout.CENTER_HORIZONTAL);
        generateButtonDeets.addRule(RelativeLayout.CENTER_VERTICAL);

        generateButtonDeets.addRule(RelativeLayout.CENTER_HORIZONTAL);
        generateButtonDeets.addRule(RelativeLayout.BELOW, middle.getId());
        generateButtonDeets.setMargins(0,0,0, 200);

        Resources r = getResources();
        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 225, r.getDisplayMetrics());
        generateButton.setWidth(px);

        generateLayout.addView(generateButton, generateButtonDeets);
        setContentView(generateLayout);


    }
}
