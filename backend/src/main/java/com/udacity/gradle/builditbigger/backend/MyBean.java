package com.udacity.gradle.builditbigger.backend;

import com.android.parthjoshi.myjoke.Joke;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    private Joke joke;

    public String getData() {
        return joke.getJoke();
    }

    public void setData() {
        joke = new Joke();
    }
}