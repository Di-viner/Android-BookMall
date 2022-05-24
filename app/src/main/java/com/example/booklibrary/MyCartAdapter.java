package com.example.booklibrary;
import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.simple.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;


public class MyCartAdapter extends BaseExpandableListAdapter {

    public static final String TAG_REFRESH_PRICE = "tag_refresh_price";
    public static final String TAG_REFRESH_NUM = "tag_refresh_num";
    private final List<MainBean> mainBeanList;
    private final List<List<MainBean.MainItemBean>> mainItemBeanList;
    private final Context context;
    private List<MainBean.MainItemBean> selectList;

    public MyCartAdapter(List<MainBean> mainBeanList, List<List<MainBean.MainItemBean>> mainItemBeanList, Context context) {
        this.mainBeanList = mainBeanList;
        this.mainItemBeanList = mainItemBeanList;
        this.context = context;

        //EventBus注册
        EventBus.getDefault().register(context);
    }

    @Override
    public int getGroupCount() {
        return mainBeanList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mainItemBeanList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mainBeanList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mainItemBeanList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            groupHolder = new GroupHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.cart_head, parent, false);
            groupHolder.check = convertView.findViewById(R.id.item_head_check);
            groupHolder.checkStatus = convertView.findViewById(R.id.item_head_checkStatus);
            groupHolder.isALL = convertView.findViewById(R.id.item_head_isALL);
            groupHolder.layout = convertView.findViewById(R.id.item_head_layout);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }

        //各种控件赋值
        MainBean mainBean = mainBeanList.get(groupPosition);
        groupHolder.checkStatus.setImageResource(mainBean.isSelect() ? R.drawable.radio_choose : R.drawable.radio_normal_black);
        groupHolder.isALL.setText(mainBean.getisALL());

        //取消父item的点击事件
        groupHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //什么处理都不做
            }
        });

        //店铺的选中监听
        groupHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainBean mainBean = mainBeanList.get(groupPosition);
                mainBean.setSelect(!mainBean.isSelect());

                for (int i = 0; i < mainItemBeanList.get(groupPosition).size(); i++) {
                    mainItemBeanList.get(groupPosition).get(i).setSelect(mainBean.isSelect());
                }

                notifyDataSetChanged();

                //更改商品价格
                EventBus.getDefault().post(getAllPrice(mainBeanList), TAG_REFRESH_PRICE);
                //更改所选商品数量
                EventBus.getDefault().post(getAllNum(mainBeanList), TAG_REFRESH_NUM);

            }
        });


        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if (convertView == null) {
            childHolder = new ChildHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
            childHolder.check = convertView.findViewById(R.id.item_check);
            childHolder.checkStatus = convertView.findViewById(R.id.item_checkStatus);
            childHolder.goodsPic = convertView.findViewById(R.id.item_pic);
            childHolder.goodsName = convertView.findViewById(R.id.item_name);
            childHolder.goodsSpec = convertView.findViewById(R.id.item_spec);
            childHolder.goodsOriginalPrice = convertView.findViewById(R.id.item_price);
            childHolder.reduce = convertView.findViewById(R.id.item_reduce);
            childHolder.num = convertView.findViewById(R.id.item_num);
            childHolder.add = convertView.findViewById(R.id.item_add);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        //各种控件赋值
        MainBean.MainItemBean mainItemBean = mainItemBeanList.get(groupPosition).get(childPosition);

        childHolder.checkStatus.setImageResource(mainItemBean.isSelect() ? R.drawable.radio_choose : R.drawable.radio_normal_black);
        childHolder.goodsPic.setImageResource(mainItemBean.getGoodsPic());
        childHolder.goodsName.setText(mainItemBean.getGoodsName());
        childHolder.goodsSpec.setText(mainItemBean.getGoodsSpec());
        childHolder.goodsOriginalPrice.setText("¥" + mainItemBean.getGoodsOriginalPrice());
        childHolder.num.setText(mainItemBean.getGoodsNum());

        //加减器的极限值显示状态（达到最小值）
        if ("1".equals(childHolder.num.getText().toString())) {
            childHolder.reduce.setTextColor(context.getResources().getColor(R.color.color_a1a1a1));
        } else {
            childHolder.reduce.setTextColor(context.getResources().getColor(R.color.color_000000));
        }

        //加减器的极限值显示状态（达到最大值）
        if ("5".equals(childHolder.num.getText().toString())) {
            childHolder.add.setTextColor(context.getResources().getColor(R.color.color_a1a1a1));
        } else {
            childHolder.add.setTextColor(context.getResources().getColor(R.color.color_000000));
        }


        //商品的点选监听
        childHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainBean.MainItemBean mainItemBean = mainItemBeanList.get(groupPosition).get(childPosition);
                mainItemBean.setSelect(!mainItemBean.isSelect());

                //当点选某个店铺下全部商品时，则选中该店铺
                boolean noSelect = false;
                for (int i = 0; i < mainItemBeanList.get(groupPosition).size(); i++) {
                    if (!mainItemBeanList.get(groupPosition).get(i).isSelect()) {
                        noSelect = true;
                    }
                }
                mainBeanList.get(groupPosition).setSelect(!noSelect);

                notifyDataSetChanged();

                //更改商品价格
                EventBus.getDefault().post(getAllPrice(mainBeanList), TAG_REFRESH_PRICE);
                //更改所选商品数量
                EventBus.getDefault().post(getAllNum(mainBeanList), TAG_REFRESH_NUM);

                Log.d("fantasychong_aaa", getAllPrice(mainBeanList));


            }
        });

        //加减器加号
        final ChildHolder finalChildHolder = childHolder;
        childHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainBean.MainItemBean mainItemBean = mainItemBeanList.get(groupPosition).get(childPosition);
                if (Integer.valueOf(mainItemBean.getGoodsNum()) < 5) {
                    mainItemBean.setGoodsNum(Integer.valueOf(finalChildHolder.num.getText().toString()) + 1 + "");
                    notifyDataSetChanged();

                    //更改商品价格
                    EventBus.getDefault().post(getAllPrice(mainBeanList), TAG_REFRESH_PRICE);
                    //更改所选商品数量
                    EventBus.getDefault().post(getAllNum(mainBeanList), TAG_REFRESH_NUM);

                }
            }
        });

        //加减器减号
        childHolder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainBean.MainItemBean mainItemBean = mainItemBeanList.get(groupPosition).get(childPosition);
                if (Integer.valueOf(mainItemBean.getGoodsNum()) > 1) {
                    mainItemBean.setGoodsNum(Integer.valueOf(finalChildHolder.num.getText().toString()) - 1 + "");
                    notifyDataSetChanged();

                    //更改商品价格
                    EventBus.getDefault().post(getAllPrice(mainBeanList), TAG_REFRESH_PRICE);
                    //更改所选商品数量
                    EventBus.getDefault().post(getAllNum(mainBeanList), TAG_REFRESH_NUM);

                }
            }
        });


        return convertView;
    }


    /**
     * 获取全选list
     */
    public List<MainBean> getMainList() {
        return mainBeanList;
    }

    /**
     * 获取商品list的list
     */
    public List<List<MainBean.MainItemBean>> getMainItemList() {
        return mainItemBeanList;
    }

    /**
     * 计算商品价格
     */
    public String getAllPrice(List<MainBean> list) {
        BigDecimal decimal = new BigDecimal("0");
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < mainItemBeanList.get(i).size(); j++) {
                    if (mainItemBeanList.get(i).get(j).isSelect()) {
                        BigDecimal decimalGoodsNum = new BigDecimal(mainItemBeanList.get(i).get(j).getGoodsNum());
                        BigDecimal decimalGoodsPrice = new BigDecimal(mainItemBeanList.get(i).get(j).getGoodsOriginalPrice());
                        //将单价和数量相乘，累加
                        decimal = decimal.add(decimalGoodsNum.multiply(decimalGoodsPrice));
                    }
                }
            }
        }
        return decimal.toString();
    }

    /**
     * 计算商品数量
     */
    public String getAllNum(List<MainBean> list) {
        selectList = new ArrayList<>();
        int allNum = 0;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < mainItemBeanList.get(i).size(); j++) {
                    if (mainItemBeanList.get(i).get(j).isSelect()) {
                        int num = 1 * Integer.valueOf(mainItemBeanList.get(i).get(j).getGoodsNum());
                        allNum = allNum + num;
                    }
                }
            }
        }
        return String.valueOf(allNum);
    }

    /**
     * 删除所选商品
     */
    public void removeGoods() {
        for (int i = 0; i < mainBeanList.size(); i++) {
            if (mainBeanList.get(i).isSelect()) {
                mainBeanList.remove(i);
                mainItemBeanList.remove(i);
            } else {
                for (int j = 0; j < mainItemBeanList.get(i).size(); j++) {
                    if (mainItemBeanList.get(i).get(j).isSelect()) {
                        mainItemBeanList.get(i).remove(j);
                    }
                }
            }
        }
        notifyDataSetChanged();

        //更改商品价格
        EventBus.getDefault().post(getAllPrice(mainBeanList), TAG_REFRESH_PRICE);
        //更改所选商品数量
        EventBus.getDefault().post(getAllNum(mainBeanList), TAG_REFRESH_NUM);

    }



    /**
     * 获取所选商品
     */
    public List<MainBean.MainItemBean> getSelectList(){
        selectList= new ArrayList<>();
        for (int i= 0; i< mainBeanList.size(); i++){
            for (int j= 0; j< mainItemBeanList.get(i).size(); j++){
                if (mainItemBeanList.get(i).get(j).isSelect()){
                    selectList.add(mainItemBeanList.get(i).get(j));
                }
            }
        }
        return selectList;
    }



    public void onDestory() {
        //EventBus取消注册
        EventBus.getDefault().unregister(context);
    }


    class ChildHolder {
        RelativeLayout check; //选择点击框
        ImageView checkStatus; //选择状态图片
        ImageView goodsPic; //商品图片
        TextView goodsName; //商品名称
        TextView goodsSpec; //商品规格
        TextView goodsOriginalPrice; //商品原价
        TextView reduce; //加减器减号
        TextView num; //加减器数量
        TextView add; //加减器加号
    }

    class GroupHolder {
        RelativeLayout check; //选择点击框
        ImageView checkStatus; //选择状态图片
        TextView isALL; //店铺名称
        LinearLayout layout; //父item布局
    }
}
