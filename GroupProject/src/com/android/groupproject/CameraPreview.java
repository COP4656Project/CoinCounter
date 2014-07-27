package com.android.groupproject;

import java.util.List;
import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

// Modified from android Camera Documentation
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Camera.Parameters params;
    public int mFrameWidth;
    public int mFrameHeight;    
    
    public CameraPreview(Context context, Camera camera) {
        super(context);
        
        // Retrieve nessasary information from camera
        mCamera = camera;
        params = mCamera.getParameters();
        
    	List<Camera.Size> sizes = params.getSupportedPreviewSizes();
    	
    	mFrameWidth = (int) sizes.get(0).width;
    	mFrameHeight = (int) sizes.get(0).height;

        mHolder = getHolder();
        mHolder.addCallback(this);
        
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
    	    // Start the preview
            mCamera.startPreview();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Not needed
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (mHolder.getSurface() == null){
          // preview surface does not exist
          return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
          // ignore
        }

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            // Ignore
        }
    }
}