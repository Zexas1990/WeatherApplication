package com.dam.tareauf5;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.tareauf5.retrofitData.Currently;
import com.dam.tareauf5.retrofitData.Weather;
import com.dam.tareauf5.retrofitUtil.APIRestService;
import com.dam.tareauf5.retrofitUtil.RetrofitClient;

import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;

public class WetherActivity extends AppCompatActivity {


    TextView tvCiudad;
    ImageView ivIcono;
    TextView tvHora;
    TextView tvTemperatura;
    TextView tvHumedad;
    TextView tvLluvia;
    TextView tvPrediccion;
    String latitud;
    String longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wether);

        tvCiudad = findViewById(R.id.tvCity);
        ivIcono = findViewById(R.id.ivIcon);
        tvHora = findViewById(R.id.tvHora);
        tvTemperatura = findViewById(R.id.tvTemp);
        tvHumedad = findViewById(R.id.tvHumedad);
        tvLluvia = findViewById(R.id.tvLluvia);
        tvPrediccion = findViewById(R.id.tvPrediccion);

        latitud = getIntent().getStringExtra(MainActivity.CLAVE_LATITUD);
        longitud = getIntent().getStringExtra(MainActivity.CLAVE_LONGITUD);

        invocarWS();

    }

    private boolean isNetworkAvailable() {
        boolean isAvailable=false;
        //Gestor de conectividad
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(WetherActivity.CONNECTIVITY_SERVICE);

        //Objeto que recupera la información de la red
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //Si la información de red no es nula y estamos conectados
        //la red está disponible
        if(networkInfo!=null && networkInfo.isConnected()){
            isAvailable=true;
        }
        return isAvailable;
    }

    private void invocarWS() {
        if (isNetworkAvailable()) {
            Retrofit r = RetrofitClient.getClient(APIRestService.BASE_URL);
            APIRestService ars = r.create(APIRestService.class);

            Call<Weather> call = ars.obtenerTiempo(APIRestService.KEY, latitud, longitud,
                    APIRestService.EXCLUDE, APIRestService.LANG);

            call.enqueue(new Callback<Weather>() {
                @Override
                public void onResponse(Call<Weather> call, Response<Weather> response) {
                    if (response.isSuccessful()) {
                        Weather w = response.body();

                        cargarDatos(w);
                    } else {
                        Log.i("RespuestaWS:", "Error - " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Weather> call, Throwable t) {
                    Log.i("onFailure:", "Error - " + t.getMessage());
                }
            });

        } else {
            Toast.makeText(this, R.string.no_network, Toast.LENGTH_LONG).show();
        }
    }

    private void cargarDatos(Weather w) {
        tvCiudad.setText(w.getTimezone());

        Currently c = w.getCurrently();
        Drawable icono = getResources().getDrawable(c.getIconId(),
                getApplicationContext().getTheme());
        ivIcono.setImageDrawable(icono);

        /*int iconID = getApplicationContext().getResources().getIdentifier(c.getIcon(),
                "drawable", getApplicationContext().getPackageName());
        ivIcon.setImageResource(iconID);*/

        tvHora.setText(c.getFormattedTime(w.getTimezone()));

        int tempC = (int) Math.round((c.getTemperature()-32)/1.8);
        tvTemperatura.setText(String.valueOf(tempC));

        String sHum = ((int) (c.getHumidity()*100)) + "%";
        tvHumedad.setText(sHum);

        String sPrec = ((int) (c.getPrecipProbability()*100)) + "%";
        tvPrediccion.setText(sPrec);

        tvLluvia.setText(c.getSummary());

    }
}