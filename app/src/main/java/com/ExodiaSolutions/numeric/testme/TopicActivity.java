package com.ExodiaSolutions.numeric.testme;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

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

public class TopicActivity extends AppCompatActivity {
    String category_id;
    String category_name;
    String category_image;
    CustomAdapter3 adapter;

    ProgressBar pb;
    String topic_id;
    ExamDialog editNameDialogFragment;
    public ArrayList<TopicClass> arraylist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ActionBar action = getSupportActionBar();
        action.setTitle("Topics");

        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.INVISIBLE);
        category_id = getIntent().getStringExtra("category_id");

        category_name = getIntent().getStringExtra("cat_name");

        category_image = getIntent().getStringExtra("image");
        SimpleDraweeView img = (SimpleDraweeView) findViewById(R.id.cat_img);

        TextView tv = (TextView) findViewById(R.id.cat_name);
        tv.setText(category_name);
        Uri imageUri = Uri.parse("http://test.numericinfosystem.in/images/"+category_image);
        Drawable myIcon = getResources().getDrawable( R.drawable.user_wh );

        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setFadeDuration(300)
                .setPlaceholderImage(myIcon)
                .build();
        img.setHierarchy(hierarchy);

        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setRoundAsCircle(true);
        img.getHierarchy().setRoundingParams(roundingParams);

        img.setImageURI(imageUri);
        if(isNetworkAvailable()){
        select_Class_Loader s = new select_Class_Loader(TopicActivity.this);
        s.execute();}
        else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        ListView list = (ListView) findViewById(R.id.topic_list);

        adapter=new CustomAdapter3(TopicActivity.this,arraylist);
        list.setAdapter(adapter);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {

                        TopicClass files_class = arraylist.get(pos);

                            topic_id=files_class.getId();

                            select_Class_Loader2 s2 = new select_Class_Loader2(TopicActivity.this);
                            s2.execute();


                    }
                });


    }

    public void close(View c){
        editNameDialogFragment.dismiss();
    }
    public class select_Class_Loader extends AsyncTask<Void,Void,String> {

        Context context;
        select_Class_Loader(Context context)
        {
            this.context=context;
        }


        @Override
        protected String doInBackground(Void... params) {

            String login_url= "http://test.numericinfosystem.in/android/gettopic.php";

            try{

                URL url =new URL(login_url);
                HttpURLConnection httpurlconnection= (HttpURLConnection) url.openConnection();
                httpurlconnection.setRequestMethod("POST");
                 httpurlconnection.setDoOutput(true);
                httpurlconnection.setDoInput(true);
                OutputStream outputstream1 = httpurlconnection.getOutputStream();
                BufferedWriter bufferedwriter1 = new BufferedWriter(new OutputStreamWriter(outputstream1, "UTF-8"));
                String post_data1 = URLEncoder.encode("category_id", "UTF-8") + "=" + URLEncoder.encode(category_id, "UTF-8");
                bufferedwriter1.write(post_data1);
                bufferedwriter1.flush();
                bufferedwriter1.close();

                InputStream inputstream= httpurlconnection.getInputStream();
                BufferedReader bufferedreader= new BufferedReader(new InputStreamReader(inputstream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedreader.readLine())!=null){
                    result+=line;
                }
                bufferedreader.close();
                inputstream.close();
                httpurlconnection.disconnect();

                return result;

            }catch(MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";

        }





        @Override
        protected void onPreExecute() {
            // Toast.makeText(Dashboard.this, "Yeah", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);


        }

        @Override
        protected void onPostExecute(String result ){
            pb.setVisibility(View.INVISIBLE);
           // Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
            if(!result.equalsIgnoreCase("0")){
                arraylist.addAll(TopicClass.jsonToArray(result));
            }
            else{
                TopicActivity.this.finish();
                Toast.makeText(context, "No Topic Found", Toast.LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();
        }


    }

    public class select_Class_Loader2 extends AsyncTask<Void,Void,String> {

        Context context;
        select_Class_Loader2(Context context)
        {
            this.context=context;
        }


        @Override
        protected String doInBackground(Void... params) {

            String login_url= "http://test.numericinfosystem.in/android/getexam.php";

            try{

                URL url =new URL(login_url);
                HttpURLConnection httpurlconnection= (HttpURLConnection) url.openConnection();
                httpurlconnection.setRequestMethod("POST");
                httpurlconnection.setDoOutput(true);
                httpurlconnection.setDoInput(true);
                OutputStream outputstream1 = httpurlconnection.getOutputStream();
                BufferedWriter bufferedwriter1 = new BufferedWriter(new OutputStreamWriter(outputstream1, "UTF-8"));
                String post_data1 = URLEncoder.encode("topic_id", "UTF-8") + "=" + URLEncoder.encode(topic_id, "UTF-8");
                bufferedwriter1.write(post_data1);
                bufferedwriter1.flush();
                bufferedwriter1.close();

                InputStream inputstream= httpurlconnection.getInputStream();
                BufferedReader bufferedreader= new BufferedReader(new InputStreamReader(inputstream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedreader.readLine())!=null){
                    result+=line;
                }
                bufferedreader.close();
                inputstream.close();
                httpurlconnection.disconnect();

                return result;

            }catch(MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";

        }

        ProgressBar pb = (ProgressBar) findViewById(R.id.pb);
        @Override
        protected void onPreExecute() {
            // Toast.makeText(Dashboard.this, "Yeah", Toast.LENGTH_SHORT).show();
            ExamDialog.arrayList.clear();
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);


        }

        @Override
        protected void onPostExecute(String result ){
            pb.setVisibility(View.INVISIBLE);
            //Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
            if(!result.equalsIgnoreCase("0")){
                ExamDialog.arrayList.addAll(ExamClass.jsonToArray(result));
            
            FragmentManager fm = getSupportFragmentManager();
            editNameDialogFragment = ExamDialog.newInstance();
            editNameDialogFragment.show(fm, "password_fd");
            }
            else{
                Toast.makeText(context, "No Exam Found", Toast.LENGTH_SHORT).show();
            }

        }


    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    class CustomAdapter3 extends ArrayAdapter<TopicClass> {
        Context c;

        public CustomAdapter3(Context context, ArrayList<TopicClass> arrayList) {
            super(context, R.layout.topic_card, arrayList);
            this.c = context;
        }


        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {

            LayoutInflater li = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.topic_card, parent, false);

            TopicClass topic_class = getItem(pos);

            TextView file_name = (TextView) convertView.findViewById(R.id.topic_name);
            file_name.setText((pos+1)+". "+topic_class.getName());

            return convertView;

        }
    }
}
