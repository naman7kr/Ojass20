package ojass20.nitjsr.in.ojass.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import androidx.annotation.Nullable;
import ojass20.nitjsr.in.ojass.R;

public class Utilities {

    public static void setGlideImage(final Context context,final String imgSrc, final ImageView iv){
        Glide.with(context).load(imgSrc).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).placeholder(R.mipmap.ic_placeholder).fitCenter().diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(iv);
    }

    public static void setGlideImageWithoutCaching(final Context context,final String imgSrc, final ImageView iv){
        Glide.with(context).load(imgSrc).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Glide.with(context).load(imgSrc).placeholder(R.drawable.placeholder_sqaure).fitCenter().into(iv);
                return true;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).placeholder(R.mipmap.ic_placeholder).fitCenter().into(iv);
    }
}
