package com.example.kevin.contactcard;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.RelativeLayout;
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
    FeedreaderDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        Intent intent = getIntent();
        currentPerson = (Person) intent.getParcelableExtra("incoming_person");

        if(currentPerson != null) {
            drawPerson(currentPerson);
        }
        else {
            new JSONAsyncTask().execute("");
        }

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
        //values.put(FeedreaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID, null);
        values.put(FeedreaderContract.FeedEntry.COLUMN_NAME_TITLE, currentPerson.getTitle());
        values.put(FeedreaderContract.FeedEntry.COLUMN_NAME_FIRST, currentPerson.getFirst());
        values.put(FeedreaderContract.FeedEntry.COLUMN_NAME_LAST, currentPerson.getLast());
        values.put(FeedreaderContract.FeedEntry.COLUMN_NAME_GENDER, currentPerson.getGender());
        values.put(FeedreaderContract.FeedEntry.COLUMN_NAME_STREET, currentPerson.getStreet());
        values.put(FeedreaderContract.FeedEntry.COLUMN_NAME_CITY, currentPerson.getCity());
        values.put(FeedreaderContract.FeedEntry.COLUMN_NAME_STATE, currentPerson.getState());
        values.put(FeedreaderContract.FeedEntry.COLUMN_NAME_ZIP, currentPerson.getZip());
        values.put(FeedreaderContract.FeedEntry.COLUMN_NAME_EMAIL, currentPerson.getEmail());
        values.put(FeedreaderContract.FeedEntry.COLUMN_NAME_PHONE, currentPerson.getPhone());
        values.put(FeedreaderContract.FeedEntry.COLUMN_NAME_IMAGESTRING, currentPerson.getImageString());
        values.put(FeedreaderContract.FeedEntry.COLUMN_NAME_NATIONALITY, currentPerson.getNationality());

        long newRowId;
        newRowId = db.insert(
                FeedreaderContract.FeedEntry.TABLE_NAME,
                FeedreaderContract.FeedEntry.COLUMN_NAME_FIRST, //nullColumnHack
                values);
        ListActivity.showAlert(CardActivity.this, CardActivity.this.getString(R.string.dbEntry), "(id: " + newRowId + ")");
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
                        json.getJSONObject("name").getString("title"),
                        json.getJSONObject("name").getString("first"),
                        json.getJSONObject("name").getString("last"),
                        json.getString("gender"),
                        json.getJSONObject("location").getString("street"),
                        json.getJSONObject("location").getString("city"),
                        json.getJSONObject("location").getString("state"),
                        json.getJSONObject("location").getString("zip"),
                        json.getString("email"),
                        json.getString("phone"),
                        json.getJSONObject("picture").getString("medium"),
                        jObject.getString("nationality")
                );

                drawPerson(person);

                //Iterator<String> iterator = jObject.keys();
                //while(iterator.hasNext()) {
                //    String key = iterator.next();
                //}
            }
            catch (JSONException jsonEx) {
                System.out.println(jsonEx.toString());
                ListActivity.showAlert(CardActivity.this, "ERROR", "Unable to parse the JSON: " + jsonEx.getMessage());
            }
        }
    }

    private void drawPerson(Person person) {
        currentPerson = person;

        TextView name = (TextView) findViewById(R.id.lbl_name);
        TextView nationality = (TextView) findViewById(R.id.lbl_nationality);
        TextView street = (TextView) findViewById(R.id.lbl_street);
        TextView cityState = (TextView) findViewById(R.id.lbl_city_state);
        TextView email = (TextView) findViewById(R.id.lbl_email);
        TextView phone = (TextView) findViewById(R.id.lbl_phone);
        ImageView flag = (ImageView) findViewById(R.id.img_flag);

        name.setText(person.getTitle() + " " + person.getFirst() + " " + person.getLast());
        nationality.setText(person.getNationality());
        street.setText(person.getStreet());
        cityState.setText(person.getCity() + ", " + person.getState() + ", " + person.getNationality());
        email.setText(person.getEmail());
        phone.setText(person.getPhone());

        //Resources res = getResources();
        //flag.setImageDrawable(res.getDrawable(R.drawable.ag));

        Resources resources = CardActivity.this.getResources();
        final int resourceId = resources.getIdentifier(person.getNationality().toLowerCase(), "drawable", CardActivity.this.getPackageName());
        flag.setImageResource(resourceId);

        new DownloadImageTask((ImageView) findViewById(R.id.img_portrait), (RelativeLayout) findViewById(R.id.loadingPanel)).execute(person.getImageString());
    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView portrait;
        RelativeLayout loader;

        public DownloadImageTask(ImageView bmImage, RelativeLayout loader) {
            this.portrait = bmImage;
            this.loader = loader;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bm = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bm = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                //ListActivity.showAlert(CardActivity.this, "ERROR", "Unable to get image: " + e.getMessage());
                e.printStackTrace();
            }
            return bm;
        }

        protected void onPostExecute(Bitmap result) {
            portrait.setImageBitmap(result);
            loader.setVisibility(View.GONE);
        }
    }
}
