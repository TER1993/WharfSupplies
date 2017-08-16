package com.speedata.wharfsupplies;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.speedata.wharfsupplies.adapter.CheckAdapter;
import com.speedata.wharfsupplies.db.bean.BaseInfor;
import com.speedata.wharfsupplies.utils.MyDateAndTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import win.reginer.adapter.CommonRvAdapter;

import static com.speedata.utils.ToolCommon.isNumeric;

public class CheckActivity extends Activity implements CommonRvAdapter.OnItemChildClickListener, View.OnClickListener {

    protected TextView mBarTitle;
    protected ImageView mBarLeft;
    //单件扫描不计数，不允许重复

    private List<BaseInfor> mList;
    private CheckAdapter mAdapter;
    private Button btnSave;
    private Button btnCheck;

    //item控件点击显示
    private AlertDialog mDialogItem;
    private EditText etTxtInput; //控件弹出对话框上的输入框
    private int mPosition; //选择的dialog的item的position
    private android.support.v7.app.AlertDialog mExitDialog; //按退出时弹出对话框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_check);
        initTitle();
        initView();
    }

    private void initView() {

        mList = new ArrayList<>();
        btnSave = (Button) findViewById(R.id.btn_save_xls);
        btnSave.setOnClickListener(this);
        btnCheck = (Button) findViewById(R.id.btn_check);
        btnCheck.setOnClickListener(this);
        
        mBarLeft.setOnClickListener(this);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_count_content);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL)); //adapter横线

        mAdapter = new CheckAdapter(this, R.layout.view_count_item_layout, mList);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(this);



        // 创建退出时的对话框，此处根据需要显示的先后顺序决定按钮应该使用Neutral、Negative或Positive
        CheckActivity.DialogOutButtonOnClickListener dialogButtonOnClickListener = new CheckActivity.DialogOutButtonOnClickListener();
        mExitDialog = new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle("退出此页面")
                .setMessage(R.string.out_message)
                .setCancelable(false)
                .setNeutralButton(R.string.out_sure, dialogButtonOnClickListener)
                .setPositiveButton(R.string.out_miss, dialogButtonOnClickListener)
                .create();

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
}
