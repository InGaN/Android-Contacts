package com.example.kevin.contactcard;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView hello;
    List<Person> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hello = (TextView) findViewById(R.id.lbl_hello);
        people = new ArrayList<>();
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

    private void showAlert(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private class JSONAsyncTask extends AsyncTask<String, String, String> {
        HttpURLConnection urlConnection;
        StringBuilder total;

        @Override
        protected String doInBackground(String... params) {
            total = new StringBuilder();
            try {
                URL url = new URL("https://randomuser.me/api/");
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
            //hello.setText(string);

            try {
                JSONObject jObject = new JSONObject(string);

                JSONObject json = jObject.getJSONArray("results").getJSONObject(0).getJSONObject("user");

                Person person = new Person(
                        0,//Integer.parseInt(key),
                        json.getString("gender"),
                        json.getJSONObject("name").getString("title"),
                        json.getJSONObject("name").getString("first"),
                        json.getJSONObject("name").getString("last"),
                        json.getJSONObject("location").getString("street"),
                        json.getJSONObject("location").getString("city"),
                        json.getJSONObject("location").getString("state"),
                        json.getJSONObject("location").getString("zip"),
                        json.getString("email"),
                        json.getString("phone"),
                        json.getJSONObject("picture").getString("medium"),
                        jObject.getString("nationality")
                );
                hello.setText(person.toString());
                people.add(person);

                //Iterator<String> iterator = jObject.keys();
                //while(iterator.hasNext()) {
                //    String key = iterator.next();
                //}
            }
            catch (JSONException jsonEx) {
                System.out.println(jsonEx.toString());
                showAlert("ERROR", "Unable to parse the JSON: " + jsonEx.getMessage());
            }

            //hello.setText(people.get(0).getLast());
        }
    }
}
