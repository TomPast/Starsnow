package com.example.starsnow.APIService;

import com.example.starsnow.model.Aeroport;

public interface VolleyCallback2{
    void onSuccess(Aeroport results);
    void onError(String results);
}
