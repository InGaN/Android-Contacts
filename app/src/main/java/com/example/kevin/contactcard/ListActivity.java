package com.example.kevin.contactcard;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {
    FeedreaderDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        TextView listText = (TextView) findViewById(R.id.lbl_teste);
        dbHelper = new FeedreaderDbHelper(ListActivity.this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Define a selection that specifies which columns from the database
        // you will actually use after this query.
        String[] select = {
                FeedreaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID,
                FeedreaderContract.FeedEntry.COLUMN_NAME_FIRST,
                FeedreaderContract.FeedEntry.COLUMN_NAME_LAST
        };
        String sortOrder = FeedreaderContract.FeedEntry.COLUMN_NAME_LAST + " DESC";

        Cursor cursor = db.query(
                FeedreaderContract.FeedEntry.TABLE_NAME,                    // The table to query
                select,                                                     // The columns to return
                FeedreaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID + "=?",   // The columns for the WHERE clause
                new String[] {"1"},                                         // The values for the WHERE clause
                null,                                                       // don't group the rows
                null,                                                       // don't filter by row groups
                sortOrder                                                   // The sort order
        );
        //Cursor c = db.rawQuery(select, null);  // shorter version


        if (cursor != null) {
            cursor.moveToFirst();
            listText.setText(cursor.getString(1) + " " + cursor.getString(2));
        }
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
}
