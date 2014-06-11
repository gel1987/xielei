package com.xl.screenshot;

import android.content.Intent;
import android.os.FileObserver;

public class ScreenshotObserver extends FileObserver {

  public String path;

  public ScreenshotObserver(String path) {
    super(path);
    this.path = path;
  }

  public void onEvent(int event, final String name) {
    switch (event) {
      case FileObserver.CLOSE_WRITE:
        Intent intent = new Intent(MainApplication.app, ScreenshotActivity.class);
        intent.putExtra(ScreenshotActivity.ACTION_PATH, path + "/" + name);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainApplication.app.startActivity(intent);
        break;
      case FileObserver.CREATE:
        // 保存截图
        break;
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((path == null) ? 0 : path.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ScreenshotObserver other = (ScreenshotObserver) obj;
    if (path == null) {
      if (other.path != null)
        return false;
    } else if (!path.equals(other.path))
      return false;
    return true;
  }

}
