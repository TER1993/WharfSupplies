package com.speedata.wharfsupplies.application;

import android.app.Application;

import com.speedata.wharfsupplies.db.bean.BaseInfor;

import java.util.List;

public class CustomerApplication extends Application {


    private int bposition = -1;
    private int aposition = -1;
    private List<BaseInfor> list;
    private List<BaseInfor> blist;
    private String txtName;
    private String checkTime;

    private String mID;
    private String pswd;
    private int changeuser = 1;


    public int getChangeuser() {
        return changeuser;
    }
    public void setChangeuser(int num) {
        this.changeuser = num;
    }


    public String getID() {
        return mID;
    }

    public void setID(String id) {
        this.mID = id;

    }
    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
    public String getPswd() {
        return pswd;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }
    public String getTxtName() {
        return txtName;
    }
    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }
    public String getCheckTime() {
        return checkTime;
    }



    public int getBposition() {
        return bposition;
    }
    public void setBposition(int num) {
        this.bposition = num;
    }


    public int getAposition() {
        return aposition;
    }
    public void setAposition(int num) {
        this.aposition = num;
    }

    public List<BaseInfor> getList() {
        return list;
    }
    public void setList(List<BaseInfor> list1) {
        this.list = list1;
    }

    public List<BaseInfor> getBaseInfor2() {
        return blist;
    }
    public void setBaseInfo2(List<BaseInfor> list1) {
        this.blist = list1;
    }



}
