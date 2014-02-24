package k.k.sharma.corouselpagerkk;
/*
 * 
 * Copyright (C) 2014 Krishna Kumar Sharma
 * 
 *  */
import java.util.HashMap;
import java.util.LinkedHashMap;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

public class KKViewPager extends ViewPager {

	public static final String TAG = "KKViewPager";
	boolean isFirstTime = true;
	public int mCenterX;
	public int mCenterY;
	private Point mPoint;
	private Display mDisplay;
	private boolean mEnabled = true;
	private boolean mFadeEnabled = false;

	private TransitionEffect mEffect = TransitionEffect.ZoomOut;

	private HashMap<Integer, Object> mObjs = new LinkedHashMap<Integer, Object>();

	private static final float ZOOM_MAX = 0.80f;

	public enum TransitionEffect {
		ZoomOut
	}

	private static final boolean API_11;
	static {
		API_11 = Build.VERSION.SDK_INT >= 11;
	}

	public KKViewPager(Context context) {
		this(context, null);

	}

	public KKViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);

		// If hardware acceleration is enabled, you should also remove
		// clipping on the pager for its children.
		setClipChildren(false);
		// to avoid fade effect at the end of the page
		setOverScrollMode(2);

	}

	public void setPagingEnabled(boolean enabled) {
		mEnabled = enabled;
	}

	public void setFadeEnabled(boolean enabled) {
		mFadeEnabled = enabled;
	}

	public boolean getFadeEnabled() {
		return mFadeEnabled;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return mEnabled ? super.onInterceptTouchEvent(arg0) : false;
	}

	private State mState = State.IDLE;
	private int oldPage;

	private View mLeft;
	private View mRight;

	private float mScale;

	private enum State {
		IDLE, GOING_LEFT, GOING_RIGHT
	}

	private void animateZoom(View left, View right, float positionOffset,
			boolean in) {
		if (left != null)
			ViewHelper.setAlpha(left, 1.0f);
		if (right != null)
			ViewHelper.setAlpha(right, 1.0f);

		if (mState != State.IDLE) {
			if (left != null) {
				manageLayer(left, true);
				mScale = in ? ZOOM_MAX + (1 - ZOOM_MAX) * (1 - positionOffset)
						: 1 + ZOOM_MAX - ZOOM_MAX * (1 - positionOffset);
				ViewHelper.setPivotX(left, left.getMeasuredWidth() * 0.5f);
				ViewHelper.setPivotY(left, left.getMeasuredHeight() * 0.5f);
				ViewHelper.setScaleX(left, mScale);
				ViewHelper.setScaleY(left, mScale);
			}
			if (right != null) {
				manageLayer(right, true);
				mScale = in ? ZOOM_MAX + (1 - ZOOM_MAX) * positionOffset : 1
						+ ZOOM_MAX - ZOOM_MAX * positionOffset;
				ViewHelper.setPivotX(right, right.getMeasuredWidth() * 0.5f);
				ViewHelper.setPivotY(right, right.getMeasuredHeight() * 0.5f);
				ViewHelper.setScaleX(right, mScale);
				ViewHelper.setScaleY(right, mScale);

			}
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void manageLayer(View v, boolean enableHardware) {
		if (!API_11)
			return;
		int layerType = enableHardware ? View.LAYER_TYPE_HARDWARE
				: View.LAYER_TYPE_NONE;
		if (layerType != v.getLayerType())
			v.setLayerType(layerType, null);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void disableHardwareLayer() {
		if (!API_11)
			return;
		View v;
		for (int i = 0; i < getChildCount(); i++) {
			v = getChildAt(i);
			if (v.getLayerType() != View.LAYER_TYPE_NONE)
				v.setLayerType(View.LAYER_TYPE_NONE, null);
		}
	}

	private Matrix mMatrix = new Matrix();
	private Camera mCamera = new Camera();
	private float[] mTempFloat2 = new float[2];

	protected float getOffsetXForRotation(float degrees, int width, int height) {
		mMatrix.reset();
		mCamera.save();
		mCamera.rotateY(Math.abs(degrees));
		mCamera.getMatrix(mMatrix);
		mCamera.restore();

		mMatrix.preTranslate(-width * 0.5f, -height * 0.5f);
		mMatrix.postTranslate(width * 0.5f, height * 0.5f);
		mTempFloat2[0] = width;
		mTempFloat2[1] = height;
		mMatrix.mapPoints(mTempFloat2);
		return (width - mTempFloat2[0]) * (degrees > 0.0f ? 1.0f : -1.0f);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

		if (mState == State.IDLE && positionOffset > 0) {
			oldPage = getCurrentItem();
			mState = position == oldPage ? State.GOING_RIGHT : State.GOING_LEFT;
		}
		boolean goingRight = position == oldPage;
		if (mState == State.GOING_RIGHT && !goingRight)
			mState = State.GOING_LEFT;
		else if (mState == State.GOING_LEFT && goingRight)
			mState = State.GOING_RIGHT;

		float effectOffset = isSmall(positionOffset) ? 0 : positionOffset;

		mLeft = findViewFromObject(position);
		mRight = findViewFromObject(position + 1);

		switch (mEffect) {

		case ZoomOut:
			animateZoom(mLeft, mRight, effectOffset, true);
			break;

		}

		super.onPageScrolled(position, positionOffset, positionOffsetPixels);

		if (effectOffset == 0) {
			disableHardwareLayer();
			mState = State.IDLE;
		}

	}

	private boolean isSmall(float positionOffset) {
		return Math.abs(positionOffset) < 0.0003;
	}

	public void setObjectForPosition(Object obj, int position) {
		mObjs.put(Integer.valueOf(position), obj);
	}

	public View findViewFromObject(int position) {
		Object o = mObjs.get(Integer.valueOf(position));
		PagerAdapter a = getAdapter();
		View v;
		for (int i = 0; i < getChildCount(); i++) {
			v = getChildAt(i);
			if (a.isViewFromObject(v, o))
				return v;
		}
		return null;
	}

	@Override
	public void setAdapter(PagerAdapter arg0) {
		// TODO Auto-generated method stub
		super.setAdapter(arg0);
		setOffscreenPageLimit(arg0.getCount() - 1);
		if (!isFirstTime) {
			update();
			hack();
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasWindowFocus);
	
		if (isFirstTime) {
			isFirstTime = false;
			update();
			hack();
		}
	}

	public void update() {
		int size = getAdapter().getCount();
		if (size >= 3)
			for (int i = 1; i < 3; i++) {
				View l = findViewFromObject(i);
				if (l != null) {
					ViewHelper.setPivotX(l, l.getMeasuredWidth() * 0.5f);
					ViewHelper.setPivotY(l, l.getMeasuredHeight() * 0.5f);
					ViewHelper.setScaleX(l, .80f);
					ViewHelper.setScaleY(l, .80f);
				}

			}

	}

	public void hack() {
		if (this != null) {
			MotionEvent event = MotionEvent.obtain(SystemClock.uptimeMillis(),
					SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN,
					mCenterX, mCenterY, 0);
			this.dispatchTouchEvent(event);
			event.recycle();

			for (int i = 0; i <= 15; i++) {

				event = MotionEvent.obtain(SystemClock.uptimeMillis(),
						SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE,
						mCenterX + ((mPoint.x * i) / 100), mCenterY, 0);
				this.dispatchTouchEvent(event);
				event.recycle();

			}

			for (int i = 15; i >= -15; i--) {

				event = MotionEvent.obtain(SystemClock.uptimeMillis(),
						SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE,
						mCenterX + ((mPoint.x * i) / 100), mCenterY, 0);
				this.dispatchTouchEvent(event);
				event.recycle();

			}
			for (int i = -15; i <= 0; i++) {

				event = MotionEvent.obtain(SystemClock.uptimeMillis(),
						SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE,
						mCenterX + ((mPoint.x * i) / 100), mCenterY, 0);
				this.dispatchTouchEvent(event);
				event.recycle();

			}

			event = MotionEvent.obtain(SystemClock.uptimeMillis(),
					SystemClock.uptimeMillis(), MotionEvent.ACTION_UP,
					mCenterX, mCenterY, 0);
			this.dispatchTouchEvent(event);
			event.recycle();
		}
	}

	public void getReadyWithNextTwoView(int arg0) {
		if (arg0 + 2 <= (getChildCount() - 1)) {
			View l = findViewFromObject(arg0 + 2);
			if (l != null) {
				ViewHelper.setPivotX(l, l.getMeasuredWidth() * 0.5f);
				ViewHelper.setPivotY(l, l.getMeasuredHeight() * 0.5f);
				ViewHelper.setScaleX(l, .80f);
				ViewHelper.setScaleY(l, .80f);
			}

			l = findViewFromObject(arg0 + 1);
			if (l != null) {
				ViewHelper.setPivotX(l, l.getMeasuredWidth() * 0.5f);
				ViewHelper.setPivotY(l, l.getMeasuredHeight() * 0.5f);
				ViewHelper.setScaleX(l, .80f);
				ViewHelper.setScaleY(l, .80f);
			}

		}
		if (arg0 - 2 >= 0) {
			View l = findViewFromObject(arg0 - 2);
			if (l != null) {
				ViewHelper.setPivotX(l, l.getMeasuredWidth() * 0.5f);
				ViewHelper.setPivotY(l, l.getMeasuredHeight() * 0.5f);
				ViewHelper.setScaleX(l, .80f);
				ViewHelper.setScaleY(l, .80f);
			}

			l = findViewFromObject(arg0 - 1);
			if (l != null) {
				ViewHelper.setPivotX(l, l.getMeasuredWidth() * 0.5f);
				ViewHelper.setPivotY(l, l.getMeasuredHeight() * 0.5f);
				ViewHelper.setScaleX(l, .80f);
				ViewHelper.setScaleY(l, .80f);
			}

		}

	}

	public void setDisplay(Display d) {

		mDisplay = d;
		mPoint = new Point();
		mDisplay.getSize(mPoint);
		mCenterX = mPoint.x / 2;
		mCenterY = mPoint.y / 2;
	}
}
