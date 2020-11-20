package com.example.starsnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import Adapter.ViewPagerAdapter;
import fragments.FragmentOACICode;
import fragments.FragmentOACIDecode;
import standardclasses.IACO_APIService;
import standardclasses.VolleyCallback;

public class codeOACI extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_o_a_c_i);

        tabLayout = findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.post(() -> tabLayout.setupWithViewPager(viewPager));

        IACO_APIService API = new IACO_APIService(this.getApplicationContext());
        API.getAPI("ENBR", new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                Log.i("ALERTE","Response is: "+ result);
            }
        });
    }


}