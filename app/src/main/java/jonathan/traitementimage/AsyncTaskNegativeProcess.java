package jonathan.traitementimage;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jonathan on 19/03/18.
 */

public class AsyncTaskNegativeProcess extends AsyncTaskAbstractProcess
{

	public AsyncTaskNegativeProcess(TextView infoView, ImageView image, MainActivity mainActivity)
	{
		super(infoView, image, mainActivity);
	}

	public AsyncTaskNegativeProcess(TextView infoView, ImageView image, MainActivity mainActivity, int xDep, int yDep)
	{
		super(infoView, image, mainActivity, xDep, yDep);
	}

	@Override
	protected int getPixelNewColor(int pixel)
	{
		int red = Color.red(pixel);
		int green = Color.green(pixel);
		int blue = Color.blue(pixel);
		int alpha = Color.alpha(pixel);

		return Color.argb(alpha, 255 - red, 255 - green, 255 - blue);
	}
}
