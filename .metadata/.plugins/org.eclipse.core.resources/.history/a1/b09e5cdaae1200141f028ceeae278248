package com.android.groupproject;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ImageView;

public class CameraActivity extends Activity {
	private Camera mCamera;
    private CameraPreview mPreview;
    private ImageProcess process;
    private Camera.AutoFocusCallback focus = new Camera.AutoFocusCallback() {
		
		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			camera.takePicture(null, null, process.ProcessCallBack);
			
		}
	};
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_camera);
	  
	    mCamera = null;
	    
	    try {
	        mCamera = Camera.open(); 
	    	mCamera.setDisplayOrientation(90);	        
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    
	    CoinCounter coins = CoinCounter.GetCounter();

        mPreview = new CameraPreview(this, mCamera);
        
        process = new ImageProcess(coins);
        
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_layout);
        preview.addView(mPreview);
        
        Button capture = (Button) findViewById(R.id.capture);
       
        capture.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			mCamera.autoFocus(focus);
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
