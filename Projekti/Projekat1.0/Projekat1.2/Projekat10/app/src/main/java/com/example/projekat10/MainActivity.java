package com.example.projekat10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
Button onePageButton;
EditText onePageEditText;
String editTextValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onePageButton=findViewById(R.id.pageOneButton);
        onePageButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v){
        if(v.getId()==R.id.pageOneButton){
            onePageEditText=findViewById(R.id.pageOneEditText);
            editTextValue=onePageEditText.getText().toString();
            Intent i2=new Intent(v.getContext(),page2.class);
            i2.putExtra("text1",editTextValue);
            startActivity(i2);
        }
    }
}
