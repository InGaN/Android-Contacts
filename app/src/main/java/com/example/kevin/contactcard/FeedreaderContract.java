package com.example.kevin.contactcard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by kevin on 2015/11/11.
 */
public final class FeedreaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedreaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "people";
        public static final String COLUMN_NAME_ENTRY_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_FIRST = "first";
        public static final String COLUMN_NAME_LAST = "last";
        public static final String COLUMN_NAME_GENDER = "gender";
        public static final String COLUMN_NAME_STREET = "street";
        public static final String COLUMN_NAME_CITY = "city";
        public static final String COLUMN_NAME_STATE = "state";
        public static final String COLUMN_NAME_ZIP = "zip";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_IMAGESTRING = "imageString";
        public static final String COLUMN_NAME_NATIONALITY = "nationality";

        private static final String TYPE_TEXT = " TEXT";
        private static final String TYPE_VARCHAR = " varchar(255)";

        public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                FeedEntry.COLUMN_NAME_ENTRY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                FeedEntry.COLUMN_NAME_TITLE + TYPE_VARCHAR + "," +
                FeedEntry.COLUMN_NAME_FIRST + TYPE_VARCHAR + ", " +
                FeedEntry.COLUMN_NAME_LAST + TYPE_VARCHAR + ", " +
                FeedEntry.COLUMN_NAME_GENDER + TYPE_VARCHAR + ", " +
                FeedEntry.COLUMN_NAME_STREET + TYPE_VARCHAR + ", " +
                FeedEntry.COLUMN_NAME_CITY + TYPE_VARCHAR + ", " +
                FeedEntry.COLUMN_NAME_STATE + TYPE_VARCHAR + ", " +
                FeedEntry.COLUMN_NAME_ZIP + TYPE_VARCHAR + ", " +
                FeedEntry.COLUMN_NAME_EMAIL + TYPE_VARCHAR + ", " +
                FeedEntry.COLUMN_NAME_PHONE + TYPE_VARCHAR + ", " +
                FeedEntry.COLUMN_NAME_IMAGESTRING + TYPE_VARCHAR + ", "+
                FeedEntry.COLUMN_NAME_NATIONALITY + TYPE_VARCHAR +
                " )";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    }
}

