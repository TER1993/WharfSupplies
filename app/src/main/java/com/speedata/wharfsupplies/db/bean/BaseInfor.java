package com.speedata.wharfsupplies.db.bean;

import com.elsw.base.db.orm.annotation.Column;
import com.elsw.base.db.orm.annotation.Table;
import com.speedata.libutils.excel.Excel;

/**
 * Created by Administrator on 2017/8/14.
 */
@Table(name = "BaseInfor")
public  class BaseInfor { //导入excel表格内容


//    @Excel(ignore = false, name = "序号NO.")
//    @Column(name = "NO")
//    private String NO; //序号
//
//    @Excel(ignore = false, name = "箱件编号PKG NO.")
//    @Column(name = "PKGNO")
//    private String PKGNO; //箱件编号
//
//    @Excel(ignore = false, name = "中文品名DESCRIPTION(CN)")
//    @Column(name = "DescriptionCN")
//    private String DescriptionCN; //中文品名
//
//    @Excel(ignore = false, name = "英文品名DESCRIPTION(EN)")
//    @Column(name = "DescriptionEN")
//    private String DescriptionEN; //英文品名
//
//    @Excel(ignore = false, name = "件数PCS")
//    @Column(name = "PCS")
//    private String PCS; //件数
//
//    @Excel(ignore = false, name = "包装方式PKG WAY")
//    @Column(name = "PKGWAY")
//    private String PKGWAY; //包装方式
//
//    @Excel(ignore = false, name = "毛重(千克)G.W.(KG)")
//    @Column(name = "GW")
//    private String GW; //毛重
//
//    @Excel(ignore = false, name = "净重(千克)N.W.(KG)")
//    @Column(name = "NW")
//    private String NW; //净重
//
//    @Excel(ignore = false, name = "长L(CM)")
//    @Column(name = "L")
//    private String L; //长
//
//    @Excel(ignore = false, name = "宽W(CM)")
//    @Column(name = "W")
//    private String W; //宽
//
//    @Excel(ignore = false, name = "高H(CM)")
//    @Column(name = "H")
//    private String H; //高
//
//    @Excel(ignore = false, name = "体积VOL.(M3)")
//    @Column(name = "VOL")
//    private String VOL; //体积
//
//    @Excel(ignore = false, name = "合同号PO NO.")
//    @Column(name = "PONO")
//    private String PONO; //合同号
//
//    @Excel(ignore = false, name = "货源地ORIGIN")
//    @Column(name = "Origin")
//    private String Origin; //货源地
//
//    @Excel(ignore = false, name = "供货商SUPPLIER")
//    @Column(name = "Supplier")
//    private String Supplier; //供货商
//
//    @Excel(ignore = false, name = "HS编码HS CODE")
//    @Column(name = "HSCODE")
//    private String HSCODE; //HS编码
//
//    @Excel(ignore = false, name = "总价TOTAL PRICE")
//    @Column(name = "TotalPrice")
//    private String TotalPrice; //总价
//
//    @Excel(ignore = false, name = "币种CURRENCY")
//    @Column(name = "Currency")
//    private String Currency; //币种


    @Excel(ignore = false, name = "净重(千克)N.W.(KG)")
    @Column(name = "NW")
    private String NW; //净重

    @Excel(ignore = false, name = "体积VOL.(M3)")
    @Column(name = "VOL")
    private String VOL; //体积

    @Excel(ignore = false, name = "箱件编号PKG NO.")
    @Column(name = "PKGNO")
    private String PKGNO; //箱件编号

    @Excel(ignore = false, name = "中文品名DESCRIPTION(CN)")
    @Column(name = "DescriptionCN")
    private String DescriptionCN; //中文品名


    @Excel(ignore = false, name = "高H(CM)")
    @Column(name = "H")
    private String H; //高

    @Excel(ignore = false, name = "合同号PO NO.")
    @Column(name = "PONO")
    private String PONO; //合同号

    @Excel(ignore = false, name = "长L(CM)")
    @Column(name = "L")
    private String L; //长

    @Excel(ignore = false, name = "英文品名DESCRIPTION(EN)")
    @Column(name = "DescriptionEN")
    private String DescriptionEN; //英文品名

