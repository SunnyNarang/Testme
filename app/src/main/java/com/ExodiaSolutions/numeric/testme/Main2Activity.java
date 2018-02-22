package com.ExodiaSolutions.numeric.testme;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {

    static public String name, email, photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(Main2Activity.this);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        photo = getIntent().getStringExtra("photo");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Exam"));
        tabLayout.addTab(tabLayout.newTab().setText("Results"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.log_out) {
            Intent i = new Intent(Main2Activity.this, Testing.class);
            i.putExtra("remember_not", true);
            startActivity(i);
            finish();
            return true;
        } else if (id == R.id.profile) {
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Main2Activity.this, Profile.class);
            i.putExtra("name", name);
            i.putExtra("email", email);
            i.putExtra("photo", photo);
            startActivity(i);
            return true;
        }else if (id == R.id.developers) {
            Intent i = new Intent(Main2Activity.this, DeveloperPage.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class RecyclerCustomAdapter extends
            RecyclerView.Adapter<Main2Activity.RecyclerCustomAdapter.ViewHolder> {

        Context mContext;
        ArrayList<result_data> mArrayList;

        //constructor
        public RecyclerCustomAdapter(Context context, ArrayList<result_data> marrayList) {
            mContext = context;
            mArrayList = marrayList;
        }

        private Context getContext() {
            return mContext;
        }

        @Override
        public Main2Activity.RecyclerCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View contactView = inflater.inflate(R.layout.result_card, parent, false);

            Main2Activity.RecyclerCustomAdapter.ViewHolder viewHolder = new Main2Activity.RecyclerCustomAdapter.ViewHolder(contactView);
            return viewHolder;

        }


        @Override
        public void onBindViewHolder(Main2Activity.RecyclerCustomAdapter.ViewHolder viewHolder, int position) {

            result_data a = mArrayList.get(position);
            viewHolder.title.setText(a.getName());
            viewHolder.time.setText("Time Taken: "+a.getTime()+" Min");
            viewHolder.score.setText(a.getGet());
            viewHolder.total.setText(a.getTotal());
        }

        @Override
        public int getItemCount() {
            return mArrayList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public TextView title,time,score,total;
            public ViewHolder(View itemView) {

                super(itemView);

                title = (TextView) itemView.findViewById(R.id.exam_name);
                time = (TextView) itemView.findViewById(R.id.time_taken);
                score = (TextView) itemView.findViewById(R.id.score_get);
                total = (TextView) itemView.findViewById(R.id.score_total);
            }
        }

    }
}