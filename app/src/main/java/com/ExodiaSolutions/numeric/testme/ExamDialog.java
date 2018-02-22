package com.ExodiaSolutions.numeric.testme;

/**
 * Created by Sunny on 09-01-2017.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;



/**
 * Created by Sunny Narang on 05-09-2016.
 */
public class ExamDialog extends DialogFragment implements View.OnClickListener {
    RecyclerCustomAdapter adapter;
    static ArrayList<ExamClass> arrayList = new ArrayList<>();
    static String exam_id;
    RecyclerView recyclerView;
    static String[] color = {"EF5350", "AB47BC", "7E57C2", "5C6BC0", "42A5F5", "29B6F6", "26A69A", "FF7043", "BDBDBD", "78909C"};
    public ExamDialog() {
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
    public static ExamDialog newInstance() {
        ExamDialog frag = new ExamDialog();
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
        TextView tv = (TextView) view.findViewById(R.id.FD_title);
        tv.setText("Exams");
        adapter=new RecyclerCustomAdapter(ExamDialog.this,arrayList);



        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener( new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // TODO Handle item click
                TextView tv1 = (TextView) view.findViewById(R.id.topic_name);

                ExamClass ec = arrayList.get(position);
                Intent i = new Intent(getContext(),Test.class);
                i.putExtra("exam_id",ec.getId());
                startActivity(i);
                dismiss();
            }
        }));

    }

    public static class RecyclerCustomAdapter extends
            RecyclerView.Adapter<RecyclerCustomAdapter.ViewHolder> {

        Context mContext;
        ArrayList<ExamClass> mArrayList;

        //constructor
        public RecyclerCustomAdapter(ExamDialog context, ArrayList<ExamClass> marrayList){
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
            View contactView = inflater.inflate(R.layout.topic_card ,parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;

        }


        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {

            ExamClass exam_class = mArrayList.get(position);


            TextView textView = viewHolder.textView;
            textView.setText((position+1)+". "+exam_class.getName());


        }

        @Override
        public int getItemCount() {
            return mArrayList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);

                textView= (TextView) itemView.findViewById(R.id.topic_name);

            }
        }
    }



}
