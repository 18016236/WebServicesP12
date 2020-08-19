package com.example.webservicesp12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class IncidentAdapter extends ArrayAdapter<Incident> {

    public static final String LOG_TAG = IncidentAdapter.class.getName();

    private ArrayList<Incident> alContact;
    private Context context;

    public IncidentAdapter(@NonNull Context context, int resource,ArrayList<Incident>objects) {
        super(context, resource,objects);
        alContact = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.incident_row,parent,false);

        TextView tvName = (TextView)rowView.findViewById(R.id.tvName);
        TextView tvDetails = (TextView)rowView.findViewById(R.id.tvDetails);

        Incident contact = alContact.get(position);

        tvName.setText(contact.getType());
        tvDetails.setText(contact.getDate()+""+contact.getMessage());

        return rowView;
    }

}
