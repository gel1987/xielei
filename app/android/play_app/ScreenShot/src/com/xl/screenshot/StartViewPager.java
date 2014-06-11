package com.xl.screenshot;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class StartViewPager extends PagerAdapter {
  public ArrayList<View> listViews;
  public Context con;

  public StartViewPager(Context con, ArrayList<View> listViews) {
    this.con = con;
    this.listViews = listViews;
  }

  @Override
  public int getCount() {
    return listViews.size();
  }

  @Override
  public boolean isViewFromObject(View arg0, Object arg1) {
    return arg0 == (View) arg1;
  }

  @Override
  public Object instantiateItem(View container, int position) {
    ((ViewPager) container).addView(listViews.get(position), 0);
    return listViews.get(position);
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    if (position == 2) {

    }
    container.removeView(listViews.get(position));
  }

}
