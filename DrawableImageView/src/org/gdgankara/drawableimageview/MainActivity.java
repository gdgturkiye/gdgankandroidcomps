package org.gdgankara.drawableimageview;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	DrawableImageView imgView;
	Button btnUndo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imgView = (DrawableImageView) this.findViewById(R.id.img);
		imgView.setBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_launcher));
		
		btnUndo = (Button) this.findViewById(R.id.buttonUndo);
		btnUndo.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		imgView.undo();
		
	}

}
