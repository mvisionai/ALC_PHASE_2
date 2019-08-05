package com.solveml.Travelmantics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.util.PrimitiveIterator;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

  private EditText email;
  private  EditText user;
  private  EditText password;
  private Button buttonSigup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email =(EditText) findViewById(R.id.txtViewEmail);
        user =(EditText) findViewById(R.id.txtViewUser);
        password =(EditText) findViewById(R.id.textViewpassword);
        buttonSigup =(Button) findViewById(R.id.buttonSignup);

        buttonSigup.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        int viewID= view.getId();

        switch (viewID){

            case R.id.buttonSignup:

                Intent userIntent=new Intent(SignupActivity.this,UserActivity.class);
                startActivity(userIntent);
                break;

            default:
                break;


        }
    }
}
