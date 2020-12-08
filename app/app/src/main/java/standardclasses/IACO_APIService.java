package standardclasses;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.starsnow.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DecimalFormat;

public class IACO_APIService extends Application {
    public Context c;
    public RequestQueue queue;
    public String API_KEY = "9210d980-38a1-11eb-87c3-599db300ae0b";

    public IACO_APIService(Context c){
        this.c = c;
        this.queue = Volley.newRequestQueue(c);
    }

    public void getSnowtam(String OACI, final VolleyCallback callback){

        String url = "https://applications.icao.int/dataservices/api/notams-realtime-list?api_key="+API_KEY+"&format=json&criticality=1&locations="+OACI;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray responseJSON = new JSONArray(response);
                            int i = 0;
                            boolean found = false;
                            while(i<responseJSON.length() && found == false){
                                JSONObject snowtamEntry = responseJSON.getJSONObject(i);
                                String snowtam = snowtamEntry.getString("all");
                                if(snowtam.contains("SNOWTAM") == true){
                                    found = true;
                                    callback.onSuccess(snowtam);
                                }
                                i++;
                            }
                            if(found == false){
                                callback.onSuccess(Resources.getSystem().getString(R.string.noSnowtam));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onSuccess(Resources.getSystem().getString(R.string.APIProblem));
                        System.out.println("That didn't work!");
                    }
                });
        this.queue.add(stringRequest);
    }


    public void getAeroport(String OACI, final VolleyCallback2 callback){
        String url = "https://applications.icao.int/dataservices/api/indicators-list?api_key="+API_KEY+"&state=&airports="+OACI+"&format=json";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.length() > 0){
                            try {
                                System.out.println(response);
                                JSONArray responseJSON = new JSONArray(response);
                                JSONObject aeroportEntry = responseJSON.getJSONObject(0);
                                String aeroportName = aeroportEntry.getString("airportName");
                                JSONArray coordinates = aeroportEntry.getJSONObject("geometry").getJSONArray("coordinates");
                                DecimalFormat df = new DecimalFormat("0.000000");
                                double latitude = (double) coordinates.get(0);
                                double longitude = (double) coordinates.get(1);
                                System.out.println(OACI+" "+aeroportName+" |"+latitude+"/"+longitude);
                                callback.onSuccess(new Aeroport(OACI, aeroportName,latitude,longitude));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            callback.onError("CODE OACI NON VALIDE");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });
        this.queue.add(stringRequest);
    }
}

