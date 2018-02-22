package com.ExodiaSolutions.numeric.testme;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
/**
 * Created by shriram on 9/24/2016.
 */
public class TabFragment2 extends Fragment {
    Main2Activity.RecyclerCustomAdapter adapter;
    ArrayList<result_data> arrayList= new ArrayList<result_data>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_2, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        adapter=new Main2Activity.RecyclerCustomAdapter(getContext(),arrayList);
        BackgroundWorker bw = new BackgroundWorker(getContext());
        bw.execute();

        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        //  GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
        return view;
    }


    public class BackgroundWorker extends AsyncTask<String, Void, String> {

        Context context;

        BackgroundWorker(Context context) {
            this.context = context;
        }


        @Override
        protected String doInBackground(String... params) {


            String Login_url = "http://test.numericinfosystem.in/android/getresult.php";

                try {

                    URL url = new URL(Login_url);
                    HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
                    httpurlconnection.setRequestMethod("POST");
                    httpurlconnection.setDoOutput(true);
                    httpurlconnection.setDoInput(true);

                    OutputStream outputstream = httpurlconnection.getOutputStream();
                    BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));

                    String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(Main2Activity.email, "UTF-8");
                    bufferedwriter.write(post_data);
                    bufferedwriter.flush();
                    bufferedwriter.close();

                    InputStream inputstream = httpurlconnection.getInputStream();
                    BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedreader.readLine()) != null) {
                        result += line;
                    }
                    bufferedreader.close();
                    inputstream.close();
                    httpurlconnection.disconnect();

                    return result;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            return "null ghjgj";

        }


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {
           // Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
            try {
                JSONArray json = new JSONArray(result);
                for(int i=0;i<json.length();i++){
                    JSONObject obj = json.getJSONObject(i);
                    result_data r = new result_data(obj.getString("exam_name"),obj.getString("mark"),obj.getString("out_of"),obj.getString("time"));
                    arrayList.add(r);
                    adapter.notifyDataSetChanged();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }



}
