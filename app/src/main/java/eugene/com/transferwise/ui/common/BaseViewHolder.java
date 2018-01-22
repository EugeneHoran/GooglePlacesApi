package eugene.com.transferwise.ui.common;

import android.databinding.ViewDataBinding;

import eugene.com.transferwise.databinding.RecyclerDetailDividerBinding;
import eugene.com.transferwise.databinding.RecyclerDetailHeaderBinding;
import eugene.com.transferwise.databinding.RecyclerDetailHeaderIconBinding;
import eugene.com.transferwise.databinding.RecyclerDetailReviewBinding;
import eugene.com.transferwise.databinding.RecyclerDetailTimeBinding;
import eugene.com.transferwise.databinding.RecyclerPlaceItemBinding;
import eugene.com.transferwise.db.entity.DetailsReview;
import eugene.com.transferwise.db.entity.Place;
import eugene.com.transferwise.model.DetailItemDivider;
import eugene.com.transferwise.model.DetailItemHeader;
import eugene.com.transferwise.model.DetailItemHeaderIcon;
import eugene.com.transferwise.model.DetailItemTime;
import eugene.com.transferwise.ui.place.list.PlaceListRecyclerAdapter;

public class BaseViewHolder extends BaseImpViewHolder {
    private ViewDataBinding binding;
    private PlaceListRecyclerAdapter.PlaceClickCallback listener;

    public BaseViewHolder(ViewDataBinding binding) {
        super(binding);
        this.binding = binding;
    }

    @Override
    public void bind(Object detailItem) {
        // Place Item
        if (binding instanceof RecyclerPlaceItemBinding) {
            RecyclerPlaceItemBinding binder = (RecyclerPlaceItemBinding) binding;
            binder.setPlace((Place) detailItem);
            if (listener != null) {
                binder.setListener(listener);
            }
            // Place Details Items
        } else if (binding instanceof RecyclerDetailDividerBinding) {
            RecyclerDetailDividerBinding binder = (RecyclerDetailDividerBinding) binding;
            binder.setDivider((DetailItemDivider) detailItem);
        } else if (binding instanceof RecyclerDetailHeaderBinding) {
            RecyclerDetailHeaderBinding binder = (RecyclerDetailHeaderBinding) binding;
            binder.setHeader((DetailItemHeader) detailItem);
        } else if (binding instanceof RecyclerDetailHeaderIconBinding) {
            RecyclerDetailHeaderIconBinding binder = (RecyclerDetailHeaderIconBinding) binding;
            binder.setHeaderIcon((DetailItemHeaderIcon) detailItem);
        } else if (binding instanceof RecyclerDetailReviewBinding) {
            RecyclerDetailReviewBinding binder = (RecyclerDetailReviewBinding) binding;
            binder.setReview((DetailsReview) detailItem);
        } else if (binding instanceof RecyclerDetailTimeBinding) {
            RecyclerDetailTimeBinding binder = (RecyclerDetailTimeBinding) binding;
            binder.setTime((DetailItemTime) detailItem);
        }
    }

    public void setListener(PlaceListRecyclerAdapter.PlaceClickCallback listener) {
        this.listener = listener;
    }
}
