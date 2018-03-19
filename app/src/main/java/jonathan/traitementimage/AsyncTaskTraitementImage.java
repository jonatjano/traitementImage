package jonathan.traitementimage;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

public class AsyncTaskTraitementImage extends AsyncTask<ImageView, ImageView, ImageView>
{

    TextView infoView;

    public AsyncTaskTraitementImage(TextView infoView)
    {
        this.infoView = infoView;
    }

    @Override
    protected void onPreExecute() {
        infoView.setText(R.string.progress_start);
    }

    @Override
    protected ImageView doInBackground(ImageView... imageView)
    {
        ImageView image = imageView[0];

        Bitmap bit = ((BitmapDrawable)image.getDrawable()).getBitmap();

        for (int i = 0 ; i < bit.getHeight() ; i ++)
            for (int j = 0 ; j < bit.getWidth() ; i++)
            {
                int pixel = bit.getPixel(i, j);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                int alpha = Color.alpha(pixel);

                bit.setPixel(i, j, Color.argb(alpha, blue, red, green));
            }

        return image;
    }

    @Override
    protected void onProgressUpdate(ImageView... imageView) {
        super.onProgressUpdate(imageView);
    }

    protected void onPostExecute(ImageView imageView) {
        infoView.setText(R.string.progress_end);
    }

}