    @Excel(ignore = false, name = "毛重(千克)G.W.(KG)")
    @Column(name = "GW")
    private String GW; //毛重

    @Excel(ignore = false, name = "币种CURRENCY")
    @Column(name = "Currency")
    private String Currency; //币种

    @Excel(ignore = false, name = "件数PCS")
    @Column(name = "PCS")
    private String PCS; //件数

    @Excel(ignore = false, name = "总价TOTAL PRICE")
    @Column(name = "TotalPrice")
    private String TotalPrice; //总价

    @Excel(ignore = false, name = "货源地ORIGIN")
    @Column(name = "Origin")
    private String Origin; //货源地

    @Excel(ignore = false, name = "宽W(CM)")
    @Column(name = "W")
    private String W; //宽

    @Excel(ignore = false, name = "供货商SUPPLIER")
    @Column(name = "Supplier")
    private String Supplier; //供货商

    @Excel(ignore = false, name = "包装方式PKG WAY")
    @Column(name = "PKGWAY")
    private String PKGWAY; //包装方式

    @Excel(ignore = false, name = "HS编码HS CODE")
    @Column(name = "HSCODE")
    private String HSCODE; //HS编码

    @Excel(ignore = false, name = "序号NO.")
    @Column(name = "NO")
    private String NO; //序号


    public String getNO() {
        return NO;
    }

    public void setNO(String NO) {
        this.NO = NO;
    }

    public String getPKGNO() {
        return PKGNO;
    }

    public void setPKGNO(String PKGNO) {
        this.PKGNO = PKGNO;
    }

    public String getDescriptionCN() {
        return DescriptionCN;
    }

    public void setDescriptionCN(String descriptionCN) {
        DescriptionCN = descriptionCN;
    }

    public String getDescriptionEN() {
        return DescriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        DescriptionEN = descriptionEN;
    }

    public String getPCS() {
        return PCS;
    }

    public void setPCS(String PCS) {
        this.PCS = PCS;
    }

    public String getPKGWAY() {
        return PKGWAY;
    }

    public void setPKGWAY(String PKGWAY) {
        this.PKGWAY = PKGWAY;
    }

    public String getGW() {
        return GW;
    }

    public void setGW(String GW) {
        this.GW = GW;
    }

    public String getNW() {
        return NW;
    }

    public void setNW(String NW) {
        this.NW = NW;
    }

    public String getL() {
        return L;
    }

    public void setL(String l) {
        L = l;
    }

    public String getW() {
        return W;
    }

    public void setW(String w) {
        W = w;
    }

    public String getH() {
        return H;
    }

    public void setH(String h) {
        H = h;
    }

    public String getVOL() {
        return VOL;
    }

    public void setVOL(String VOL) {
        this.VOL = VOL;
    }

    public String getPONO() {
        return PONO;
    }

    public void setPONO(String PONO) {
        this.PONO = PONO;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getSupplier() {
        return Supplier;
    }

    public void setSupplier(String supplier) {
        Supplier = supplier;
    }

    public String getHSCODE() {
        return HSCODE;
    }

    public void setHSCODE(String HSCODE) {
        this.HSCODE = HSCODE;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }


    @Override
    public String toString() {
        return "BaseInfor{" +
                "NO='" + NO + '\'' +
                ", PKGNO='" + PKGNO + '\'' +
                ", DescriptionCN='" + DescriptionCN + '\'' +
                ", DescriptionEN='" + DescriptionEN + '\'' +
                ", PCS='" + PCS + '\'' +
                ", PKGWAY='" + PKGWAY + '\'' +
                ", GW='" + GW + '\'' +
                ", NW='" + NW + '\'' +
                ", L='" + L + '\'' +
                ", W='" + W + '\'' +
                ", H='" + H + '\'' +
                ", VOL='" + VOL + '\'' +
                ", PONO='" + PONO + '\'' +
                ", Origin='" + Origin + '\'' +
                ", Supplier='" + Supplier + '\'' +
                ", HSCODE='" + HSCODE + '\'' +
                ", TotalPrice='" + TotalPrice + '\'' +
                ", Currency='" + Currency + '\'' +
                '}';
    }



}
