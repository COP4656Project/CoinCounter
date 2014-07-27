package com.android.groupproject;

import java.text.NumberFormat;
import java.util.ArrayList;

import android.widget.TextView;

// Keeps track of coins and manages UI elements
public class CoinCounter {
	// Set initial values
	private static CoinCounter instance = null;
	private double changeAmount = 0.0;
	private int quarterCount = 0;
	private int nickleCount = 0;
	private int dimeCount = 0;
	private int pennyCount = 0;
	private int largest = 0;
	private ArrayList<Double> coinList;
	
	// UI Elements
	private TextView quarter;
	private TextView nickel;
	private TextView dime;
	private TextView penny;
	private TextView total;	
	
	// Relative Diameters of coins
	private final double QUARTER_DIAM = 95.5;
	private final double NICKLE_DIAM = 83.5;
	private final double DIME_DIAM = 70.5;
	private final double PENNY_DIAM = 75.0;
	
	// Error of margin for circle detection
	private final double ERROR_MARGIN = 2.0;
	
	// Singleton Constructor
	public static CoinCounter GetCounter() {
		if (instance == null) {
			instance = new CoinCounter();
			return instance;
		} else {
			return instance;
		}
	}
	
	// Private Constructor
	private CoinCounter() {
		coinList = new ArrayList<Double>();
	}
	
	// Sets next list of radius found by camera
	public void SetNextBatch(ArrayList<Double> coins) {
		coinList.clear();
		
		// Get different coins here
		coinList = coins;	
	}
	
	// Returns number of coins found
	public int GetLastCount() {
		return coinList.size();
	}
	
	// Experimental, sets coin that has largest radius
	public void SetLargest(int i) {
		// 1 = quarter, 2 = nickle, 4 = penny, 3 = dime
		largest = i;
		boolean isSingle = false;
		UpdateCount(isSingle);
	}
	
	// Used for homogeneous sets of coins
	public void SetSingle(int i) {
		// 1 = quarter, 2 = nickle, 4 = penny, 3 = dime
		largest = i;
		boolean isSingle = true;
		UpdateCount(isSingle);
	}
	
	// When called updates the UI with current state
	public void UpdateUI() {
		quarter.setText("" + quarterCount);
		dime.setText("" + dimeCount);
		nickel.setText("" + nickleCount);
		penny.setText("" + pennyCount);
		NumberFormat df = NumberFormat.getCurrencyInstance();
		total.setText(df.format(changeAmount));
	}
	
	// Sets the UI elements that this class interacts with
	public void SetUIElements(TextView quarters, TextView nickles, TextView pennies, 
			TextView dimes, TextView total) {
		this.quarter = quarters;
		this.nickel = nickles;
		this.dime = dimes;
		this.penny = pennies;
		this.total = total;
	}
	
	// Resets the state of CoinCounter
	public void Reset() {
		quarterCount = 0;
		nickleCount = 0;
		dimeCount = 0;
		pennyCount = 0;
		UpdateTotal();
	}
	
	// Updates the state.
	private void UpdateCount(boolean isSingle) {
		// Used if coins set is homogeneous
		if (isSingle) {
			switch (largest) {
				case 1:
					quarterCount += coinList.size();
					break;
				case 2:
					nickleCount += coinList.size();
					break;
				case 3:
					dimeCount += coinList.size();
					break;
				default:
					pennyCount += coinList.size();
					break;
			}
			
		} else {
			// Needs to be improved or removed, experimental
			double quarter_max, quarter_min;
			double dime_max, dime_min;
			double penny_max, penny_min;
			double nickel_max, nickel_min;
			
			// Sets error of margin for relative coin diameters we are looking for
			quarter_max = QUARTER_DIAM + ERROR_MARGIN;
			quarter_min = QUARTER_DIAM - ERROR_MARGIN;
			
			dime_max = DIME_DIAM + ERROR_MARGIN;
			dime_min = DIME_DIAM - ERROR_MARGIN;
			
			penny_max = PENNY_DIAM + ERROR_MARGIN;
			penny_min = PENNY_DIAM - ERROR_MARGIN;
			
			nickel_max = NICKLE_DIAM + ERROR_MARGIN;
			nickel_min = NICKLE_DIAM - ERROR_MARGIN;
			
			double size = 0;
			
			// Iterate over coin radius list and look for radius
			// that fit in range. Does not seem to work well because
			// the typcial error of margin from the camera is greater
			// then the relative differences in sizes between the coins
			for (int i = 0; i < coinList.size(); ++i) {
				size = coinList.get(i);
				
				if (size <= quarter_max && size >= quarter_min) {
					++quarterCount;
				} else if (size <= dime_max && size >= dime_min) {
					++dimeCount;
				} else if (size <= nickel_max && size >= nickel_min) {
					++nickleCount;
				} else if (size <= penny_max && size >= penny_min) {
					++pennyCount;
				}			
			}	
		}
		
		// Updates total
		UpdateTotal();
	}
	
	// Calculates the total amount of change
	private void UpdateTotal()
	{
		changeAmount = (dimeCount * 0.10)
				      + (quarterCount * 0.25)
				      + (pennyCount * 0.01)
				      + (nickleCount * 0.05)
				      ;
	}
}
