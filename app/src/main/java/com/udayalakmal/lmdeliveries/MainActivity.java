package com.udayalakmal.lmdeliveries;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.udayalakmal.lmdeliveries.model.Deliveries;
import com.udayalakmal.lmdeliveries.util.Constants;
import com.udayalakmal.lmdeliveries.util.DelevieryAPI;
import com.udayalakmal.lmdeliveries.util.RetrofitHelper;
import com.udayalakmal.lmdeliveries.view.DeliveryDataAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    DeliveryDataAdapter adapter;
    private int offset =0;
    private ArrayList<Deliveries> arrayList=  new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setSupportActionBar(toolbar);



        initializeElement();
        getAPIData(Integer.toString(offset));

    }


    private void initializeElement(){
        recyclerView = findViewById(R.id.rv_deliveries);

    }


    private void getAPIData(String offset){


        try {
            progressDoalog = new ProgressDialog(MainActivity.this);
            progressDoalog.setMessage("Loading....");
            progressDoalog.show();

            Map<String,String> data = new HashMap<>();
            data.put("offset", offset);
            data.put("limit", Integer.toString(Constants.limit));

            DelevieryAPI service = new RetrofitHelper().getRetrofit(getApplicationContext()).create(DelevieryAPI.class);

            Call<List<Deliveries>> call = service.getDeliveries(data);

            call.enqueue(new Callback<List<Deliveries>>() {
                @Override
                public void onResponse(Call<List<Deliveries>> call, Response<List<Deliveries>> response) {

                    try {
                        progressDoalog.dismiss();
                        loadData((ArrayList<Deliveries>) response.body());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<List<Deliveries>> call, Throwable t) {
                    try {
                        progressDoalog.dismiss();
                        Toast.makeText(MainActivity.this, "Data not sync correctly...Please try later!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    private void loadData(ArrayList<Deliveries> data){

        arrayList.addAll(data);

        adapter = new DeliveryDataAdapter(getApplicationContext(), arrayList) {
            @Override
            public void load() {
                offset = offset+Constants.limit;
                getAPIData(Integer.toString(offset));
            }
        };

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
