package me.kunalpatel.firebasetest.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import me.kunalpatel.firebasetest.Models.Location;
import me.kunalpatel.firebasetest.Models.LocationManager;
import me.kunalpatel.firebasetest.Models.LocationType;
import me.kunalpatel.firebasetest.Models.Role;
import me.kunalpatel.firebasetest.Models.User;
import me.kunalpatel.firebasetest.Models.UserManager;
import me.kunalpatel.firebasetest.R;

public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private Spinner roleSpinner;
    private Spinner locationSpinner;
    private TextView password;
    private TextView confirmPassword;

    private UserManager userManager;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        this.userManager = UserManager.getInstance();
        this.locationManager = LocationManager.getInstance();
        //readLocationData();

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        roleSpinner = findViewById(R.id.roleSpinner);
        locationSpinner = findViewById(R.id.locationSpinner);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);

        ArrayAdapter<Role> roleAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, Role.values());
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(roleAdapter);

        ArrayAdapter<Location> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locationManager.getLocations());
        locationSpinner.setAdapter(locationAdapter);

        roleSpinner.setOnItemSelectedListener(this);

        locationSpinner.setVisibility(View.GONE);
    }

    public void onRegisterClicked(View view) {
        Location location = (roleSpinner.getSelectedItem() == Role.LOCATIONEMPLOYEE) ?
                (Location) locationSpinner.getSelectedItem() : null;
        User user = new User(firstName.getText().toString(), lastName.getText().toString(),
                email.getText().toString(), password.getText().toString(),
                (Role) roleSpinner.getSelectedItem(), location);
        if (!userManager.addUser(email.getText().toString(), user)) {
            Toast.makeText(getApplicationContext(), "An account with that email already exists",Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        if (parent.getItemAtPosition(pos) == Role.LOCATIONEMPLOYEE) {
            locationSpinner.setVisibility(View.VISIBLE);
        } else {
            locationSpinner.setVisibility(View.GONE);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void readLocationData() {
        InputStream instream = getResources().openRawResource(R.raw.location_data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(instream, Charset.forName("UTF-8")));
        String ln;
        try {
            //skip header line
            reader.readLine();
            while ((ln = reader.readLine()) != null) {
                String[] tokens = ln.split(",");
                Location loc = new Location();
                loc.setKey(Integer.parseInt(tokens[0]));
                loc.setName(tokens[1]);
                loc.setLatitude(Double.parseDouble(tokens[2]));
                loc.setLongitude(Double.parseDouble(tokens[3]));
                loc.setStreetAddress(tokens[4]);
                loc.setCity(tokens[5]);
                loc.setState(tokens[6]);
                loc.setZip(tokens[7]);
                if (tokens[8].equals("Drop Off")) loc.setType(LocationType.DROPOFF);
                else if (tokens[8].equals("Store")) loc.setType(LocationType.STORE);
                else if (tokens[8].equals("Warehouse")) loc.setType(LocationType.WAREHOUSE);
                loc.setPhoneNumber(tokens[9]);
                loc.setWebsite(tokens[10]);
                locationManager.addLocation(loc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
