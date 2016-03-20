package dev.mirror.library.android.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

public class UIHelper {

	private static AlertDialog.Builder mBuilder;
	public static void makePhoneCall(final Activity context,final String phone){
		if(mBuilder == null){
			mBuilder = new AlertDialog.Builder(context);
		}

		mBuilder = new AlertDialog.Builder(context);
		mBuilder.setTitle("拨打电话");
		mBuilder.setMessage("确认拨打客服电话?\n"+phone);
		mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				Intent intent=new Intent("android.intent.action.DIAL",Uri.parse("tel:"+phone));
				context.startActivity(intent);

			}
		});
		mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		});
		Dialog d = mBuilder.create();
		d.show();
	}
}
