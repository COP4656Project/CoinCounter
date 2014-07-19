package com.android.groupproject;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class CameraActivity extends Activity {
	private Camera mCamera;
    private CameraPreview mPreview;
    private ImageProcess process;
    private TextView count;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_camera);
	    
	    count = (TextView)findViewById(R.id.textView1);
	    
	        
	    
	    mCamera = null;
	    
	    try {
	        mCamera = Camera.open(); 
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	  
        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        process = new ImageProcess(count);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_layout);
        preview.addView(mPreview);
        
       Button capture = (Button) findViewById(R.id.capture);
       
       capture.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			mCamera.takePicture(null, null, process.ProcessCallBack);
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
