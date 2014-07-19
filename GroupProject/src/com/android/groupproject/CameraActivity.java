package com.android.groupproject;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class CameraActivity extends Activity {
	private Camera mCamera;
    private CameraPreview mPreview;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_camera);
	    
	    mCamera = null;
	    
	    try {
	        mCamera = Camera.open(); 
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	  
        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_layout);
        preview.addView(mPreview);
        
       Button capture = (Button) findViewById(R.id.capture);
       
       capture.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			mCamera.takePicture(null, null, null);
			
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
