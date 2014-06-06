package com.donate.gf;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class PayService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	PayInterface.Stub binder = new PayInterface.Stub() {
		@Override
		public void pay(PayCallback cb, String item, int minFee)
				throws RemoteException {
			Pay pay = new Pay();
			pay.pay(getApplicationContext(), cb, item, minFee);
		}
	};
}
