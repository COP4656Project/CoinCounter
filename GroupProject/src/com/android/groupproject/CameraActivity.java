package com.android.groupproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Button;
import android.widget.PopupWindow;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

// Manages the camera activity
public class CameraActivity extends Activity {
	// Values of coins 
	private final int QUARTER = 1;
	private final int NICKEL = 2;
	private final int DIME = 3;
	private final int PENNY = 4;
	
	private Camera mCamera;
	private CoinCounter coins;
	private CameraActivity actContext = this;
    private CameraPreview mPreview;
    private ImageProcess process;
    
    // Callback that takes picture
    private Camera.AutoFocusCallback focus = new Camera.AutoFocusCallback() {
		
		@Override
		public void onAutoFocus(boolean success, Camera camera) {
	        
			// Get size of window to set popup size
	        Display display = getWindowManager().getDefaultDisplay();
	        Point size = new Point();
	        display.getSize(size);
	        final int width = size.x;
	        
			camera.takePicture(null, null, process.ProcessCallBack);
			
			LayoutInflater inflater = (LayoutInflater)
				       actContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View popup = inflater.inflate(R.layout.set_coins, null, false);
			
			Button cancel = (Button)popup.findViewById(R.id.cancel);
			Button sQuarter = (Button)popup.findViewById(R.id.sbtn_quarter);
			Button sNickel = (Button)popup.findViewById(R.id.sbtn_nickel);
			Button sDime = (Button)popup.findViewById(R.id.sbtn_dime);
			Button sPenny = (Button)popup.findViewById(R.id.sbtn_penny);
			
			Button lQuarter = (Button)popup.findViewById(R.id.lbtn_quarter);
			Button lNickel = (Button)popup.findViewById(R.id.lbtn_nickel);
			Button lDime = (Button)popup.findViewById(R.id.lbtn_dime);
			Button lPenny = (Button)popup.findViewById(R.id.lbtn_penny);
			
			// Set popup size with margin
			final int MARGIN = 40;
			
			final PopupWindow pw = new PopupWindow(popup, 
				       width - MARGIN, 
				       width - MARGIN, 
				       true);
			
			// Set click listeners
			sQuarter.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					coins.SetSingle(QUARTER);
				    pw.dismiss();
				    //mCamera.startPreview();
				    finish();
				}
				
			});
			
			sNickel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					coins.SetSingle(NICKEL);
				    pw.dismiss();
				    //mCamera.startPreview();
				    finish();
				}
				
			});
			
			sDime.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					coins.SetSingle(DIME);
				    pw.dismiss();
				    finish();
				    //mCamera.startPreview();
				}
				
			});
			
			sPenny.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					coins.SetSingle(PENNY);
				    pw.dismiss();
				    //mCamera.startPreview();
				    finish();
				}
				
			});
			
			lQuarter.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					coins.SetLargest(QUARTER);
				    pw.dismiss();
				    //mCamera.startPreview();
				    finish();
				}
				
			});
			
			lNickel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					coins.SetLargest(NICKEL);
				    pw.dismiss();
				    //mCamera.startPreview();
				    finish();
				}
				
			});
			
			lDime.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					coins.SetLargest(DIME);
				    pw.dismiss();
				    //mCamera.startPreview();
				    finish();
				}
				
			});
			
			lPenny.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					coins.SetLargest(PENNY);
				    pw.dismiss();
				    //mCamera.startPreview();
				    finish();
				}
				
			});
			
			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
				    pw.dismiss();
				    mCamera.startPreview();
				}
				
			});
			// Show popup
		    pw.showAtLocation(actContext.findViewById(R.id.main), Gravity.CENTER, 0, 0); 
		}
	};
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_camera);
	  
	    // Setup camera
	    mCamera = null;
	    
	    try {
	        mCamera = Camera.open(0); // should allow for devices with front facing cameras to function
	    	mCamera.setDisplayOrientation(90);	        
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    
	    coins = CoinCounter.GetCounter();
        mPreview = new CameraPreview(this, mCamera);    
        process = new ImageProcess(coins);
        
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        
        
        // Set height of FrameLayout to ensure aspect ratio is correct
        int realWidth = size.x;
        int realHeight = (mPreview.mFrameWidth * realWidth) / mPreview.mFrameHeight;
        
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_layout);
        android.view.ViewGroup.LayoutParams params = preview.getLayoutParams();
        
        params.height = realHeight;
        params.width = realWidth;        
        
        preview.addView(mPreview);
        Button capture = (Button) findViewById(R.id.capture);
        Button backButton = (Button) findViewById(R.id.backBtn);

        
        capture.setOnClickListener(new OnClickListener() {
       
			@Override
			public void onClick(View arg0) {
				mCamera.autoFocus(focus);
			}
	    	   
	   });

       backButton.setOnClickListener(new OnClickListener() {
           
    			@Override
    			public void onClick(View arg0) {
    				finish();
    			}
       });   
	}
	
	
	
	@Override
	public void onPause() {
		super.onPause();
		
		mCamera.release();
	}

	@Override
    public void onStop() {
		super.onStop();
		
		mCamera.release();
	}
	
	@Override
    public void onDestroy() {
		super.onDestroy();
		
		mCamera.release();		
    }

}
