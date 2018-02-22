package com.ExodiaSolutions.numeric.testme;

/**
 * Created by Sunny Narang on 25-09-2016.
 */

public class result_data {
    String name,get,total,time;

    public String getTime() {
        return time;
    }

    result_data(String name, String get, String total, String time){
        this.name = name;
        this.get=get;
        this.total=total;
        this.time=time;

    }

    public String getName() {
        return name;
    }

    public String getGet() {
        return get;
    }

    public String getTotal() {
        return total;
    }
}
