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

/**
 * Created by Sunny Narang on 05-09-2016.
 */
public class AfterExamDialogFragment extends DialogFragment {
    public AfterExamDialogFragment() {
    }
    String que_no,total_marks,topic,attempted,timetaken;

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        String data[] = tag.split("``%f%``");
        total_marks = data[0];
        que_no = data[1];
        topic=data[3];
        attempted=data[4];
        timetaken=data[2];
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
    public static AfterExamDialogFragment newInstance() {
        AfterExamDialogFragment frag = new AfterExamDialogFragment();
        Bundle args = new Bundle();

        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.after_exam_dialog, container);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView t1 = (TextView) view.findViewById(R.id.correct);
        TextView t2 = (TextView) view.findViewById(R.id.score);
        TextView t3 = (TextView) view.findViewById(R.id.timetaken);
        TextView t4 = (TextView) view.findViewById(R.id.topic);
        TextView t5 = (TextView) view.findViewById(R.id.attempted);
        t1.setText(que_no);
        t2.setText(total_marks);
        t3.setText(timetaken+" Min");
        t4.setText(topic);
        t5.setText(attempted);
    }


}