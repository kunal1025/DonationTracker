package me.kunalpatel.firebasetest.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import me.kunalpatel.firebasetest.Models.Donation;
import me.kunalpatel.firebasetest.Models.DonationManager;
import me.kunalpatel.firebasetest.Models.Location;
import me.kunalpatel.firebasetest.Models.Role;
import me.kunalpatel.firebasetest.Models.UserManager;
import me.kunalpatel.firebasetest.R;

public class PageLocation extends AppCompatActivity {
    private Button addItem;
    private Button locationDetails;
    private Location location;
    ArrayList<Donation> donationsAtLocation = new ArrayList<>();
    ArrayAdapter<Donation> adapter;
    ListView donationListView;

    private UserManager userManager;
    private DonationManager donationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_location);
        this.userManager = UserManager.getInstance();



        Intent intent = getIntent();
        location = (Location) intent.getSerializableExtra("location");
        
        this.donationManager = DonationManager.getInstance();
        for (Donation donation : donationManager.getDonations()) {
            if (donation.getLocation().getKey() == location.getKey()) {
                donationsAtLocation.add(donation);
            }
        }
        donationListView = findViewById(R.id.donationList);
        DonationListAdapter locationAdapter = new DonationListAdapter(this, R.layout.layout_donationitem, donationsAtLocation);
        donationListView.setAdapter(locationAdapter);

        donationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PageLocation.this, DonationItemDetail.class);
                intent.putExtra("donation", donationManager.getDonations().get(position));
                startActivity(intent);
            }
        });
        

        addItem = findViewById(R.id.addItemBtn);
        if ((userManager.getCurrentUser().getRole() == Role.LOCATIONEMPLOYEE && userManager.getCurrentUser().getLocation().getKey() == location.getKey())
                || userManager.getCurrentUser().getRole() == Role.ADMIN) {
            addItem.setVisibility(View.VISIBLE);
            addItem.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent addItemIntent = new Intent(PageLocation.this, AddDonation.class);
                    startActivity(addItemIntent);
                }
            });
        } else {
            addItem.setVisibility(View.GONE);
        }

        locationDetails = findViewById(R.id.locationDetailsBtn);
        locationDetails.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent detailsIntent = new Intent(PageLocation.this, LocationItemDetails.class);
                detailsIntent.putExtra("location", location);
                startActivity(detailsIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(this, LocationList.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(a);
    }

}
