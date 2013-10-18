package com.hilton.vulcan;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TabsFragment extends Fragment {
    protected static final String TAG = TabsFragment.class.getName();
    private static final String PREFS = "default_prefs";
    private static final String CURRENT_TAB = "current_tab";
    private Tab[] mTabs = new Tab[] {
            new Tab("Tab 1", "Home", R.drawable.home),
            new Tab("Tab 2", "Bookmarks", R.drawable.favorite_bookmark),
            new Tab("Tab 3", "Favorites", R.drawable.star),
            new Tab("Tab 4", "Users", R.drawable.user),
            new Tab("Tab 5", "Bastkets", R.drawable.shopping_basket) };

    private TabHost mTabHost;
    private LayoutInflater mFactory;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFactory = LayoutInflater.from(activity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveCurrentTab();
    }

    private void saveCurrentTab() {
        SharedPreferences sp = getActivity().getSharedPreferences(PREFS,
                Context.MODE_PRIVATE);
        Editor ed = sp.edit();
        ed.putInt(CURRENT_TAB, mTabHost.getCurrentTab());
        ed.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tabs, container, false);
        mTabHost = (TabHost) root.findViewById(android.R.id.tabhost);
        setupTabs(mTabHost);
        restoreCurrentTab();
        return root;
    }

    private void restoreCurrentTab() {
        SharedPreferences sp = getActivity().getSharedPreferences(PREFS,
                Context.MODE_PRIVATE);
        final int current = sp.getInt(CURRENT_TAB, 0);
        mTabHost.setCurrentTab(current);
    }

    private void setupTabs(final TabHost tabHost) {
        tabHost.setup();
        for (final Tab tab : mTabs) {
            TabSpec spec = tabHost.newTabSpec(tab.mTag);
            View indicator = mFactory.inflate(R.layout.tab_indicator, null,
                    false);
            ImageView icon = (ImageView) indicator.findViewById(R.id.tab_icon);
            icon.setImageResource(tab.mIcon);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            title.setText(tab.mTitle);
            spec.setIndicator(indicator);
            spec.setContent(new TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    TextView tv = new TextView(getActivity());
                    tv.setText(tab.mTitle);
                    return tv;
                }

            });
            tabHost.addTab(spec);
        }
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.e(TAG, "which is showing ? tab id " + tabId);
            }
        });
    }

    private class Tab {
        public String mTag;
        public String mTitle;
        public int mIcon;

        public Tab(final String tag, String title, int icon) {
            mTag = tag;
            mTitle = title;
            mIcon = icon;
        }

        @Override
        public String toString() {
            return "[tag " + mTag + "]";
        }
    }
}
