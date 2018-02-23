package k.k.sharma.corouselpagerkk;
/*
 * 
 * Copyright (C) 2014 Krishna Kumar Sharma
 * 
 **/
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class MainActivity extends FragmentActivity {
    private KKViewPager mPager;
    boolean toggle = true;
    protected static final String[] CONTENT1 = new String[]{"Pager Carousel 1", "Pager Carousel 2",
            "Pager Carousel 2", "Pager Carousel 4"};
    protected static final String[] CONTENT2 = new String[]{"Pager Carousel", "Title 2",
            "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"};

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initKKViewPager();
        findViewById(R.id.update).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateNoOfCards();
                    }
                });
    }

    private void initKKViewPager() {
        mPager = (KKViewPager) findViewById(R.id.kk_pager);
        mPager.setAdapter(new TestFragmentAdapter(getSupportFragmentManager(),
                MainActivity.this, CONTENT2));
    }
    private void updateNoOfCards() {
        if (toggle = !toggle) {
            mPager.setAdapter(new TestFragmentAdapter(getSupportFragmentManager(),
                    MainActivity.this, CONTENT2));
        } else {
            mPager.setAdapter(new TestFragmentAdapter(getSupportFragmentManager(),
                    MainActivity.this, CONTENT1));
        }
    }
}
