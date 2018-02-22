package com.ExodiaSolutions.numeric.testme;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by shriram on 7/27/2016.
 */

public class PageFragment extends Fragment implements ViewPager.OnClickListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    JSONObject obj; SimpleDraweeView que_img;
    String answered;
    static ImageView img_a,img_b,img_c,img_d;
   // String day[] = new String[] {"", "Monday", "Tuesday", "Wednesday","Thursday", "Friday", "Saturday" };
    private int mPage;
    String exam_json;
    int flag=0;
    ArrayList<TestData> arrayList = new ArrayList<>();
    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.layout_test_playground, container, false);
        File filesDir = getContext().getFilesDir();

        File todoFile = new File(filesDir, "questionare.txt");
        try {
            exam_json = new String(FileUtils.readFileToString(todoFile));
        } catch (IOException e) {
        }
        File todoFile1 = new File(filesDir, "answered.txt");
        try {
            answered = new String(FileUtils.readFileToString(todoFile1));
        } catch (IOException e) {
        }

        if (!exam_json.equalsIgnoreCase("")) {
            try {

                JSONArray root = new JSONArray(exam_json);


                obj = root.optJSONObject(mPage-1);
                arrayList.add(new TestData(obj.getString("question"), obj.getString("option_a"), obj.getString("option_b"), obj.getString("option_c"), obj.getString("option_d"), obj.getString("answer"), obj.getString("ques_image")));

            } catch (JSONException e) {
                e.printStackTrace();
            }}

        TextView que = (TextView) view.findViewById(R.id.que);

        que_img = (SimpleDraweeView) view.findViewById(R.id.ques_img);
        TextView a = (TextView) view.findViewById(R.id.option_a);
        TextView b = (TextView) view.findViewById(R.id.option_b);
        TextView c = (TextView) view.findViewById(R.id.option_c);
        TextView d = (TextView) view.findViewById(R.id.option_d);

        img_a = (ImageView) view.findViewById(R.id.option_a_img);
        img_b = (ImageView) view.findViewById(R.id.option_b_img);
        img_c = (ImageView) view.findViewById(R.id.option_c_img);
        img_d = (ImageView) view.findViewById(R.id.option_d_img);


        TextDrawable drawablea = TextDrawable.builder()
                .buildRound(String.valueOf("A"), Color.parseColor("#546E7A"));
        img_a.setImageDrawable(drawablea);
        TextDrawable drawableb = TextDrawable.builder()
                .buildRound(String.valueOf("B"), Color.parseColor("#546E7A"));
        img_b.setImageDrawable(drawableb);
        TextDrawable drawablec = TextDrawable.builder()
                .buildRound(String.valueOf("C"), Color.parseColor("#546E7A"));
        img_c.setImageDrawable(drawablec);
        TextDrawable drawabled = TextDrawable.builder()
                .buildRound(String.valueOf("D"), Color.parseColor("#546E7A"));
        img_d.setImageDrawable(drawabled);

        if(String.valueOf(answered.charAt(mPage-1)).equalsIgnoreCase("a")){
            TextDrawable drawable1 = TextDrawable.builder()
                    .buildRound(String.valueOf("A"), Color.parseColor("#00c853"));
            img_a.setImageDrawable(drawable1);
        }else if(String.valueOf(answered.charAt(mPage-1)).equalsIgnoreCase("b")){
            TextDrawable drawable1 = TextDrawable.builder()
                    .buildRound(String.valueOf("B"), Color.parseColor("#00c853"));
            img_b.setImageDrawable(drawable1);
        }else if(String.valueOf(answered.charAt(mPage-1)).equalsIgnoreCase("c")){
            TextDrawable drawable1 = TextDrawable.builder()
                    .buildRound(String.valueOf("C"), Color.parseColor("#00c853"));
            img_c.setImageDrawable(drawable1);
        } else if(String.valueOf(answered.charAt(mPage-1)).equalsIgnoreCase("d")){
            TextDrawable drawable1 = TextDrawable.builder()
                    .buildRound(String.valueOf("D"), Color.parseColor("#00c853"));
            img_d.setImageDrawable(drawable1);
        }


        LinearLayout l= (LinearLayout) view.findViewById(R.id.linear_select_a);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearall();
                //Toast.makeText(getContext(), "he jkdsjklagjdlk kjanvlkannkdjlaf"+mPage, Toast.LENGTH_SHORT).show();

                if(Test.answer[mPage-1]!=null&&Test.answer[mPage-1].equalsIgnoreCase("1")){
                Test.answer[mPage-1]=null;
                }
                else {
                    optionSelect(getView());
                    TextDrawable drawablea = TextDrawable.builder()
                            .buildRound(String.valueOf("A"), Color.parseColor("#00C407"));
                    ImageView imageView = (ImageView) getView().findViewById(R.id.option_a_img);
                    imageView.setImageDrawable(drawablea);
                    Test.answer[mPage - 1] = "1";
                }

            }
        });

        LinearLayout l2= (LinearLayout) view.findViewById(R.id.linear_select_b);
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearall();
                //Toast.makeText(getContext(), "he jkdsjklagjdlk kjanvlkannkdjlaf"+mPage, Toast.LENGTH_SHORT).show();


                if(Test.answer[mPage-1]!=null&&Test.answer[mPage-1].equalsIgnoreCase("2")){
                    Test.answer[mPage-1]=null;
                }
                else{
                optionSelect(getView());

                TextDrawable drawablea = TextDrawable.builder()
                        .buildRound(String.valueOf("B"), Color.parseColor("#00C407"));
                ImageView imageView= (ImageView) getView().findViewById(R.id.option_b_img);
                Test.answer[mPage-1] = "2";
                imageView.setImageDrawable(drawablea);}
            }
        }); LinearLayout l3= (LinearLayout) view.findViewById(R.id.linear_select_c);
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearall();
                //Toast.makeText(getContext(), "he jkdsjklagjdlk kjanvlkannkdjlaf"+mPage, Toast.LENGTH_SHORT).show();

                if(Test.answer[mPage-1]!=null&&Test.answer[mPage-1].equalsIgnoreCase("3")){
                    Test.answer[mPage-1]=null;
                }
                else{

                optionSelect(getView());
                Test.answer[mPage-1] = "3";
                TextDrawable drawablea = TextDrawable.builder()
                        .buildRound(String.valueOf("C"), Color.parseColor("#00C407"));
                ImageView imageView= (ImageView) getView().findViewById(R.id.option_c_img);
                imageView.setImageDrawable(drawablea);}
            }
        }); LinearLayout l4= (LinearLayout) view.findViewById(R.id.linear_select_d);
        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearall();
                //Toast.makeText(getContext(), "he jkdsjklagjdlk kjanvlkannkdjlaf"+mPage, Toast.LENGTH_SHORT).show();

                if(Test.answer[mPage-1]!=null&&Test.answer[mPage-1].equalsIgnoreCase("4")){
                    Test.answer[mPage-1]=null;
                }
                else{
                optionSelect(getView());
                Test.answer[mPage-1] = "4";
                TextDrawable drawablea = TextDrawable.builder()
                        .buildRound(String.valueOf("D"), Color.parseColor("#00C407"));
                ImageView imageView= (ImageView) getView().findViewById(R.id.option_d_img);
                imageView.setImageDrawable(drawablea);}
            }
        });
        TestData td = arrayList.get(0);

        que.setText(mPage+". "+td.getQuestion());
        if(td.getQuesImage().equals("0")||td.getQuesImage().equals("none")||td.getQuesImage().equals("")){
            que_img.setVisibility(View.GONE);
            //Toast.makeText(getContext(), "inside if", Toast.LENGTH_SHORT).show();
            //Log.d("dfa","inside if");
        }else{
            Uri imageUri = Uri.parse( "http://test.numericinfosystem.in/images/"+td.getQuesImage() );

            que_img.setImageURI(imageUri);
          //  Toast.makeText(getContext(), "inside else : "+td.getQuesImage(), Toast.LENGTH_SHORT).show();

        }
        a.setText(td.getA());
        b.setText(td.getB());
        c.setText(td.getC());
        d.setText(td.getD());


        return view;

    }


    @Override
    public void onClick(View view) {
        Toast.makeText(getContext(), "YOYO", Toast.LENGTH_SHORT).show();

    }

    private void writeItems(String s,String filename) {

        File filesDir = getContext().getFilesDir();
        File todoFile = new File(filesDir, filename);
        try {


            FileUtils.writeStringToFile(todoFile ,s);   // TODO: add depenencies for fill utils
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void optionSelect(View view) {
        ImageView imageView= (ImageView) view.findViewById(R.id.option_a_img);
        TextDrawable drawablea = TextDrawable.builder()
                .buildRound(String.valueOf("A"), Color.parseColor("#546E7A"));
        imageView.setImageDrawable(drawablea);

        imageView= (ImageView) view.findViewById(R.id.option_b_img);
        TextDrawable drawableb = TextDrawable.builder()
                .buildRound(String.valueOf("B"), Color.parseColor("#546E7A"));
        imageView.setImageDrawable(drawableb);

        imageView= (ImageView) view.findViewById(R.id.option_c_img);
        TextDrawable drawablec = TextDrawable.builder()
                .buildRound(String.valueOf("C"), Color.parseColor("#546E7A"));
        imageView.setImageDrawable(drawablec);

        imageView= (ImageView) view.findViewById(R.id.option_d_img);
        TextDrawable drawabled = TextDrawable.builder()
                .buildRound(String.valueOf("D"), Color.parseColor("#546E7A"));
        imageView.setImageDrawable(drawabled);
    }


    public void clearall(){

        ImageView imageViewa= (ImageView) getView().findViewById(R.id.option_a_img);
        ImageView imageViewb= (ImageView) getView().findViewById(R.id.option_b_img);
        ImageView imageViewc= (ImageView) getView().findViewById(R.id.option_c_img);
        ImageView imageViewd= (ImageView) getView().findViewById(R.id.option_d_img);



        TextDrawable drawablea = TextDrawable.builder()
                .buildRound(String.valueOf("A"), Color.parseColor("#546E7A"));
        imageViewa.setImageDrawable(drawablea);

        drawablea = TextDrawable.builder()
                .buildRound(String.valueOf("B"), Color.parseColor("#546E7A"));
        imageViewb.setImageDrawable(drawablea);

        drawablea = TextDrawable.builder()
                .buildRound(String.valueOf("C"), Color.parseColor("#546E7A"));
        imageViewc.setImageDrawable(drawablea);

        drawablea = TextDrawable.builder()
                .buildRound(String.valueOf("D"), Color.parseColor("#546E7A"));
        imageViewd.setImageDrawable(drawablea);
    }


}
