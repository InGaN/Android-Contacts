package com.example.kevin.contactcard;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    FeedreaderDbHelper dbHelper;
    ListView idList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        fillListWithPeople(queryDatabase());
    }

    @Override
    public void onResume() {
        super.onResume();
        setContentView(R.layout.activity_list);
        fillListWithPeople(queryDatabase());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
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

    private ArrayList<Person> queryDatabase() {
        dbHelper = new FeedreaderDbHelper(ListActivity.this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Define a selection that specifies which columns from the database
        // you will actually use after this query.
        String[] select = {
                FeedreaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID,
                FeedreaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedreaderContract.FeedEntry.COLUMN_NAME_FIRST,
                FeedreaderContract.FeedEntry.COLUMN_NAME_LAST,
                FeedreaderContract.FeedEntry.COLUMN_NAME_GENDER,
                FeedreaderContract.FeedEntry.COLUMN_NAME_STREET,
                FeedreaderContract.FeedEntry.COLUMN_NAME_CITY,
                FeedreaderContract.FeedEntry.COLUMN_NAME_STATE,
                FeedreaderContract.FeedEntry.COLUMN_NAME_ZIP,
                FeedreaderContract.FeedEntry.COLUMN_NAME_EMAIL,
                FeedreaderContract.FeedEntry.COLUMN_NAME_PHONE,
                FeedreaderContract.FeedEntry.COLUMN_NAME_IMAGESTRING,
                FeedreaderContract.FeedEntry.COLUMN_NAME_NATIONALITY
        };
        String sortOrder = FeedreaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID + " DESC";

        Cursor cursor_old = db.query(
                FeedreaderContract.FeedEntry.TABLE_NAME,                    // The table to query
                select,                                                     // The columns to return
                FeedreaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID + "=?",   // The columns for the WHERE clause
                new String[] {"1"},                                         // The values for the WHERE clause
                null,                                                       // don't group the rows
                null,                                                       // don't filter by row groups
                sortOrder                                                   // The sort order
        );
        //Cursor c = db.rawQuery(select, null);  // shorter version

        Cursor cursor = db.query(
                FeedreaderContract.FeedEntry.TABLE_NAME,                    // The table to query
                select,                                                     // The columns to return
                null,                                                       // The columns for the WHERE clause
                null,                                                       // The values for the WHERE clause
                null,                                                       // don't group the rows
                null,                                                       // don't filter by row groups
                sortOrder                                                   // The sort order
        );

        if (cursor != null) {
            ArrayList<Person> people = new ArrayList<>();
            cursor.moveToFirst();

            for (int x = 0; x < cursor.getCount(); x++) {
                people.add(new Person(
                                Integer.parseInt(cursor.getString(0)),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7),
                                cursor.getString(8),
                                cursor.getString(9),
                                cursor.getString(10),
                                cursor.getString(11),
                                cursor.getString(12))
                );
                cursor.moveToNext();
            }
            return people;
        }
        return null;
    }

    private void fillListWithPeople(final ArrayList<Person> people) {
        CustomListAdapter customListAdapter = new CustomListAdapter(this, people);

        idList = (ListView)findViewById(R.id.listviewMain);
        idList.setAdapter(customListAdapter);

        idList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callCardActivity(people.get(position));
            }
        });
    }

    private void callCardActivity(Person person) {
        Intent intent = new Intent(this, CardActivity.class);
        intent.putExtra("incoming_person", person);
        startActivity(intent);
    }
}
