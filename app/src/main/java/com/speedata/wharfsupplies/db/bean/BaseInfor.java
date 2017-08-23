package com.speedata.wharfsupplies.db.bean;

import com.elsw.base.db.orm.annotation.Column;
import com.elsw.base.db.orm.annotation.Table;
import com.speedata.libutils.excel.Excel;

/**
 * Created by Administrator on 2017/8/14.
 */
@Table(name = "BaseInfor")
public  class BaseInfor { //导入excel表格内容


    @Excel(ignore = false, name = "序号NO.")
    @Column(name = "aNO")
    private String aNO; //序号

    @Excel(ignore = false, name = "箱件编号PKG NO.")
    @Column(name = "bPKGNO")
    private String bPKGNO; //箱件编号

    @Excel(ignore = false, name = "中文品名DESCRIPTION(CN)")
    @Column(name = "cDescriptionCN")
    private String cDescriptionCN; //中文品名

    @Excel(ignore = false, name = "英文品名DESCRIPTION(EN)")
    @Column(name = "dDescriptionEN")
    private String dDescriptionEN; //英文品名

    @Excel(ignore = false, name = "件数PCS")
    @Column(name = "ePCS")
    private String ePCS; //件数

    @Excel(ignore = false, name = "包装方式PKG WAY")
    @Column(name = "fPKGWAY")
    private String fPKGWAY; //包装方式

    @Excel(ignore = false, name = "毛重(千克)G.W.(KG)")
    @Column(name = "gGW")
    private String gGW; //毛重

    @Excel(ignore = false, name = "净重(千克)N.W.(KG)")
    @Column(name = "hNW")
    private String hNW; //净重

    @Excel(ignore = false, name = "长L(CM)")
    @Column(name = "iL")
    private String iL; //长

    @Excel(ignore = false, name = "宽W(CM)")
    @Column(name = "jW")
    private String jW; //宽

    @Excel(ignore = false, name = "高H(CM)")
    @Column(name = "kH")
    private String kH; //高

    @Excel(ignore = false, name = "体积VOL.(M3)")
    @Column(name = "lVOL")
    private String lVOL; //体积

    @Excel(ignore = false, name = "合同号PO NO.")
    @Column(name = "mPONO")
    private String mPONO; //合同号

    @Excel(ignore = false, name = "货源地ORIGIN")
    @Column(name = "nOrigin")
    private String nOrigin; //货源地

    @Excel(ignore = false, name = "供货商SUPPLIER")
    @Column(name = "oSupplier")
    private String oSupplier; //供货商

    @Excel(ignore = false, name = "HS编码HS CODE")
    @Column(name = "pHSCODE")
    private String pHSCODE; //HS编码

    @Excel(ignore = false, name = "总价TOTAL PRICE")
    @Column(name = "qTotalPrice")
    private String qTotalPrice; //总价

    @Excel(ignore = false, name = "币种CURRENCY")
    @Column(name = "rCurrency")
    private String rCurrency; //币种


//    @Excel(ignore = false, name = "净重(千克)N.W.(KG)")
//    @Column(name = "NW")
//    private String NW; //净重
//
//    @Excel(ignore = false, name = "体积VOL.(M3)")
//    @Column(name = "VOL")
//    private String VOL; //体积
//
//    @Excel(ignore = false, name = "箱件编号PKG NO.")
//    @Column(name = "PKGNO")
//    private String PKGNO; //箱件编号
//
//    @Excel(ignore = false, name = "中文品名DESCRIPTION(CN)")
//    @Column(name = "DescriptionCN")
//    private String DescriptionCN; //中文品名
//
//
//    @Excel(ignore = false, name = "高H(CM)")
//    @Column(name = "H")
//    private String H; //高
//
//    @Excel(ignore = false, name = "合同号PO NO.")
//    @Column(name = "PONO")
//    private String PONO; //合同号
//
//    @Excel(ignore = false, name = "长L(CM)")
//    @Column(name = "L")
//    private String L; //长
//
//    @Excel(ignore = false, name = "英文品名DESCRIPTION(EN)")
//    @Column(name = "DescriptionEN")
//    private String DescriptionEN; //英文品名
//
//    @Excel(ignore = false, name = "毛重(千克)G.W.(KG)")
//    @Column(name = "GW")
//    private String GW; //毛重
//
//    @Excel(ignore = false, name = "币种CURRENCY")
//    @Column(name = "Currency")
//    private String Currency; //币种
//
//    @Excel(ignore = false, name = "件数PCS")
//    @Column(name = "PCS")
//    private String PCS; //件数
//
//    @Excel(ignore = false, name = "总价TOTAL PRICE")
//    @Column(name = "TotalPrice")
//    private String TotalPrice; //总价
//
//    @Excel(ignore = false, name = "货源地ORIGIN")
//    @Column(name = "Origin")
//    private String Origin; //货源地
//
//    @Excel(ignore = false, name = "宽W(CM)")
//    @Column(name = "W")
//    private String W; //宽
//
//    @Excel(ignore = false, name = "供货商SUPPLIER")
//    @Column(name = "Supplier")
//    private String Supplier; //供货商
//
//    @Excel(ignore = false, name = "包装方式PKG WAY")
//    @Column(name = "PKGWAY")
//    private String PKGWAY; //包装方式
//
//    @Excel(ignore = false, name = "HS编码HS CODE")
//    @Column(name = "HSCODE")
//    private String HSCODE; //HS编码
//
//    @Excel(ignore = false, name = "序号NO.")
//    @Column(name = "NO")
//    private String NO; //序号


