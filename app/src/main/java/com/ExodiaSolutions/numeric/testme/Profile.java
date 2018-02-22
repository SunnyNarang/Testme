package com.ExodiaSolutions.numeric.testme;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

public class Profile extends AppCompatActivity {
    private String photo,name,email;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                Profile.this.finish();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(Profile.this);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        name = getIntent().getStringExtra("name");
        photo = getIntent().getStringExtra("photo");
        email = getIntent().getStringExtra("email");
        TextView namev= (TextView) findViewById(R.id.namev);
        TextView emailv = (TextView) findViewById(R.id.emailv);
        SimpleDraweeView photov = (SimpleDraweeView) findViewById(R.id.photov);
        namev.setText(name);
        emailv.setText(email);


        Uri imageUri = Uri.parse(photo);
        Drawable myIcon = getResources().getDrawable( R.drawable.user_wh );

        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setFadeDuration(300)
                .setPlaceholderImage(myIcon)
                .build();
        photov.setHierarchy(hierarchy);

        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setBorder(Color.parseColor("#ffffff"), 3);
        roundingParams.setRoundAsCircle(true);
        photov.getHierarchy().setRoundingParams(roundingParams);
        photov.setImageURI(imageUri);
    }
}
