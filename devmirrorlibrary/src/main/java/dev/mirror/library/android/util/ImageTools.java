package dev.mirror.library.android.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageTools{

	private Context mContext;
	private String[] mItems = { "相机", "图库" };

	private String mFolderString = "/kkwy/";
	private static String mPath;
	public static final int CAMARA = 300;
	public static final int GALLERY = 301;
	public static final int BITMAP = 302;
	private int defaultHeight = 720;

	private int defaultWidth = 1280;
	private boolean fromFragment;
	private Fragment mFragment;
	@SuppressWarnings("unused")
	private ImageTools(){
	}

	public void setFromFragment(boolean from)
	{
		this.fromFragment = from;
	}

	public void setFragment(Fragment fragment) {
		this.mFragment = fragment;
	}


	public void setFolder(String folder)
	{
		this.mFolderString = folder;
	}

	public void setHeight(int height) {
		this.defaultHeight = height;
	}

	public void setWidth(int width) {
		this.defaultWidth = width;
	}

	public ImageTools(Context context)
	{
		this.mContext = context;
		initFile();
	}

	public ImageTools(Fragment fragment)
	{
		this.mFragment = fragment;
		this.mContext = fragment.getActivity();
		this.fromFragment = true;
		initFile();
	}

	@SuppressLint({"NewApi"})
	public String bitmapToString(Bitmap bitmap)
	{
		if (bitmap != null) {
			String str = null;
			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
			byte[] bytes = bStream.toByteArray();
			str = Base64.encodeToString(bytes, 0);
			return str;
		}
		return null;
	}

	private void initFile()
	{
		File file = new File(Environment.getExternalStorageDirectory() + 
				this.mFolderString);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(file + ".nomedia");
		if (!file.exists())
			try {
				file.createNewFile();
			}
		catch (IOException e) {
			try {
				throw new IOException("无法创建" + file.toString() + "文件");
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void showGetImageDialog(String title)
	{
		AlertDialog.Builder mBuilder = new AlertDialog.Builder(this.mContext);
		mBuilder.setTitle(title);
		mBuilder.setItems(this.mItems, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				switch (which) {
				case 0:
					ImageTools.this.openCamara();
					break;
				case 1:
					ImageTools.this.openGallery();
					break;
				}
			}
		});
		mBuilder.show();
	}

	public void openCamara()
	{
		Intent intent = new Intent();

		if (Environment.getExternalStorageState().equals(
				"mounted"))
		{
			mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + 
					this.mFolderString;
			mPath += System.currentTimeMillis();
			intent.setAction("android.media.action.IMAGE_CAPTURE");
			intent.putExtra("output", 
					Uri.fromFile(new File(mPath)));
			if (this.fromFragment)
				this.mFragment.startActivityForResult(intent, CAMARA);
			else
				((Activity)this.mContext).startActivityForResult(intent, CAMARA );
		}
	}

	public void openGallery()
	{
		Intent intent = new Intent("android.intent.action.PICK", 
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		if (this.fromFragment)
			this.mFragment.startActivityForResult(intent, GALLERY);
		else
			((Activity)this.mContext).startActivityForResult(intent, GALLERY);
	}

	public void getBitmapFromCamara(final OnBitmapCreateListener onBitmapCreateListener)
	{
		final Handler mHandler = new Handler() {
			public void handleMessage(Message msg) {
				onBitmapCreateListener.onBitmapCreate((Bitmap)msg.obj, ImageTools.mPath);
			}
		};
		new Thread() {
			@SuppressWarnings("unused")
			public void run() {

				BitmapFactory.Options options=new BitmapFactory.Options();
				options.inJustDecodeBounds = false;

				Bitmap b = BitmapFactory.decodeFile(ImageTools.mPath);
				int size = 1;
				int width = b.getWidth();
				int height = b.getHeight();
				do {
					width /= 2;
					height /= 2;
					size *= 2;
				}while ((width >= ImageTools.this.defaultWidth) && (height >= ImageTools.this.defaultHeight));

				b.recycle();
				options.inSampleSize = size;

//				options.inSampleSize = 5;

				Bitmap mBitmap = BitmapFactory.decodeFile(ImageTools.mPath, options);

				Message message = new Message();
				message.obj = mBitmap;
				message.what = 0;
				mHandler.sendMessage(message);
			}
		}
		.start();
	}


	public Bitmap getBitmapFromGallery(Intent data)
	{
		Uri selectedImage = data.getData();
		String[] filePathColumn = { "_data" };

		Cursor cursor = this.mContext.getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String filePath = cursor.getString(columnIndex);
		cursor.close();

		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		int size = 1;
		int width = 1;
		int height = 1;
		do {
			width /= 2;
			height /= 2;
			size *= 2;
		}while ((width >= ImageTools.this.defaultWidth) && (height >= ImageTools.this.defaultHeight));

		options.inSampleSize = size;
		Bitmap btp =BitmapFactory.decodeFile(filePath, options);

		return btp;
	}


	public void startZoomPhoto(Uri uri, int outputX, int outputY){
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");

		intent.putExtra("crop", "true");
		//剪裁比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		//输出大小
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);

		intent.putExtra("noFaceDetection", true);//不采用脸部识别
		intent.putExtra("outputFormat", "JPEG");// 返回格式
		intent.putExtra("return-data", true);//回调方法data.getExtras().getParcelable("data")返回数据为空


		try {
			if (this.fromFragment)
				this.mFragment.startActivityForResult(intent, BITMAP);
			else
				((Activity)this.mContext).startActivityForResult(intent, BITMAP);
		}
		catch (ActivityNotFoundException e)
		{
			e.printStackTrace();
			Toast.makeText(this.mContext, "未找到可以剪裁图片的程序", Toast.LENGTH_SHORT).show();
		}
	}

	public Bitmap getBitmapFromZoomPhoto(Intent data)
	{
		Bundle extras = data.getExtras();
		if (extras != null) {
			return (Bitmap)extras.getParcelable("data");
		}

		return null;
	}

	/**
	 * sd卡中的图片转bitmap后再转string
	 * 用于上传图片使用
	 * @param st
	 * @return
	 */
	public String filePathToString2(String st) {
		String str = null;
		try{
//			Bitmap bitmap = BitmapFactory.decodeFile(st);
//			int size = 1;
//			int width = bitmap.getWidth();
//			int height = bitmap.getHeight();
//			do {
//				width /= 2;
//				height /= 2;
//				size *= 2;
//			}while ((width >= ImageTools.this.defaultWidth) && (height >= ImageTools.this.defaultHeight));
			BitmapFactory.Options options=new BitmapFactory.Options();
			options.inJustDecodeBounds = false;
			options.inSampleSize = 4;
//			bitmap.recycle();

			Bitmap mBitmap = toturn(BitmapFactory.decodeFile(st, options),readPictureDegree(st));

			if (mBitmap != null) {
				ByteArrayOutputStream bStream = new ByteArrayOutputStream();
				mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
				byte[] bytes = bStream.toByteArray();
				str = Base64.encodeToString(bytes, 0);
				//回收bitmap
				mBitmap.recycle();
			}

			return str;
		}
		catch (Exception e){
			return null;
		}
	}

	/**
	 * sd卡中的图片转bitmap后再转string
	 * 用于上传图片使用
	 * @param st
	 * @return
	 */
	public String filePathToString(String st) {
		String str = null;
		try{

			//1.加载位图
//			String path = Environment.getExternalStorageDirectory().toString()+"/DCIM/device.png";

			InputStream is = new FileInputStream(st);
			//2.为位图设置100K的缓存
			BitmapFactory.Options opts=new BitmapFactory.Options();
			opts.inTempStorage = new byte[100 * 1024];
			//3.设置位图颜色显示优化方式
			//ALPHA_8：每个像素占用1byte内存（8位）
			//ARGB_4444:每个像素占用2byte内存（16位）
			//ARGB_8888:每个像素占用4byte内存（32位）
			//RGB_565:每个像素占用2byte内存（16位）
			//Android默认的颜色模式为ARGB_8888，这个颜色模式色彩最细腻，显示质量最高。但同样的，占用的内存//也最大。也就意味着一个像素点占用4个字节的内存。我们来做一个简单的计算题：3200*2400*4 bytes //=30M。如此惊人的数字！哪怕生命周期超不过10s，Android也不会答应的。
			opts.inPreferredConfig = Bitmap.Config.RGB_565;
			//4.设置图片可以被回收，创建Bitmap用于存储Pixel的内存空间在系统内存不足时可以被回收
//						opts.inPurgeable = true;
			//5.设置位图缩放比例
			//width，hight设为原来的四分一（该参数请使用2的整数倍）,这也减小了位图占用的内存大小；例如，一张//分辨率为2048*1536px的图像使用inSampleSize值为4的设置来解码，产生的Bitmap大小约为//512*384px。相较于完整图片占用12M的内存，这种方式只需0.75M内存(假设Bitmap配置为//ARGB_8888)。
//			opts.inSampleSize = 4;
			//6.设置解码位图的尺寸信息
//						opts.inInputShareable = true;
			//7.解码位图
			Bitmap bitmap =BitmapFactory.decodeStream(is,null, opts);


//			Bitmap bitmap = BitmapFactory.decodeFile(st);
			int size = 1;
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			do {
				width /= 2;
				height /= 2;
				size *= 2;
			}while ((width >= ImageTools.this.defaultWidth) && (height >= ImageTools.this.defaultHeight));
			BitmapFactory.Options options=new BitmapFactory.Options();
			options.inJustDecodeBounds = false;
//			options.inSampleSize = size;
			options.inSampleSize = 3;
			bitmap.recycle();

//			Bitmap mBitmap = BitmapFactory.decodeFile(st, options);
			Bitmap mBitmap = toturn(BitmapFactory.decodeFile(st, options),readPictureDegree(st));


			if (bitmap != null) {
				ByteArrayOutputStream bStream = new ByteArrayOutputStream();
				mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
				byte[] bytes = bStream.toByteArray();
				str = Base64.encodeToString(bytes, 0);
				//回收bitmap
				mBitmap.recycle();
			}

			return str;
		}
		catch (Exception e){
			return null;
		}
	}

	public static abstract interface OnBitmapCreateListener
	{
		public abstract void onBitmapCreate(Bitmap paramBitmap, String paramString);
	}


	public static Bitmap toturn(Bitmap img,int rotate){
		Matrix matrix = new Matrix();
		matrix.postRotate(rotate); /*翻转90度*/
		int width = img.getWidth();
		int height =img.getHeight();
		img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
		return img;
	}

	/**
	 * 读取照片exif信息中的旋转角度
	 * @param path 照片路径
	 * @return角度
	 */
	public static int readPictureDegree(String path) {
		int degree  = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
}