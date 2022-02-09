package com.love_quotes_arabic.sge;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Quotes extends AppCompatActivity {

    private InterstitialAd interstitialAd;
    private AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);
        RecyclerView quoteList = findViewById(R.id.quoteslist);
        quoteList.setLayoutManager(new LinearLayoutManager(this));
        quoteList.setAdapter(new QuotesAdapter(getQuotes(),this));


        //facebook ads implementation
        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);

// Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

// Add the ad view to your activity layout
        adContainer.addView(adView);

// Request an ad
        adView.loadAd();

        interstitialAd = new InterstitialAd(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
        interstitialAd.loadAd();

    }

    // quotes method
    private List<String> getQuotes()
    {
        String data = getIntent().getStringExtra("data");
        List<String> quotes = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader((new InputStreamReader(this.getAssets()
                    .open(data), "UTF-8")));
            String line;
            while ((line = bufferedReader.readLine())!= null)
            {
                quotes.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }
        return quotes;
    }
    //ends here


    void setads(){
            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    // Interstitial ad displayed callback
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    // Interstitial dismissed callback
                    AdsUtility.destroyAds(interstitialAd, adView);
                    Intent intent = new Intent(Quotes.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    // Ad error callback
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Interstitial ad is loaded and ready to be displayed
                    // Show the ad

                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Ad clicked callback

                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Ad impression logged callback
                }
            };
            // For auto play video ads, it's recommended to load the ad
            // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }


    @Override
    public void onBackPressed()
    {
        if(interstitialAd != null && interstitialAd.isAdLoaded())
        {
            interstitialAd.show();
            setads();
        }
        else {
            AdsUtility.destroyAds(interstitialAd, adView);
            Intent intent = new Intent(Quotes.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        AdsUtility.destroyAds(interstitialAd, adView);
        super.onDestroy();
    }
}