package com.android.groupproject;
import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Mat;
import org.opencv.android.Utils;
import org.opencv.android.OpenCVLoader;
import android.util.Log;

// Class processes image and determines amount of circles
public class ImageProcess {
	public int numberOfCircles = 0;
	private CoinCounter coins;
	private ArrayList<Double> scaledRadius;
	public ArrayList<Double> adjustedRadius;
	
	// Call back called when picture taken
	public Camera.PictureCallback ProcessCallBack = new Camera.PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera cam) {
			
			double max = 0;
			scaledRadius.clear();
			adjustedRadius.clear();
			
			// Initialize OpenCV
			if (!OpenCVLoader.initDebug()) {
		        // Handle initialization error
		    }
			
			// Convert data array into bitmap
			Bitmap bmp = BitmapFactory.decodeByteArray(data , 0, data.length);
			Mat image = new Mat();
			Bitmap myBitmap32 = bmp.copy(Bitmap.Config.ARGB_8888, true);
			Utils.bitmapToMat(myBitmap32, image);
			
			// Convert image to grayscale
			Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);
			
			Mat circles = new Mat();
			
			// This is working now it seems, the parameters were determined after testing
			// might be able to improve although it seems that the parameters are sensitive to
			// the way images are lit and the quality of the camera.
   			Imgproc.HoughCircles(image, circles, Imgproc.CV_HOUGH_GRADIENT,1,50,165,50,50,300);
			
			// The following line does not seem to work as well.
			//Imgproc.HoughCircles(image, circles, Imgproc.CV_HOUGH_GRADIENT, 1, 20);
			
			numberOfCircles = circles.cols();
			
			Log.v("cirles","Number of circles " + circles.cols());
			
			// Find number max radius size
			for (int i = 0; i < numberOfCircles; ++i) {
				 double vCircle[] = circles.get(0,i);
				 double radius;
				 
				 if (vCircle == null)
			            break;
				 
				 radius = (double)Math.round(vCircle[2]);
				
				if (max < radius) {
					max = radius;
				}
				
				// Add radius to list
				scaledRadius.add(radius);
			}
			
			// Find factor to scale all values to 100
			double scaleFactor = 100d / max;
			
			// Add scaled values to new list
			for (int i = 0; i < scaledRadius.size(); ++i) {
				adjustedRadius.add(scaledRadius.get(i) * scaleFactor);
			}	
			
			// Set list in next batch in instance of CoinCounter class
			coins.SetNextBatch(adjustedRadius);
		}
	};
	
	// Public Constructor
	public ImageProcess(CoinCounter coins) {
		this.scaledRadius = new ArrayList<Double>();
		this.adjustedRadius = new ArrayList<Double>();
		this.coins = coins; 
	}

}
