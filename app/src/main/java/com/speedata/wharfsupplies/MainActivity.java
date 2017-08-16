package com.speedata.wharfsupplies;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.speedata.libutils.excel.ExcelUtils;
import com.speedata.utils.ProgressDialogUtils;
import com.speedata.wharfsupplies.application.CustomerApplication;
import com.speedata.wharfsupplies.db.bean.BaseInfor;
import com.speedata.wharfsupplies.db.dao.BaseInforDao;
import com.speedata.wharfsupplies.utils.ExcelReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.format.Colour;

public class MainActivity extends Activity implements View.OnClickListener {

    private AlertDialog mDialog;
    private Context mContext;
    private EditText etTxtName;
    private BaseInforDao baseInforDao;
    private BaseInfor baseInfor;
    private List<BaseInfor> mlist;
    private List<BaseInfor> daolist;
    private CustomerApplication application;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mContext = MainActivity.this;
        baseInforDao = new BaseInforDao(mContext);
        findViewById(R.id.iv_write).setOnClickListener(this);
        findViewById(R.id.iv_check).setOnClickListener(this);
        findViewById(R.id.btn_import).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        baseInfor = new BaseInfor();
        mlist = new ArrayList<>();
        daolist = new ArrayList<>();
        mlist = baseInforDao.imQueryList();
        application = (CustomerApplication) getApplication();
        application.setList(mlist);

        newFile();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_write:
                Intent intent1 = new Intent(MainActivity.this, WriteActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_check:
                Intent intent2 = new Intent(MainActivity.this, CheckActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_import:
                //弹出对话框输入excel文件名，并导入文件
                DialogButtonOnClickListener dialogButtonOnClickListener = new DialogButtonOnClickListener();
                etTxtName = new EditText(this);
                mDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.good_input_filename)
                        .setView(etTxtName)
                        .setPositiveButton(R.string.sure, dialogButtonOnClickListener)
                        .setNegativeButton(R.string.miss, dialogButtonOnClickListener)
                        .show();
                break;
            case R.id.btn_clear:

                /**
                 * Created by carl_yang on 2017/5/23.
                 * 使用示例
                 * ExcelUtils.getInstance()
                 * .setSHEET_NAME("测试Sheet")//设置表格名称
                 * .setFONT_COLOR(Colour.BLUE)//设置标题字体颜色
                 * .setFONT_TIMES(8)//设置标题字体大小
                 * .setFONT_BOLD(true)//设置标题字体是否斜体
                 * .setBACKGROND_COLOR(Colour.GRAY_25)//设置标题背景颜色
                 * .setContent_list_Strings(list)//设置excel内容
                 * .createExcel(MenuActivity.this);
                 */
                if (mlist.size() == 0) {
                    Toast.makeText(this, "当前没有数据，请添加数据", Toast.LENGTH_SHORT).show();
                    return;
                }

                ExcelUtils.getInstance()
                        .setSHEET_NAME("sheet1")
                        .setFONT_COLOR(Colour.BLACK)
                        .setFONT_TIMES(8)
                        .setFONT_BOLD(false)
                        .setBACKGROND_COLOR(Colour.WHITE)
                        .setContent_list_Strings(mlist)
                        .createExcel(this);

                Log.d("excel", mlist.toString());
                scanFile(this, "/sdcard/testExcel.xls");

                break;
        }
    }

    /**
     * 退出时的对话框的按钮点击事件
     */
    private class DialogButtonOnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE: // 确定
                    String txtName = etTxtName.getText().toString();
                    mDialog.dismiss();
                    readFile(txtName);

                    break;
                case DialogInterface.BUTTON_NEGATIVE: // 取消
                    // 取消显示对话框
                    mDialog.dismiss();

                    break;
            }
        }
    }

    private void readFile(final String txtName) {
        baseInforDao.imDeleteAll();
        ProgressDialogUtils.showProgressDialog(this, getString(R.string.good_is_import));
        new Thread(new Runnable() {
            @Override
            public void run() {

                String path = "/sdcard/WharfSupplies/import/" + txtName + ".xlsx";
                int acount;
                String s = ExcelReader.readXLSX(path);
                if ("".equals(s)) {
                    acount = -1;
                } else {
                    acount = 1;
                    stringToList(s); //将string字符串规整成list
                    Log.d("excel" , s);
                }

                Message msg = new Message();
                msg.obj = acount;
                handler.sendMessage(msg);
            }
        }).start();


    }

    private void stringToList(String s) {
        String str = "";
        String a[] = s.split("\n\n\n\n"); //把excel表格分成头和内容
        String aa[] = a[0].split("   "); //把头分成一项一项
        for (int j = 0; j < 18; j++) {
               fuzhi(aa[j], j);
        }
            mlist.add(baseInfor); //得到了头
            baseInfor = new BaseInfor();
        //接下来是内容，挨个赋值
        String b[] = a[1].split("\n\n"); //分成一行一行内容

        for (int i = 0; i < b.length; i++) {
            String bb[] = b[i].split("   ");
            for (int j = 0; j < bb.length; j++) {
                fuzhi(bb[j], j);
            }
            mlist.add(baseInfor);
            baseInfor = new BaseInfor();
        }
        Log.d("excel", mlist.toString());
        baseInforDao.imInsertList(mlist); //向数据库加入读取的excel文件内容
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressDialogUtils.dismissProgressDialog();
            int count = (int) msg.obj;
            if (count == -1) {
                new AlertDialog.Builder(mContext).setTitle(getString(R.string.good_wrong)).setMessage(R.string.good_null_fail).show();
                return;
            }

            Toast.makeText(mContext, "导入成功", Toast.LENGTH_SHORT).show();
        }
    };



    private void newFile() {
        File file = new File(getString(R.string.import_path));
        if (!file.exists()) {
            file.mkdirs();

        }
        scanFile(this, getString(R.string.import_path));
        file = new File(getString(R.string.export_path));
        if (!file.exists()) {
            file.mkdirs();
        }
        scanFile(this, getString(R.string.export_path));
    }


    //更新文件显示的广播，在生成文件后调用一次。
    public static void scanFile(Context context, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }


    private void fuzhi(String neirong, int diji) {
        switch (diji) {
            case 0:
                baseInfor.setNO(neirong);
                break;
            case 1:
                baseInfor.setPKGNO(neirong);
                break;
            case 2:
                baseInfor.setDescriptionCN(neirong);
                break;
            case 3:
                baseInfor.setDescriptionEN(neirong);
                break;
            case 4:
                baseInfor.setPCS(neirong);
                break;
            case 5:
                baseInfor.setPKGWAY(neirong);
                break;
            case 6:
                baseInfor.setGW(neirong);
                break;
            case 7:
                baseInfor.setNW(neirong);
                break;
            case 8:
                baseInfor.setL(neirong);
                break;
            case 9:
                baseInfor.setW(neirong);
                break;
            case 10:
                baseInfor.setH(neirong);
                break;
            case 11:
                baseInfor.setVOL(neirong);
                break;
            case 12:
                baseInfor.setPONO(neirong);
                break;
            case 13:
                baseInfor.setOrigin(neirong);
                break;
            case 14:
                baseInfor.setSupplier(neirong);
                break;
            case 15:
                baseInfor.setHSCODE(neirong);
                break;
            case 16:
                baseInfor.setTotalPrice(neirong);
                break;
            case 17:
                baseInfor.setCurrency(neirong);
                break;
        }

    }

}
