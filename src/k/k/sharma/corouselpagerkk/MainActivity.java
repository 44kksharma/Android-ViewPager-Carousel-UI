package k.k.sharma.corouselpagerkk;
/*
 * 
 * Copyright (C) 2014 Krishna Kumar Sharma
 * 
 *  */
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.View;

public class MainActivity extends FragmentActivity implements
		ViewPager.OnPageChangeListener {
	private Point p;
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		Log.d("config","changed");
	}

	private Display display;
	private KKViewPager mPager;
	private TestFragmentAdapter mAdapter;
	boolean toggle = true;
	protected static final String[] CONTENT1 = new String[] { "This", "Is",
			"A", "Test", "And", "I", "Am", "Giving", "It" };
	protected static final String[] CONTENT2 = new String[] { "kk", "Is",
			"going", "to", "be", "a", "rock", "star", "are", "you", "going",
			"to ", "join", "him","android","app","development","is","fun","do","you","enjoy", "it"};
	int old_currentPosition = 0;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setKKViewPager();
		findViewById(R.id.update).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						updateNoOfCards();
					}

				});

	}

	private void setKKViewPager() {
		display = getWindowManager().getDefaultDisplay();
		p = new Point();
		display.getSize(p);
		mPager = (KKViewPager) findViewById(R.id.kk_pager);
		mAdapter = new TestFragmentAdapter(getSupportFragmentManager(), mPager,
				MainActivity.this, CONTENT2);
	
		mPager.setDisplay(display);
		mPager.setAdapter(mAdapter);
		mPager.setOnPageChangeListener(this);
		// set page margin to 18 percent of the width
		// you can adjust this according to your requirement
		mPager.setPageMargin(-((p.x * 18) / 100));
	

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		mPager.onWindowFocusChanged(hasFocus);
		Log.d("onwindow","changed");
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		// we are keeping few cards in memory so as soon as new card comes in
		// the memory attach animation to them
		mPager.getReadyWithNextTwoView(arg0);

	}

	private void updateNoOfCards() {
		mAdapter = null;

		toggle = !toggle;
		if (toggle) {
			mAdapter = new TestFragmentAdapter(getSupportFragmentManager(),
					mPager, MainActivity.this, CONTENT1);
		} else {
			mAdapter = new TestFragmentAdapter(getSupportFragmentManager(),
					mPager, MainActivity.this, CONTENT2);
		}

		mPager.setAdapter(mAdapter);

	}
}
