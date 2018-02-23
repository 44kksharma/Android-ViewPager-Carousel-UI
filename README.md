# Android-ViewPager-Carousel-UI

### ViewPager Carousel can be used as Top Level Navigation or as a Image / Video Gallery View


<a href="https://imgflip.com/gif/2557e5"><img src="https://i.imgflip.com/2557e5.gif" title="made at imgflip.com"/></a>
<!--- this is for screenshot ![Screenshot](Screenshot_20180223-161250.png) --->
## Getting Started

### setup pager in layout.xml:
```
<k.k.sharma.corouselpagerkk.KKViewPager xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/kk_pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent" 
       />
```
### setup pager in you Fragment or Activity :
```
mPager = (KKViewPager) findViewById(R.id.kk_pager);
mPager.setAdapter(new TestFragmentAdapter(getSupportFragmentManager(),
                MainActivity.this, CONTENT2));
```

### Adjust Page Spacing :
 
Adjust Page Spacing only if defalult(40dp) is not giving the desired result  :
you can adjust page spacing by set calling method
```
mPager.setPageMargin(marging in pixels);
```
### Enable/Desable scale and fading animation:
```
mPager.setAnimationEnabled(true/false);
```
### Enable/Desable fading animation and configure fading factor:
```
mPager.setFadeEnabled(true);
mPager.setFadeFactor(0.6f);
```
