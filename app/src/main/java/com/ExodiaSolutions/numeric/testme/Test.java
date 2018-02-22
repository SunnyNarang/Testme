package com.ExodiaSolutions.numeric.testme;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

public class Test extends AppCompatActivity {
    public static ViewPager viewPager;
    private ProgressDialog pd;
    BeforeExamDialogFragment beforeExamDialogFragment;
    AfterExamDialogFragment afterExamDialogFragment;
    ArrayList<TestData> arrayList = new ArrayList<>();
    static String[] answer;
    TextView timerTv;String[] data;
    int que_value=1;String each_mark;
    String timertime;
    String total_marks;
    static int no_of_page;
    String que_no;
    int count=1;
    static int total_que;
    int attempted=0;int timetaken=0;
    String score;String topic;
    int correct;String totalmarks;
    String time,exam_completion_time;
    int Total_marks;
    static TextView next_btn,pre_btn;
    MyAlertDialogFragment editNameDialogFragment;

    private String decipline,exam_id;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_test);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        next_btn = (TextView) findViewById(R.id.next_btn);
        pre_btn = (TextView) findViewById(R.id.pre_btn);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setCurrentItem(0);
        exam_id = getIntent().getStringExtra("exam_id");



        /*viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Toast.makeText(Test.this,"aa",Toast.LENGTH_LONG).show();
                return false;
            }
        });*/
        if(isNetworkAvailable()){
            final select_Class_Loader select_class_loader = new select_Class_Loader(Test.this);
            select_class_loader.execute();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run() {
                    if ( select_class_loader.getStatus() == AsyncTask.Status.RUNNING )
                    {select_class_loader.cancel(true);
                        Toast.makeText(Test.this, "Time OUT", Toast.LENGTH_SHORT).show();
                        if(pd.isIndeterminate())
                            pd.dismiss();
                    }
                }
            }, 20000 );
        }
        else {
            Toast.makeText(this, "Please Connect To Internet", Toast.LENGTH_SHORT).show();
        }
    }

    /*   public void selectA(View v){
          int pageno= viewPager.getCurrentItem();
           Toast.makeText(this, "A,page no-"+pageno, Toast.LENGTH_SHORT).show();

           answer[pageno]= "A";
       }
    public void selectB(View v){
        int pageno= viewPager.getCurrentItem();
        Toast.makeText(this, "B,page no-"+pageno, Toast.LENGTH_SHORT).show();

        answer[pageno] = "B";

    }
    public void selectC(View v){
        int pageno= viewPager.getCurrentItem();
        Toast.makeText(this, "C,page no-"+pageno, Toast.LENGTH_SHORT).show();

        answer[pageno] = "C";


    }
    public void selectD(View v){
        int pageno= viewPager.getCurrentItem();
        Toast.makeText(this, "D,page no-"+pageno, Toast.LENGTH_SHORT).show();
        answer[pageno] = "D";


    }*/
    public void closethis(View v){
        finish();
        beforeExamDialogFragment.dismiss();
        //counts.setText("1/"+total_que);
    }
    public void start_exam(View v){
        CountDown(Integer.parseInt(time));
        beforeExamDialogFragment.dismiss();
        viewPager.setAdapter(new RoutineFragmentPagerAdapter(getSupportFragmentManager()));
    }
    public void submit(View v){
    endExam(exam_completion_time);

        /**/


    }
    public void next(View v){
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
        if(count!=no_of_page){
            count++;
          //  counts.setText(count+"/"+total_que);
           // Toast.makeText(this, "This is last Que.", Toast.LENGTH_SHORT).show();
        }
    }
    public void previous(View v){
        viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
        if(count!=1){
            count--;
           // counts.setText(count+"/"+total_que);
            //Toast.makeText(this, "This is First Que.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public class select_Class_Loader extends AsyncTask<Void,Void,String> {

        Context context;
        select_Class_Loader(Context context)
        {
            this.context=context;
        }


        @Override
        protected String doInBackground(Void... params) {

            String login_url= "http://test.numericinfosystem.in/android/getque.php";

            try{

                URL url =new URL(login_url);
                HttpURLConnection httpurlconnection= (HttpURLConnection) url.openConnection();
                httpurlconnection.setRequestMethod("POST");
                httpurlconnection.setDoOutput(true);
                httpurlconnection.setDoInput(true);
                OutputStream outputstream = httpurlconnection.getOutputStream();
                BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));

                String post_data = URLEncoder.encode("exam_id", "UTF-8") + "=" + URLEncoder.encode(exam_id, "UTF-8");
                bufferedwriter.write(post_data);
                bufferedwriter.flush();
                bufferedwriter.close();

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
            pd = new ProgressDialog(Test.this);
            pd.setTitle("Loading.");
            pd.setMessage("Please wait...\nLoading Class Details.");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected void onPostExecute(String result ){
            pd.dismiss();
           // Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();

            data = result.split("``%f%``");
            String que_no= data[2];
            String time_per_que= "0";
            try {
                JSONObject obj = new JSONObject(data[0]);

                time_per_que = obj.getString("time");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            time=Integer.parseInt(time_per_que)*Integer.parseInt(que_no)+"";



            FragmentManager fm =getSupportFragmentManager();
            beforeExamDialogFragment = BeforeExamDialogFragment.newInstance();
            beforeExamDialogFragment.setCancelable(false);
            beforeExamDialogFragment.show(fm, data[0]+"``%f%``"+data[2]);

            if(!data[1].equalsIgnoreCase("")){
                writeItems(data[1],"questionare.txt");

                JSONArray jsonArray = null;
                String q="";
                try {
                    jsonArray = new JSONArray(data[1]);
                    no_of_page=RoutineFragmentPagerAdapter.PAGE_COUNT = jsonArray.length();
                    answer = new String[jsonArray.length()];
                    for(int i =0;i<jsonArray.length();i++) {
                        JSONObject obj = jsonArray.optJSONObject(i);
                        //Toast.makeText(context, "+"+ result, Toast.LENGTH_SHORT).show();

                        arrayList.add(new TestData(obj.getString("question"),obj.getString("option_a"),obj.getString("option_b"),obj.getString("option_c"),obj.getString("option_d"),obj.getString("answer"),obj.getString("ques_image")));
                        q+="0";
                    }
                    writeItems(q,"answered.txt");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               // viewPager.setAdapter(new RoutineFragmentPagerAdapter(getSupportFragmentManager()));
            }
        }
    }
    public void showInfo(View v){
        showEditDialog2();
    }

    private void showEditDialog2() {
        MyAlertDialogFragment.arrayList.clear();
        for(int i=0;i<answer.length;i++){
            if(answer[i]!=null)
                MyAlertDialogFragment.arrayList.add(new Details(""+(i+1),"1"));
            else
                MyAlertDialogFragment.arrayList.add(new Details(""+(i+1),"0"));
        }
        FragmentManager fm = getSupportFragmentManager();
        editNameDialogFragment = MyAlertDialogFragment.newInstance();
        editNameDialogFragment.show(fm, "password_fd");
    }
    public void close(View c){
        editNameDialogFragment.dismiss();
    }

    private void writeItems(String s,String filename) {

        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, filename);
        try {


            FileUtils.writeStringToFile(todoFile ,s);   // TODO: add depenencies for fill utils
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class ResultToServer extends AsyncTask<String,Void,String> {

        Context context;
        ResultToServer(Context context)
        {
            this.context=context;
        }


        @Override
        protected String doInBackground(String... params) {

            String login_url= "http://test.numericinfosystem.in/android/savedata.php";

            try{

                URL url =new URL(login_url);
                HttpURLConnection httpurlconnection= (HttpURLConnection) url.openConnection();
                httpurlconnection.setRequestMethod("POST");
                httpurlconnection.setDoOutput(true);
                httpurlconnection.setDoInput(true);
                OutputStream outputstream = httpurlconnection.getOutputStream();
                BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));

                String post_data = URLEncoder.encode("exam_id", "UTF-8") + "=" + URLEncoder.encode(exam_id, "UTF-8")  + "&"
                        + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(Main2Activity.email, "UTF-8") + "&"
                        + URLEncoder.encode("score", "UTF-8") + "=" + URLEncoder.encode(score, "UTF-8") + "&"
                        + URLEncoder.encode("que_attempted", "UTF-8") + "=" + URLEncoder.encode(attempted+"", "UTF-8") + "&"
                        + URLEncoder.encode("time_taken", "UTF-8") + "=" + URLEncoder.encode(timetaken+"", "UTF-8") + "&"
                        + URLEncoder.encode("total", "UTF-8") + "=" + URLEncoder.encode(totalmarks, "UTF-8");
                bufferedwriter.write(post_data);
                bufferedwriter.flush();
                bufferedwriter.close();

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
            super.onPreExecute();
            pd = new ProgressDialog(Test.this);
            pd.setTitle("Loading.");
            pd.setMessage("Please wait...\nLoading Class Details.");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected void onPostExecute(String result ){
            pd.dismiss();

            //Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
                FragmentManager fm =getSupportFragmentManager();
                afterExamDialogFragment.show(fm, ""+correct*Integer.parseInt(each_mark)+"``%f%``"+correct+"``%f%``"+timetaken+"``%f%``"+topic+"``%f%``"+attempted);




        }
    }
    public void close_after_dialog(View v){
        afterExamDialogFragment.dismiss();
        finish();
    }


    public void CountDown(int minute){
        minute=minute*60*1000;
        String currentTime="";
        timerTv = (TextView) findViewById(R.id.timertv);
        new CountDownTimer(minute, 1000) {

            public void onTick(long millisUntilFinished) {
                exam_completion_time ="" + millisUntilFinished;
                String seconds = (millisUntilFinished/1000)%60>9 ? (millisUntilFinished/1000)%60+"":"0"+(millisUntilFinished/1000)%60;
                String minutes = (int)millisUntilFinished/60000>9 ? (int)millisUntilFinished/60000+"":"0"+(int)millisUntilFinished/60000;
                timertime = minutes+":"+seconds;
                timerTv.setText(timertime);
                if((int)millisUntilFinished/60000 <1)
                {
                    timerTv.setTextColor(Color.parseColor("#ff0000"));
                }
            }

            public void onFinish() {
                timerTv.setText("done!");
                exam_completion_time = ""+Integer.parseInt(time)*60*1000;
                endExam(exam_completion_time);
            }
        }.start();
    }

    public void endExam (String completion_time){
        String s="";
        for(int i=0;i<answer.length;i++){
            s+=answer[i];
        }
       // Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();

        correct = 0;

        for(int i=0;i<answer.length;i++){
            if(answer[i]!=null&&answer[i].equalsIgnoreCase(arrayList.get(i).getAns())){
                correct++;
            }
            if(answer[i]!=null){
                attempted++;
            }
        }
        Total_marks = correct*que_value;
      //  Toast.makeText(this, ""+s+"\nCorrect="+correct, Toast.LENGTH_SHORT).show();
        JSONObject obj = null;
        try {
            obj = new JSONObject(data[0]);

            que_no = data[2];

            each_mark = obj.getString("mark");

            topic = obj.getString("exam_name");


            String time[]= timertime.split(":");
            String time_per_que=obj.getString("time");
            String totaltime=Integer.parseInt(time_per_que)*Integer.parseInt(que_no)+"";
            //Toast.makeText(context, ""+timertime+"\nMin: "+time[0]+"\nTotal Time: "+totaltime, Toast.LENGTH_SHORT).show();
            timetaken= Integer.parseInt(totaltime)-Integer.parseInt(time[0]);

            afterExamDialogFragment = AfterExamDialogFragment.newInstance();
            afterExamDialogFragment.setCancelable(false);
            score= correct*Integer.parseInt(each_mark)+"";

        } catch (JSONException e) {
            e.printStackTrace();
        }
        totalmarks = Integer.parseInt(each_mark)*Integer.parseInt(que_no)+"";

        ResultToServer resulttoserver = new ResultToServer(Test.this);
        resulttoserver.execute(completion_time);
    }
}
