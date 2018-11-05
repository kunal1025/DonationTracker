package me.kunalpatel.firebasetest.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import me.kunalpatel.firebasetest.Models.Location;
import me.kunalpatel.firebasetest.R;

public class LocationItemDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_item_details);

        TextView locationTitle = findViewById(R.id.locationTitleTxt);
        TextView locationType = findViewById(R.id.typeTxt);
        TextView locationCoords = findViewById(R.id.coordsTxt);
        TextView locationAddress = findViewById(R.id.streetAddressTxt);
        TextView locationPhone = findViewById(R.id.phoneTxt);

        TextView locationWebsite = findViewById(R.id.websiteTxt);

        Intent intent = getIntent();
        Location location = (Location) intent.getSerializableExtra("location");

        locationTitle.setText(location.getName());
        locationType.setText(location.getType().toString());
        String coords = Double.toString(location.getLatitude()) + ", "
                + Double.toString(location.getLatitude());
        locationCoords.setText(coords);
        locationAddress.setText(location.getStreetAddress());
        locationPhone.setText(intent.getStringExtra(location.getPhoneNumber()));
        locationWebsite.setText(intent.getStringExtra(location.getWebsite()));
    }
}
