package jonathan.traitementimage;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
	private int imageId = 1;

	private List<AsyncTask> arTask = new ArrayList<AsyncTask>();

	String imageActuelle = "-1";

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

	@Override
	protected void onPause()
	{
		super.onPause();
		for (AsyncTask at : arTask)
			at.cancel(true);
	}

	public void processImage(View v)
	{
		this.processImage(0, 0, 1);
	}

	public void clickImage2(View v)
	{
		this.processImage(0, 0, 2);
	}

	public synchronized void processImage(int xDep, int yDep, int imageActuelle)
	{
		if (imageActuelle == 1)
		{
			((ImageView)findViewById(R.id.image1)).setImageDrawable(getDrawable(getResources().getIdentifier(String.format("image%02d",imageId), "drawable", getPackageName())));
			processGBRImage(xDep, yDep);
		}
		else
		{
			processNegativeImage(xDep, yDep);
		}
	}

	public void processGBRImage(int xDep, int yDep)
	{
		arTask.add(new AsyncTaskGBRProcess((TextView) findViewById(R.id.infoView), this, xDep, yDep, 1).execute());
	}

	public void processNegativeImage(int xDep, int yDep)
	{
		arTask.add(new AsyncTaskNegativeProcess((TextView) findViewById(R.id.infoView), this, xDep, yDep, 2).execute());
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

		String imageActuelle = savedInstanceState.getString("imageActuelle");

		if (!imageActuelle.equals("-1"))
		{
			ImageView iv = null;

			String[] info = imageActuelle.split(":");

			System.out.println(imageActuelle);
			processImage(Integer.parseInt(info[1])+1, Integer.parseInt(info[2]), Integer.parseInt(info[0]));
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
		outState.putString("imageActuelle", imageActuelle);

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
