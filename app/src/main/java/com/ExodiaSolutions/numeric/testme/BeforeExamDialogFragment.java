package com.ExodiaSolutions.numeric.testme;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sunny Narang on 05-09-2016.
 */
public class BeforeExamDialogFragment extends DialogFragment {
    public BeforeExamDialogFragment() {
    }
    String que_no,total_marks,each_mark,time,topic,time_per_que;

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        try {
            String data[] = tag.split("``%f%``");
            JSONObject obj = new JSONObject(data[0]);
            que_no = data[1];
           // Test.counts.setText("1/"+que_no);
            Test.total_que=Integer.parseInt(que_no);
            each_mark=obj.getString("mark");
            time_per_que=obj.getString("time");
            time=Integer.parseInt(time_per_que)*Integer.parseInt(que_no)+"";
            total_marks=(Integer.parseInt(each_mark)*Integer.parseInt(que_no))+"";

            topic=obj.getString("exam_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
    public static BeforeExamDialogFragment newInstance() {
        BeforeExamDialogFragment frag = new BeforeExamDialogFragment();
        Bundle args = new Bundle();

        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.before_exam_dialog, container);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView t1 = (TextView) view.findViewById(R.id.total_marks);
        TextView t2 = (TextView) view.findViewById(R.id.time);
        TextView t3 = (TextView) view.findViewById(R.id.each_que_mark);
        TextView t4 = (TextView) view.findViewById(R.id.no_of_que);
        TextView t5 = (TextView) view.findViewById(R.id.topic);
        t1.setText(total_marks);
        t2.setText(time);
        t3.setText(each_mark);
        t4.setText(que_no);
        t5.setText(topic);

    }


}