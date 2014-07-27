package com.android.groupproject;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.content.Intent;
import com.android.groupproject.CameraActivity;

public class MainActivity extends ActionBarActivity {
	private CoinCounter coins;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Register launch camera Button
		Button launchCamera = (Button) findViewById(R.id.launch_camera_btn);
		Button reset = (Button) findViewById(R.id.reset);
		final Context mainContext = this;
		
		// Call singleton constructor for CoinCounter
		coins = CoinCounter.GetCounter();
		
		// Set UI Elements
		coins.SetUIElements((TextView)findViewById(R.id.quarter_count),
							(TextView)findViewById(R.id.nickle_count),
							(TextView)findViewById(R.id.penny_count),
							(TextView)findViewById(R.id.dime_count),
							(TextView)findViewById(R.id.total_text)
							);
		
		// Set launch camera click listener
		launchCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Launch CameraActivity
				Intent intent = new Intent(mainContext, CameraActivity.class);
			    startActivity(intent);
				
			}
			
		});
		
		reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				coins.Reset();
				coins.UpdateUI();				
			}
			
		});
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (coins != null) {
			coins.UpdateUI();
		}
	}

}
