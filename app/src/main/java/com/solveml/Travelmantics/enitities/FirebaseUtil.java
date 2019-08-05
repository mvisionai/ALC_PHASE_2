package com.solveml.Travelmantics.enitities;

import android.app.Activity;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtil {

    public  static FirebaseDatabase firebaseDatabase;
    public  static DatabaseReference databaseReference;
    private  static  FirebaseUtil firebaseUtil;

    public  static FirebaseAuth firebaseAuth;
    private  static Activity callerActivity;
    public  static  FirebaseAuth.AuthStateListener authStateListener;

    public  static ArrayList<TravelEntity> mtravelEntities;
    private  static  final  int RC_SIGN_IN=128;
    public  static FirebaseStorage firebaseStorage;
    public  static StorageReference storageReference;

    private  FirebaseUtil(){


    }


    public  static  void  openFBReference(String ref,final  Activity mcallerActivity){

        if(firebaseUtil==null){

            firebaseUtil = new FirebaseUtil();
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseDatabase =FirebaseDatabase.getInstance();
            callerActivity =mcallerActivity;

            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                    if(firebaseAuth.getCurrentUser()==null){
                        FirebaseUtil.signIn();
                        Toast.makeText(callerActivity.getBaseContext(),"Welcome back", Toast.LENGTH_LONG).show();

                    }




                }
            };

            connectStorage();


        }
         mtravelEntities = new ArrayList<TravelEntity>();
         databaseReference =firebaseDatabase.getReference().child(ref);


    }


    public  static  void  attachListener(){

        firebaseAuth.addAuthStateListener(authStateListener);

    }


    public  static  void  detachListener(){
        firebaseAuth.removeAuthStateListener(authStateListener);

    }


    private  static  void signIn(){

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());


// Create and launch sign-in intent
        callerActivity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);

    }



    public  static  void  connectStorage(){

        firebaseStorage =FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference().child("deals_pictures");

    }

}
