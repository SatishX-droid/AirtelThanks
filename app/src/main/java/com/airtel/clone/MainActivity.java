package com.airtel.clone;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    private TextView tvBalance;
    private double currentBalance = 37150.40;
    private DecimalFormat formatter = new DecimalFormat("#,##0.00");
    private ViewPager2 bannerViewPager;
    private Handler bannerHandler;
    private Runnable bannerRunnable;
    private List<Integer> bannerList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initViews();
        setupBalance();
        setupBannerCarousel();
        setupBottomNavigation();
        setupServiceIcons();
    }
    
    private void initViews() {
        tvBalance = findViewById(R.id.tv_balance);
        bannerViewPager = findViewById(R.id.banner_viewpager);
    }
    
    private void setupBalance() {
        updateBalanceDisplay();
        findViewById(R.id.balance_card).setOnClickListener(v -> showEditBalanceDialog());
        tvBalance.setOnClickListener(v -> showEditBalanceDialog());
    }
    
    private void updateBalanceDisplay() {
        tvBalance.setText("₹" + formatter.format(currentBalance));
    }
    
    private void showEditBalanceDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_balance);
        
        EditText etBalance = dialog.findViewById(R.id.et_balance);
        TextView btnSave = dialog.findViewById(R.id.btn_save);
        TextView btnCancel = dialog.findViewById(R.id.btn_cancel);
        
        etBalance.setText(String.valueOf(currentBalance));
        
        btnSave.setOnClickListener(v -> {
            String input = etBalance.getText().toString().trim();
            if (!input.isEmpty()) {
                try {
                    currentBalance = Double.parseDouble(input);
                    updateBalanceDisplay();
                    Toast.makeText(this, "Balance updated!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
    
    private void setupBannerCarousel() {
        bannerList = new ArrayList<>();
        bannerList.add(R.drawable.banner_kotak);
        bannerList.add(R.drawable.banner_wynk);
        bannerList.add(R.drawable.banner_kotak);
        
        BannerAdapter adapter = new BannerAdapter(this, bannerList);
        bannerViewPager.setAdapter(adapter);
        
        bannerHandler = new Handler(Looper.getMainLooper());
        bannerRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = bannerViewPager.getCurrentItem();
                int nextItem = (currentItem + 1) % bannerList.size();
                bannerViewPager.setCurrentItem(nextItem, true);
                bannerHandler.postDelayed(this, 3000);
            }
        };
        bannerHandler.postDelayed(bannerRunnable, 3000);
        
        LinearLayout dotsLayout = findViewById(R.id.dots_indicator);
        for (int i = 0; i < bannerList.size(); i++) {
            ImageView dot = new ImageView(this);
            dot.setImageResource(R.drawable.dot_inactive);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(16, 16);
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            dotsLayout.addView(dot);
        }
        
        bannerViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateDots(dotsLayout, position);
            }
        });
    }
    
    private void updateDots(LinearLayout dotsLayout, int position) {
        for (int i = 0; i < dotsLayout.getChildCount(); i++) {
            ImageView dot = (ImageView) dotsLayout.getChildAt(i);
            dot.setImageResource(i == position ? R.drawable.dot_active : R.drawable.dot_inactive);
        }
    }
    
    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        });
    }
    
    private void setupServiceIcons() {
        findViewById(R.id.icon_broadband).setOnClickListener(v -> 
            Toast.makeText(this, "Broadband", Toast.LENGTH_SHORT).show());
        findViewById(R.id.icon_postpaid).setOnClickListener(v -> 
            Toast.makeText(this, "Postpaid", Toast.LENGTH_SHORT).show());
        findViewById(R.id.icon_dth).setOnClickListener(v -> 
            Toast.makeText(this, "DTH", Toast.LENGTH_SHORT).show());
        findViewById(R.id.icon_prepaid).setOnClickListener(v -> 
            Toast.makeText(this, "Prepaid", Toast.LENGTH_SHORT).show());
        findViewById(R.id.btn_home_delivery).setOnClickListener(v -> 
            Toast.makeText(this, "Home Delivery of SIM", Toast.LENGTH_SHORT).show());
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bannerHandler != null) bannerHandler.removeCallbacks(bannerRunnable);
    }
}