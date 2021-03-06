package com.example.heenapatel.m4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import java.util.ArrayList;

import java.util.List;

public class ShelterDetails extends AppCompatActivity {


    public int userID, shelterID;
    public int familyAvailable;
    public int apartmentAvailable;
    public int roomAvailable;
    public String shelterName;



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        userID = getIntent().getIntExtra("UserID", 99999);
        shelterID = getIntent().getIntExtra("ShelterID", 10000);
        Log.d("UserID", "" + userID);

        Credentials cred = (Credentials) getApplicationContext();
        final User user = cred.get(userID);

        setContentView(R.layout.activity_shelterdetails);
        Button reserve = (Button) findViewById(R.id.reserveButton);
        Button cancelReserve = (Button) findViewById(R.id.cancelReserveButton);

        final int userID = getIntent().getIntExtra("userID", 0);
        final String s = getIntent().getStringExtra("shelterName");
        String s1 = getIntent().getStringExtra("Address");
        String s2 = getIntent().getStringExtra("Capacity");
        final int[] capacityArray = getIntent().getIntArrayExtra("capacityArray");
        boolean s3 = getIntent().getBooleanExtra("Maleok", false);
        boolean s4 = getIntent().getBooleanExtra("Femaleok", false);
        boolean reserved = false;
        TextView shelterInfo = (TextView) findViewById(R.id.shelterText);
        List<DataItem> shelterHolder = SimpleModel.INSTANCE.getItems();
        shelterInfo.setText(s + "\n Address: " + s1 + "\n Capacity: " + s2 + "\n Male: " + s3 + "\n Female: " + s4);
        if (user.hasReservation) {
            cancelReserve.setVisibility(View.VISIBLE);
            reserve.setVisibility(View.INVISIBLE);
        } else {
            cancelReserve.setVisibility(View.INVISIBLE);
            reserve.setVisibility(View.VISIBLE);
        }
        reserve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newIntent = new Intent(ShelterDetails.this, ReserveBedActivity.class);
                newIntent.putExtra("shelterName", s);
                newIntent.putExtra("capacityArray", capacityArray);
                newIntent.putExtra("UserID", userID);
                startActivity(newIntent);
            }
        });
        cancelReserve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                user.hasReservation = false;
                List<DataItem> sheltersList = SimpleModel.INSTANCE.getItems();
                DataItem shelter = sheltersList.get(shelterID);
                int[] capacity = new int[1];
                capacity[0] = shelter.getIntCapacity()[0] + user.bookedNumber;
                shelter.setIntCapacity(capacity);
                Intent newIntent = new Intent(ShelterDetails.this, MainActivity.class);
                newIntent.putExtra("UserID", userID);
                startActivity(newIntent);
            }
        });
    }
}
