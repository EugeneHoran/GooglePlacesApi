package eugene.com.transferwise.ui.common;


import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import eugene.com.transferwise.model.DetailItem;

public abstract class BaseImpViewHolder<T> extends RecyclerView.ViewHolder {
    BaseImpViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
    }

    public abstract void bind(T detailItem);
}
