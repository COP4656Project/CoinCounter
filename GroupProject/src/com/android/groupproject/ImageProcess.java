package com.android.groupproject;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.android.OpenCVLoader;
import android.widget.TextView;

public class ImageProcess {
	public int numberOfCircles = 0;
	public TextView label;
	
	public Camera.PictureCallback ProcessCallBack = new Camera.PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera arg1) {
			
			if (!OpenCVLoader.initDebug()) {
		        // Handle initialization error
				
		    }
			
			/*Bitmap bmp = BitmapFactory.decodeByteArray(data , 0, data.length);
			Mat image = new Mat(bmp.getHeight(),bmp.getWidth(),CvType.CV_8UC3);
			Bitmap myBitmap32 = bmp.copy(Bitmap.Config.ARGB_8888, true);
			Utils.bitmapToMat(myBitmap32, image);*/
			
			Mat image = new Mat(480, 320, CvType.CV_8UC1);
			image.put(0, 0, data);
			
			
			Imgproc.cvtColor(image, image, Imgproc.COLOR_YUV420sp2GRAY);
			
			Mat circles = new Mat();
			Imgproc.HoughCircles(image, circles, Imgproc.CV_HOUGH_GRADIENT, 1d, 600d);
			
			if (circles != null)
			{
				numberOfCircles = circles.cols();
			}
		}	
	};
	
	public ImageProcess(TextView label) {
		this.label = label;
		
	}
	
	

}
