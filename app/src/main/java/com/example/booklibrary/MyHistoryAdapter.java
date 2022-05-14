package com.example.booklibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyHistoryAdapter extends BaseAdapter {
    private List<HistoryItem> historyItemList;
    private Context context;

    public MyHistoryAdapter(List<HistoryItem> historyItems, Context context){
        this.historyItemList = historyItems;
        this.context = context;
    }
    @Override
    public int getCount() {
        return historyItemList.size();
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
        MyHistoryAdapter.ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.history_item,parent,false);
            viewHolder = new MyHistoryAdapter.ViewHolder();
            viewHolder.item_pic = (ImageView) convertView.findViewById(R.id.item_pic);
            viewHolder.item_num = (TextView) convertView.findViewById(R.id.item_num);
            viewHolder.item_name = (TextView) convertView.findViewById(R.id.item_name);
            viewHolder.item_price = (TextView) convertView.findViewById(R.id.item_price);
            viewHolder.item_orderID = (TextView) convertView.findViewById(R.id.item_orderID);
            viewHolder.item_time = (TextView) convertView.findViewById(R.id.item_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.item_pic.setImageResource(historyItemList.get(position).getPic());
        //String s1 = historyItemList.get(position).getName();
        //String s2 = historyItemList.get(position).getTotalPrice();
        //String s3 = historyItemList.get(position).getOrderID();
        //String s4 = historyItemList.get(position).getTime();
        //String s5 = historyItemList.get(position).getNum();
        viewHolder.item_name.setText(historyItemList.get(position).getName());
        viewHolder.item_price.setText("￥"+historyItemList.get(position).getTotalPrice());
        viewHolder.item_orderID.setText(historyItemList.get(position).getOrderID());
        viewHolder.item_time.setText(historyItemList.get(position).getTime());
        viewHolder.item_num.setText(historyItemList.get(position).getNum());
        return convertView;
    }

    private class ViewHolder{
        TextView item_name;
        TextView item_orderID;
        TextView item_price;
        TextView item_time;
        TextView item_num;
        ImageView item_pic;
    }
}
