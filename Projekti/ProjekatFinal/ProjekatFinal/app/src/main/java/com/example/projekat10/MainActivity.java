package com.example.projekat10;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button onePageButton;
    EditText onePageEditText;
    String editTextValue = "";
    ListView listViewOnePage;
    ListAdapter adapter;
    HttpHelper httpHelper;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onePageButton = findViewById(R.id.pageOneButton);
        onePageButton.setOnClickListener(this);

        adapter = new ListAdapter(this);
        adapter.addCharacter(new Character("Novi sad"));
        adapter.addCharacter(new Character("Kula"));
        adapter.addCharacter(new Character("Vrbas"));
        adapter.addCharacter(new Character("Kucura"));
        adapter.addCharacter(new Character("Wien"));

        httpHelper = new HttpHelper();
        handler = new Handler();

        listViewOnePage = findViewById(R.id.pageOneListView);
        listViewOnePage.setAdapter(adapter);

        listViewOnePage.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.removeChaacter(position);
                return true;
            }
        });

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pageOneButton) {
            onePageEditText = findViewById(R.id.pageOneEditText);
            editTextValue = onePageEditText.getText().toString();
            if (!editTextValue.isEmpty() && editTextValue.length() < 50) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final boolean zahtev = httpHelper.zahtev(editTextValue);

                            handler.post(new Runnable() {
                                public void run() {
                                    if (zahtev) {
                                        adapter.addCharacter(new Character(editTextValue));

                                        onePageEditText.setText("");
                                        onePageEditText.setHint("");
                                    } else {
                                        Toast.makeText(MainActivity.this, "Pogresan grad", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        }
    }
}

