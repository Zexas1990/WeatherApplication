package com.dam.tareauf5.retrofitUtil;

import com.dam.tareauf5.retrofitData.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIRestService {

    public  static final String BASE_URL = "https://api.darksky.net/forecast/";
    public  static final String KEY = "11ce4328111023379e0fdc9d28c24a02";
    public  static final String EXCLUDE = "minutely,hourly,daily,alerts,flags&lang=";
    public  static final String LANG = "es";



    @GET("{key}/{lat},{lng}")
    Call<Weather> obtenerTiempo (@Path("key") String key,
                                 @Path("lat") String lat,
                                 @Path("lng") String lng,
                                 @Query("exclude") String exclude,
                                 @Query("lang") String lang);
}
