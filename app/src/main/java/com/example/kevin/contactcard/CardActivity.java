package com.example.kevin.contactcard;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CardActivity extends AppCompatActivity {
    Person currentPerson;
    TextView name;
    TextView nationality;
    TextView street;
    FeedreaderDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        name = (TextView) findViewById(R.id.lbl_name);
        nationality = (TextView) findViewById(R.id.lbl_nationality);
        street = (TextView) findViewById(R.id.lbl_street);

        dbHelper = new FeedreaderDbHelper(CardActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card, menu);
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

    public void randomPerson(View view) {
        new JSONAsyncTask().execute("");
    }

    public void savePerson(View view) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedreaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID, 0);
        values.put(FeedreaderContract.FeedEntry.COLUMN_NAME_TITLE, currentPerson.getTitle());
        values.put(FeedreaderContract.FeedEntry.COLUMN_NAME_FIRST, currentPerson.getFirst());
        values.put(FeedreaderContract.FeedEntry.COLUMN_NAME_LAST, currentPerson.getLast());
        // continue here

        //long newRowId;
        //newRowId = db.insert(
                //FeedreaderContract.FeedEntry.TABLE_NAME,
                //FeedreaderContract.FeedEntry.COLUMN_NAME_NULLABLE,
                //values);
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
                currentPerson = person;
                name.setText(person.getTitle() + " " + person.getFirst() + " " + person.getLast());
                nationality.setText(person.getNationality());
                street.setText(person.getStreet());

                new DownloadImageTask((ImageView) findViewById(R.id.img_portrait)).execute(person.getImageString());


                //Iterator<String> iterator = jObject.keys();
                //while(iterator.hasNext()) {
                //    String key = iterator.next();
                //}
            }
            catch (JSONException jsonEx) {
                System.out.println(jsonEx.toString());
                MainActivity.showAlert(CardActivity.this, "ERROR", "Unable to parse the JSON: " + jsonEx.getMessage());
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView portrait;

        public DownloadImageTask(ImageView bmImage) {
            this.portrait = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                MainActivity.showAlert(CardActivity.this, "ERROR", "Unable to get image: " + e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            portrait.setImageBitmap(result);
        }
    }
}
