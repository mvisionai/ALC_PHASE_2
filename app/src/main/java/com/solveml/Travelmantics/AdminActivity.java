package com.solveml.Travelmantics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.solveml.Travelmantics.enitities.FirebaseUtil;
import com.solveml.Travelmantics.enitities.TravelEntity;
import com.squareup.picasso.Picasso;


public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextPlace;
    private  EditText editTextMile;
    private  EditText editTextDescription;
    private ImageView imageViewImg;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button buttonSave;
    private  TravelEntity entity;
    private  static  final  int PICTURE_RESULT=42;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        editTextPlace=(EditText)  findViewById(R.id.txtViewPlace);
        editTextMile =(EditText) findViewById(R.id.txtViewMile);
        editTextDescription =(EditText) findViewById(R.id.textViewPlaceDes);
        imageViewImg = (ImageView) findViewById(R.id.imageViewSelected);
        buttonSave =(Button)  findViewById(R.id.buttonSaveDeal);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent imageIntent= new Intent(Intent.ACTION_GET_CONTENT);
                imageIntent.setType("image/jpeg");
                imageIntent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(imageIntent.createChooser(imageIntent,"Insert Picture"),PICTURE_RESULT);

            }
        });

        FirebaseUtil.openFBReference("traveldeals",this);
        firebaseDatabase =FirebaseUtil.firebaseDatabase;

        databaseReference = FirebaseUtil.databaseReference;

        Intent intent =getIntent();

        TravelEntity entity = (TravelEntity) intent.getSerializableExtra("Deal");

        if(entity==null){

            entity =  new TravelEntity();

        }

        this.entity=entity;

        editTextPlace.setText(entity.getPlace());
        editTextMile.setText(entity.getMiles());
        editTextDescription.setText(entity.getDescription());


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.app_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        switch (item.getItemId()){


            case R.id.saveTravel:

                saveDeals();

                cleanDeals();

                Toast.makeText(this,"Saved successfull",Toast.LENGTH_LONG).show();

                backAction();
                return true;

            case R.id.viewTravel:
                backAction();
                return  true;


            case R.id.deleteTravel:
                deleteDeal();
                backAction();
                return  true;

            case R.id.logOut:

                logOut();

                return  true;
            default:
                break;



        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (checkPermissionREAD_EXTERNAL_STORAGE(this)) {
            if (requestCode==PICTURE_RESULT && resultCode==RESULT_OK){

                imageViewImg.setVisibility(View.VISIBLE);

                final Uri imageUri= data.getData();
                //imageViewImg.setImageURI(imageUri);
               final StorageReference storageReference = FirebaseUtil.storageReference.child(imageUri.getLastPathSegment());
                storageReference.putFile(imageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        final Task<Uri> downloadUri=taskSnapshot.getMetadata().getReference().getDownloadUrl();


                        downloadUri.addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete( Task<Uri> task) {


                                 String url =downloadUri.getResult().toString();
                                 //Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();

                                   showImage(url);
                                   //Glide.with(getApplicationContext()).load(url).into(imageViewImg);
                                   entity.setImage(url);

                            }
                        });






                    }
                });

            }
        }

    }

    public   void  saveDeals(){

        entity.setPlace(editTextPlace.getText().toString().trim());
        entity.setMiles(editTextMile.getText().toString().trim());
        entity.setDescription(editTextDescription.getText().toString().trim());
        entity.setImage(entity.getImage());

        if (entity.getId()==null){
            databaseReference.push().setValue(entity);
        }else {

            databaseReference.child(entity.getId()).setValue(entity);
        }


        imageViewImg.setVisibility(View.GONE);




    }




    public  void  deleteDeal(){

        if(entity.getId()==null){

            Toast.makeText(getApplicationContext(),"Kindly save deal before deletetion",Toast.LENGTH_LONG).show();
            return;

        }else {

            databaseReference.child(entity.getId()).removeValue();
            Toast.makeText(getApplicationContext(),"Deal deleted",Toast.LENGTH_LONG).show();


        }

    }

    public  void  backAction(){

        Intent userIntent=new Intent(getApplicationContext(),UserActivity.class);
        startActivity(userIntent);
    }
    public  void cleanDeals(){

        editTextDescription.setText("");
        editTextMile.setText("");
        editTextPlace.setText("");
        editTextPlace.requestFocus();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){


            case  R.id.buttonSaveDeal:

               break;

            default:
                break;


        }
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    public  void showImage(String url){

        if (url !=null && url.isEmpty()==false){

            int width= Resources.getSystem().getDisplayMetrics().widthPixels;
            Picasso.get().load(url).resize(width,width*2/3).centerCrop().into(imageViewImg);


        }



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
}
