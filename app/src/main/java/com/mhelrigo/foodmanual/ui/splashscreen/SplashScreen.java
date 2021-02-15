package com.mhelrigo.foodmanual.ui.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.ui.home.HomeActivity;

public class SplashScreen extends AppCompatActivity {

    ShimmerFrameLayout shimmerFrameLayoutSplashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        shimmerFrameLayoutSplashScreen = findViewById(R.id.shimmerFrameLayoutSplashScreen);

        shimmerFrameLayoutSplashScreen.startShimmer();

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            shimmerFrameLayoutSplashScreen.stopShimmer();
            startActivity(new Intent(SplashScreen.this, HomeActivity.class));
            finish();
        }, 3000);
    }
}
