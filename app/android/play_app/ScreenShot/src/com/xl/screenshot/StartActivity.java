package com.xl.screenshot;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class StartActivity extends Activity implements OnClickListener {

  @Override 
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_start);
    init();
  }

  public void init() {
    ViewPager startVp = (ViewPager) findViewById(R.id.start_vp);
    startVp.setAdapter(new StartViewPager(this, setPage()));
  }

  public ArrayList<View> setPage() {
    ArrayList<View> listViews = new ArrayList<View>();
    LayoutInflater inflater = LayoutInflater.from(this);
    View viewOne = inflater.inflate(R.layout.start_guidance, null);
    viewOne.setBackgroundResource(R.drawable.how1);
    View viewTwo = inflater.inflate(R.layout.start_guidance, null);
    viewTwo.setBackgroundResource(R.drawable.how2);
    TextView startTv = (TextView) viewTwo.findViewById(R.id.start_tv);
    startTv.setVisibility(View.VISIBLE);
    startTv.setOnClickListener(this);
    listViews.add(viewOne);
    listViews.add(viewTwo);
    return listViews;
  }

  @Override
  public void onClick(View v) {
    finish();
  }
}
