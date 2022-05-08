package com.example.booklibrary;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 3;
    private BookList bookList = null;
    private ShoppingCart shoppingCart = null;
    private History history = null;

    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Book> alb){
        super(fm);
        bookList = new BookList(fm, alb);
        shoppingCart = new ShoppingCart();
        history = new History();
    }


    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case MainActivity.PAGE_ONE:
                fragment = bookList;
                break;
            case MainActivity.PAGE_TWO:
                fragment = shoppingCart;
                break;
            case MainActivity.PAGE_THREE:
                    fragment = history;
                    break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
