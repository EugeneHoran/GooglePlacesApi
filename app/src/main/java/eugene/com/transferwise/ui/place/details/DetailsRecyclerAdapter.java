package eugene.com.transferwise.ui.place.details;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import eugene.com.transferwise.databinding.RecyclerDetailDividerBinding;
import eugene.com.transferwise.databinding.RecyclerDetailHeaderBinding;
import eugene.com.transferwise.databinding.RecyclerDetailHeaderIconBinding;
import eugene.com.transferwise.databinding.RecyclerDetailReviewBinding;
import eugene.com.transferwise.databinding.RecyclerDetailTimeBinding;
import eugene.com.transferwise.model.DetailItem;
import eugene.com.transferwise.ui.common.BaseViewHolder;

public class DetailsRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_HEADER_ICON = 1;
    public static final int TYPE_DIVIDER = 2;
    public static final int TYPE_TIME = 3;
    public static final int TYPE_REVIEW = 4;

    private List<DetailItem> detailItemList = new ArrayList<>();

    void setItems(List<DetailItem> items) {
        if (items == null) return;
        detailItemList.clear();
        detailItemList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return detailItemList.get(position).viewType();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_HEADER:
                return new BaseViewHolder(RecyclerDetailHeaderBinding.inflate(layoutInflater, parent, false));
            case TYPE_HEADER_ICON:
                return new BaseViewHolder(RecyclerDetailHeaderIconBinding.inflate(layoutInflater, parent, false));
            case TYPE_DIVIDER:
                return new BaseViewHolder(RecyclerDetailDividerBinding.inflate(layoutInflater, parent, false));
            case TYPE_TIME:
                return new BaseViewHolder(RecyclerDetailTimeBinding.inflate(layoutInflater, parent, false));
            case TYPE_REVIEW:
                return new BaseViewHolder(RecyclerDetailReviewBinding.inflate(layoutInflater, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(detailItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return detailItemList.size();
    }
}
