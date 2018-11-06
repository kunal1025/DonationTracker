package me.kunalpatel.firebasetest.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import me.kunalpatel.firebasetest.Models.FireBaseDB;
import me.kunalpatel.firebasetest.Models.Location;
import me.kunalpatel.firebasetest.Models.LocationManager;
import me.kunalpatel.firebasetest.Models.UserManager;
import me.kunalpatel.firebasetest.R;

public class LocationList extends AppCompatActivity {

    private LocationManager locationManager;
    private UserManager userManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.locationManager = LocationManager.getInstance();
        this.userManager = UserManager.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationlist);
        ListView locationListView = findViewById(R.id.locationList);
        LocationListAdapter locationAdapter = new LocationListAdapter(this, R.layout.layout_locationitem, (ArrayList<Location>) locationManager.getLocations());
        locationListView.setAdapter(locationAdapter);

        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("FirebaseDB", "List position: " + position);
                Intent intent = new Intent(LocationList.this, PageLocation.class);
                intent.putExtra("location", locationManager.getLocations().get(position));
                startActivity(intent);
            }
        });

    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.logoutBtn:
                Intent logoutIntent = new Intent(this, Welcome.class);
                startActivity(logoutIntent);
                userManager.clearCurrentUser();
                Toast.makeText(getApplicationContext(), "Logout Successful!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.search:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.map:
                Intent mapIntent = new Intent(this, MapsActivity.class);
                startActivity(mapIntent);
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }


}
