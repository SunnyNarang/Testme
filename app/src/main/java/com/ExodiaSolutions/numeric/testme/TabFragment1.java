package com.ExodiaSolutions.numeric.testme;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
/**
 * Created by shriram on 9/24/2016.
 */
public class TabFragment1 extends Fragment implements View.OnClickListener {

    String categoryid;
    String topic_id,exam_id;
    View view;
    private ProgressDialog pd;
    private ImageView sm;
    ProgressBar pb;
    private Spinner category_spinner,topic_spinner,exam_spinner;
    private String category_select="",topic_select="",exam_select="";
    private JSONArray category_json,topic_json,exam_json;
    private ArrayList<String> category = new ArrayList<>();

    public ArrayList<CategoryClass> cat_arrayList = new ArrayList<CategoryClass>();




    private  RecyclerView recyclerView ;
    private RecyclerCustomAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Context getContext() {
        return super.getContext();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Fresco.initialize(getContext());

        view = inflater.inflate(R.layout.tab_fragment_1, container, false);

        pb = (ProgressBar) view.findViewById(R.id.pb);
        pb.setVisibility(View.INVISIBLE);
        if(isNetworkAvailable()){
            final select_Class_Loader select_class_loader = new select_Class_Loader(getContext());
            select_class_loader.execute();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run() {
                    if ( select_class_loader.getStatus() == AsyncTask.Status.RUNNING )
                    {select_class_loader.cancel(true);
                        Toast.makeText(getContext(), "Time OUT", Toast.LENGTH_SHORT).show();
                        if(pd.isIndeterminate())
                            pd.dismiss();
                    }
                }
            }, 20000 );
        }
        else {
            Toast.makeText(getContext(), "Please Connect To Internet", Toast.LENGTH_SHORT).show();
        }


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        adapter= new RecyclerCustomAdapter(getContext(),cat_arrayList);
        recyclerView.setAdapter(adapter);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
         GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

       // sm = (ImageView) view.findViewById(R.id.img);

        recyclerView.addOnItemTouchListener( new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                // TODO Handle item click

                CategoryClass c = cat_arrayList.get(position);
                Intent in = new Intent(getContext(),TopicActivity.class);
                in.putExtra("category_id",c.getId());
                in.putExtra("image",c.getIcon());
                in.putExtra("cat_name",c.getName());
                startActivity(in);

            }
        }));

        return view;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                getActivity().supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View view) {

    if(!isNetworkAvailable()){
    Toast.makeText(getContext(), "Check Internet Connection..", Toast.LENGTH_SHORT).show();
    }
    else if(category_select.equalsIgnoreCase("")||topic_select.equalsIgnoreCase("")||exam_select.equalsIgnoreCase("")){
          Toast.makeText(getContext(), "Nothing selected..", Toast.LENGTH_SHORT).show();
    }
    else {
            Intent intent = new Intent(getContext(), Test.class);
            intent.putExtra("exam_id", exam_id);
            startActivity(intent);
        }


    }

    public class select_Class_Loader extends AsyncTask<Void,Void,String> {

        Context context;
        select_Class_Loader(Context context)
        {
            this.context=context;
        }


        @Override
        protected String doInBackground(Void... params) {

            String login_url= "http://test.numericinfosystem.in/android/getdata.php";

            try{

                URL url =new URL(login_url);
                HttpURLConnection httpurlconnection= (HttpURLConnection) url.openConnection();
                //httpurlconnection.setRequestMethod("POST");
               // httpurlconnection.setDoOutput(true);
                httpurlconnection.setDoInput(true);

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
         //  Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
            pb.setVisibility(View.INVISIBLE);
            if(!result.equalsIgnoreCase("")){

                try {
                    category_json =new JSONArray(result);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(int i=0;i<category_json.length();i++)
                {
                    JSONObject obj= category_json.optJSONObject(i);

                    try {
                        cat_arrayList.add(new CategoryClass(obj.getString("name"),obj.getString("description"),obj.getString("icon"),obj.getString("id")));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            adapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(context, "Server is down :-(", Toast.LENGTH_SHORT).show();
            }



            //Toast.makeText(context, " result : -"+result, Toast.LENGTH_SHORT).show();
        }


    }
    public static class RecyclerCustomAdapter extends
            RecyclerView.Adapter<RecyclerCustomAdapter.ViewHolder> {

        Context mContext;
        ArrayList<CategoryClass> mArrayList;

        //constructor
        public RecyclerCustomAdapter(Context context,ArrayList<CategoryClass> marrayList){
            mContext = context;
            mArrayList = marrayList;
        }

        //easy access to context items objects in recyclerView
        private Context getContext() {
            return mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.categorycard, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;

        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {

            // Get the data model based on position
            CategoryClass cat = mArrayList.get(position);

            // Set item views based on your views and data model
            TextView head_tv = viewHolder.title;
            SimpleDraweeView img = viewHolder.cat_img;
            head_tv.setText(cat.getName());
            TextView desc = viewHolder.desc;
            desc.setText(cat.getDesc());

            Uri imageUri = Uri.parse("http://test.numericinfosystem.in/images/"+cat.getIcon());
            Drawable myIcon = getContext().getResources().getDrawable( R.drawable.user_wh );

            GenericDraweeHierarchyBuilder builder =
                    new GenericDraweeHierarchyBuilder(getContext().getResources());
            GenericDraweeHierarchy hierarchy = builder
                    .setFadeDuration(300)
                    .setPlaceholderImage(myIcon)
                    .build();
            img.setHierarchy(hierarchy);

            RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
            roundingParams.setRoundAsCircle(true);
            img.getHierarchy().setRoundingParams(roundingParams);

            img.setImageURI(imageUri);

        }

        @Override
        public int getItemCount() {
            return mArrayList.size();
        }

        // Provide a direct reference to each of the views within a data item
        // Used to cache the views within the item layout for fast access
        public static class ViewHolder extends RecyclerView.ViewHolder {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            public TextView title,desc;
            SimpleDraweeView cat_img;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);


                title = (TextView) itemView.findViewById(R.id.cat_text);
                desc = (TextView) itemView.findViewById(R.id.cat_desc);
                cat_img = (SimpleDraweeView) itemView.findViewById(R.id.cat_img);

            }
        }
    }


}
