package com.example.booklibrary;

import java.io.Serializable;


public class SelectBean implements Serializable {
    private String id;
    private boolean isSelect;

    public SelectBean(String id, boolean isSelect) {
        this.id = id;
        this.isSelect = isSelect;
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
}
