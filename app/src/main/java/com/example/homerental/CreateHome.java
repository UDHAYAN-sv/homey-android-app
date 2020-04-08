package com.example.homerental;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class CreateHome extends AppCompatActivity {


    private EditText communty,add_flat,add_street,add_city,add_state,add_pin,no_bed,sq_ft,adv,rentt,des;
    private Button submit,img_bt1;
    private ImageView img_v1,img_v2,img_v3,img_v4;
    FirebaseDatabase database2;
    DatabaseReference ref2;
    ArrayList<Uri> ImageList=new ArrayList<Uri>();
    ArrayList<String>strlist=new ArrayList<String>();
    private static final int PICK_IMAGE=1;
    private Uri ImageUri1,ImageUri2,ImageUri3,ImageUri4;
    private int uploadcount=0;
    private Long mobile;
    private int unique_count;
    private String img1=null,img2=null,img3=null,img4=null;
    private int i=0;
    private String id;
    SharedPreferences sharedPreferences;
    HashMap<String,String> hashMap=new HashMap<>();

    private FusedLocationProviderClient fusedLocationClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;

    private Location currentLocation;

    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_home);

        communty=(EditText)findViewById(R.id.comm_name);
        add_flat=(EditText)findViewById(R.id.address_flat);
        add_street=(EditText)findViewById(R.id.address_street);
        add_city=(EditText)findViewById(R.id.address_city);
        add_state=(EditText)findViewById(R.id.address_state);
        add_pin=(EditText)findViewById(R.id.pin);
        no_bed=(EditText)findViewById(R.id.bed);
        sq_ft=(EditText)findViewById(R.id.sq_ft);
        adv=(EditText)findViewById(R.id.advance);
        rentt=(EditText)findViewById(R.id.rent);
        des=(EditText)findViewById(R.id.desc);
        submit=(Button)findViewById(R.id.sub);
        img_bt1=(Button) findViewById(R.id.imageButton1);
        img_v1=(ImageView)findViewById(R.id.imageView1);
        img_v2=(ImageView)findViewById(R.id.imageView2);
        img_v3=(ImageView)findViewById(R.id.imageView3);
        img_v4=(ImageView)findViewById(R.id.imageView4);

        sharedPreferences = getSharedPreferences("shareddetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        mobile=sharedPreferences.getLong("mobile",0);
        unique_count=sharedPreferences.getInt("homenumber",0);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation=locationResult.getLocations().get(0);
                getAddress();
            }
        };

        startLocationUpdates();

        img_bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent i1=new Intent(Intent.ACTION_GET_CONTENT);
                i1.setType("image/*");
                i1.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(i1,PICK_IMAGE);


            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                database2=FirebaseDatabase.getInstance();
                ref2=database2.getReference("Homes");
                id=ref2.push().getKey();

                final String community_name=communty.getText().toString();
                final String address_flat=add_flat.getText().toString();
                final String street=add_street.getText().toString();
                final String city=add_city.getText().toString();
                final String state=add_state.getText().toString();
                final String pincode=add_pin.getText().toString();
                final String no_of_bedrooms=no_bed.getText().toString();
                final String square_feet=sq_ft.getText().toString();
                final String advance=adv.getText().toString();
                final String rent=rentt.getText().toString();
                final String description=des.getText().toString();




                dbcreatehome obj2=new dbcreatehome(community_name,address_flat,street,city,state,pincode,no_of_bedrooms,square_feet,rent,advance,description,"","","","");

                ref2.child(String.valueOf(mobile)).child(String.valueOf(id)).setValue(obj2);
                ref2.child(String.valueOf(mobile)).child(String.valueOf(id)).child("unique_id").setValue(id);

                if (ImageUri1 != null) {
                    ImageList.add(ImageUri1);
                }
                if (ImageUri2 != null) {
                    ImageList.add(ImageUri2);
                }
                if (ImageUri3 != null) {
                    ImageList.add(ImageUri3);
                }
                if (ImageUri4 != null) {
                    ImageList.add(ImageUri4);
                }

                StorageReference ImageFolder= FirebaseStorage.getInstance().getReference().child(String.valueOf(mobile)).child(String.valueOf(id));


                for (uploadcount=0;uploadcount<ImageList.size()&&uploadcount<4;uploadcount++){
                    Uri IndividualImage=ImageList.get(uploadcount);
                    final StorageReference ImageName=ImageFolder.child(IndividualImage.getLastPathSegment());

                    ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url=String.valueOf(uri);
                                    StoreLink(url);


                                }
                            });
                        }
                    });



                }


                Intent intent2 = new Intent(CreateHome.this, HomePage.class);
                startActivity(intent2);


            }
        });


    }



    private void StoreLink(String url) {
        DatabaseReference db_ref=FirebaseDatabase.getInstance().getReference().child("Homes").child(String.valueOf(mobile)).child(String.valueOf(id));
        if(i==0) {
            db_ref.child("image1").setValue(url);
            i=1;

        }
        else if (i == 1) {
            db_ref.child("image2").setValue(url);
            i=2;

        }
        else if(i==2)
        {
            db_ref.child("image3").setValue(url);
            i=3;
        }
        else if(i==3){
            db_ref.child("image4").setValue(url);
            i=4;
        }

        Toast.makeText(this,"Home Created Successfully",Toast.LENGTH_SHORT).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK) {


            ImageUri1=null;
            ImageUri2=null;
            ImageUri3=null;
            ImageUri4=null;

            img_v1.setImageURI(Uri.parse(""));
            img_v2.setImageURI(Uri.parse(""));
            img_v3.setImageURI(Uri.parse(""));
            img_v4.setImageURI(Uri.parse(""));

            ClipData clipData=null;
            clipData= data.getClipData();

            if (clipData != null) {
                int count=clipData.getItemCount();
              for(int i=0;i<count;i++)
              {
                if(i==0) {
                    ImageUri1=data.getClipData().getItemAt(0).getUri();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),ImageUri1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    img_v1.setImageBitmap(bitmap);

                }
                if(i==1) {
                    ImageUri2=data.getClipData().getItemAt(1).getUri();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),ImageUri2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    img_v2.setImageBitmap(bitmap);
                }
                if(i==2) {
                    ImageUri3=data.getClipData().getItemAt(2).getUri();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),ImageUri3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    img_v3.setImageBitmap(bitmap);
                }
                if(i==3) {
                    ImageUri4=data.getClipData().getItemAt(3).getUri();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),ImageUri4);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    img_v4.setImageBitmap(bitmap);                }
            }
            }
            else
            {ImageUri1=data.getData();
            if(ImageUri1!=null)
            {
                Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(ImageUri1));
                img_v1.setImageBitmap(bitmap);
            }
            Toast.makeText(this,"please select multiple images",Toast.LENGTH_SHORT).show();

                }

        }


    }

    @SuppressWarnings("MissingPermission")
    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    null);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void getAddress() {

        if (!Geocoder.isPresent()) {
            Toast.makeText(CreateHome.this,
                    "Can't find current address, ",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        if (currentLocation == null) {

            Toast.makeText(CreateHome.this,
                    "Address not found, " ,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    currentLocation.getLatitude(),
                    currentLocation.getLongitude(),
                    1);
        } catch (Exception ioException) {
            Log.e("", "Error in getting address for the location");
            Log.d("Address", "Location null retrying");
            getAddress();
        }

        if (addresses == null || addresses.size()  == 0) {
            Toast.makeText(CreateHome.this,"No address found for the location",Toast.LENGTH_SHORT).show();


        } else {
            Address address = addresses.get(0);
            add_flat.setText(address.getFeatureName()+","+address.getThoroughfare());
            add_street.setText(address.getLocality());
            add_city.setText(address.getSubAdminArea());
            add_state.setText(address.getAdminArea());
            add_pin.setText(address.getPostalCode());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {
                    Toast.makeText(this, "Location permission not granted, " +
                                    "restart the app if you want the feature",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }


}



