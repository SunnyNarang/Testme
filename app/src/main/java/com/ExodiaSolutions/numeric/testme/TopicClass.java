package com.ExodiaSolutions.numeric.testme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sunny on 09-01-2017.
 */

public class TopicClass {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TopicClass(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String id,name;

    public static ArrayList<TopicClass> jsonToArray(String json){
        ArrayList<TopicClass> arraylist = new ArrayList<>();
        try {
            JSONArray topic_json = new JSONArray(json);
            for(int i=0;i<topic_json.length();i++){
                JSONObject obj = topic_json.getJSONObject(i);
                arraylist.add(new TopicClass(obj.getString("id"),obj.getString("topic_name")));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return arraylist;
    }


}
