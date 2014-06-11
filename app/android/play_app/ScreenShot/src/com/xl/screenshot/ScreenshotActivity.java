package com.xl.screenshot;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
 
public class ScreenshotActivity extends Activity {

  public final static String ACTION_PATH = "ACTION_PATH";

  private static ScreenshotActivity current = null;

  private HandWriteView handWriteView = null;
  String path = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GAD.showbannerB(this);
    Intent intent = getIntent();
    if (intent == null) {
      finish();
      return;
    }
    path = intent.getStringExtra(ACTION_PATH);
    if (TextUtils.isEmpty(path)) {
      finish();
      return;
    }
    if (current != null) {
      current.finish();
    }
    current = this;
    setContentView(R.layout.activity_screenshot);
    handWriteView = (HandWriteView) findViewById(R.id.handwriteview);
    handWriteView.setBitmap(path);
  }

  public void share(View view) {
    startShare(path);
    GAD.showCover(this);
  }

  /**
   * 分享
   * 
   * @param subject
   * @param text
   */
  public void startShare(String photoUri) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("image/*");
    File file = new File(photoUri);
    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent = Intent.createChooser(intent, getString(R.string.share));
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  public void save(View view) {

  }

  public void clear(View view) {

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
