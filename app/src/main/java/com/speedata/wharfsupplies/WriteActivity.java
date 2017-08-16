package com.speedata.wharfsupplies;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.speedata.libuhf.IUHFService;
import com.speedata.libuhf.UHFManager;
import com.speedata.wharfsupplies.adapter.WriteAdapter;
import com.speedata.wharfsupplies.application.CustomerApplication;
import com.speedata.wharfsupplies.db.bean.BaseInfor;
import com.speedata.wharfsupplies.db.dao.BaseInforDao;

import java.util.ArrayList;
import java.util.List;

import win.reginer.adapter.CommonRvAdapter;

public class WriteActivity extends Activity implements View.OnClickListener, CommonRvAdapter.OnItemClickListener {

    private Button btnSearch;
    private EditText etInput;

    protected TextView mBarTitle;
    protected ImageView mBarLeft;
    //单件扫描不计数，不允许重复

    private List<BaseInfor> mList;
    private WriteAdapter mAdapter;
    private Button btnOne;
    private AlertDialog mExitDialog; //按退出时弹出对话框
    private IUHFService iuhfService;
    private CustomerApplication application;

    //item控件点击显示
    private android.app.AlertDialog mDialogItem;
    private TextView tvTxt; //控件弹出对话框上的输入框
    private int mPosition; //选择的dialog的item的position

    private BaseInforDao baseInforDao;
    private Context mContext;

    private String TAG = "Test";
    //输入法管理器
    protected InputMethodManager mimm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_write);
        initTitle();
        initView();

        iuhfService = UHFManager.getUHFService(this);
        if (iuhfService.OpenDev() == 0) {
            Toast.makeText(this, "上电成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "上电失败", Toast.LENGTH_SHORT).show();
        }

    }

    private void initView() {
        //输入法管理
        mimm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mList = new ArrayList<>();
        btnOne = (Button) findViewById(R.id.btn_choose_card);

        btnSearch = (Button) findViewById(R.id.btn_search);
        etInput = (EditText) findViewById(R.id.et_input);

        btnSearch.setOnClickListener(this);
        btnOne.setOnClickListener(this);
        mBarLeft.setOnClickListener(this);

        application = (CustomerApplication) getApplication();

        mList = application.getList();
        mContext = WriteActivity.this;
        baseInforDao = new BaseInforDao(mContext);



        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_one_content);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL)); //adapter横线

        mAdapter = new WriteAdapter(this, R.layout.view_one_item_layout, mList);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);


        // 创建退出时的对话框，此处根据需要显示的先后顺序决定按钮应该使用Neutral、Negative或Positive
        DialogButtonOnClickListener dialogButtonOnClickListener = new DialogButtonOnClickListener();
        mExitDialog = new AlertDialog.Builder(this)
                .setTitle("退出此页面")
                .setMessage(R.string.out_message)
                .setCancelable(false)
                .setNeutralButton(R.string.out_sure, dialogButtonOnClickListener)
                .setPositiveButton(R.string.out_miss, dialogButtonOnClickListener)
                .create();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    private void initTitle() {
        setNavigation(1, getString(R.string.one_title));
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


    private void changeCount(int position) {
        BaseInfor message = mList.get(position);
        //item的解决方案按钮

        DialogItemOnClickListener dialogButtonOnClickListener = new DialogItemOnClickListener();
        tvTxt = new TextView(WriteActivity.this);
        mDialogItem = new android.app.AlertDialog.Builder(WriteActivity.this)
                .setTitle("确认写入此信息？")
                .setView(tvTxt)
                .setPositiveButton("确定", dialogButtonOnClickListener)
                .setNegativeButton("取消", dialogButtonOnClickListener)
                .show();
        mPosition = position;
        tvTxt.append(message.toString());
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, View view, int position) {
        changeCount(position);
    }


    /**
     * 退出时的对话框的按钮点击事件
     */
    private class DialogButtonOnClickListener implements DialogInterface.OnClickListener {
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
            case R.id.btn_choose_card:
                // TODO: 2017/8/16 选择卡片


                break;
            case R.id.iv_left:
                finish();
                break;

            case R.id.btn_search:
                String input = etInput.getText().toString();
                if ("".equals(input)) {
                    mList.clear();
                    mList.addAll(baseInforDao.imQueryList());
                    mAdapter.notifyDataSetChanged();
                } else {
                    search(input);
                }

                break;
        }
    }


    private void search(String input) {
        Log.d(TAG, "开始查询");
        List<BaseInfor> baseInfors = baseInforDao.imQueryList("DescriptionCN=?", new String[]{input});
        if (baseInfors.size() == 0) { //中文没搜到，搜英文的内容
            baseInfors = baseInforDao.imQueryList("DescriptionEN=?", new String[]{input});
            Log.d(TAG, "没搜到中文");
            if (baseInfors.size() == 0) { //没搜到结果
                Toast.makeText(this, "没有搜索到匹配的结果，请确认搜索内容是否正确", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "没搜到英文");
            } else { //搜到匹配的英文结果
                mList.clear();
                mList.addAll(baseInfors);
                Log.d(TAG, "搜到英文" + mList.toString());
            }
        } else { //搜到匹配的中文结果
            mList.clear();
            mList.addAll(baseInfors);
            Log.d(TAG, "搜到中文" + mList.toString());
        }
        Log.d(TAG, "结束查询");

        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        iuhfService.CloseDev();
        super.onDestroy();
    }


    /**
     * 问题退出时的对话框的按钮点击事件
     */
    private class DialogItemOnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE: // 确定

                    // TODO: 2017/8/15 点击确定向已选择卡片user区写入数据



                    break;
                case DialogInterface.BUTTON_NEGATIVE: // 取消
                    // 取消显示对话框
                    mDialogItem.dismiss();

                    break;
            }
        }

    }
}
