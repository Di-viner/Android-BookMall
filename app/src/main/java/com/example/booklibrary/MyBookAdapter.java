package com.example.booklibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class MyBookAdapter extends BaseAdapter implements SectionIndexer, StickyListHeadersAdapter {

    private List<Book> mBook;
    private Context mContext;

    ///
    private LayoutInflater layoutInflater;
    private int[] sectionIndices;   //第一个item
    private String[] sectionHeaders; //{哲理、青春}

    public MyBookAdapter(List<Book> mBook, Context mContext){
        this.mBook = mBook;
        this.mContext = mContext;
        sectionIndices = getSectionIndices();
        sectionHeaders = getSectionHeaders();
    }
    private int[] getSectionIndices() {
        List<Integer> sectionIndices = new ArrayList<Integer>();
        sectionIndices.add(0);
        sectionIndices.add(4);
        sectionIndices.add(7);
        int[] sections = new int[sectionIndices.size()];
        for (int i = 0; i < sectionIndices.size(); i++) {
            sections[i] = sectionIndices.get(i);
        }
        return sections;
    }

    private String[] getSectionHeaders() {
        return new String[]{"哲理","散文","青春"};
    }


    @Override
    public int getCount() {
        return mBook.size();
    }

    @Override
    public Object getItem(int i) {
        //return null;
        return mBook.get(i);
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

    @Override
    public Object[] getSections() {
        return sectionHeaders;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        if (sectionIndex >= sectionIndices.length) {
            sectionIndex = sectionIndices.length - 1;
        } else if (sectionIndex < 0) {
            sectionIndex = 0;
        }
        return sectionIndices[sectionIndex];

    }

    @Override
    public int getSectionForPosition(int position) {
        for (int i = 0; i < sectionIndices.length; i++) {
            if (position < sectionIndices[i]) {
                return i - 1;
            }
        }
        return sectionIndices.length - 1;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {

        HeaderViewHolder hvh;
        if(convertView == null){
            hvh = new HeaderViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.book_header, null);
            hvh.tvHeader = (TextView) convertView.findViewById(R.id.tvHeader);
            convertView.setTag(hvh);
        }else{
            hvh = (HeaderViewHolder)convertView.getTag();
        }
        //hvh.tvHeader.setText(Helper.getFormatDate(tasks.get(position).getCreateTime()));
        hvh.tvHeader.setText(mBook.get(position).getIntro());
        return convertView;

    }

    @Override
    public long getHeaderId(int position) {
        if (mBook.get(position).getIntro().equals("哲理")){
            return 0;
        }
        else if(mBook.get(position).getIntro().equals("散文")){
            return 1;
        }
        else{
            return 2;
        }
    }

    private class ViewHolder{
        TextView tv_name;
        TextView tv_intro;
        TextView tv_price;
        ImageView iv_pic;
    }


    private class HeaderViewHolder{
        TextView tvHeader;
    }

}
