package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class JokesEndpointTaskTest {

    private static final String TAG = JokesEndpointTaskTest.class.getSimpleName();

    @Test
    public void test(){

        // Create  a signal to let us know when our task is done.
        final CountDownLatch signal = new CountDownLatch(1);

        final AsyncTask<Void, Void, String> myTask = new AsyncTask<Void, Void, String>() {
            private MyApi myApiService = null;

            @Override
            protected String doInBackground(Void... voids) {
                if (myApiService == null) {
                    MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                            new JacksonFactory(), null)
                            // options for running against local devappserver
                            // - 10.0.2.2 is localhost's IP address in Android emulator
                            // - turn off compression when running against local devappserver
                            .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                            .setApplicationName("Build it Bigger")
                            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                @Override
                                public void initialize(AbstractGoogleClientRequest<?> request) {
                                    request.setDisableGZipContent(true);
                                }
                            });
                    // end options for devappserver

                    myApiService = builder.build();
                }

                try {
                    return myApiService.tellJoke().execute().getData();
                } catch (IOException e) {
                    return e.toString();
                }
            }

            @Override
            protected void onPostExecute(String joke) {

                signal.countDown();
            }
        };

        myTask.execute();

        try {
            signal.await(5, TimeUnit.SECONDS);

            assertTrue(myTask.get().equalsIgnoreCase("This is a sample joke"));
        } catch (Exception e) {
            Log.e(TAG, "Exception Occured: " + e.toString());
        }
    }
}
