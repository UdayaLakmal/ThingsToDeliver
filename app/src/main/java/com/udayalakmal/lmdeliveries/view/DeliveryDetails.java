package com.udayalakmal.lmdeliveries.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.udayalakmal.lmdeliveries.R;
import com.udayalakmal.lmdeliveries.model.Deliveries;

public class DeliveryDetails extends AppCompatActivity {


   // public static String mapURL = "https://maps.googleapis.com/maps/api/staticmap?autoscale=1&size=600x300&maptype=roadmap&format=png&visual_refresh=true&markers=size:mid|color:0xff0000|label:O|40.718217,-73.998284";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        Deliveries deliveries = (Deliveries) getIntent().getSerializableExtra("data");


        ImageView ivMap = findViewById(R.id.mapimageview);
        String mapurl = "https://maps.googleapis.com/maps/api/staticmap?autoscale=1&size="+width+"x300&maptype=roadmap&format=png&visual_refresh=true&markers=size:mid|color:0xff0000|label:O|"+deliveries.getLocation().getLat()+","+deliveries.getLocation().getLng();

        Log.w("DeliveryDetails", mapurl);
        try {
            Glide.with(this).load(mapurl).into(ivMap);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Set Profile data
        TextView description = findViewById(R.id.description);
        ImageView imageView = findViewById(R.id.imageView);
        TextView location = findViewById(R.id.location_text);


        description.setText(deliveries.getDescription());
        location.setText("Address :"+deliveries.getLocation().getAddress());

        try {
            Glide.with(this).load(deliveries.getImageUrl()).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
