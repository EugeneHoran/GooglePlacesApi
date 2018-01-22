package eugene.com.transferwise.ui.place.list;


import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import eugene.com.transferwise.databinding.RecyclerPlaceItemBinding;
import eugene.com.transferwise.db.entity.Place;
import eugene.com.transferwise.ui.common.BaseViewHolder;
import eugene.com.transferwise.util.view.PlacesDiffUtil;

public class PlaceListRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Place> places = new ArrayList<>();
    private PlaceClickCallback listener;

    PlaceListRecyclerAdapter(PlaceClickCallback listener) {
        this.listener = listener;
    }

    void setItems(List<Place> placeList) {
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new PlacesDiffUtil(places, placeList));
        this.places.clear();
        this.places.addAll(placeList);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(RecyclerPlaceItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setListener(listener);
        holder.bind(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public interface PlaceClickCallback {
        void onPlaceClick(Place place);
    }
}
