package fr.isep.ii3510.assignment4;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Rates {

    private String values;

    public Rates(String values) {
        this.values = values;
    }

    public HashMap<String, String> toHmap () {
        try {
            HashMap<String, String> hmap = new HashMap<>();
            JSONObject obj = new JSONObject(this.values);
            JSONObject jObject = new JSONObject(obj.get("rates").toString());
            JSONArray keys = jObject.names();
            for (int i = 0; i < keys.length(); ++i) {
                String key = keys.getString(i);
                String value = jObject.getString(key);
                //System.out.println(key+"  -  "+value);
                hmap.put(key, value);
            }
            return hmap;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<String> getHmapKey (HashMap<String, String> uniqueRecs) {
        return new ArrayList<>(uniqueRecs.keySet());
    }

    public CharSequence[] getCharSequence (HashMap<String, String> uniqueRecs) {
        ArrayList<String> arrayList = new ArrayList<>(uniqueRecs.keySet());
        CharSequence[] cs = arrayList.toArray(new CharSequence[arrayList.size()]);
        return cs;
    }
}
