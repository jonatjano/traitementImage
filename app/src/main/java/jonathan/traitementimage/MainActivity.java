package jonathan.traitementimage;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
{
	private int imageId = 1;

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

		new AsyncTaskTraitementImage((TextView) findViewById(R.id.infoView), iv).execute();
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

	public void onSaveInstanceState(Bundle bundle)
	{
		super.onSaveInstanceState(bundle);
	}

	public void onRestoreInstanceState(Bundle bundle)
	{
		super.onRestoreInstanceState(bundle);
	}
}
