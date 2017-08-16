package com.speedata.wharfsupplies.adapter;

import android.content.Context;

import com.speedata.wharfsupplies.R;
import com.speedata.wharfsupplies.db.bean.BaseInfor;

import java.util.List;

import win.reginer.adapter.BaseAdapterHelper;
import win.reginer.adapter.CommonRvAdapter;


/**
 * Created by xu on 2017/8/14.
 */

public class CheckAdapter extends CommonRvAdapter<BaseInfor> {
    private List<BaseInfor> mList;

    public CheckAdapter(Context context, int layoutResId, List<BaseInfor> data) {
        super(context, layoutResId, data);
        mList = data;
    }

    @Override
    public void convert(BaseAdapterHelper helper, BaseInfor item, int position) {
        helper.setText(R.id.tv_count_line1, item.getNO());
        helper.setText(R.id.tv_count_line2, item.getDescriptionCN());
        setOnItemChildClickListener(helper, position, R.id.tv_count_line2);

    }
}
