package jonathan.traitementimage;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class AsyncTaskAbstractProcess extends AsyncTask<ImageView, Bitmap, ImageView>
{

    MainActivity mainActivity;
    TextView infoView;
    ImageView image;
    int yDep;

    public AsyncTaskAbstractProcess(TextView infoView, ImageView image, MainActivity mainActivity)
    {
        this(infoView, image, mainActivity, 0);
    }

    public AsyncTaskAbstractProcess(TextView infoView, ImageView image, MainActivity mainActivity, int yDep)
    {
        this.mainActivity = mainActivity;
        this.infoView = infoView;
        this.image = image;
        this.yDep = yDep;
    }

    @Override
    protected void onPreExecute() {
        infoView.setText(R.string.progress_start);
    }

    @Override
    protected ImageView doInBackground(ImageView... imageView)
    {

        Bitmap bit = ((BitmapDrawable)image.getDrawable()).getBitmap();
        bit = bit.copy(bit.getConfig(), true);

        for (int i = yDep ; i < bit.getHeight() ; i ++)
        {
            mainActivity.yActuel++;
            for (int j = 0; j < bit.getWidth(); j++) {
                int pixel = bit.getPixel(j, i);

                bit.setPixel(j, i, getPixelNewColor(pixel));
            }
            publishProgress(bit);
        }

        return image;
    }

    @Override
    protected void onProgressUpdate(Bitmap... bitmap) {
        image.setImageBitmap(bitmap[0]);
    }

    protected void onPostExecute(ImageView imageView) {
        infoView.setText(R.string.progress_end);
    }

	protected abstract int getPixelNewColor(int pixel);
}
