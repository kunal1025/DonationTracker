package me.kunalpatel.firebasetest.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import me.kunalpatel.firebasetest.Models.Donation;
import me.kunalpatel.firebasetest.Models.DonationManager;
import me.kunalpatel.firebasetest.Models.FireBaseDB;
import me.kunalpatel.firebasetest.Models.Location;
import me.kunalpatel.firebasetest.Models.LocationManager;
import me.kunalpatel.firebasetest.Models.UserManager;
import me.kunalpatel.firebasetest.R;

public class Welcome extends AppCompatActivity {

    private FireBaseDB db;
    private UserManager userManager;
    private LocationManager locationManager;
    private DonationManager donationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        this.db = FireBaseDB.getInstance();
        this.userManager = UserManager.getInstance();
        this.locationManager = LocationManager.getInstance();
        this.donationManager = DonationManager.getInstance();

    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.loginBtn:
                Intent loginIntent = new Intent(this, Login.class);
                startActivity(loginIntent);
                break;
            case R.id.signupBtn:
                Intent registrationIntent = new Intent(this, Registration.class);
                startActivity(registrationIntent);
                Toast.makeText(Welcome.this, "Register", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
