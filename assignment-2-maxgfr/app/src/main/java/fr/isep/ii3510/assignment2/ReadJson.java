package fr.isep.ii3510.assignment2;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class ReadJson {

    private static ReadJson INSTANCE = null;

    private ReadJson() {

    }

    public static  ReadJson getInstance() {
        if (INSTANCE == null)
        { 	INSTANCE = new ReadJson();
        }
        return INSTANCE;
    }

    public String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public ArrayList<String> getData(String data) {
        ArrayList<String> res = new ArrayList<>();
        try {
            JSONObject jObject = new JSONObject(data);
            JSONArray jArray = jObject.getJSONArray("result");
            if (jArray != null) {
                for (int i=0;i<jArray.length();i++){
                    res.add(jArray.getString(i).toUpperCase());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return res;

    }

    public String selectRandomWords(ArrayList<String> list)
    {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
