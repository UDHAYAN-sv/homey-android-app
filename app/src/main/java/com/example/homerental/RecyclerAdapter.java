package com.example.homerental;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    Context context;
    ArrayList<dbcreatehome> list;
    private Long mobil;


    public RecyclerAdapter(Context c, ArrayList<dbcreatehome> l,Long m){
        context=c;
        list=l;
        mobil=m;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final dbcreatehome list2=list.get(position);
        holder.txtcommunity_name.setText(list2.getCommunity_name());
        holder.txtaddress_flat.setText(list2.getAddress_flat());
        holder.txtstreet.setText(list2.getStreet());
        holder.txtcity.setText(list2.getCity());
        holder.txtstate.setText(list2.getState());
        holder.txtpincode.setText(list2.getPincode());
        holder.txtno_of_bedrooms.setText(list2.getNo_of_bedrooms());
        holder.txtsquare_ft.setText(list2.getSquare_ft());
        holder.txtrent.setText(list2.getRent());
        holder.txtadvance.setText(list2.getAdvance());
        holder.txtdescription.setText(list2.getDescription());
        if((list2.getImage1()!=null) && (!(list2.getImage1().isEmpty())))
        {
            Picasso.get().load(list2.getImage1()).into(holder.img1);
        }
        if((list2.getImage2()!=null) && (!(list2.getImage2().isEmpty())))
        {
            Picasso.get().load(list2.getImage2()).into(holder.img2);
        }
        if((list2.getImage3()!=null) && (!(list2.getImage3().isEmpty())))
        {
            Picasso.get().load(list2.getImage3()).into(holder.img3);
        }
        if((list2.getImage4()!=null) && (!(list2.getImage4().isEmpty())))
        {
            Picasso.get().load(list2.getImage4()).into(holder.img4);
        }

        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,list2.getUniqueid(),Toast.LENGTH_SHORT).show();
                String uid=list2.getUniqueid();
                Intent intent2 = new Intent(context, UpdateHome.class);
                intent2.putExtra("id",uid);
                context.startActivity(intent2);

            }
        });
        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
        holder.bdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                builder.setTitle("Delete");
                builder.setMessage("Are you sure to delete this Home");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String uid=list2.getUniqueid();

                        DatabaseReference delhome;
                        StorageReference delimg;


                        delhome= FirebaseDatabase.getInstance().getReference("Homes").child(String.valueOf(mobil)).child(uid);
                        String imag1=list2.getImage1();
                        String imag2=list2.getImage2();
                        String imag3=list2.getImage3();
                        String imag4=list2.getImage4();

                        delhome.removeValue();

                        if (!(imag1.isEmpty()) ){
                            delimg= FirebaseStorage.getInstance().getReferenceFromUrl(imag1);
                            delimg.delete();
                        }
                        if(!(imag2.isEmpty())){
                            delimg= FirebaseStorage.getInstance().getReferenceFromUrl(imag2);
                            delimg.delete();
                        }
                        if(!(imag3.isEmpty())){
                            delimg= FirebaseStorage.getInstance().getReferenceFromUrl(imag3);
                            delimg.delete();
                        }
                        if(!(imag4.isEmpty()))
                        {
                            delimg= FirebaseStorage.getInstance().getReferenceFromUrl(imag4);
                            delimg.delete();
                        }
                        Toast.makeText(context,"Home Deleted Successfully",Toast.LENGTH_SHORT).show();

                        Intent intent2 = new Intent(context, HomePage.class);
                        context.startActivity(intent2);


                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();




            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtcommunity_name,txtaddress_flat,txtstreet,txtcity,txtstate,txtpincode,txtno_of_bedrooms,txtsquare_ft,txtrent,txtadvance,txtdescription;
        ImageView img1,img2,img3,img4;
        Button b1,bdel;
        public ViewHolder(View itemView) {
            super(itemView);
            txtcommunity_name=(TextView)itemView.findViewById(R.id.com_name2);
            txtaddress_flat=(TextView)itemView.findViewById(R.id.address_flat2);
            txtstreet=(TextView)itemView.findViewById(R.id.address_street2);
            txtcity=(TextView)itemView.findViewById(R.id.address_city2);
            txtstate=(TextView)itemView.findViewById(R.id.address_state2);
            txtpincode=(TextView)itemView.findViewById(R.id.pin2);
            txtno_of_bedrooms=(TextView)itemView.findViewById(R.id.bed2);
            txtsquare_ft=(TextView)itemView.findViewById(R.id.sq_ft2);
            txtrent=(TextView)itemView.findViewById(R.id.rent2);
            txtadvance=(TextView)itemView.findViewById(R.id.advance2);
            txtdescription=(TextView)itemView.findViewById(R.id.desc2);
            img1=(ImageView)itemView.findViewById(R.id.imageView11);
            img2=(ImageView)itemView.findViewById(R.id.imageView12);
            img3=(ImageView)itemView.findViewById(R.id.imageView13);
            img4=(ImageView)itemView.findViewById(R.id.imageView14);
            b1=(Button)itemView.findViewById(R.id.upd);
            bdel=(Button)itemView.findViewById(R.id.delete);
        }
    }
}
