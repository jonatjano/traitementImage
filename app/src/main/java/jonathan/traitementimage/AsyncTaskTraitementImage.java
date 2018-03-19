package jonathan.traitementimage;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

public class AsyncTaskTraitementImage extends AsyncTask<ImageView, Bitmap, ImageView>
{

    TextView infoView;
    ImageView image;

    public AsyncTaskTraitementImage(TextView infoView, ImageView image)
    {
        this.infoView = infoView;
        this.image = image;
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

        for (int i = 0 ; i < bit.getHeight() ; i ++)
        {
            for (int j = 0; j < bit.getWidth(); i++) {
                int pixel = bit.getPixel(i, j);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                int alpha = Color.alpha(pixel);

                bit.setPixel(i, j, Color.argb(alpha, blue, red, green));
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

}
