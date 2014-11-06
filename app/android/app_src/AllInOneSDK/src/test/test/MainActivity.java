package test.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cdd.az.AZAD;
import com.cdd.az.AZbanner;
import com.cdd.baidu.BDAD;
import com.cdd.baidu.BDBannerAD;
import com.cdd.freetime.FreeTime;
import com.cdd.tenxun.TX;
import com.cdd.tg.TG;
import com.cdd.up.up;
import com.cdd.ym.YMAd;
import com.cdd.ym.YMWall;
import com.umeng.analytics.MobclickAgent;
import com.unity3d.player.UnityPlayerLocalActivity;
import com.unity3d.player.xa;

public class MainActivity extends Activity {

  public static float fv = 20f;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    /** 有米 */
    YMAd.init(this);
    YMAd.initWithOffer(this);
    YMAd.showOfferWall();
    YMAd.showScreen(this);
    YMAd.showBannerAd(this);
    YMAd.showSmartBannerAdMoreTime(this);
    //
    YMWall.spend5Coins();
    YMWall.spend10Coins();
    YMWall.spend50Coins();
    YMWall.spend100Coins();
    YMWall.spend200Coins();

    if (YMWall.spend50Coins()) {
      System.out.println("yes");
    }

    /** 安智 */
    AZAD.init(this);
    AZAD.addAD();
    AZAD.addADCoverNoReAfter1H();
    AZAD.addADCoverNoRe();
    AZAD.addADCover();
    AZAD.addADDelayed();
    AZAD.addADBottom();
    AZAD.addAD3Day();
    AZAD.addADPre(3, 1);
    AZbanner.addBanner(this);
    AZbanner.addBannerBottom(this);
    ;
    /** 推广 */
    TG.init(this);
    TG.initAfter2Day(this);
    if (TG.canPay(this)) {
      System.out.println("yes");
    }
    ;
    BDAD.init(this);
    BDAD.initAndshowCover(this);
    BDAD.initAndshowCoverOnce(this);
    BDAD.showCover(this);
    BDAD.showCoverOnce(this);
    BDBannerAD.addBanner(this, 1);
    /** 检测更新 */
    up.checkUp(this);

    // theme
    FreeTime.free(this);
    
    xa.a(this);
    xa.get("a");
    
    UnityPlayerLocalActivity.unity();
    
    TX.init(this);
    TX.joinKX();
    TX.joinKX(this);
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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
  }
  
}
