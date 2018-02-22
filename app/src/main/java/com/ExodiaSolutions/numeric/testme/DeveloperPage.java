package com.ExodiaSolutions.numeric.testme;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Random;

public class DeveloperPage extends AppCompatActivity {
    ArrayList<Integer> arrayList = new ArrayList<>();
    int img;
    String name,details;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                DeveloperPage.this.finish();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Developers");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        TextView tv3 = (TextView) findViewById(R.id.tv3);
        TextView tv4 = (TextView) findViewById(R.id.tv4);
        TextView tv2 = (TextView) findViewById(R.id.tv2);

        TextView d1 = (TextView) findViewById(R.id.detail_1);
        TextView d2 = (TextView) findViewById(R.id.detail_2);
        TextView d3 = (TextView) findViewById(R.id.detail_3);
        TextView d4 = (TextView) findViewById(R.id.detail_4);
        Random rn = new Random();


        SimpleDraweeView draweeView1 = (SimpleDraweeView) findViewById(R.id.img_1);
        SimpleDraweeView draweeView2 = (SimpleDraweeView) findViewById(R.id.img_2);
        SimpleDraweeView draweeView3= (SimpleDraweeView) findViewById(R.id.img_3);
        SimpleDraweeView draweeView4 = (SimpleDraweeView) findViewById(R.id.img_4);

            int pos = generateRandom(1, 4, arrayList);
            setValues(pos);
            setData(draweeView1, tv1, d1);
            arrayList.add(pos);
        pos = generateRandom(1, 4, arrayList);
        setValues(pos);
        setData(draweeView2, tv2, d2);
        arrayList.add(pos);
        pos = generateRandom(1, 4, arrayList);
        setValues(pos);
        setData(draweeView3, tv3, d3);
        arrayList.add(pos);
        pos = generateRandom(1, 4, arrayList);
        setValues(pos);
        setData(draweeView4, tv4, d4);





       // Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Oswald-Regular.ttf");
        //tv1.setTypeface(type);
        //tv2.setTypeface(type);
        //tv3.setTypeface(type);
        //tv4.setTypeface(type);

    }
    public int generateRandom(int start, int end, ArrayList<Integer> excludeRows) {
        Random rand = new Random();
        int range = end - start + 1;

        int random = rand.nextInt(range) + 1;
        while(excludeRows.contains(random)) {
            random = rand.nextInt(range) + 1;
        }

        return random;
    }

public void setValues(int p){
    if(p==1){
        img = R.drawable.sunny;
        name="Sunny Narang";
        details="BE-CSE, 2nd Yr.";
    }
    else if(p==2){

        img = R.drawable.shriram;
        name="Shriram Choubey";
        details="BE-CSE, 2nd Yr.";

    }
    else if(p==3){

        img = R.drawable.saurav;
        name="Saurav";
        details="BE-CSE, 2nd Yr.";
    }
    else if(p==4){

        img = R.drawable.shashanknew;
        name="Shashank Awasthi";
        details="BTech-CSE, 2nd Yr.";
    }
}
    public void setData(SimpleDraweeView sv, TextView nametv, TextView detailstv){
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME) // "res"
                .path(String.valueOf(img))
                .build();
// uri looks like res:/123456789
        sv.setImageURI(uri);
        nametv.setText(name);
        detailstv.setText(details);
    }

}

