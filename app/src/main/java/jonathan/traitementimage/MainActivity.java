package jonathan.traitementimage;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
	private int imageId = 1;

	public int xActuel = -1;
	public int yActuel = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Drawable image = getDrawable(R.drawable.image01);
		ImageView iv = findViewById(R.id.image1);
		iv.setImageDrawable(image);
		iv = findViewById(R.id.image2);
		iv.setImageDrawable(image);
	}

	public void processImage(View v)
	{
		this.processImage(0,0);
	}

	public synchronized void processImage(int xDep, int yDep)
	{
		String idNum;
		if (imageId < 10)
		{
			idNum = "0" + imageId;
		}
		else
		{
			idNum = "" + imageId;
		}

		Drawable image = getDrawable(getResources().getIdentifier("image" + idNum, "drawable", getPackageName()));
		ImageView iv = findViewById(R.id.image1);
		iv.setImageDrawable(image);


		if ( yDep < ((BitmapDrawable)iv.getDrawable()).getBitmap().getHeight())
		{
			new AsyncTaskGBRProcess((TextView) findViewById(R.id.infoView), iv, this, xDep, yDep).execute();
			xDep = yDep = 0;
		}
		else
			yDep -= ((BitmapDrawable)iv.getDrawable()).getBitmap().getHeight();

		iv = findViewById(R.id.image2);

		new AsyncTaskNegativeProcess((TextView) findViewById(R.id.infoView), iv, this, xDep, yDep).execute();
	}

	public void changeImage(View v)
	{
		imageId++;

		String idNum;
		if (imageId < 10)
		{
			idNum = "0" + imageId;
		}
		else
		{
			idNum = "" + imageId;
		}

		Drawable image;
		try
		{
			image = getDrawable(getResources().getIdentifier("image" + idNum, "drawable", getPackageName()));
		}
		catch (Exception e)
		{
			imageId = 1;
			image = getDrawable(R.drawable.image01);
		}

		ImageView iv = findViewById(R.id.image2);
		iv.setImageDrawable(image);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);

		int xActuel = savedInstanceState.getInt("xActuel");
		int yActuel = savedInstanceState.getInt("yActuel");

		if (xActuel != -1 && yActuel != -1)
		{
			processImage(xActuel, yActuel);
		}

		imageId = savedInstanceState.getInt("imageId");

		ImageView iv = findViewById(R.id.image1);
		Bitmap bm = Bitmap.createBitmap(savedInstanceState.getIntArray("image1"), savedInstanceState.getInt("image1x"), savedInstanceState.getInt("image1y"), Bitmap.Config.RGB_565);
		iv.setImageDrawable(new BitmapDrawable(getResources(), bm));

		ImageView iv2 = findViewById(R.id.image2);
		Bitmap bm2 = Bitmap.createBitmap(savedInstanceState.getIntArray("image2"), savedInstanceState.getInt("image2x"), savedInstanceState.getInt("image2y"), Bitmap.Config.RGB_565);
		iv2.setImageDrawable(new BitmapDrawable(getResources(), bm2));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		outState.putInt("xActuel", xActuel);
		outState.putInt("yActuel", yActuel);

		outState.putInt("imageId", imageId);

		ImageView iv = findViewById(R.id.image1);

		Bitmap bm = ((BitmapDrawable) iv.getDrawable()).getBitmap();
		int[] pixels1 = new int[bm.getWidth() * bm.getHeight()];
		bm.getPixels(pixels1, 0, bm.getWidth(), 0, 0, bm.getWidth(), bm.getHeight());
		outState.putIntArray("image1", pixels1);
		outState.putInt("image1x", bm.getWidth());
		outState.putInt("image1y", bm.getHeight());


		ImageView iv2 = findViewById(R.id.image2);

		Bitmap bm2 = ((BitmapDrawable) iv2.getDrawable()).getBitmap();
		int[] pixels2 = new int[bm2.getWidth() * bm2.getHeight()];
		bm2.getPixels(pixels2, 0, bm2.getWidth(), 0, 0, bm2.getWidth(), bm2.getHeight());
		outState.putIntArray("image2", pixels2);
		outState.putInt("image2x", bm2.getWidth());
		outState.putInt("image2y", bm2.getHeight());

		super.onSaveInstanceState(outState);
	}
}
