package com.speedata.wharfsupplies;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.speedata.libuhf.bean.Tag_Data;
import com.speedata.libutils.DataConversionUtils;
import com.speedata.libutils.excel.ExcelUtils;
import com.speedata.wharfsupplies.adapter.CheckAdapter;
import com.speedata.wharfsupplies.db.bean.BaseInfor;
import com.speedata.wharfsupplies.db.dao.BaseInforDao;
import com.speedata.wharfsupplies.utils.MyDateAndTime;
import com.speedata.wharfsupplies.utils.ProgressDialogUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import jxl.format.Colour;

import static com.speedata.wharfsupplies.MainActivity.scanFile;
import static com.speedata.wharfsupplies.application.CustomerApplication.iuhfService;

public class CheckActivity extends Activity implements View.OnClickListener {

    protected TextView mBarTitle;
    protected ImageView mBarLeft;
    //单件扫描不计数，不允许重复

    private List<BaseInfor> mList;
    private BaseInfor baseInfor;
    private CheckAdapter mAdapter;
    private Button btnSave;
    private Button btnCheck;
    private ListView EpcList;
    //item控件点击显示
    private AlertDialog mDialogItem;
    private int mPosition; //选择的dialog的item的position
    private android.support.v7.app.AlertDialog mExitDialog; //按退出时弹出对话框

    private SoundPool soundPool;
    private int soundId;
    private long scant = 0;
    private CheckBox cbb;
    private boolean inSearch = false;
    private List<EpcDataBase> firm = new ArrayList<EpcDataBase>();
    private Handler handler = null;
    private ArrayAdapter<EpcDataBase> adapter;
    private TextView Status;
    private boolean IsUtf8 = false;

    private String TAG = "Test";
    private BaseInforDao baseInforDao;
    private Context mContext;

