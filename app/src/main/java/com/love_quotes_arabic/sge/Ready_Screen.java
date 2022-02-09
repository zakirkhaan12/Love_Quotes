package com.love_quotes_arabic.sge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;

public class Ready_Screen extends AppCompatActivity {
    private InterstitialAd interstitialAd;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_screen);


        AudienceNetworkAds.initialize(this);
        interstitialAd = new InterstitialAd(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");

        Button readybtn = findViewById(R.id.readybtn);
        readybtn.setOnClickListener(new View.OnClickListener() {

            String link = getIntent().getStringExtra("data");
            @Override
            public void onClick(View view) {
                AdsUtility.destroyAds(interstitialAd, adView);
                Intent intent = new Intent(getApplicationContext(), Quotes.class);
                intent.putExtra("data", link);
                startActivity(intent);
                finish();
            }
        });
        // Button click method ends here

        //facebook Ads Implementation
        adView = new AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.RECTANGLE_HEIGHT_250);
        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);
        // Request an ad
        adView.loadAd();

    }

    @Override
    protected void onDestroy() {
        AdsUtility.destroyAds(interstitialAd, adView);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        AdsUtility.destroyAds(interstitialAd, adView);
        Intent intent = new Intent(Ready_Screen.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}