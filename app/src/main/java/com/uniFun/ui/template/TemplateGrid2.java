package com.uniFun.ui.template;

import android.content.Context;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.uniFun.module.BaseModule;
import com.uniFun.module.TemplateModule;
import com.uniFun.utils.DisplayUtil;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author jixiongxu
 * @version [版本号, 2018/1/12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class TemplateGrid2 extends BaseView
{

    public static String TemplateID = "template_grid_2";

    private int maxCow = 5;

    private TemplateModule newModule;

    private int max_count = 10;

    private int width;

    private int height;

    private LinearLayout mRootView;

    public TemplateGrid2(Context context)
    {
        super(context);
        initView();
    }

    private void initView()
    {
        this.setPadding(0, 0, 0, 10);
        width = DisplayUtil.screenWidthPx(mContext);
        height = DisplayUtil.screenHeightPx(mContext);
        mRootView = new LinearLayout(mContext);
        mRootView.setLayoutParams(new LayoutParams(-1, -2));
        mRootView.setOrientation(VERTICAL);
    }

    @Override
    public void setData(BaseModule module)
    {
        mRootView.removeAllViews();
        if (module == null || !(module instanceof TemplateModule))
        {
            return;
        }
        this.module = module;
        if (((TemplateModule) module).templateItems.size() > max_count)
        {
            newModule = (TemplateModule) module;

            while (newModule.templateItems.size() > max_count)
            {
                newModule.templateItems.remove(newModule.templateItems.size() - 1);
            }
            fillData(newModule);
        }
        else
        {
            fillData(module);
        }
    }

    @Override
    public void fillData(BaseModule module)
    {
        if (module == null || !(module instanceof TemplateModule) || ((TemplateModule) module).templateItems == null)
        {
            return;
        }
        int i = 0;
        LinearLayout line = null;
        for (final TemplateModule item : ((TemplateModule) module).templateItems)
        {
            if (i % maxCow == 0)
            {
                // 一行
                line = new LinearLayout(mContext);
                line.setOrientation(HORIZONTAL);
                line.setLayoutParams(new LayoutParams(-1, -2));
                mRootView.addView(line);
            }
            // 单项
            LinearLayout itemContainer = new LinearLayout(mContext);
            itemContainer.setOrientation(VERTICAL);
            itemContainer.setLayoutParams(new LayoutParams(width / maxCow, -2));
            itemContainer.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    onItemClick(item);
                }
            });
            // 图标, 圆图
            SimpleDraweeView itemImg = new SimpleDraweeView(mContext);
            itemImg.setLayoutParams(new LayoutParams(width / maxCow, width / maxCow));
            RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
            roundingParams.setRoundAsCircle(true);
            itemImg.getHierarchy().setRoundingParams(roundingParams);
            itemImg.setPadding(20, 20, 20, 20);
            itemImg.setImageURI(Uri.parse(item.img_url));

            // 标题
            TextView title = new TextView(mContext);
            title.setGravity(Gravity.CENTER);
            title.setText(item.title);

            itemContainer.addView(itemImg);
            itemContainer.addView(title);

            if (line != null)
            {
                line.addView(itemContainer);
            }
            i++;
        }
        addTemplateView();
    }

    @Override
    public void addTemplateView()
    {
        this.removeAllViews();
        addView(mRootView);
    }

    @Override
    public void reFresh()
    {

    }
}
