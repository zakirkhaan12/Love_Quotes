package com.love_quotes_arabic.sge;

import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;

public class AdsUtility {

    public static void destroyAds(InterstitialAd interstitialAd, AdView adView) {
        if (adView != null)
        {
            adView.destroy();
        }
        if (interstitialAd != null)
        {
            interstitialAd.destroy();
        }
    }

}