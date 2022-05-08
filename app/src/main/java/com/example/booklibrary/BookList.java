package com.example.booklibrary;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class BookList extends Fragment implements AdapterView.OnItemClickListener {
    private ListView lv_booklist;
    private FragmentManager fManager;
    private ArrayList<Book> books;

    public BookList(FragmentManager fm, ArrayList<Book> alb){
        this.fManager = fm;
        this.books = alb;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fg_booklist, container, false);
        lv_booklist = (ListView) view.findViewById(R.id.lv_booklist);
        MyAdapter myAdapter = new MyAdapter(books, getActivity());
        lv_booklist.setAdapter(myAdapter);
        lv_booklist.setOnItemClickListener(this);
        //TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        //txt_content.setText("书单页");
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        BookDetails bookDetails = new BookDetails();
        Bundle bd = new Bundle();
        bd.putString("name",books.get(position).getName());
        bd.putString("intro",books.get(position).getIntro());
        bd.putString("price",books.get(position).getPrice());
        bd.putInt("pic",books.get(position).getPic());
        bookDetails.setArguments(bd);

        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fl,bookDetails);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
