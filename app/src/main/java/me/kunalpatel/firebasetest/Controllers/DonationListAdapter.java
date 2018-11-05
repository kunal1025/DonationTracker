package me.kunalpatel.firebasetest.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.kunalpatel.firebasetest.Models.Donation;
import me.kunalpatel.firebasetest.R;

public class DonationListAdapter extends ArrayAdapter<Donation> {
    private Context mContext;
    private int mResource;
    private TextView nameTxt;
    private TextView valueTxt;
    private TextView descTxt;

    public DonationListAdapter(@NonNull Context context, int resource, ArrayList<Donation> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        String value = Double.toString(getItem(position).getValue());
        String desc = getItem(position).getShortDescription();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        nameTxt = (TextView) convertView.findViewById(R.id.donationNameText);
        valueTxt = (TextView) convertView.findViewById(R.id.donationValueText);
        descTxt = (TextView) convertView.findViewById(R.id.donationDescText);

        nameTxt.setText(name);
        valueTxt.setText(value);
        descTxt.setText(desc);

        return convertView;
    }
}
