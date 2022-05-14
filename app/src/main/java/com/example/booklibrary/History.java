package com.example.booklibrary;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

public class History extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private SmartRefreshLayout smartRefreshLayout;
    View view;
    String username;
    private ArrayList<HistoryItem> historyItems;
    ///
    private boolean isVisibleToUser;
    public boolean isInit = false;
    public History(String username){
        this.username = username;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        setParam();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        if(view == null){
            view = inflater.inflate(R.layout.history, container, false);
            isInit = true;
            setParam();
        }


        return view;
    }
    private void setParam(){
        if (isInit && isVisibleToUser){
            initViews();


            initData();
            initAdapter();
        }

    }

    private void initViews() {
        listView = (ListView) view.findViewById(R.id.main_listView);
        smartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.main_smartRefreshLayout);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });


    }

    private void initAdapter(){
        MyHistoryAdapter myHistoryAdapter = new MyHistoryAdapter(historyItems, getActivity());
        listView.setAdapter(myHistoryAdapter);
        listView.setOnItemClickListener(this);
        Log.d("msg","history create");
    }

    private void initData(){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(getActivity(), "db_test.db", null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        this.historyItems = new ArrayList<>();
        Cursor cursor = db.query("history",null,"username = ?",new String[]{username},null,null,null);
        if(cursor.moveToFirst()){
            do {
                int i = cursor.getColumnIndex("orderID"), j = cursor.getColumnIndex("name"),
                        k = cursor.getColumnIndex("totalPrice");
                int q = cursor.getColumnIndex("time"), n = cursor.getColumnIndex("num"),
                        p = cursor.getColumnIndex("pic");
                String orderID = cursor.getString(i);
                String name = cursor.getString(j);
                String price = cursor.getString(k);
                String time = cursor.getString(q);
                String num = cursor.getString(n);
                Integer pic = cursor.getInt(p);
                HistoryItem historyItem = new HistoryItem(orderID, name, num, price, time, pic);
                this.historyItems.add(historyItem);
                //Book book = new Book(name, price, intro, pic, ID, author, content);
                //this.historyItems.add(book);
            }while(cursor.moveToNext());
        }
        cursor.close();
        smartRefreshLayout.finishRefresh();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        BookDetails bookDetails = new BookDetails();
        Bundle bd = new Bundle();
        bd.putString("name",historyItems.get(position).getName());
        DBOpenHelper dbOpenHelper = new DBOpenHelper(getActivity(), "db_test.db", null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("book",null,"name = ?", new String[]{historyItems.get(position).getName()},null,null,null);
        if(cursor.moveToFirst()){
            int i = cursor.getColumnIndex("name"), j = cursor.getColumnIndex("price"), k = cursor.getColumnIndex("ID"),
                    l = cursor.getColumnIndex("author"), m = cursor.getColumnIndex("content"), n = cursor.getColumnIndex("pic"),
                    o = cursor.getColumnIndex("intro");
            bd.putString("intro",cursor.getString(o));
            bd.putString("price",cursor.getString(j));
            bd.putString("ID",cursor.getString(k));
            bd.putString("author",cursor.getString(i));
            bd.putString("content",cursor.getString(m));
            bd.putInt("pic",cursor.getInt(n));
            bd.putString("username",username);
        }
        bookDetails.setArguments(bd);
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fl,bookDetails);
        //fragmentTransaction.hide(this);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
