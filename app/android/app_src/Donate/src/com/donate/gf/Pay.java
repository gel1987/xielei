package com.donate.gf;

import java.util.ArrayList;
import java.util.List;

import net.umipay.android.GameParamInfo;
import net.umipay.android.UmiPaySDKManager;
import net.umipay.android.UmiPaymentInfo;
import net.umipay.android.UmipayOrderInfo;
import net.umipay.android.UmipaySDKStatusCode;
import net.umipay.android.interfaces.InitCallbackListener;
import net.umipay.android.interfaces.OrderReceiverListener;
import android.content.Context;
import android.widget.Toast;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Pay implements InitCallbackListener, OrderReceiverListener {

	Context context;
	PayCallback callback;

	String item;
	int minFee;

	public void pay(Context ctx, PayCallback cb, String item, int minFee) {
		callback = cb;
		this.context = ctx;
		this.item = item;
		this.minFee = minFee;
		initSDK();
	}

	/**
	 * 初始化安全支付sdk
	 */
	private void initSDK() {
		// 初始化参数
		GameParamInfo gameParamInfo = new GameParamInfo();
		// 您的应用的AppId
		gameParamInfo.setAppId("327fde3116a77ed8");
		// 您的应用的AppSecret
		gameParamInfo.setAppSecret("85ca112e78eefe17");

		// false 订单充值成功后是使用服务器通知 true 订单充值成功后使用客户端回调
		gameParamInfo.setSDKCallBack(true);

		// 调用sdk初始化接口
		UmiPaySDKManager.initSDK(context, gameParamInfo, this, this);
	}

	private void startPay() {
		// 调用支付充值
		UmiPaymentInfo paymentInfo = new UmiPaymentInfo();

		// 【可选】设置充值金币数量，可选。ps：只是传入值，非最终充值数额，最终充值额度以服务器通知结果为准
		paymentInfo.setAmount(100);

		// 【可选】开发商自定义数据，可选。该值将在用户充值成功后，在支付工具服务器回调给游戏开发商时携带该数据
		// paymentInfo.setCustomInfo("100金币");

		// 【可选】设置用户的游戏角色级别
		// paymentInfo.setRoleGrade("setRoleGrade");

		// 【可选】设置用户的游戏角色的ID
		paymentInfo.setRoleId(item);

		// 【可选】设置用户的游戏角色名字
		// paymentInfo.setRoleName("setRoleName");

		// 【可选】false:支付完成会允许继续充值； true： 支付完成后关闭支付界面,不能继续充值
		// paymentInfo.setSinglePayMode(true);

		// 【可选】充值时选择的最小金额,用户选择低于最小金额的金额将无法支付
		paymentInfo.setMinFee(minFee);
		paymentInfo.setSinglePayMode(true);
		UmiPaySDKManager.showPayView(context, paymentInfo);
	}

	/**
	 * 
	 * 初始化成功，返回用户信息
	 * 
	 */
	@Override
	public void onInitCallback(int code, String msg) {
		if (code == UmipaySDKStatusCode.SUCCESS) {
			// 初始化成功后，即可正常调用充值
			Toast.makeText(context, "初始化成功", Toast.LENGTH_SHORT).show();

			startPay();
		} else if (code == UmipaySDKStatusCode.INIT_FAIL) {
			// 初始化失败，一般在这里提醒用户网络有问题，反馈，等等问题
			Toast.makeText(context, "初始化失败,请重试!", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 接收到服务器返回的订单信息 ！！！注意，该返回是在非ui线程中回调，如果需要更新界面，需要手动使用主线刷新
	 */
	@Override
	public List onReceiveOrders(List list) {
		// 未处理的订单
		List<UmipayOrderInfo> newOrderList = list;
		// 已处理的订单
		List<UmipayOrderInfo> doneOrderList = new ArrayList<UmipayOrderInfo>();
		// 出来服务器返回的订单信息newOrderList，并将已经处理好充值的订单返回给sdk
		// sdk将已经处理完的订单通知给服务器。服务器下次将不再返回游戏客户端已经处理过的订单
		for (UmipayOrderInfo newOrder : newOrderList) {
			try {
				// 对订单order进行结算
				if (newOrder.getStatus() == 1) {
					// 注意，转成毫秒需要 乘以长整型1000L
					// String payTime = DateFormat.format("yyyy-MM-dd kk:mm:ss",
					// newOrder.getPayTime_s() * 1000L).toString();
					// final String tips = String.format("支付订单号:%s \n"
					// + "用户标记:%s \n" + "订单状态:%s \n" + "支付通道:%s \n"
					// + "获得金币:%s \n" + "金额:%s￥ \n" + "支付时间:%s \n"
					// + "自定义数据:%s \n", newOrder.getOid(),
					// newOrder.getRid(), newOrder.getStatus() + "",
					// newOrder.getPayType(), newOrder.getAmount() + "",
					// newOrder.getRealMoney() + "", payTime,
					// newOrder.getCData());
					callback.paySucess(newOrder.getRid());
					// 添加到已处理订单列表
					doneOrderList.add(newOrder);
				} else {
					callback.payFailed(newOrder.getRid());
				}
			} catch (Exception e) {

			}
		}
		return doneOrderList; // 将已经处理过的订单返回给sdk，下次服务器不再返回这些订单
	}

}
