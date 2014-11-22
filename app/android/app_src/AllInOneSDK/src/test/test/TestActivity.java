package test.test;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.cdd.freetime.FreeTime;
import com.cdd.sign.SignFree;
import com.umeng.analytics.MobclickAgent;

public class TestActivity extends Activity {

  public static float fv = 20f;

//  private SignFree sign;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    /** 有米 */
//    YMAd.init(this);
//    YMAd.initWithOffer(this);
//    YMAd.showOfferWall();
//    YMAd.showScreen(this);
//    YMAd.showBannerAd(this);
//    YMAd.showSmartBannerAdMoreTime(this);
//    //
//    YMWall.spend5Coins();
//    YMWall.spend10Coins();
//    YMWall.spend50Coins();
//    YMWall.spend100Coins();
//    YMWall.spend200Coins();
//
//    if (YMWall.spend50Coins()) {
//      System.out.println("yes");
//    }

    /** 安智 */
//    AZAD.init(this);
//    AZAD.addAD();
//    AZAD.addADCoverNoReAfter1H();
//    AZAD.addADCoverNoRe();
//    AZAD.addADCover();
//    AZAD.addADDelayed();
//    AZAD.addADBottom();
//    AZAD.addAD3Day();
//    AZAD.addADPre(3, 1);
//    AZbanner.addBanner(this);
//    AZbanner.addBannerBottom(this);
    ;
    /** 推广 */
//    TG.init(this);
//    TG.initAfter2Day(this);
//    if (TG.canPay(this)) {
//      System.out.println("yes");
//    }
//    ;
//    BDAD.init(this);
//    BDAD.initAndshowCover(this);
//    BDAD.initAndshowCoverOnce(this);
//    BDAD.showCover(this);
//    BDAD.showCoverOnce(this);
//    BDBannerAD.addBanner(this, 1);
//    /** 检测更新 */
//    up.checkUp(this);
//
//    CopyObb.copyMainObb(this);
//    CopyObb.copyPatchObb(this);
//    // theme
//    QInstance.initialize(getApplicationContext());
//    MainThread.init(this);
//    MainThread.runOnUIThread(null);
    
//    FreeTime.free(this);
    try{
    PackageInfo info = getPackageManager().getPackageInfo("com.coco.entertainment.immortalracer.qihu", PackageManager.GET_SIGNATURES);
    Log.e("SignFree",info.signatures[0].toCharsString() );
        Log.e("SignFree"," hashcode : "+info.signatures[0].hashCode() );
    }catch(Exception e){
      e.printStackTrace();
    }
    
    
  }

  @Override
  protected void onResume() {
    super.onResume();
    MobclickAgent.onResume(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPause(this);
  }

  public void tt() {
    Intent intent = new Intent();
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
  }

//  @Override
//  public PackageManager getPackageManager() {
//    if (sign == null) {
//      sign = new SignFree();
//      sign.setPackageManager(super.getPackageManager());
//    }
//    return sign;
//  }
}
