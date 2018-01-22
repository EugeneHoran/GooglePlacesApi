package eugene.com.transferwise.util.view;

import android.databinding.BindingAdapter;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import eugene.com.transferwise.BuildConfig;

public class DataBindingAdapters {
    private static final String PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo?photoreference=%s&key=%s&maxheight=400";

    @BindingAdapter("imageUrl")
    public static void bindImage(ImageView imageView, String imageReference) {
        if (imageReference == null) {
            return;
        }
        String url = String.format(PHOTO_URL, imageReference, BuildConfig.PLACES_KEY);
        GlideApp.with(imageView.getContext()).load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    @BindingAdapter("src")
    public static void setImageResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter("loadCircleImage")
    public static void loadCircleImage(ImageView imageView, String imageReference) {
        if (TextUtils.isEmpty(imageReference)) return;
        GlideApp.with(imageView.getContext())
                .load(imageReference)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    @BindingAdapter("hasDivider")
    public static void addRecyclerDivider(RecyclerView recycler, boolean hasDivider) {
        if (hasDivider) {
            recycler.addItemDecoration(new DividerItemDecoration(recycler.getContext(), DividerItemDecoration.VERTICAL));
        }
    }
}
