package com.example.booklibrary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class BookDetails extends Fragment {
    BookDetails(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_detail, container, false);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_intro = (TextView) view.findViewById(R.id.tv_intro);
        TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
        ImageView iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
        //getArgument获取传递过来的Bundle对象
        tv_name.setText(getArguments().getString("name"));
        tv_intro.setText(getArguments().getString("intro"));
        tv_price.setText(getArguments().getString("price"));
        iv_pic.setImageResource(getArguments().getInt("pic"));
        return view;
    }
}
