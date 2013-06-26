package k.k.sharma.corouselpagerkk;

import k.k.sharma.corouselpagerkk.KKViewPager.TransitionEffect;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

public class MainActivity extends Activity {

	private KKViewPager mPager;
	PagerContainer mContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContainer = (PagerContainer) findViewById(R.id.pager_container);

		setupEffect(TransitionEffect.ZoomOut);

	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		String[] effects = this.getResources().getStringArray(
//				R.array.kk_effects);
//		for (String effect : effects)
//			menu.add(effect);
//		return true;
//	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		if (item.getTitle().toString().equals("ToggleFade")) {
//			mPager.setFadeEnabled(!mPager.getFadeEnabled());
//		} else {
//			TransitionEffect effect = TransitionEffect.valueOf(item.getTitle()
//					.toString());
//			setupEffect(effect);
//		}
//		return true;
//	}

	private void setupEffect(TransitionEffect effect) {
		mPager = mContainer.getViewPager();
		mPager.setTransitionEffect(effect);
		mPager.setAdapter(new MainAdapter());
		mPager.setPageMargin(120);
		mPager.setOffscreenPageLimit(9);

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		View l = mPager.findViewFromObject(0);
		if (l != null) {
			
			ViewHelper.setPivotX(l, l.getMeasuredWidth() * 0.5f);
			ViewHelper.setPivotY(l, l.getMeasuredHeight() * 0.5f);
			ViewHelper.setScaleX(l, 1.5f);
			ViewHelper.setScaleY(l, 1.5f);
		}

	}

	private class MainAdapter extends PagerAdapter {
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			TextView text = new TextView(MainActivity.this);
			text.setGravity(Gravity.CENTER);
			text.setTextSize(30);
			text.setTextColor(Color.WHITE);
			text.setText("Page " + position);
			text.setPadding(30, 30, 30, 30);
			int bg = Color.rgb((int) Math.floor(Math.random() * 128) + 64,
					(int) Math.floor(Math.random() * 128) + 64,
					(int) Math.floor(Math.random() * 128) + 64);
			text.setBackgroundColor(bg);

			container.addView(text, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);

			mPager.setObjectForPosition(text, position);

			return text;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object obj) {

			container.removeView(mPager.findViewFromObject(position));
		}

		@Override
		public int getCount() {

			return 10;

		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			if (view instanceof OutlineContainer) {
				return ((OutlineContainer) view).getChildAt(0) == obj;
			} else {
				return view == obj;
			}
		}
	}

}