    private List<BaseInfor> showList; //用于dialog显示点击结果
    private TextView tvTxt; //控件弹出对话框上的显示框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_check);
        initTitle();
        initView();
        ProgressDialogUtils.dismissProgressDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iuhfService.SetInvMode(3, 0, 32);
    }



    private void initView() {
        baseInfor = new BaseInfor();
        mContext = CheckActivity.this;
        baseInforDao = new BaseInforDao(mContext);
        mList = new ArrayList<>();
        showList = new ArrayList<>();
        btnSave = (Button) findViewById(R.id.btn_save_xls);
        btnSave.setOnClickListener(this);
        btnCheck = (Button) findViewById(R.id.btn_check);
        btnCheck.setOnClickListener(this);
        mBarLeft.setOnClickListener(this);
        EpcList = (ListView) findViewById(R.id.listView_search_epclist);
        Status = (TextView) findViewById(R.id.textView_search_status);
        cbb = (CheckBox) findViewById(R.id.checkBox_beep);
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load("/system/media/audio/ui/VideoRecord.ogg", 0);

        // 创建退出时的对话框，此处根据需要显示的先后顺序决定按钮应该使用Neutral、Negative或Positive
        CheckActivity.DialogOutButtonOnClickListener dialogButtonOnClickListener = new CheckActivity.DialogOutButtonOnClickListener();
        mExitDialog = new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle("退出此页面")
                .setMessage(R.string.out_message)
                .setCancelable(false)
                .setNeutralButton(R.string.out_sure, dialogButtonOnClickListener)
                .setPositiveButton(R.string.out_miss, dialogButtonOnClickListener)
                .create();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    scant++;
                    if (!cbb.isChecked()) {
                        if (scant % 10 == 0) {
                            soundPool.play(soundId, 1, 1, 0, 0, 1);
                        }
                    }
                    ArrayList<Tag_Data> ks = (ArrayList<Tag_Data>) msg.obj;
                    int i, j;
                    for (i = 0; i < ks.size(); i++) {
                        for (j = 0; j < firm.size(); j++) {
                            if (ks.get(i).epc.equals(firm.get(j).epc)) {
                                firm.get(j).valid++;
                                firm.get(j).setRssi(ks.get(i).rssi);
                                break;
                            }
                        }
                        if (j == firm.size()) {
                            if (TextUtils.isEmpty(ks.get(i).epc)) {
                                break;
                            }
                            String tid = ks.get(i).tid;
                    //        byte[] toByteArray = DataConversionUtils.hexStringToByteArray(tid);
                            //可能是张明调用的方法不对。
                            byte[] toByteArray = DataConversionUtils.HexString2Bytes(tid);

                            String user = "";
                            // 为处理结果中，中文显示乱码而修改。
                            if (isUTF8(toByteArray)) {
                                try {
                                    user = new String(toByteArray, "utf8");
                                } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            } else {
                                try {
                                    user = new String(toByteArray, "gbk");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }

                        //    String user = new String(toByteArray);
                            firm.add(new EpcDataBase(ks.get(i).epc, 1,
                                    ks.get(i).rssi,user));
                            if (cbb.isChecked()) {
                                soundPool.play(soundId, 1, 1, 0, 0, 1);
                            }
                        }
                    }
                }
                adapter = new ArrayAdapter<EpcDataBase>(
                        getApplicationContext(), android.R.layout.simple_list_item_1, firm);
                EpcList.setAdapter(adapter);

                Status.setText("Total: " + firm.size());
            }
        };

        //listView的item点击事件
        EpcList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击后根据内容查数据库，显示查询到的结果
                String userMessage = firm.get(position).getTid_user();
                String[] user = userMessage.split("   ");
                String pkgno = user[0];
                search(pkgno); //给showlist赋予显示用的内容
                changeCount(); //显示点击的项的显示内容
            }
        });
    }


    //判断扫描的内容是否是UTF8的中文内容
    private boolean isUTF8(byte[] sx) {
        //Log.d(TAG, "begian to set codeset");
        for (int i = 0; i < sx.length; ) {
            if (sx[i] < 0) {
                if ((sx[i] >>> 5) == 0x7FFFFFE) {
                    if (((i + 1) < sx.length) && ((sx[i + 1] >>> 6) == 0x3FFFFFE)) {
                        i = i + 2;
                        IsUtf8 = true;
                    } else {
                        if (IsUtf8)
                            return true;
                        else
                            return false;
                    }
                } else if ((sx[i] >>> 4) == 0xFFFFFFE) {
                    if (((i + 2) < sx.length) && ((sx[i + 1] >>> 6) == 0x3FFFFFE) && ((sx[i + 2] >>> 6) == 0x3FFFFFE)) {
                        i = i + 3;
                        IsUtf8 = true;
                    } else {
                        if (IsUtf8)
                            return true;
                        else
                            return false;
                    }
                } else {
                    if (IsUtf8)
                        return true;
                    else
                        return false;
                }
            } else {
                i++;
            }
        }
        return true;
    }


    @Override
    protected void onStop() {
        Log.w("stop", "im stopping");
        if (inSearch) {
            iuhfService.inventory_stop();
            inSearch = false;
        }
        soundPool.release();
        super.onStop();
    }


    private void initTitle() {
        setNavigation(1, getString(R.string.count_title));
    }

    /**
     * 这是导航
     *
     * @param left  左侧图标
     * @param title 标题
     */
    protected void setNavigation(int left, String title) {
        mBarTitle = (TextView) findViewById(R.id.tv_bar_title);
        mBarLeft = (ImageView) findViewById(R.id.iv_left);
        if (!TextUtils.isEmpty(title)) {
            mBarTitle.setText(title);
        }
        mBarLeft.setVisibility(left == 0 ? View.GONE : View.VISIBLE);
    }


    /**
     * 退出时的对话框的按钮点击事件
     */
    private class DialogOutButtonOnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE: // 取消
                    // 取消显示对话框
                    mExitDialog.dismiss();
                    break;
                case DialogInterface.BUTTON_NEUTRAL: // 退出程序
                    // 结束，将导致onDestroy()方法被回调
                    finish();
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 判断是否按下“BACK”(返回)键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 弹出退出时的对话框
            mExitDialog.show();
            // 返回true以表示消费事件，避免按默认的方式处理“BACK”键的事件
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save_xls:
                //导出为txt文件，制作文件名
                outPutFile();
                break;
            case R.id.iv_left:
                finish();
                break;
            case R.id.btn_check:
                //开始盘点
                if (inSearch) {
                    inSearch = false;
                    int inventoryStop = iuhfService.inventory_stop();
                    if (inventoryStop != 0) {
                        Toast.makeText(this, "停止失败", Toast.LENGTH_SHORT).show();
                    }
                    btnCheck.setText("开始");
                } else {
                    inSearch = true;
                    scant = 0;
                    iuhfService.inventory_start(handler);
                    btnCheck.setText("停止");
                }
                break;
        }
    }

    private void outPutFile() {

        List<BaseInfor> list = getBaseList(firm);
        List<BaseInfor> resultList = new ArrayList<>();
        if (list.size() == 0) {
            Toast.makeText(this, "当前没有可导出的数据，请添加数据", Toast.LENGTH_SHORT).show();
            return;
        }
        // 得到盘点到的信息的完整内容列表，导出到excel文件中
        for (int i = 0; i < list.size(); i++) {
            BaseInfor baseInfor = new BaseInfor();
            baseInfor.setBPKGNO(list.get(i).getBPKGNO());
            List<BaseInfor> baseInfors = baseInforDao.imQueryList("BPKGNO=?", new String[]{baseInfor.getBPKGNO()});
            if (baseInfors.size() == 1) {
                resultList.add(baseInfors.get(0));
            }
        }

        //导出到文件, 自定义一个文件名
        try {
            String filename = createFilename();

        ExcelUtils.getInstance()
                .setSHEET_NAME("sheet1")
                .setFONT_COLOR(Colour.BLACK)
                .setFONT_TIMES(8)
                .setFONT_BOLD(false)
                .setBACKGROND_COLOR(Colour.WHITE)
                .setContent_list_Strings(resultList)
                .setWirteExcelPath(filename)
                .createExcel(this);
        Log.d("excel", resultList.toString());
        scanFile(this, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<BaseInfor> getBaseList(List<EpcDataBase> firm) {

        List<BaseInfor> list1 = new ArrayList<>();
        baseInfor = new BaseInfor();
        for (int i = 0; i < firm.size(); i++) {
            String a = firm.get(i).getTid_user();
            Log.d(TAG, "getBaseList: a:" + a);
            String b[] = a.split("   ");
            Log.d(TAG, "getBaseList: b:" + b[0]);
            for (int j = 0; j < b.length; j++) {
                //取到的箱件编号和中文名赋值到list中
                Log.d(TAG, "getBaseList: b[j]:" + b[j]);
                fuzhi(b[j], j + 1);
            }
            list1.add(baseInfor);
            baseInfor = new BaseInfor();
        }
        Log.d(TAG, "getBaseList: list1:" + list1);
        return list1;
    }

    private void fuzhi(String neirong, int diji) {
        switch (diji) {
            case 0:
                baseInfor.setANO(neirong);
                break;
            case 1:
                baseInfor.setBPKGNO(neirong);
                break;
            case 2:
                baseInfor.setCDescriptionCN(neirong);
                break;
            case 3:
                baseInfor.setDDescriptionEN(neirong);
                break;
            case 4:
                baseInfor.setEPCS(neirong);
                break;
            case 5:
                baseInfor.setFPKGWAY(neirong);
                break;
            case 6:
                baseInfor.setGGW(neirong);
                break;
            case 7:
                baseInfor.setHNW(neirong);
                break;
            case 8:
                baseInfor.setIL(neirong);
                break;
            case 9:
                baseInfor.setJW(neirong);
                break;
            case 10:
                baseInfor.setKH(neirong);
                break;
            case 11:
                baseInfor.setLVOL(neirong);
                break;
            case 12:
                baseInfor.setMPONO(neirong);
                break;
            case 13:
                baseInfor.setNOrigin(neirong);
                break;
            case 14:
                baseInfor.setOSupplier(neirong);
                break;
            case 15:
                baseInfor.setPHSCODE(neirong);
                break;
            case 16:
                baseInfor.setQTotalPrice(neirong);
                break;
            case 17:
                baseInfor.setRCurrency(neirong);
                break;
        }

    }





    //创建导出文件的名字
    public String createFilename() throws IOException {
        String checktime = MyDateAndTime.getMakerDate();

        String date = checktime.substring(0, 8); //获得年月日
        String time = checktime.substring(8, 12); //获得年月日
        String name = "exportExcel_" + date + "_" + time;

        return  "/sdcard/" + name + ".xls";

    }


        //显示点击item后的匹配信息
    private void changeCount() {
        BaseInfor message = new BaseInfor();
        String show = "";
        if (showList.size() == 0) {
            show = "  没有匹配此项的信息";
        } else {
            message = showList.get(0);
            show = showResult(message);
        }

        //item的解决方案按钮
        CheckActivity.DialogButtonOnClickListener dialogButtonOnClickListener = new  CheckActivity.DialogButtonOnClickListener();
        tvTxt = new TextView(CheckActivity.this);
        mDialogItem = new android.app.AlertDialog.Builder(CheckActivity.this)
                .setTitle("显示匹配结果")
                .setView(tvTxt)
                .setPositiveButton("确定", dialogButtonOnClickListener)
                .show();

        tvTxt.append(show);
    }


    private String showResult(BaseInfor message) {
        String result = "";
        List<BaseInfor> list = new ArrayList<>();
        list = baseInforDao.imQueryList();
        BaseInfor baseInfor = list.get(0);
        Log.d(TAG, "数据库0位置数据" + baseInfor.toString());
        for (int i = 0; i < 18; i++) {
            result += quzhi(i, baseInfor) + " : " + quzhi(i, message) + "\n";
        }
        return result;
    }

    private String quzhi(int i, BaseInfor baseInfor) {
        String quzhi = "";
        switch (i) {
            case 0:
                quzhi = baseInfor.getANO();
                quzhi = quzhi.replaceAll("\n", "");
                break;
            case 1:
                quzhi = baseInfor.getBPKGNO();
                quzhi = quzhi.replaceAll("\n", "");
                break;
            case 2:
                quzhi = baseInfor.getCDescriptionCN();
                quzhi = quzhi.replaceAll("\n", "");
                break;
            case 3:
                quzhi = baseInfor.getDDescriptionEN();
                quzhi = quzhi.replaceAll("\n", "");
                break;
            case 4:
                quzhi = baseInfor.getEPCS();
                quzhi = quzhi.replaceAll("\n", "");
                break;
            case 5:
                quzhi = baseInfor.getFPKGWAY();
                quzhi = quzhi.replaceAll("\n", "");
                break;
            case 6:
                quzhi = baseInfor.getGGW();
                quzhi = quzhi.replaceAll("\n", "");
                break;
            case 7:
                quzhi = baseInfor.getHNW();
                quzhi = quzhi.replaceAll("\n", "");
                break;
            case 8:
                quzhi = baseInfor.getIL();
                quzhi = quzhi.replaceAll("\n", "");
                break;
            case 9:
                quzhi = baseInfor.getJW();
                quzhi = quzhi.replaceAll("\n", "");
                break;
            case 10:
                quzhi = baseInfor.getKH();
                quzhi = quzhi.replaceAll("\n", "");
                break;
            case 11:
                quzhi = baseInfor.getLVOL();
                quzhi = quzhi.replaceAll("\n", "");
                break;
            case 12:
                quzhi = baseInfor.getMPONO();
                quzhi = quzhi.replaceAll("\n", "");
                break;
            case 13:
                quzhi = baseInfor.getNOrigin();
                if (quzhi != null) {
                    quzhi = quzhi.replaceAll("\n", "");
                }
                break;
            case 14:
                quzhi = baseInfor.getOSupplier();
                if (quzhi != null) {
                    quzhi = quzhi.replaceAll("\n", "");
                }
                break;
            case 15:
                quzhi = baseInfor.getPHSCODE();
                if (quzhi != null) {
                    quzhi = quzhi.replaceAll("\n", "");
                }
                break;
            case 16:
                quzhi = baseInfor.getQTotalPrice();
                if (quzhi != null) {
                    quzhi = quzhi.replaceAll("\n", "");
                }
                break;
            case 17:
                quzhi = baseInfor.getRCurrency();
                if (quzhi != null) {
                    quzhi = quzhi.replaceAll("\n", "");
                }
                break;

        }
        return quzhi;
    }


    /**
     * 显示信息退出时的对话框的按钮点击事件
     */
    private class DialogButtonOnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE: // 确定
                    // 取消显示对话框
                    mDialogItem.dismiss();
                    break;
            }
        }
    }

    class EpcDataBase {
        String epc;
        int valid;
        String rssi;

        public String getTid_user() {
            return tid_user;
        }

        public void setTid_user(String tid_user) {
            this.tid_user = tid_user;
        }

        String tid_user;

        public EpcDataBase(String e, int v, String rssi, String tid_user) {
            // TODO Auto-generated constructor stub
            epc = e;
            valid = v;
            this.rssi = rssi;
            this.tid_user = tid_user;
        }

        public String getRssi() {
            return rssi;
        }

        public void setRssi(String rssi) {
            this.rssi = rssi;
        }

        @Override
        public String toString() {
            if (TextUtils.isEmpty(tid_user)) {
                return "EPC:" + epc + "\n"
                        + "(" + "COUNT:" + valid + ")" + " RSSI:" + rssi+"\n";
            } else {
                return "EPC:" + epc + "\n"
                        + "User:" + tid_user + "\n"
                        + "(" + "COUNT:" + valid + ")" + " RSSI:" + rssi+"\n";
            }
        }
    }

    //查询所点击的项目有没有匹配的信息
    private void search(String input) {
        Log.d(TAG, "开始查询");
        List<BaseInfor> baseInfors = baseInforDao.imQueryList("BPKGNO=?", new String[]{input});
        if (baseInfors.size() == 0) { //没搜到这个编号的产品
            Log.d(TAG, "没搜到此货品");
            showList.clear();
            Toast.makeText(this, "没有搜索到匹配的结果，请确认扫描标签是否正确", Toast.LENGTH_SHORT).show();
        } else { //搜到匹配的货品
            showList.clear();
            showList.addAll(baseInfors);
            Log.d(TAG, "搜到货品" + mList.toString());
        }
        Log.d(TAG, "结束查询");
    }
}
