package com.example.UDBanDoAn.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.UDBanDoAn.Tab_Fragment.ChoXacNhanFragment;
import com.example.UDBanDoAn.Tab_Fragment.DaNhanFragment;
import com.example.UDBanDoAn.Tab_Fragment.GiaoHangFragment;
import com.example.UDBanDoAn.Tab_Fragment.HuyFragment;

public class ViewPager2Adapter extends FragmentStateAdapter {
    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new ChoXacNhanFragment();
        } else if (position == 1) {
            return new GiaoHangFragment();
        } else if (position == 2) {
            return new DaNhanFragment();
        } else if (position == 3) {
            return new HuyFragment();
        } else {
            return new ChoXacNhanFragment(); // Trả về mặc định nếu không khớp với bất kỳ trường hợp nào
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