    public String getaNO() {
        return aNO;
    }

    public void setaNO(String aNO) {
        this.aNO = aNO;
    }

    public String getbPKGNO() {
        return bPKGNO;
    }

    public void setbPKGNO(String bPKGNO) {
        this.bPKGNO = bPKGNO;
    }

    public String getcDescriptionCN() {
        return cDescriptionCN;
    }

    public void setcDescriptionCN(String cDescriptionCN) {
        this.cDescriptionCN = cDescriptionCN;
    }

    public String getdDescriptionEN() {
        return dDescriptionEN;
    }

    public void setdDescriptionEN(String dDescriptionEN) {
        this.dDescriptionEN = dDescriptionEN;
    }

    public String getePCS() {
        return ePCS;
    }

    public void setePCS(String ePCS) {
        this.ePCS = ePCS;
    }

    public String getfPKGWAY() {
        return fPKGWAY;
    }

    public void setfPKGWAY(String fPKGWAY) {
        this.fPKGWAY = fPKGWAY;
    }

    public String getgGW() {
        return gGW;
    }

    public void setgGW(String gGW) {
        this.gGW = gGW;
    }

    public String gethNW() {
        return hNW;
    }

    public void sethNW(String hNW) {
        this.hNW = hNW;
    }

    public String getiL() {
        return iL;
    }

    public void setiL(String iL) {
        this.iL = iL;
    }

    public String getjW() {
        return jW;
    }

    public void setjW(String jW) {
        this.jW = jW;
    }

    public String getkH() {
        return kH;
    }

    public void setkH(String kH) {
        this.kH = kH;
    }

    public String getlVOL() {
        return lVOL;
    }

    public void setlVOL(String lVOL) {
        this.lVOL = lVOL;
    }

    public String getmPONO() {
        return mPONO;
    }

    public void setmPONO(String mPONO) {
        this.mPONO = mPONO;
    }

    public String getnOrigin() {
        return nOrigin;
    }

    public void setnOrigin(String nOrigin) {
        this.nOrigin = nOrigin;
    }

    public String getoSupplier() {
        return oSupplier;
    }

    public void setoSupplier(String oSupplier) {
        this.oSupplier = oSupplier;
    }

    public String getpHSCODE() {
        return pHSCODE;
    }

    public void setpHSCODE(String pHSCODE) {
        this.pHSCODE = pHSCODE;
    }

    public String getqTotalPrice() {
        return qTotalPrice;
    }

    public void setqTotalPrice(String qTotalPrice) {
        this.qTotalPrice = qTotalPrice;
    }

    public String getrCurrency() {
        return rCurrency;
    }

    public void setrCurrency(String rCurrency) {
        this.rCurrency = rCurrency;
    }

    @Override
    public String toString() {
        return "BaseInfor{" +
                "aNO='" + aNO + '\'' +
                ", bPKGNO='" + bPKGNO + '\'' +
                ", cDescriptionCN='" + cDescriptionCN + '\'' +
                ", dDescriptionEN='" + dDescriptionEN + '\'' +
                ", ePCS='" + ePCS + '\'' +
                ", fPKGWAY='" + fPKGWAY + '\'' +
                ", gGW='" + gGW + '\'' +
                ", hNW='" + hNW + '\'' +
                ", iL='" + iL + '\'' +
                ", jW='" + jW + '\'' +
                ", kH='" + kH + '\'' +
                ", lVOL='" + lVOL + '\'' +
                ", mPONO='" + mPONO + '\'' +
                ", nOrigin='" + nOrigin + '\'' +
                ", oSupplier='" + oSupplier + '\'' +
                ", pHSCODE='" + pHSCODE + '\'' +
                ", qTotalPrice='" + qTotalPrice + '\'' +
                ", rCurrency='" + rCurrency + '\'' +
                '}';
    }
}
