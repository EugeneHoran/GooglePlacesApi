package eugene.com.transferwise.ui.place.details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import eugene.com.transferwise.databinding.PagerPhotosBinding;
import eugene.com.transferwise.db.entity.Photo;

public class PlaceDetailsPagerAdapter extends PagerAdapter {

    private List<Photo> photoRefList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;

    PlaceDetailsPagerAdapter(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    void setItems(List<Photo> photoRefList) {
        this.photoRefList.clear();
        this.photoRefList.addAll(photoRefList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return photoRefList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PagerPhotosBinding binding = PagerPhotosBinding.inflate(mLayoutInflater, container, false);
        binding.setPhotoRef(photoRefList.get(position).getPhotoReference());
        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}