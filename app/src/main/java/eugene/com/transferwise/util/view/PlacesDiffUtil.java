package eugene.com.transferwise.util.view;

import android.support.v7.util.DiffUtil;

import java.util.List;

import eugene.com.transferwise.db.entity.Place;


public class PlacesDiffUtil extends DiffUtil.Callback {
    private List<Place> oldList, newList;

    public PlacesDiffUtil(List<Place> oldList, List<Place> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList == null ? 0 : oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList == null ? 0 : newList.size();
    }


    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getPlaceId().equals(newList.get(newItemPosition).getPlaceId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).toString().equals(newList.get(newItemPosition).toString());
    }
}
