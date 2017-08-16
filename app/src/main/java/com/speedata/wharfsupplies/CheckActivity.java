package com.speedata.wharfsupplies;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.speedata.libuhf.bean.Tag_Data;
import com.speedata.libutils.DataConversionUtils;
import com.speedata.wharfsupplies.adapter.CheckAdapter;
import com.speedata.wharfsupplies.db.bean.BaseInfor;
import com.speedata.wharfsupplies.utils.MyDateAndTime;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import win.reginer.adapter.CommonRvAdapter;

import static com.speedata.utils.ToolCommon.isNumeric;
import static com.speedata.wharfsupplies.application.CustomerApplication.iuhfService;

public class CheckActivity extends Activity implements CommonRvAdapter.OnItemChildClickListener, View.OnClickListener {

    protected TextView mBarTitle;
    protected ImageView mBarLeft;
    //单件扫描不计数，不允许重复

    private List<BaseInfor> mList;
    private CheckAdapter mAdapter;
    private Button btnSave;
    private Button btnCheck;
    private ListView EpcList;
    //item控件点击显示
    private AlertDialog mDialogItem;
    private EditText etTxtInput; //控件弹出对话框上的输入框
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_check);
        initTitle();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (iuhfService.OpenDev() == 0) {
            Toast.makeText(this, "上电成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "上电失败", Toast.LENGTH_SHORT).show();
        }
        iuhfService.SetInvMode(3, 0, 32);
    }



    private void initView() {

        mList = new ArrayList<>();
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
                            byte[] toByteArray = DataConversionUtils.hexStringToByteArray(tid);

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
        iuhfService.CloseDev();
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

                // TODO: 2017/8/16 开始盘点 
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
            // TODO: 2017/8/14 拿到盘点结果的list，创建导出盘点excel
            
        
        
        try {

            MainActivity.scanFile(CheckActivity.this, createFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //创建导出文件的名字
    public String createFilename() throws IOException {
        String checktime = MyDateAndTime.getMakerDate();

        String date = checktime.substring(0, 8); //获得年月日
        String time = checktime.substring(8, 12); //获得年月日
        String name = getString(R.string.count_) + date + "_" + time;

        return  getString(R.string.export_path_) + name + getString(R.string.txt);

    }

    @Override
    public void onChildClick(View v, int position) {
        switch (v.getId()) { //点击数量时弹出修改输入对话框
            case R.id.tv_count_line2:
                changeCount(position);
                break;
        }
    }


    private void changeCount(int position) {
        String count = mList.get(position).getPKGNO();
        //item的解决方案按钮

        DialogButtonOnClickListener dialogButtonOnClickListener = new DialogButtonOnClickListener();
        etTxtInput = new EditText(CheckActivity.this);
        mDialogItem = new AlertDialog.Builder(CheckActivity.this)
                .setTitle("请输入数量")
                .setView(etTxtInput)
                .setPositiveButton("确定", dialogButtonOnClickListener)
                .setNegativeButton("取消", dialogButtonOnClickListener)
                .show();
        mPosition = position;
        etTxtInput.append(count);
    }


    /**
     * 问题退出时的对话框的按钮点击事件
     */
    private class DialogButtonOnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE: // 确定

                    String txt = etTxtInput.getText().toString();

                    if (isNumeric(txt)) {
                        mDialogItem.dismiss();

                        updateList(txt);
                    } else {
                        Toast.makeText(CheckActivity.this, "只能输入数字，请重新输入数量", Toast.LENGTH_SHORT).show();
                    }


                    break;
                case DialogInterface.BUTTON_NEGATIVE: // 取消
                    // 取消显示对话框
                    mDialogItem.dismiss();

                    break;
            }
        }

        //更新数组以及控件的显示
        private void updateList(String txt) {
            if ("".equals(txt)) {
                Toast.makeText(CheckActivity.this, "未修改", Toast.LENGTH_SHORT).show();
            } else {
                mList.get(mPosition).setPKGNO(txt);
            }

            mAdapter.notifyDataSetChanged();
        }

    }

    class EpcDataBase {
        String epc;
        int valid;
        String rssi;
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
}
