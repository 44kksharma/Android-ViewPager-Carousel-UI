package k.k.sharma.corouselpagerkk;
/*
 * 
 * Copyright (C) 2014 Krishna Kumar Sharma
 * 
 *  */
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public final class TestFragment extends Fragment {

	private TextView title;
	private RelativeLayout card;
	boolean toggle = true;
	long duration = 200;
	private Point p;
	private Display display;
	String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
			"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux",
			"OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
			"Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Android",
			"iPhone", "WindowsMobile", "Android", "iPhone", "WindowsMobile",
			"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux",
			"OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
			"Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Android",
			"iPhone", "WindowsMobile", "Android", "iPhone", "WindowsMobile",
			"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux",
			"OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
			"Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Android",
			"iPhone", "WindowsMobile", "Android", "iPhone", "WindowsMobile",
			"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux",
			"OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
			"Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Android",
			"iPhone", "WindowsMobile" };
	int col[] = { Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.CYAN };

	public static TestFragment newInstance(String content, Context c) {

		TestFragment fragment = new TestFragment();

		fragment.mContent = content;

		return fragment;
	}

	private String mContent = "???";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		display = getActivity().getWindowManager().getDefaultDisplay();
		p = new Point();
		display.getSize(p);
		ViewGroup Rootview = (ViewGroup) inflater.inflate(R.layout.item,
				container, false);
		int bg = Color.rgb((int) Math.floor(Math.random() * 128) + 64,
				(int) Math.floor(Math.random() * 128) + 64,
				(int) Math.floor(Math.random() * 128) + 64);
		ListView listView = (ListView) Rootview.findViewById(R.id.listView1);

		listView.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, values));
		card = (RelativeLayout) Rootview.findViewById(R.id.card);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// setting left and right margin to 6% of width
		// you can adjust this according to your requirement
		lp.rightMargin = lp.leftMargin = ((p.x * 6) / 100);
		card.setBackgroundColor(bg);
		card.setLayoutParams(lp);
		title = (TextView) Rootview.findViewById(R.id.title);
		title.setText("" + mContent);
		Button show = (Button) Rootview.findViewById(R.id.viewSwitcher);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getActivity(), values[arg2], Toast.LENGTH_SHORT)
						.show();

			}
		});
		show.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(getActivity(), DetailView.class);

				startActivity(i);

			}
		});

		return Rootview;
	}
}
