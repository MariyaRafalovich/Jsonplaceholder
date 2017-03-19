package com.example.mariya.jsonplaceholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mariya on 3/13/2017.
 */

public class ItemAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private ArrayList<Contact> items;

    public ItemAdapter(Context context, int resource, ArrayList<Contact> items) {
        super(context, resource, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        if (rowView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item, parent, false);
        }

        // Set data into the view.
        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView email = (TextView) rowView.findViewById(R.id.email);
        TextView mobile = (TextView) rowView.findViewById(R.id.mobile);

        //get Item
        Contact item = this.items.get(position);

        name.setText(item.getName());
        email.setText(item.getEmail());


        //http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
        //Para obtener una img de internet y pintarla

        return rowView;
    }
}
