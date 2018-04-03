package jonathan.traitementimage;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public abstract class AsyncTaskAbstractProcess extends AsyncTask<ImageView, Bitmap, ImageView>
{

    MainActivity mainActivity;
    TextView infoView;
    int image;
    int yDep;
    int xDep;

    public AsyncTaskAbstractProcess(TextView infoView,MainActivity mainActivity)
    {
        this(infoView, mainActivity, 0, 0, 0);
    }

    public AsyncTaskAbstractProcess(TextView infoView,MainActivity mainActivity, int xDep, int yDep, int image)
    {
        this.mainActivity = mainActivity;
        this.infoView = infoView;
        this.image = image;
        this.xDep = xDep;
        this.yDep = yDep;
    }

    @Override
    protected void onPreExecute() {
        infoView.setText(R.string.progress_start);
    }

    @Override
    protected ImageView doInBackground(ImageView... imageView)
    {
        ImageView iv = null;
        if (this.image == 1)
            iv = mainActivity.findViewById(R.id.image1);
        else
            iv = mainActivity.findViewById(R.id.image2);

        Bitmap bit = ((BitmapDrawable)iv.getDrawable()).getBitmap();
        bit = bit.copy(bit.getConfig(), true);

        for (int i = yDep ; i < bit.getHeight() ; i ++)
        {
            for (int j = xDep; j < bit.getWidth(); j++) {
                int pixel = bit.getPixel(j, i);
                xDep = 0;
                mainActivity.imageActuelle = image + ":" + j + ":" + i;
                bit.setPixel(j, i, getPixelNewColor(pixel));
            }
            publishProgress(bit);
        }

        mainActivity.imageActuelle = "-1";

        return iv;
    }

    @Override
    protected void onProgressUpdate(Bitmap... bitmap)
    {
        ImageView iv = null;
        if (this.image == 1)
            iv = mainActivity.findViewById(R.id.image1);
        else
            iv = mainActivity.findViewById(R.id.image2);
        iv.setImageBitmap(bitmap[0]);
    }

    protected void onPostExecute(ImageView imageView) {
        infoView.setText(R.string.progress_end);
    }

	protected abstract int getPixelNewColor(int pixel);
}
