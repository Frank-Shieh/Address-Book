//package com.reach.tong2;
//
//import java.io.File;
//
//import com.reach.share.Datamanager;
//
//import android.app.Service;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.IBinder;
//import android.provider.MediaStore;
//import android.widget.Toast;
//
//public class HeadPhotoGetService extends Service {
//
//	private int CODE;
//	private String IMAGE_FILE_NAME = "header.jpg";
//	private static final int FROMIMAGE = -1;
//	private static final int FROMCCAMERA = -2;
//	private static final int IMAGE_REQUEST_CODE = 0;
//	private static final int CAMERA_REQUEST_CODE = 1;
//	private static final int RESIZE_REQUEST_CODE = 2;
//	private Bitmap target;
//
//	@Override
//	public IBinder onBind(Intent intent) {
//		// TODO Auto-generated method stub
//		getHeadphoto(intent.getIntExtra("code", 0), intent.getIntExtra("from", 0));
//		return null;
//	}
//	
//	@Override
//	public void onCreate() {
//		// TODO Auto-generated method stub
//		super.onCreate();
//		
//
//	}
//	
//	public void getHeadphoto(int code,int from){
//		this.CODE = code;
//		switch(from){
//		case FROMIMAGE:
//			Intent galleryIntent = new Intent(Intent.ACTION_PICK);
//			galleryIntent.setType("image/*");
//			startActivityForResult(galleryIntent, CODE);
//			break;
//		case FROMCCAMERA:
//			if (isSdcardExisting()) {
//				Intent cameraIntent = new Intent(
//						"android.media.action.IMAGE_CAPTURE");
//				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
//				cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
//				startActivityForResult(cameraIntent, CODE);
//			} else {
//				Toast.makeText(this, "«Î≤Â»Îsdø®", Toast.LENGTH_LONG)
//						.show();
//			}
//			break; 
//		}
//		
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (resultCode != RESULT_OK) {
//			this.finish();
//		} else {
//			switch (requestCode) {
//			case IMAGE_REQUEST_CODE:
//				resizeImage(data.getData());
//				break;
//			case CAMERA_REQUEST_CODE:
//				if (isSdcardExisting()) {
//					resizeImage(getImageUri());
//				} else {
//					Toast.makeText(this, "Œ¥’“µΩ¥Ê¥¢ø®£¨Œﬁ∑®¥Ê¥¢’’∆¨£°", Toast.LENGTH_LONG)
//							.show();
//				}
//				break;
//			case RESIZE_REQUEST_CODE:
//				if (data != null) {
//					showResizeImage(data);
//				}
//				break;
//			}
//		}
//
//		super.onActivityResult(requestCode, resultCode, data);
//	}
//
//	public void resizeImage(Uri uri) {
//		Intent intent = new Intent("com.android.camera.action.CROP");
//		intent.setDataAndType(uri, "image/*");
//		intent.putExtra("crop", "true");
//		intent.putExtra("aspectX", 1);
//		intent.putExtra("aspectY", 1);
//		intent.putExtra("outputX", 250);
//		intent.putExtra("outputY", 250);
//		intent.putExtra("return-data", true);
//		startActivityForResult(intent, RESIZE_REQUEST_CODE);
//	}
//
//	private void showResizeImage(Intent data) {
//		Bundle extras = data.getExtras();
//		if (extras != null) {
//			target = extras.getParcelable("data");
//		}
//		DataManager.setHeadphoto(target);
//	}
//
//	private boolean isSdcardExisting() {
//		final String state = Environment.getExternalStorageState();
//		if (state.equals(Environment.MEDIA_MOUNTED)) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	private Uri getImageUri() {
//		return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
//				IMAGE_FILE_NAME));
//	}
//
//}
