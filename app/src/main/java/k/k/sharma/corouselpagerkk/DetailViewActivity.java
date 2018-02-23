package k.k.sharma.corouselpagerkk;
/*
 * 
 * Copyright (C) 2014 Krishna Kumar Sharma
 * 
 *  */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class DetailViewActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.detail_view);
		findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
		
	}

}
