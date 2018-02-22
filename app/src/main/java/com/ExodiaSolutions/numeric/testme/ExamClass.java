package com.ExodiaSolutions.numeric.testme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sunny on 09-01-2017.
 */

public class ExamClass {
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public ExamClass( String id,String name) {
        this.name = name;
        this.id = id;
    }


    public String getId() {
        return id;

    }

    public void setId(String id) {
        this.id = id;
    }

    String name,id;
    public static ArrayList<ExamClass> jsonToArray(String json){
        ArrayList<ExamClass> arraylist = new ArrayList<>();
        try {
            JSONArray topic_json = new JSONArray(json);
            for(int i=0;i<topic_json.length();i++){
                JSONObject obj = topic_json.getJSONObject(i);
                arraylist.add(new ExamClass(obj.getString("id"),obj.getString("exam_name")));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return arraylist;
    }
}
