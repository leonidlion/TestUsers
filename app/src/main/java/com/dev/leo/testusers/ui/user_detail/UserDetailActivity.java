package com.dev.leo.testusers.ui.user_detail;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.dev.leo.testusers.GlideApp;
import com.dev.leo.testusers.R;
import com.dev.leo.testusers.databinding.ActivityUserDetailBinding;
import com.dev.leo.testusers.enums.BundleKeys;
import com.dev.leo.testusers.ui.BaseActivity;

public class UserDetailActivity extends BaseActivity<ActivityUserDetailBinding, UserDetailViewModel> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null && getIntent().getExtras() != null) {
            binding.setUser(getIntent().getExtras().getParcelable(BundleKeys.KEY_USER.name()));
        }else finish();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_user_detail;
    }

    @BindingAdapter({"app:imageUrl", "app:errorImage"})
    public static void serUserImage(ImageView view, String url, Drawable errorImage){
        GlideApp.with(view.getContext())
                .load(url)
                .error(errorImage)
                .into(view);
    }

    @Override
    protected boolean useAnimation() {
        return true;
    }

    @Override
    protected Class<UserDetailViewModel> getViewModelClass() {
        return UserDetailViewModel.class;
    }
}
