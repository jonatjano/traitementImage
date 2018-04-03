package jonathan.traitementimage;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jonathan on 19/03/18.
 */

public class AsyncTaskGBRProcess extends AsyncTaskAbstractProcess
{
	public AsyncTaskGBRProcess(TextView infoView,MainActivity mainActivity)
	{
		super(infoView, mainActivity);
	}

	public AsyncTaskGBRProcess(TextView infoView,MainActivity mainActivity, int xDep, int yDep, int image)
	{
		super(infoView, mainActivity, xDep, yDep, image);
	}

	@Override
	protected int getPixelNewColor(int pixel)
	{
		int red = Color.red(pixel);
		int green = Color.green(pixel);
		int blue = Color.blue(pixel);
		int alpha = Color.alpha(pixel);
		return Color.argb(alpha, blue, red, green);
	}
}
