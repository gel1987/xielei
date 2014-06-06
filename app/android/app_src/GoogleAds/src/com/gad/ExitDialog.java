package com.gad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

public class ExitDialog {

  public static void exit(final Activity act) {
    AlertDialog.Builder builder = new AlertDialog.Builder(act);
    builder.setTitle("Tip");
    builder.setMessage("Exit,Are you sure?");
    builder.setNegativeButton("Rate", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        gotoRate(act);
      }
    });
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        System.exit(0);
      }
    });
    builder.show();
  }

  public static void rate(final Activity act) {
    AlertDialog.Builder builder = new AlertDialog.Builder(act);
    builder.setTitle("Tip");
    builder.setMessage("Do you want to rate?");
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    });
    builder.setPositiveButton("Rate", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        gotoRate(act);
      }
    });
    builder.show();
  }

  public static void gotoRate(Activity act) {
    Uri uri = Uri.parse("market://details?id="+act.getPackageName());
    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    act.startActivity(intent);
  }
  
}
