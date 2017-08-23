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
    @Column(name = "ANO")
    private String ANO; //序号

    @Excel(ignore = false, name = "箱件编号PKG NO.")
    @Column(name = "BPKGNO")
    private String BPKGNO; //箱件编号

    @Excel(ignore = false, name = "中文品名DESCRIPTION(CN)")
    @Column(name = "CDescriptionCN")
    private String CDescriptionCN; //中文品名

    @Excel(ignore = false, name = "英文品名DESCRIPTION(EN)")
    @Column(name = "DDescriptionEN")
    private String DDescriptionEN; //英文品名

    @Excel(ignore = false, name = "件数PCS")
    @Column(name = "EPCS")
    private String EPCS; //件数

    @Excel(ignore = false, name = "包装方式PKG WAY")
    @Column(name = "FPKGWAY")
    private String FPKGWAY; //包装方式

    @Excel(ignore = false, name = "毛重(千克)G.W.(KG)")
    @Column(name = "GGW")
    private String GGW; //毛重

    @Excel(ignore = false, name = "净重(千克)N.W.(KG)")
    @Column(name = "HNW")
    private String HNW; //净重

    @Excel(ignore = false, name = "长L(CM)")
    @Column(name = "IL")
    private String IL; //长

    @Excel(ignore = false, name = "宽W(CM)")
    @Column(name = "JW")
    private String JW; //宽

    @Excel(ignore = false, name = "高H(CM)")
    @Column(name = "KH")
    private String KH; //高

    @Excel(ignore = false, name = "体积VOL.(M3)")
    @Column(name = "LVOL")
    private String LVOL; //体积

    @Excel(ignore = false, name = "合同号PO NO.")
    @Column(name = "MPONO")
    private String MPONO; //合同号

    @Excel(ignore = false, name = "货源地ORIGIN")
    @Column(name = "NOrigin")
    private String NOrigin; //货源地

    @Excel(ignore = false, name = "供货商SUPPLIER")
    @Column(name = "OSupplier")
    private String OSupplier; //供货商

    @Excel(ignore = false, name = "HS编码HS CODE")
    @Column(name = "PHSCODE")
    private String PHSCODE; //HS编码

    @Excel(ignore = false, name = "总价TOTAL PRICE")
    @Column(name = "QTotalPrice")
    private String QTotalPrice; //总价

    @Excel(ignore = false, name = "币种CURRENCY")
    @Column(name = "RCurrency")
    private String RCurrency; //币种


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


    public String getANO() {
        return ANO;
    }

    public void setANO(String ANO) {
        this.ANO = ANO;
    }

    public String getBPKGNO() {
        return BPKGNO;
    }

    public void setBPKGNO(String BPKGNO) {
        this.BPKGNO = BPKGNO;
    }

    public String getCDescriptionCN() {
        return CDescriptionCN;
    }

    public void setCDescriptionCN(String CDescriptionCN) {
        this.CDescriptionCN = CDescriptionCN;
    }

    public String getDDescriptionEN() {
        return DDescriptionEN;
    }

    public void setDDescriptionEN(String DDescriptionEN) {
        this.DDescriptionEN = DDescriptionEN;
    }

    public String getEPCS() {
        return EPCS;
    }

    public void setEPCS(String EPCS) {
        this.EPCS = EPCS;
    }

    public String getFPKGWAY() {
        return FPKGWAY;
    }

    public void setFPKGWAY(String FPKGWAY) {
        this.FPKGWAY = FPKGWAY;
    }

    public String getGGW() {
        return GGW;
    }

    public void setGGW(String GGW) {
        this.GGW = GGW;
    }

    public String getHNW() {
        return HNW;
    }

    public void setHNW(String HNW) {
        this.HNW = HNW;
    }

    public String getIL() {
        return IL;
    }

    public void setIL(String IL) {
        this.IL = IL;
    }

    public String getJW() {
        return JW;
    }

    public void setJW(String JW) {
        this.JW = JW;
    }

    public String getKH() {
        return KH;
    }

    public void setKH(String KH) {
        this.KH = KH;
    }

    public String getLVOL() {
        return LVOL;
    }

    public void setLVOL(String LVOL) {
        this.LVOL = LVOL;
    }

    public String getMPONO() {
        return MPONO;
    }

    public void setMPONO(String MPONO) {
        this.MPONO = MPONO;
    }

    public String getNOrigin() {
        return NOrigin;
    }

    public void setNOrigin(String NOrigin) {
        this.NOrigin = NOrigin;
    }

    public String getOSupplier() {
        return OSupplier;
    }

    public void setOSupplier(String OSupplier) {
        this.OSupplier = OSupplier;
    }

    public String getPHSCODE() {
        return PHSCODE;
    }

    public void setPHSCODE(String PHSCODE) {
        this.PHSCODE = PHSCODE;
    }

    public String getQTotalPrice() {
        return QTotalPrice;
    }

    public void setQTotalPrice(String QTotalPrice) {
        this.QTotalPrice = QTotalPrice;
    }

    public String getRCurrency() {
        return RCurrency;
    }

    public void setRCurrency(String RCurrency) {
        this.RCurrency = RCurrency;
    }

    @Override
    public String toString() {
        return "BaseInfor{" +
                "ANO='" + ANO + '\'' +
                ", BPKGNO='" + BPKGNO + '\'' +
                ", CDescriptionCN='" + CDescriptionCN + '\'' +
                ", DDescriptionEN='" + DDescriptionEN + '\'' +
                ", EPCS='" + EPCS + '\'' +
                ", FPKGWAY='" + FPKGWAY + '\'' +
                ", GGW='" + GGW + '\'' +
                ", HNW='" + HNW + '\'' +
                ", IL='" + IL + '\'' +
                ", JW='" + JW + '\'' +
                ", KH='" + KH + '\'' +
                ", LVOL='" + LVOL + '\'' +
                ", MPONO='" + MPONO + '\'' +
                ", NOrigin='" + NOrigin + '\'' +
                ", OSupplier='" + OSupplier + '\'' +
                ", PHSCODE='" + PHSCODE + '\'' +
                ", QTotalPrice='" + QTotalPrice + '\'' +
                ", RCurrency='" + RCurrency + '\'' +
                '}';
    }
}
