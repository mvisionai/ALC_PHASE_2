package com.solveml.Travelmantics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.solveml.Travelmantics.enitities.FirebaseUtil;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button buttonEmail;
    private  Button buttonGoogle;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUtil.openFBReference("traveldeals",this);

        //buttonEmail =(Button) findViewById(R.id.buttonEmail);
       // buttonGoogle =(Button) findViewById(R.id.buttonGoogle);

       // buttonEmail.setOnClickListener(this);
        //buttonGoogle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();

        switch (viewId){

            case 1: //R.id.buttonEmail:

                Intent signupIntent=new Intent(MainActivity.this,SignupActivity.class);
                startActivity(signupIntent);
                break;

            case 2:// R.id.buttonGoogle:

                Intent userIntent=new Intent(MainActivity.this,UserActivity.class);
                startActivity(userIntent);
                break;
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }


    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtil.attachListener();
    }
}
