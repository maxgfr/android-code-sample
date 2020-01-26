package fr.isep.ii3510.assignment4;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Rates rates;
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private EditText editText;
    private TextView textView;
    private HashMap<String, String> data = new HashMap<>();
    private String deviseFrom = "";
    private String deviseTo = "";
    private ArrayAdapter<String> adapterFrom;
    private ArrayAdapter<String> adapterTo;
    private int posSpinnerFrom = 0;
    private int posSpinnerTo = 0;
    private boolean selectUSD = false;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        pref = getApplicationContext().getSharedPreferences("@STORE", 0);
        if(!isNetwork()) {
            String offlineRates = pref.getString("ratesUSD", "");
            //System.out.println(offlineRates);
            if(!offlineRates.matches("")) {
                initSpinnerOfflineUSD(offlineRates);
            } else {
                textView.setText("This application must at least launch 1 time with the Network");
            }
        } else {
            initSpinnerNetwork();
        }

    }

    private boolean isNetwork() {
        ConnectivityManager cm =  (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void init() {
        spinnerFrom = (Spinner) findViewById(R.id.spinnerFrom);
        spinnerTo = (Spinner) findViewById(R.id.spinnerTo);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        ArrayList<String> list = new ArrayList<>();
        adapterFrom = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapterFrom);
        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                deviseFrom = adapterView.getItemAtPosition(i).toString();
                if(adapterView.getItemAtPosition(i).toString().matches("USD")) {
                    selectUSD = true;
                }
                posSpinnerFrom = i;
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        adapterTo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo.setAdapter(adapterTo);
        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                deviseTo = adapterView.getItemAtPosition(i).toString();
                posSpinnerTo = i;
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    private void initSpinnerNetwork() {
        ApiFetcher.getInstance().getRates("latest", "USD", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    //System.out.println(responseBody.string());
                    String ratesJson = responseBody.string();
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("ratesUSD", ratesJson);
                    editor.commit();
                    rates = new Rates(ratesJson);
                    //System.out.println(rates.getHmapKey(rates.toHaHmap()));
                    data = rates.toHmap();
                    final ArrayList<String> list = rates.getHmapKey(data);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapterFrom.clear();
                            adapterFrom.addAll(list);
                            adapterFrom.notifyDataSetChanged();
                            adapterTo.clear();
                            adapterTo.addAll(list);
                            adapterTo.notifyDataSetChanged();
                        }
                    });


                }
            }
        });
    }

    private void initSpinnerOfflineUSD(String offlineData) {
        rates = new Rates(offlineData);
        //System.out.println(rates.getHmapKey(rates.toHaHmap()));
        data = rates.toHmap();
        final ArrayList<String> list = rates.getHmapKey(data);
        adapterFrom.clear();
        adapterFrom.addAll(list);
        adapterFrom.notifyDataSetChanged();
        adapterTo.clear();
        adapterTo.addAll(list);
        adapterTo.notifyDataSetChanged();
    }

    public void onPress(View view) {
        if(!editText.getText().toString().matches("")) {
            if(!isNetwork()) {
                if(selectUSD) {
                    String price = editText.getText().toString();
                    final Double res = Double.parseDouble(price) * Double.parseDouble(data.get(deviseTo));
                    textView.setText(String.valueOf(res));
                } else {
                    textView.setText("The offline mode works just for USD");
                }
                return;
            }
            ApiFetcher.getInstance().getRates("latest", this.deviseFrom, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                        //System.out.println(responseBody.string());
                        rates = new Rates(responseBody.string());
                        //System.out.println(rates.getHmapKey(rates.toHaHmap()));
                        data = rates.toHmap();
                        String price = editText.getText().toString();
                        final Double res = Double.parseDouble(price) * Double.parseDouble(data.get(deviseTo));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(String.valueOf(res));
                            }
                        });
                    }
                }
            });
        } else {
            textView.setText("Choose a correct amount...");
        }
    }

    public void onSwitch(View view) {
        if(!isNetwork()) {
            textView.setText("You cannot switch offline.");
            return;
        }
        if(!editText.getText().toString().matches("")) {
            ApiFetcher.getInstance().getRates("latest", this.deviseTo, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                        //System.out.println(responseBody.string());
                        rates = new Rates(responseBody.string());
                        //System.out.println(rates.getHmapKey(rates.toHaHmap()));
                        data = rates.toHmap();
                        String price = editText.getText().toString();
                        final Double res = Double.parseDouble(price) * Double.parseDouble(data.get(deviseFrom));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(String.valueOf(res));
                                spinnerFrom.setSelection(posSpinnerTo);
                                spinnerTo.setSelection(posSpinnerFrom);
                            }
                        });
                    }
                }
            });
        } else {
            textView.setText("Choose a correct amount...");
        }
    }
}
