package standardclasses;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IACO_APIService extends Application {
    public Context c;
    public RequestQueue queue;

    public IACO_APIService(Context c){
        this.c = c;
        this.queue = Volley.newRequestQueue(c);
    }

    public void getAPI(String OACI, final VolleyCallback callback){
        String url = "https://applications.icao.int/dataservices/api/notams-realtime-list?api_key=e5200000-25b5-11eb-b802-47888fda0890&format=json&criticality=1&locations="+OACI;
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
                                    callback.onSuccess(snowtamEntry);
                                }
                                i++;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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

