package com.example.booklibrary;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
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



    View view;
    public ShoppingCart(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //super.onCreateView(inflater,container,savedInstanceState);
        view = inflater.inflate(R.layout.cartlist, container, false);
        initViews();
        //配置适配器
        initAdapter();
        //配置数据
        initData();
        return view;
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
        MainBean.MainItemBean mainItemBean = new MainBean.MainItemBean(false, "1", R.mipmap.ic_launcher, "iPhoneXs Max", "银色 8G+256G", "9989", "1");
        MainBean.MainItemBean mainItemBean1 = new MainBean.MainItemBean(false, "2", R.mipmap.ic_launcher, "华为P20 Pro", "深空灰色 6G+128G", "5299", "1");
        MainBean.MainItemBean mainItemBean2 = new MainBean.MainItemBean(false, "3", R.mipmap.ic_launcher, "小米9", "中国红 8G+128G", "4999", "2");
        MainBean.MainItemBean mainItemBean3 = new MainBean.MainItemBean(false, "4", R.mipmap.ic_launcher, "三星Galaxy S10+", "黑色 4G+64G", "9998", "1");
        MainBean.MainItemBean mainItemBean4 = new MainBean.MainItemBean(false, "5", R.mipmap.ic_launcher, "魅族16th+", "黑色 4G+64G", "2698", "1");
        MainBean.MainItemBean mainItemBean5 = new MainBean.MainItemBean(false, "6", R.mipmap.ic_launcher, "OPPO FINDX", "黑色 4G+64G", "2698", "1");

        List<MainBean.MainItemBean> mainItemBeanList = new ArrayList<>();
        mainItemBeanList.add(mainItemBean);
        mainItemBeanList.add(mainItemBean1);

        List<MainBean.MainItemBean> mainItemBeanList1 = new ArrayList<>();
        mainItemBeanList1.add(mainItemBean2);
        mainItemBeanList1.add(mainItemBean3);

        List<MainBean.MainItemBean> mainItemBeanList2 = new ArrayList<>();
        mainItemBeanList2.add(mainItemBean4);
        mainItemBeanList2.add(mainItemBean5);

        //店铺测试数据
        MainBean mainBean = new MainBean(false, "天猫金牌卖家一号", mainItemBeanList);
        MainBean mainBean1 = new MainBean(false, "地汪银牌卖家二号", mainItemBeanList);
        MainBean mainBean2 = new MainBean(false, "京西铜牌卖家三号", mainItemBeanList);

        //添加店铺列表
        adapter.getMainList().clear();
        adapter.getMainList().add(mainBean);
        adapter.getMainList().add(mainBean1);
        adapter.getMainList().add(mainBean2);

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
            for (int i = 0; i < mainItemBeanList.size(); i++) {
                for (int j = 0; j < selectBeanList.size(); j++) {
                    if (selectBeanList.get(j).getId().equals(mainItemBeanList1.get(i).getId())) {
                        mainItemBeanList1.get(i).setSelect(selectBeanList.get(j).isSelect());
                    }
                }
            }
            for (int i = 0; i < mainItemBeanList.size(); i++) {
                for (int j = 0; j < selectBeanList.size(); j++) {
                    if (selectBeanList.get(j).getId().equals(mainItemBeanList2.get(i).getId())) {
                        mainItemBeanList2.get(i).setSelect(selectBeanList.get(j).isSelect());
                    }
                }
            }
        }

        //添加商品布局
        adapter.getMainItemList().clear();
        adapter.getMainItemList().add(mainItemBeanList);
        adapter.getMainItemList().add(mainItemBeanList1);
        adapter.getMainItemList().add(mainItemBeanList2);

        //根据子item的选中状态来决定店铺的点选状态
        boolean noSelect = false;
        for (int j = 0; j < adapter.getMainItemList().get(0).size(); j++) {
            if (!adapter.getMainItemList().get(0).get(j).isSelect()) {
                noSelect = true;
            }
            adapter.getMainList().get(0).setSelect(!noSelect);
        }

        boolean noSelect1 = false;
        for (int j = 0; j < adapter.getMainItemList().get(1).size(); j++) {
            if (!adapter.getMainItemList().get(1).get(j).isSelect()) {
                noSelect1 = true;
            }
            adapter.getMainList().get(1).setSelect(!noSelect1);
        }

        boolean noSelect2 = false;
        for (int j = 0; j < adapter.getMainItemList().get(2).size(); j++) {
            if (!adapter.getMainItemList().get(2).get(j).isSelect()) {
                noSelect2 = true;
            }
            adapter.getMainList().get(2).setSelect(!noSelect2);
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
                    Log.d("fantasychong_selctList", adapter.getSelectList().toString());
                } else {
                    adapter.removeGoods();
                }
                break;
            default:
                break;
        }
    }
}
