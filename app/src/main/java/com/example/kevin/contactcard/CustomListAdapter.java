package com.example.kevin.contactcard;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2015/11/13.
 */
public class CustomListAdapter extends ArrayAdapter<Person> {
    private Activity activity;
    private ArrayList<Person> people;

    public CustomListAdapter(Activity activity, ArrayList<Person> people) {
        super(activity, R.layout.contactcard_list_item, people);
        this.activity = activity;
        this.people = people;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.contactcard_list_item, null, true);

        TextView text1 = (TextView) rowView.findViewById(R.id.list_text1);
        TextView text2 = (TextView) rowView.findViewById(R.id.list_text2);

        text1.setText(people.get(position).getTitle() + " " + people.get(position).getFirst() + " " + people.get(position).getLast());
        text2.setText(people.get(position).getPhone() + " - " +people.get(position).getNationality());

        new CardActivity.DownloadImageTask((ImageView) rowView.findViewById(R.id.list_image)).execute(people.get(position).getImageString());

        return rowView;
    }
}
