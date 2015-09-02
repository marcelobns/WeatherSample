package br.senai.rr.clima;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity {

    private Clima clima = new Clima();
    private ImageView atualizar;
    private TextView hora;
    private ImageView icon;
    private TextView temperatura;
    private TextView humidade;
    private TextView chuva;
    private TextView sumario;
    private ProgressBar progressBar;
    private boolean chamada = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        atualizar = (ImageView) findViewById(R.id.atualizarImageView);
        hora = (TextView) findViewById(R.id.horaTextView);
        icon = (ImageView) findViewById(R.id.iconImageView);
        temperatura = (TextView) findViewById(R.id.temperaturaTextView);
        humidade = (TextView)findViewById(R.id.humidadeTextView);
        chuva = (TextView)findViewById(R.id.chuvaTextView);
        sumario = (TextView)findViewById(R.id.sumarioTextView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if(hasNetwork()){
            getLocation();
        } else {
            toast("Rede indisponivel! Dados de Teste.");
            setClima(clima.getTest());
            setView();
        }

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkProgress();
                chamada = false;
            }
        });
    }

    private void setView(){
        Drawable drawable = getResources().getDrawable(clima.getIconId());
        icon.setImageDrawable(drawable);

        hora.setText(clima.getTime());
        temperatura.setText(clima.getTemperature());
        humidade.setText(clima.getHumidity());
        chuva.setText(clima.getPrecipProbability());
        sumario.setText(clima.getSummary());

        checkProgress();
    }

    private void getLocation(){
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.e("LOCATION", location.toString());

                Log.e("LOCATION", String.valueOf(location.getLatitude()));
                Log.e("LOCATION", String.valueOf(location.getLongitude()));

                if(!chamada){
                    getForecast(location.getLatitude(), location.getLongitude());
                    chamada = true;
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    private void getForecast(double latitude, double longitude) {
        String apiKey = "cec4f0c96c87bdb318038b4ffaac178b";
        String apiUrl = "https://api.forecast.io/forecast/"+apiKey+"/"+latitude+","+longitude;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl).build();
        client.setConnectTimeout(10, TimeUnit.SECONDS);

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                alert();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                setClima(response.body().string());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setView();
                    }
                });
            }
        });
    }

    private void alert() {
        AlertFragment alert = new AlertFragment();
        alert.show(getFragmentManager(), "error_dialog");
    }

    protected void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void setClima(String response_body){
        try {
            JSONObject json = new JSONObject(response_body);
            clima.setTimezone(json.getString("timezone"));

            JSONObject currently = json.getJSONObject("currently");
            clima.setIcon(currently.getString("icon"));
            clima.setTime(currently.getLong("time"));
            clima.setTemperature(currently.getDouble("temperature"));
            clima.setHumidity(currently.getDouble("humidity"));
            clima.setPrecipProbability(currently.getDouble("precipProbability"));
            clima.setSummary(currently.getString("summary"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected boolean hasNetwork(){
        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = manager.getActiveNetworkInfo();

        return (network != null && network.isConnected());
    }

    private void checkProgress(){
        if(progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
            atualizar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            atualizar.setVisibility(View.INVISIBLE);
        }
    }
}














