package com.example.projekat10;

import android.widget.Button;
import android.widget.RadioButton;

public class Character {
    public String mName;
    public Button button;

    public Character(String name) {
        this.mName=name;
    }


    public String getmName(){
        return this.mName;
    }
}
