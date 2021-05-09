package com.coooldoggy.shopably.ui;

import androidx.recyclerview.widget.RecyclerView;

import com.coooldoggy.shopably.ui.common.IAdapterDelegate;

import java.util.HashMap;
import java.util.Map;

public class DelegateMap {
    public static Map<Integer, Class<? extends IAdapterDelegate>> delegateViewTypeMap =
            new HashMap<Integer, Class<? extends IAdapterDelegate>>(){
        {
            put(HomeListAdapter.VIEW_TYPE_BANNER, BannerDelegate.class);
            put(HomeListAdapter.VIEW_TYPE_ITEM, GoodsDelegate.class);
        }
    };

    public static Map<Class<? extends RecyclerView.ViewHolder>, Class<? extends IAdapterDelegate>> delegateHolderMap =
            new HashMap<Class<? extends RecyclerView.ViewHolder>, Class<? extends IAdapterDelegate>>(){
        {
            put(BannerViewHolder.class, BannerDelegate.class);
            put(GoodsViewHolder.class, GoodsDelegate.class);
        }
    };

    public static Map<Class<? extends IAdapterDelegate>, IAdapterDelegate> delegateMap =
            new HashMap<Class<? extends IAdapterDelegate>, IAdapterDelegate>() {
        {
            put(BannerDelegate.class, new BannerDelegate());
            put(GoodsDelegate.class, new GoodsDelegate());
        }
    };
}

