package com.dev.leo.testusers.ui.user_list;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.dev.leo.testusers.Constants;
import com.dev.leo.testusers.R;
import com.dev.leo.testusers.adapter.UserListAdapter;
import com.dev.leo.testusers.data.models.Result;
import com.dev.leo.testusers.databinding.ActivityUserListBinding;
import com.dev.leo.testusers.ui.BaseActivity;
import com.dev.leo.testusers.utils.IntentBuilder;

public class UserListActivity extends BaseActivity<ActivityUserListBinding, UserListViewModel> {
    private UserListAdapter adapter;
    private Handler queryHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new UserListAdapter();
        adapter.setItemClickListener(this::startDetailActivity);

        binding.setAdapter(adapter);
        binding.setViewModel(viewModel);
        binding.setTextChangeListener((s, start, before, count) -> {
            queryHandler.removeCallbacksAndMessages(null);

            if (s.length() < Constants.MIN_SEARCH_LENGTH) return;

            queryHandler.postDelayed(()-> viewModel.searchUserByNames(),
                    Constants.SEARCH_DELAY_MS);
        });

        viewModel.getResultLiveData().observe(this, results -> adapter.submitList(results));

        viewModel.getNetworkStateLiveData().observe(this, networkState -> adapter.setNetworkState(networkState));
    }

    private void startDetailActivity(Result result){
        startActivity(IntentBuilder.getUserDetailIntent(this, result));
    }

    @Override
    protected boolean useAnimation() {
        return true;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_user_list;
    }

    @Override
    protected Class<UserListViewModel> getViewModelClass() {
        return UserListViewModel.class;
    }
}
