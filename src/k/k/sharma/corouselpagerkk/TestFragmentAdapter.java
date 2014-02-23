package k.k.sharma.corouselpagerkk;
/*
 * 
 * Copyright (C) 2014 Krishna Kumar Sharma
 * 
 *  */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

class TestFragmentAdapter extends FragmentStatePagerAdapter {

	private KKViewPager mPager;
	private Context context;
	protected String[] CONTENT;
	private int mCount;

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return PagerAdapter.POSITION_NONE;
	}

	public KKViewPager getmPager() {
		return mPager;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);

	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		Object obj = super.instantiateItem(container, position);

		mPager.setObjectForPosition(obj, position);

		return obj;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		if (object != null) {
			return ((Fragment) object).getView() == view;
		} else {
			return false;
		}
	}

	public TestFragmentAdapter(FragmentManager fm, KKViewPager mPager,
			Context context, String[] data) {
		super(fm);
		this.mPager = mPager;
		this.context = context;
		CONTENT = data;
		mCount = CONTENT.length;
	}

	@Override
	public Fragment getItem(int position) {

		return TestFragment.newInstance(CONTENT[position % CONTENT.length],
				context);
	}

	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return CONTENT[position % CONTENT.length];
	}

	public void setCount(int count) {

		mCount = count;
		notifyDataSetChanged();

	}
}