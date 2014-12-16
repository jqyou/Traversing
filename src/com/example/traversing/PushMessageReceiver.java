package com.example.traversing;

import com.baidu.android.pushservice.PushConstants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PushMessageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {

		if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {

			// 获取消息内容

			String message = intent.getExtras().getString(

			PushConstants.EXTRA_PUSH_MESSAGE_STRING);

		} else if (intent.getAction().equals(PushConstants.ACTION_RECEIVE)) {

			// 处理绑定等方法的返回数据

			// PushManager.startWork()的返回值通过PushConstants.METHOD_BIND得到

			final String method = intent
					.getStringExtra(PushConstants.EXTRA_METHOD);

			// 方法返回错误码。若绑定返回错误（非0），则应用将不能正常接收消息。

			// 绑定失败的原因有多种，如网络原因，或access token过期。

			// 请不要在出错时进行简单的startWork调用，这有可能导致死循环。

			// 可以通过限制重试次数，或者在其他时机重新调用来解决。

			int errorCode = intent.getIntExtra(PushConstants.EXTRA_ERROR_CODE,
					PushConstants.ERROR_SUCCESS);

			String content = "";

			if (intent.getByteArrayExtra(PushConstants.EXTRA_CONTENT) != null)

				content = new String(
						intent.getByteArrayExtra(PushConstants.EXTRA_CONTENT));

			// 用户在此自定义处理消息：TODO Something

			// 通知用户点击事件处理

		} else if (intent.getAction().equals(

		PushConstants.ACTION_RECEIVER_NOTIFICATION_CLICK)) {

			String title = intent
					.getStringExtra(PushConstants.EXTRA_NOTIFICATION_TITLE);

			String content = intent
					.getStringExtra(PushConstants.EXTRA_NOTIFICATION_CONTENT);

			// 用户在此自定义处理点击事件：TODO Something

		}

	}

}