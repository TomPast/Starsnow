package com.example.starsnow.APIService;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.starsnow.R;
import com.example.starsnow.model.Aeroport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IACO_APIService extends Application {
    public Context c;
    public RequestQueue queue;
    public String API_KEY = "34ea4c90-3a5a-11eb-97e5-85d18d462712";

    public IACO_APIService(Context c){
        this.c = c;
        this.queue = Volley.newRequestQueue(c);
    }

    //Appel API pour récupérer le snowtam (premier trouvé) à partir du code OACI
    public void getSnowtam(String OACI, final VolleyCallback callback){
        String url = "https://applications.icao.int/dataservices/api/notams-realtime-list?api_key="+API_KEY+"&format=json&criticality=50&locations="+OACI;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        System.out.println("SUCCESS" + response);

                        JSONArray responseJSON = new JSONArray(response);
                        int i = 0;
                        boolean found = false;
                        while(i<responseJSON.length() && !found){
                            JSONObject snowtamEntry = responseJSON.getJSONObject(i);
                            String snowtam = snowtamEntry.getString("all");
                            if(snowtam.contains("SNOWTAM")){
                                found = true;
                                callback.onSuccess(snowtam);
                            }
                            i++;
                        }
                        if(!found){
                            callback.onSuccess(this.c.getString(R.string.noSnowtam));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    callback.onSuccess(this.c.getString(R.string.APIProblem));
                    System.out.println("That didn't work!");
                });
        this.queue.add(stringRequest);
    }

    //Appel API pour récupérer le nom, la longitude et la latitude à partir du code OACI
    public void getAeroport(String OACI, final VolleyCallback2 callback){
        String url = "https://applications.icao.int/dataservices/api/indicators-list?api_key="+API_KEY+"&state=&airports="+OACI+"&format=json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {

                    if(response.length() > 2){
                        try {

                            JSONArray responseJSON = new JSONArray(response);
                            JSONObject aeroportEntry = responseJSON.getJSONObject(0);
                            String aeroportName = aeroportEntry.getString("airportName");
                            JSONArray coordinates = aeroportEntry.getJSONObject("geometry").getJSONArray("coordinates");

                            double latitude = (double) coordinates.get(0);
                            double longitude = (double) coordinates.get(1);

                            callback.onSuccess(new Aeroport(OACI, aeroportName,latitude,longitude));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        callback.onError(this.c.getString(R.string.codeOACIInvalide));
                    }

                }, error -> System.out.println("That didn't work!"));
        this.queue.add(stringRequest);
    }
}

