package com.android.parthjoshi.jokepresenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokePresenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_presenter);

        TextView tvJoke = findViewById(R.id.joke);
        String joke = "";

        Intent intent = getIntent();
        if(intent.hasExtra(getString(R.string.joke_extra)))
            joke = intent.getStringExtra(getString(R.string.joke_extra));

        tvJoke.setText(joke);
    }
}
