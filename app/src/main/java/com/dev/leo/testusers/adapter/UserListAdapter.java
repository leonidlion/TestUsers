package com.dev.leo.testusers.adapter;

import android.arch.paging.PagedListAdapter;
import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dev.leo.testusers.GlideApp;
import com.dev.leo.testusers.R;
import com.dev.leo.testusers.data.api.NetworkState;
import com.dev.leo.testusers.data.models.Result;
import com.dev.leo.testusers.databinding.ItemUserBinding;

public class UserListAdapter extends PagedListAdapter<Result, RecyclerView.ViewHolder> {
    private static final int TYPE_DATA = 1;
    private static final int TYPE_LOAD = 0;

    private ItemClickListener clickListener;
    private NetworkState networkState;

    public UserListAdapter() {
        super(Result.DIFF_CALLBACK);
    }

    public interface ItemClickListener{
        void onItemClick(Result result);
    }

    public void setItemClickListener(ItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    public void setNetworkState(NetworkState networkState) {
        this.networkState = networkState;
        if (isLoadingData()) notifyItemRemoved(getItemCount());
        else notifyItemInserted(getItemCount());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view;
        if (i == TYPE_DATA) {
            view = inflater.inflate(R.layout.item_user, viewGroup, false);
            return new UserListHolder(view);
        } else{
            view = inflater.inflate(R.layout.item_loading, viewGroup, false);
            return new UserLoadingHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof UserListHolder)
            ((UserListHolder)holder).onBind(getItem(i));
    }

    @Override
    public int getItemViewType(int position) {
        return isLoadingData() && position == getItemCount() -1 ? TYPE_LOAD : TYPE_DATA;
    }

    private boolean isLoadingData(){
        return networkState != null && networkState != NetworkState.LOADED;
    }

    @BindingAdapter("app:imageUrl")
    public static void bindImageThumb(ImageView imageView, String url){
        GlideApp.with(imageView.getContext())
                .load(url)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    class UserListHolder extends BaseViewHolder<ItemUserBinding, Result>{
        UserListHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Result data) {
            binding.setUserData(data);
            binding.setClickListener(clickListener);
            binding.executePendingBindings();
        }
    }

    class UserLoadingHolder extends RecyclerView.ViewHolder {
        UserLoadingHolder(View itemView) {
            super(itemView);
        }
    }
}
