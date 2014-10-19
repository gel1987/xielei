package com.tizmoplay.androgens;

import android.app.Activity;
import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.mdplay.androgens.R;

public class AdPreference extends Preference {

  public AdPreference(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public AdPreference(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public AdPreference(Context context) {
    super(context);
  }

  @Override
  protected View onCreateView(ViewGroup parent) {
    // this will create the linear layout defined in ads_layout.xml
    View view = super.onCreateView(parent);

    // the context is a PreferenceActivity
    Activity activity = (Activity) getContext();

    // Create the adView
    AdView adView = new AdView(activity);
    adView.setAdSize(AdSize.BANNER);
    adView.setAdUnitId(getContext().getResources().getString(R.string.admob_banner_id));

//    ((LinearLayout) view).addView(adView);

    // Initiate a generic request to load it with an ad
    AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();

    // Start loading the ad in the background.
    // 不显示广告
//    adView.loadAd(adRequest);

    return view;
  }
}
