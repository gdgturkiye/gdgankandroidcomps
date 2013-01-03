package org.gdgankara.halfcheckbox;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnClickListener{

	HalfCheckBox half;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		half = new HalfCheckBox(this);

		LinearLayout lin = (LinearLayout)this.findViewById(R.id.lay);
		lin.addView(half);
		Button btn = (Button)this.findViewById(R.id.button1);
		btn.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		if(half.getCheckState() == HalfCheckBox.HALF_CHECKED)
			half.setCheckBox(HalfCheckBox.NOT_CHECKED);
		else
			half.setCheckBox(HalfCheckBox.HALF_CHECKED);
	}

}
