package com.example.homerental;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UpdateHome extends AppCompatActivity {
    private String intentid;
    SharedPreferences sharedPreferences;
    private Long mob;
    DatabaseReference updatehome_db2;
    private Button sub;
    private EditText communty2, add_flat2, add_street2, add_city2, add_state2, add_pin2, no_bed2, sq_ft2, adv2, rent2, des2;
    private ImageView i1, i2, i3, i4;
    private String a2, b2, c2, d2;
    private final int CODE_IMG_GALLERY1 = 1;
    private final int CODE_IMG_GALLERY2 = 2;
    private final int CODE_IMG_GALLERY3 = 3;
    private final int CODE_IMG_GALLERY4 = 4;
    DatabaseReference delhome;
    StorageReference delimg,newimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_home);

        Intent ii = getIntent();
        intentid = ii.getStringExtra("id");

        delhome = FirebaseDatabase.getInstance().getReference("Homes").child(String.valueOf(mob)).child(intentid);
        sharedPreferences = getSharedPreferences("shareddetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        mob = sharedPreferences.getLong("mobile", 0);

        communty2 = (EditText) findViewById(R.id.upd_comm_name);
        add_flat2 = (EditText) findViewById(R.id.upd_address_flat);
        add_street2 = (EditText) findViewById(R.id.upd_address_street);
        add_city2 = (EditText) findViewById(R.id.upd_address_city);
        add_state2 = (EditText) findViewById(R.id.upd_address_state);
        add_pin2 = (EditText) findViewById(R.id.upd_pin);
        no_bed2 = (EditText) findViewById(R.id.upd_bed);
        sq_ft2 = (EditText) findViewById(R.id.upd_sq_ft);
        adv2 = (EditText) findViewById(R.id.upd_advance);
        rent2 = (EditText) findViewById(R.id.upd_rent);
        des2 = (EditText) findViewById(R.id.upd_desc);
        i1 = (ImageView) findViewById(R.id.upd_imageView1);
        i2 = (ImageView) findViewById(R.id.upd_imageView2);
        i3 = (ImageView) findViewById(R.id.upd_imageView3);
        i4 = (ImageView) findViewById(R.id.upd_imageView4);
        sub = (Button) findViewById(R.id.btnupdatehome);

        updatehome_db2 = FirebaseDatabase.getInstance().getReference("Homes").child(String.valueOf(mob)).child(intentid);

        updatehome_db2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String cname2 = dataSnapshot.child("community_name").getValue().toString();
                String aflat2 = dataSnapshot.child("address_flat").getValue().toString();
                String astreet2 = dataSnapshot.child("street").getValue().toString();
                String acity2 = dataSnapshot.child("city").getValue().toString();
                String astate2 = dataSnapshot.child("state").getValue().toString();
                String apin2 = dataSnapshot.child("pincode").getValue().toString();
                String nbed2 = dataSnapshot.child("no_of_bedrooms").getValue().toString();
                String sqft2 = dataSnapshot.child("square_ft").getValue().toString();
                String ren2 = dataSnapshot.child("rent").getValue().toString();
                String advance2 = dataSnapshot.child("advance").getValue().toString();
                String description2 = dataSnapshot.child("description").getValue().toString();
                a2 = dataSnapshot.child("image1").getValue().toString();
                b2 = dataSnapshot.child("image2").getValue().toString();
                c2 = dataSnapshot.child("image3").getValue().toString();
                d2 = dataSnapshot.child("image4").getValue().toString();
                String id2 = dataSnapshot.child("unique_id").getValue().toString();

                communty2.setText(cname2);
                add_flat2.setText(aflat2);
                add_street2.setText(astreet2);
                add_city2.setText(acity2);
                add_state2.setText(astate2);
                add_pin2.setText(apin2);
                no_bed2.setText(nbed2);
                sq_ft2.setText(sqft2);
                adv2.setText(advance2);
                rent2.setText(ren2);
                des2.setText(description2);
                if (!a2.isEmpty()) {
                    Picasso.get().load(a2).into(i1);
                }
                if (!b2.isEmpty()) {
                    Picasso.get().load(b2).into(i2);
                }
                if (!c2.isEmpty()) {
                    Picasso.get().load(c2).into(i3);
                }
                if (!d2.isEmpty()) {
                    Picasso.get().load(d2).into(i4);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        final AlertDialog.Builder builder = new AlertDialog.Builder(UpdateHome.this);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String options[] = {"Change Image", "Delete Image"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            StorageReference delimg;
                            if (!(a2.isEmpty())) {
                                delimg = FirebaseStorage.getInstance().getReferenceFromUrl(a2);
                                delimg.delete();
                            }
                            startActivityForResult(Intent.createChooser(new Intent().setAction(Intent.ACTION_GET_CONTENT).setType("image/*"), "Select Image"), CODE_IMG_GALLERY1);
                        }
                        if (which == 1) {
                            delhome = FirebaseDatabase.getInstance().getReference("Homes").child(String.valueOf(mob)).child(intentid);
                            String delvalue = "";
                            delhome.child("image1").setValue(delvalue);

                            if (!(a2.isEmpty())) {
                                delimg = FirebaseStorage.getInstance().getReferenceFromUrl(a2);
                                delimg.delete();
                            }
                            Uri u = Uri.parse("");
                            i1.setImageURI(u);
                        }
                    }
                });
                builder.create().show();
            }

        });

        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String options[] = {"Change Image", "Delete Image"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            StorageReference delimg;
                            if (!(b2.isEmpty())) {
                                delimg = FirebaseStorage.getInstance().getReferenceFromUrl(b2);
                                delimg.delete();
                            }
                            startActivityForResult(Intent.createChooser(new Intent().setAction(Intent.ACTION_GET_CONTENT).setType("image/*"), "Select Image"), CODE_IMG_GALLERY2);
                        }
                        if (which == 1) {
                            delhome = FirebaseDatabase.getInstance().getReference("Homes").child(String.valueOf(mob)).child(intentid);
                            String delvalue = "";
                            delhome.child("image2").setValue(delvalue);

                            if (!(b2.isEmpty())) {
                                delimg = FirebaseStorage.getInstance().getReferenceFromUrl(b2);
                                delimg.delete();
                            }
                            Uri u = Uri.parse("");
                            i2.setImageURI(u);
                        }
                    }
                });
                builder.create().show();
            }

        });

        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String options[] = {"Change Image", "Delete Image"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            StorageReference delimg;
                            if (!(c2.isEmpty())) {
                                delimg = FirebaseStorage.getInstance().getReferenceFromUrl(c2);
                                delimg.delete();
                            }
                            startActivityForResult(Intent.createChooser(new Intent().setAction(Intent.ACTION_GET_CONTENT).setType("image/*"), "Select Image"), CODE_IMG_GALLERY3);
                        }
                        if (which == 1) {
                            delhome = FirebaseDatabase.getInstance().getReference("Homes").child(String.valueOf(mob)).child(intentid);
                            String delvalue = "";
                            delhome.child("image3").setValue(delvalue);
                            if (!(a2.isEmpty())) {
                                delimg = FirebaseStorage.getInstance().getReferenceFromUrl(c2);
                                delimg.delete();
                            }
                            Uri u = Uri.parse("");
                            i3.setImageURI(u);
                        }
                    }
                });
                builder.create().show();
            }

        });

        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String options[] = {"Change Image", "Delete Image"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            StorageReference delimg;
                            if (!(d2.isEmpty())) {
                                delimg = FirebaseStorage.getInstance().getReferenceFromUrl(a2);
                                delimg.delete();
                            }
                            startActivityForResult(Intent.createChooser(new Intent().setAction(Intent.ACTION_GET_CONTENT).setType("image/*"), "Select Image"), CODE_IMG_GALLERY4);
                        }
                        if (which == 1) {
                            delhome = FirebaseDatabase.getInstance().getReference("Homes").child(String.valueOf(mob)).child(intentid);
                            String delvalue = "";
                            delhome.child("image4").setValue(delvalue);

                            if (!(d2.isEmpty())) {
                                delimg = FirebaseStorage.getInstance().getReferenceFromUrl(d2);
                                delimg.delete();
                            }
                            Uri u = Uri.parse("");
                            i4.setImageURI(u);
                        }
                    }
                });
                builder.create().show();
            }

        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cname2 = communty2.getText().toString();
                String aflat2 = add_flat2.getText().toString();
                String astreet2 = add_street2.getText().toString();
                String acity2 = add_city2.getText().toString();
                String astate2 = add_state2.getText().toString();
                String apin2 = add_pin2.getText().toString();
                String nbed2 = no_bed2.getText().toString();
                String sqft2 = sq_ft2.getText().toString();
                String ren2 = adv2.getText().toString();
                String advance2 = rent2.getText().toString();
                String description2 = des2.getText().toString();

                updatehome_db2.child("community_name").setValue(cname2);
                updatehome_db2.child("address_flat").setValue(aflat2);
                updatehome_db2.child("street").setValue(astreet2);
                updatehome_db2.child("city").setValue(acity2);
                updatehome_db2.child("state").setValue(astate2);
                updatehome_db2.child("pincode").setValue(apin2);
                updatehome_db2.child("no_of_bedrooms").setValue(nbed2);
                updatehome_db2.child("square_ft").setValue(sqft2);
                updatehome_db2.child("rent").setValue(ren2);
                updatehome_db2.child("advance").setValue(advance2);
                updatehome_db2.child("description").setValue(description2);

                Intent intent2 = new Intent(UpdateHome.this, HomePage.class);
                startActivity(intent2);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_IMG_GALLERY1 && resultCode == RESULT_OK)
        {
            Uri imguri = data.getData();
            if (imguri != null) {
                i1.setImageURI(imguri);
            }
            newimg=FirebaseStorage.getInstance().getReference().child(String.valueOf(mob)).child(intentid);
            final StorageReference ImageName=newimg.child(imguri.getLastPathSegment());
            ImageName.getDownloadUrl();
             ImageName.putFile(imguri);
            ImageName.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url=String.valueOf(uri);
                            delhome = FirebaseDatabase.getInstance().getReference("Homes").child(String.valueOf(mob)).child(intentid);
                            delhome.child("image1").setValue(url);
                        }
                    });
                }
            });
        }

        if (requestCode == CODE_IMG_GALLERY2 && resultCode == RESULT_OK)
        {
            Uri imguri = data.getData();
            if (imguri != null) {
                i2.setImageURI(imguri);

            }
            newimg=FirebaseStorage.getInstance().getReference().child(String.valueOf(mob)).child(intentid);
            final StorageReference ImageName=newimg.child(imguri.getLastPathSegment());
            ImageName.getDownloadUrl();
            ImageName.putFile(imguri);
            ImageName.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url=String.valueOf(uri);
                            delhome = FirebaseDatabase.getInstance().getReference("Homes").child(String.valueOf(mob)).child(intentid);
                            delhome.child("image2").setValue(url);
                        }
                    });
                }
            });

        }
        if (requestCode == CODE_IMG_GALLERY3 && resultCode == RESULT_OK)
        {
            Uri imguri = data.getData();
            if (imguri != null) {
                i1.setImageURI(imguri);

            }
            newimg=FirebaseStorage.getInstance().getReference().child(String.valueOf(mob)).child(intentid);
            final StorageReference ImageName=newimg.child(imguri.getLastPathSegment());
            ImageName.getDownloadUrl();
            ImageName.putFile(imguri);
            ImageName.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url=String.valueOf(uri);
                            delhome = FirebaseDatabase.getInstance().getReference("Homes").child(String.valueOf(mob)).child(intentid);
                            delhome.child("image3").setValue(url);
                        }
                    });
                }
            });

        }   if (requestCode == CODE_IMG_GALLERY4 && resultCode == RESULT_OK)
        {
            Uri imguri = data.getData();
            if (imguri != null) {
                i1.setImageURI(imguri);

            }
            newimg=FirebaseStorage.getInstance().getReference().child(String.valueOf(mob)).child(intentid);
            final StorageReference ImageName=newimg.child(imguri.getLastPathSegment());
            ImageName.getDownloadUrl();
            ImageName.putFile(imguri);
            ImageName.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url=String.valueOf(uri);
                            delhome = FirebaseDatabase.getInstance().getReference("Homes").child(String.valueOf(mob)).child(intentid);
                            delhome.child("image4").setValue(url);
                        }
                    });
                }
            });
        }
    }
}
