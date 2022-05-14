package com.example.booklibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<Book> mBook;
    private Context mContext;
    //private Integer[] icons = {R.drawable.user, R.drawable.user, R.drawable.user, R.drawable.user,
            //R.drawable.user, R.drawable.user, R.drawable.user, R.drawable.user, R.drawable.user,R.drawable.user};
    public MyAdapter(List<Book> mBook, Context mContext){
        this.mBook = mBook;
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return mBook.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.book_context,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
            viewHolder.tv_intro = (TextView) convertView.findViewById(R.id.tv_intro);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.iv_pic.setImageResource(mBook.get(position).getPic());
        viewHolder.tv_name.setText(mBook.get(position).getName());
        viewHolder.tv_intro.setText(mBook.get(position).getIntro());
        viewHolder.tv_price.setText("¥" + mBook.get(position).getPrice());
        return convertView;
    }

    private class ViewHolder{
        TextView tv_name;
        TextView tv_intro;
        TextView tv_price;
        ImageView iv_pic;
    }
}
