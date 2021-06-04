package com.naman.actnetworkstest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.naman.actnetworkstest.adapters.AdapterCountry;
import com.naman.actnetworkstest.models.CounttryDetailModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Queue;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private LinearLayout countryLL;
    private TextView countryName;
    private ImageView flag;
    ArrayList<CounttryDetailModel> counttryDetailModelArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initializing widgets
        countryLL = findViewById(R.id.countyLL);
        countryName = findViewById(R.id.country);
        flag = findViewById(R.id.flag);



//        fetching flag and country name from shared preferences
        fetchFromSP();





        countryLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              bottomsheet open
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = getLayoutInflater()
                        .inflate(R.layout.country_select_bottomsheet, (LinearLayout) bottomSheetDialog.findViewById(R.id.layout_bottom_sheet_container));

//              initialize recyclerView
                RecyclerView countryRv = bottomSheetView.findViewById(R.id.countryRv);
                countryRv.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                countryRv.setLayoutManager(linearLayoutManager);

                AdapterCountry adapterCountry = new AdapterCountry(MainActivity.this, counttryDetailModelArrayList);
                adapterCountry.setHasStableIds(true);
                countryRv.setAdapter(adapterCountry);

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
                bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        fetchFromSP();

                    }
                });

            }
        });


        fetchNameAndFlag();


    }

    private void fetchFromSP() {
        SharedPreferences sp = getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        String url = sp.getString("Image", null);
        String name = sp.getString("Name", null);
        if (url == null) {
            setCountryAndFlag("India", "https://www.countryflags.io/in/flat/64.png");

        } else {
            setCountryAndFlag(name, url);
        }
    }

    private void setCountryAndFlag(String india, String s) {
        countryName.setText(india);
        Glide.with(getApplicationContext())
                .load(s)
                .thumbnail(0.5f)
                .into(flag);
    }

    private void fetchNameAndFlag() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://api.printful.com/countries";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray objectArrayList = response.getJSONArray("result");
                            for (int x = 0; x < objectArrayList.length(); x++) {
                                CounttryDetailModel countryDetail = CounttryDetailModel.fromJson(objectArrayList.getJSONObject(x));
                                String url = "https://www.countryflags.io/country_code/flat/64.png".replace("country_code", countryDetail.getCode());
                                countryDetail.setImg(url);
                                counttryDetailModelArrayList.add(countryDetail);
                            }

                            Log.d(TAG, "onResponse2: " + counttryDetailModelArrayList.size());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        queue.add(jsonObjectRequest);
    }


}