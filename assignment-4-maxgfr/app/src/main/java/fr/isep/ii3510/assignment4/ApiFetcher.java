package fr.isep.ii3510.assignment4;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ApiFetcher {

    private static ApiFetcher INSTANCE = null;
    private OkHttpClient client;
    private static final String BASE_URL = "https://api.exchangeratesapi.io/";

    private ApiFetcher() {
        this.client = new OkHttpClient();
    }

    public static ApiFetcher getInstance() {
        if (INSTANCE == null)
        { 	INSTANCE = new ApiFetcher();
        }
        return INSTANCE;
    }

    public Call getRates (String time, String currency, Callback callback) {
        Request request = new Request.Builder()
                .url(BASE_URL+time+"?base="+currency)
                .build();
        Call call = this.client.newCall(request);
        call.enqueue(callback);
        return call;
    }


}
