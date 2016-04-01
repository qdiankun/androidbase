package com.example;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyClass {

    public static void main(String[] args) {
        JSONArray array = new JSONArray();
        try {
            for (int i = 0; i < 10; i++) {
                JSONObject obj = new JSONObject();
                obj.put("id", "" + i);
                array.put(obj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int j = 0; j < array.length(); j++) {
            try {
                JSONObject obj = array.getJSONObject(j);
                System.out.println("j =" + obj.get("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
