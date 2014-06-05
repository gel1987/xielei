package test.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.don.R;
import com.donate.pay.Pay;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Pay.init(this);

	}

	public void onPay(View view) {
		Pay.pay("xxx_" + System.currentTimeMillis(), 1);
	}
}
