package com.example.booklibrary;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.example.booklibrary.MyCartAdapter.TAG_REFRESH_NUM;
import static com.example.booklibrary.MyCartAdapter.TAG_REFRESH_PRICE;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

public class ShoppingCart extends Fragment implements View.OnClickListener {

    public static final String TAG_REFRESH_PRICE = "tag_refresh_price";
    public static final String TAG_REFRESH_NUM = "tag_refresh_num";

    private ExpandableListView expandableListView;
    private SmartRefreshLayout smartRefreshLayout;
    private MyCartAdapter adapter;
    private TextView priceTv;
    private LinearLayout submit;
    private TextView numTv;
    private TextView submitTv;
    private TextView edit;
    private SelectBean selectBean;


    ///
    private boolean isVisibleToUser;
    public boolean isInit = false;
    String username;

    private View view;
    public ShoppingCart(String username){
        this.username = username;
    }
    ///
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        setParam();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //super.onCreateView(inflater,container,savedInstanceState);
        if (view == null)
        {
            view = inflater.inflate(R.layout.cartlist, container, false);
            isInit = true;
            setParam();
        }

        return view;
    }
    private void setParam() {
        if (isInit && isVisibleToUser) {
            //页面的逻辑和网络请求，下面我随便贴的一点我的代码
            initViews();
            //配置适配器
            initAdapter();
            //配置数据
            initData();
        }
    }


    /**
     * 配置控件
     */
    private void initViews() {
        EventBus.getDefault().register(this);

        expandableListView = (ExpandableListView) view.findViewById(R.id.main_expandableListView);
        smartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.main_smartRefreshLayout);
        priceTv = (TextView) view.findViewById(R.id.main_price);
        submit = (LinearLayout) view.findViewById(R.id.main_submit);
        submitTv = (TextView) view.findViewById(R.id.main_submitTv);
        numTv = (TextView) view.findViewById(R.id.main_num);
        edit = view.findViewById(R.id.main_edit);

        edit.setOnClickListener((View.OnClickListener) this);
        submit.setOnClickListener((View.OnClickListener) this);

        //去除父item的箭头
        expandableListView.setGroupIndicator(null);

        //下拉刷新监听
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
    }




    /**
     * 配置适配器
     */
    private void initAdapter() {
        adapter = new MyCartAdapter(new ArrayList<MainBean>(), new ArrayList<List<MainBean.MainItemBean>>(), getActivity());
        expandableListView.setAdapter(adapter);
    }

    /**
     * 配置数据
     */
    private void initData() {
        //商品测试数据
        List<MainBean.MainItemBean> mainItemBeanList = new ArrayList<>() ;

        ///
        DBOpenHelper dbOpenHelper = new DBOpenHelper(getActivity(), "db_test.db", null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("cart",null,"username = ?",new String[]{username},null,null,null);
        if(cursor.moveToFirst()) {
            do {
                int i = cursor.getColumnIndex("name"),
                        k = cursor.getColumnIndex("price"), p = cursor.getColumnIndex("pic");
                int q = cursor.getColumnIndex("num"),
                        y = cursor.getColumnIndex("ID");
                String name = cursor.getString(i);
                String price = cursor.getString(k);
                String num = cursor.getString(q);
                String author = cursor.getString(y);
                int pic = cursor.getInt(p);
                String ID = cursor.getString(y);
                mainItemBeanList.add(new MainBean.MainItemBean(false,ID,pic,name,author,price,num));
            } while (cursor.moveToNext());
        }
        cursor.close();


        //店铺测试数据
        MainBean mainBean = new MainBean(false, "全选", mainItemBeanList);


        //添加店铺列表
        adapter.getMainList().clear();
        adapter.getMainList().add(mainBean);


        //保存选中状态
        List<SelectBean> selectBeanList = new ArrayList<>();
        if (adapter.getMainItemList().size() != 0 && adapter.getMainItemList() != null) {
            for (int i = 0; i < adapter.getMainList().size(); i++) {
                for (int j = 0; j < adapter.getMainItemList().get(i).size(); j++) {
                    if (adapter.getMainItemList().get(i).get(j).isSelect()) {
                        selectBean = new SelectBean(adapter.getMainItemList().get(i).get(j).getId(), adapter.getMainItemList().get(i).get(j).isSelect());
                        selectBeanList.add(selectBean);
                    }
                }
            }

            for (int i = 0; i < mainItemBeanList.size(); i++) {
                for (int j = 0; j < selectBeanList.size(); j++) {
                    if (selectBeanList.get(j).getId().equals(mainItemBeanList.get(i).getId())) {
                        mainItemBeanList.get(i).setSelect(selectBeanList.get(j).isSelect());
                    }
                }
            }
        }

        //将保存的选中状态赋给新请求到的数据
        if (adapter.getMainItemList().size() != 0 && adapter.getMainItemList() != null) {
            for (int i = 0; i < adapter.getMainList().size(); i++) {
                for (int j = 0; j < adapter.getMainItemList().get(i).size(); j++) {
                    if (adapter.getMainItemList().get(i).get(j).isSelect()) {
                        selectBean = new SelectBean(adapter.getMainItemList().get(i).get(j).getId(), adapter.getMainItemList().get(i).get(j).isSelect());
                        selectBeanList.add(selectBean);
                    }
                }
            }
            for (int i = 0; i < mainItemBeanList.size(); i++) {
                for (int j = 0; j < selectBeanList.size(); j++) {
                    if (selectBeanList.get(j).getId().equals(mainItemBeanList.get(i).getId())) {
                        mainItemBeanList.get(i).setSelect(selectBeanList.get(j).isSelect());
                    }
                }
            }

        }

        //添加商品布局
        adapter.getMainItemList().clear();
        adapter.getMainItemList().add(mainItemBeanList);
        //adapter.getMainItemList().add(mainItemBeanList1);
        //adapter.getMainItemList().add(mainItemBeanList2);

        //根据子item的选中状态来决定店铺的点选状态
        boolean noSelect = false;
        for (int j = 0; j < adapter.getMainItemList().get(0).size(); j++) {
            if (!adapter.getMainItemList().get(0).get(j).isSelect()) {
                noSelect = true;
            }
            adapter.getMainList().get(0).setSelect(!noSelect);
        }

        adapter.notifyDataSetChanged();

        //设置所有的子item都展开
        for (int i = 0; i < adapter.getMainList().size(); i++) {
            expandableListView.expandGroup(i);
        }

        //结束刷新
        smartRefreshLayout.finishRefresh();

    }

    @Subscriber(tag = TAG_REFRESH_PRICE, mode = ThreadMode.MAIN)
    public void onRefreshPrice(String price) {
        priceTv.setText("总价格:" + price);
    }

    /**
     * 更改数量
     */
    @Subscriber(tag = TAG_REFRESH_NUM, mode = ThreadMode.MAIN)
    public void onRefreshNum(String num) {
        numTv.setText("(" + num + ")");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //执行adapter的onDestroy方法
        adapter.onDestory();

        //EventBus取消注册
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onClick(View v) {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(getActivity(), "db_test.db", null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        switch (v.getId()) {
            case R.id.main_edit:
                if ("结算".equals(submitTv.getText().toString())) {
                    submitTv.setText("删除");
                    edit.setText("完成");
                } else {
                    submitTv.setText("结算");
                    edit.setText("编辑");
                }
                break;
            case R.id.main_submit:
                if ("结算".equals(submitTv.getText().toString())) {
                    addToHistory();
                } else {

                    List<MainBean.MainItemBean> selectList = adapter.getSelectList();
                    for(int i = 0; i < selectList.size(); i++) {
                        db.delete("cart", "name = ?", new String[]{selectList.get(i).getGoodsName()});
                    }
                    adapter.removeGoods();
                }
                break;
            default:
                break;
        }
    }

    private void addToHistory() {
        List<MainBean.MainItemBean> selectList = adapter.getSelectList();
        DBOpenHelper dbOpenHelper = new DBOpenHelper(getActivity(), "db_test.db", null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        for(int i = 0; i < selectList.size(); i++){
            SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日   HH:mm:ss");
            Date curDate =  new Date(System.currentTimeMillis());
            ContentValues values = new ContentValues();
            values.put("pic",selectList.get(i).getGoodsPic());
            values.put("username", username);
            values.put("orderID", System.currentTimeMillis() + selectList.get(i).getId());
            values.put("name", selectList.get(i).getGoodsName());
            values.put("num", selectList.get(i).getGoodsNum());
            values.put("time",formatter.format(curDate));
            int totalPrice = Integer.parseInt(selectList.get(i).getGoodsOriginalPrice()) * Integer.parseInt(selectList.get(i).getGoodsNum());
            values.put("totalPrice",String.valueOf(totalPrice));
            db.delete("cart","name = ? and username = ?", new String[]{selectList.get(i).getGoodsName(), username});
            adapter.removeGoods();
            db.insert("history",null, values);
        }
        Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
        Log.d("fantasychong_selctList", adapter.getSelectList().toString());
    }
}
