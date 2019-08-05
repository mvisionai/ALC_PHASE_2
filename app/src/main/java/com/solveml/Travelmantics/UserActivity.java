package com.solveml.Travelmantics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.solveml.Travelmantics.adaptor.TravelsAdapter;
import com.solveml.Travelmantics.enitities.FirebaseUtil;
import com.solveml.Travelmantics.enitities.TravelEntity;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private ArrayList<TravelEntity> travelEntities;


    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);




    }



    private void setupAvailableTravels() {
        // COMPLETED (5) Remove the logging and the call to loadAllJournals, this is done in the ViewModel now
        // COMPLETED (6) Declare a ViewModel variable and initialize it by calling ViewModelProviders.of
/*
        ArrayList<TravelEntity> travelEntities = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            TravelEntity availableTravels = new TravelEntity("Ghana Kof", "I love the place", String.valueOf(200), "love");

            travelEntities.add(availableTravels);
        }*/




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.app_menu_user, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.addTravel:
                Intent adminIntent = new Intent(UserActivity.this, AdminActivity.class);
                startActivity(adminIntent);
                return true;

            case R.id.logOut:

                logOut();

                return  true;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

  public  void  logOut(){
      AuthUI.getInstance()
              .signOut(this)
              .addOnCompleteListener(new OnCompleteListener<Void>() {
                  public void onComplete(@NonNull Task<Void> task) {
                      FirebaseUtil.attachListener();
                  }
              });


      FirebaseUtil.detachListener();

  }
    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }


    @Override
    protected void onResume() {
        super.onResume();

        FirebaseUtil.openFBReference("traveldeals",this);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTravels);
        final TravelsAdapter travelsAdapter = new TravelsAdapter(this);

        recyclerView.setAdapter(travelsAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));

        FirebaseUtil.attachListener();
    }
}




