package com.example.kevin.contactcard;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView hello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hello = (TextView) findViewById(R.id.lbl_hello);

        new JSONAsyncTask().execute("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class JSONAsyncTask extends AsyncTask<String, String, String> {
        HttpURLConnection urlConnection;
        StringBuilder total;

        @Override
        protected String doInBackground(String... params) {
            total = new StringBuilder();
            try {
                URL url = new URL("http://returnoftambelon.com/koken_recepten.php?id=1&section=main");
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader read = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = read.readLine()) != null) {
                    total.append(line);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }
            return total.toString();
        }

        @Override
        protected void onPostExecute(String string) {
            //super.onPostExecute(string);
            hello.setText(string);
        }
    }
}
