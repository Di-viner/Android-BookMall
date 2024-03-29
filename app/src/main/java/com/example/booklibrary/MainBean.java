package com.example.booklibrary;

import java.io.Serializable;
import java.util.List;


public class MainBean implements Serializable {
    private boolean isSelect; //购物车全选状态
    private String isALL; //显示全选
    private List<MainItemBean> cartItemBeanList; //商品list

    @Override
    public String toString() {
        return "MainBean{" +
                "isSelect=" + isSelect +
                ", isALL='" + isALL + '\'' +
                ", cartItemBeanList=" + cartItemBeanList +
                '}';
    }

    public MainBean(boolean isSelect, String isALL, List<MainItemBean> cartItemBeanList) {
        this.isSelect = isSelect;
        this.isALL = isALL;
        this.cartItemBeanList = cartItemBeanList;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getisALL() {
        return isALL;
    }

    public void setisALL(String isALL) {
        this.isALL = isALL;
    }

    public List<MainItemBean> getCartItemBeanList() {
        return cartItemBeanList;
    }

    public void setCartItemBeanList(List<MainItemBean> cartItemBeanList) {
        this.cartItemBeanList = cartItemBeanList;
    }

    public static class MainItemBean implements Serializable {
        private boolean isSelect; //商品选中状态
        private int goodsPic; //商品图片
        private String id; //商品id
        private String goodsName; //商品名称
        private String goodsSpec; //商品规格
        private String goodsOriginalPrice; //商品价格
        private String goodsNum;  //商品数量

        public MainItemBean(boolean isSelect, String id, int goodsPic, String goodsName, String goodsSpec, String goodsOriginalPrice, String goodsNum) {
            this.isSelect = isSelect;
            this.id= id;
            this.goodsPic = goodsPic;
            this.goodsName = goodsName;
            this.goodsSpec = goodsSpec;
            this.goodsOriginalPrice = goodsOriginalPrice;
            this.goodsNum = goodsNum;
        }

        @Override
        public String toString() {
            return "CartItemBean{" +
                    "isSelect=" + isSelect +
                    ", goodsPic=" + goodsPic +
                    ", goodsName='" + goodsName + '\'' +
                    ", goodsSpec='" + goodsSpec + '\'' +
                    ", goodsOriginalPrice='" + goodsOriginalPrice + '\'' +
                    ", goodsNum='" + goodsNum + '\'' +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getGoodsPic() {
            return goodsPic;
        }

        public void setGoodsPic(int goodsPic) {
            this.goodsPic = goodsPic;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsSpec() {
            return goodsSpec;
        }

        public void setGoodsSpec(String goodsSpec) {
            this.goodsSpec = goodsSpec;
        }

        public String getGoodsOriginalPrice() {
            return goodsOriginalPrice;
        }

        public void setGoodsOriginalPrice(String goodsOriginalPrice) {
            this.goodsOriginalPrice = goodsOriginalPrice;
        }

        public String getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(String goodsNum) {
            this.goodsNum = goodsNum;
        }
    }
}
