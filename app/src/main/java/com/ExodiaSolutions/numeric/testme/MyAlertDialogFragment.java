package com.ExodiaSolutions.numeric.testme;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;

/**
 * Created by Sunny Narang on 05-09-2016.
 */
public class MyAlertDialogFragment extends DialogFragment implements View.OnClickListener {
    RecyclerCustomAdapter adapter;
    static ArrayList<Details> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    static String[] color = {"EF5350", "AB47BC", "7E57C2", "5C6BC0", "42A5F5", "29B6F6", "26A69A", "FF7043", "BDBDBD", "78909C"};
    public MyAlertDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
       /* int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);
        */dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
    public static MyAlertDialogFragment newInstance() {
        MyAlertDialogFragment frag = new MyAlertDialogFragment();
        Bundle args = new Bundle();

        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.password_fd, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        adapter=new RecyclerCustomAdapter(MyAlertDialogFragment.this,arrayList);
        recyclerView.setAdapter(adapter);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
         GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnItemTouchListener( new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // TODO Handle item click
                Test.viewPager.setCurrentItem(position);
                dismiss();
            }
        }));

    }

   public static class RecyclerCustomAdapter extends
            RecyclerView.Adapter<RecyclerCustomAdapter.ViewHolder> {

        Context mContext;
        ArrayList<Details> mArrayList;

        //constructor
        public RecyclerCustomAdapter(MyAlertDialogFragment context, ArrayList<Details> marrayList){
            mContext = getContext();
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
            View contactView = inflater.inflate(R.layout.subject_card ,parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;

        }


       @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {

            // Get the data model based on position
            Details details = mArrayList.get(position);
            //Toast.makeText(mContext, ""+sub_class1.getName(), Toast.LENGTH_SHORT).show();
           String color="#b00b00";
if(details.getGiven().equalsIgnoreCase("1")) {
    color="#00b00b";
}
           else{
    color="#b00b00";
}
           TextDrawable drawable = TextDrawable.builder()
                   .buildRound(String.valueOf(details.getQ_no()), Color.parseColor(color));

           ImageView imageView = viewHolder.imageView;
           imageView.setImageDrawable(drawable);

            // Set item views based on your views and data model

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

            public ImageView imageView;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);

                imageView= (ImageView) itemView.findViewById(R.id.sub_card_img);

            }
        }
    }



}