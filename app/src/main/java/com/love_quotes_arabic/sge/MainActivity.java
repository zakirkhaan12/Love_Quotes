package com.love_quotes_arabic.sge;

import static androidx.core.view.GravityCompat.START;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.facebook.ads.*;
import com.google.android.material.navigation.NavigationView;

import hotchemi.android.rate.AppRate;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private InterstitialAd interstitialAd;
    private AdView adView;
    String[] files = new String[12];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/////_________________________________________________________________//Menubar Coding
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav_drawer, R.string.close_nav_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_view);
/////_________________________________________________________________//Menubar Coding


        ///Internet dialog
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable())
        {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alert);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations =
                    android.R.style.Animation_Dialog;

            Button button = dialog.findViewById(R.id.btn);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recreate();
                }
            });

            dialog.show();
        }
        ///Internet dialog

        /////Rate us coding
        AppRate.with(this)
                .setInstallDays(0)
                .setLaunchTimes(3)
                .setRemindInterval(2)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);
        ////Rate us coding

        //facebook ads implementation
        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);

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
        if (drawerLayout.isDrawerOpen(START)) {
            drawerLayout.closeDrawer(START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("هل ترغب فعلا بالخروج؟");
            builder.setCancelable(false);
            builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    AdsUtility.destroyAds(interstitialAd, adView);
                    finish();
                }
            });

            builder.setNegativeButton("رقم", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();

                }
            });
            builder.create().show();
        }
    }
    ////______________________________________________________________//bottons using switch statment

    public void Click(View view) {

        files[0] = "first_love_arabi.txt";
        files[1] = "crush_arabi.txt";
        files[2] = "falling_in_love_arabi.txt";
        files[3] = "heart_touching_arabi.txt";
        files[4] = "insperational_arabi.txt";
        files[5] = "kiss_arabi.txt";
        files[6] = "missyou_arabi.txt";
        files[7] = "romantic_arabi.txt";
        files[8] = "sad_broken_heart_arabi.txt";
        files[9] = "sweetlove_arabi.txt";



        switch (view.getId()) {

            case R.id.First_love:
                dataSend(files[0]);
                break;

            case R.id.Crush_quotes:
                dataSend(files[1]);
                break;

            case R.id.Falling_in_love:
                dataSend(files[2]);
                break;

            case R.id.Heart_touching:
                dataSend(files[3]);
                break;

            case R.id.Inspirational_love_quotes:
                dataSend(files[4]);
                break;

            case R.id.kiss_quotes:
                dataSend(files[5]);
                break;

            case R.id.Miss_you:
                dataSend(files[6]);
                break;

            case R.id.Roamantic_love:
                dataSend(files[7]);
                break;

            case R.id.Sad_broken_heart:
                dataSend(files[8]);
                break;

            case R.id.Sweet_love_quotes:
                dataSend(files[9]);
                break;

        }
    }
    ///_____________________________________________________________//using switch statment

    public void dataSend(String url) {
        AdsUtility.destroyAds(interstitialAd, adView);
        Intent intent = new Intent(getApplicationContext(), Ready_Screen.class);
        intent.putExtra("data", url);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                break;

            case R.id.nav_share:
                AdsUtility.destroyAds(interstitialAd, adView);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"ونقلت الحب التطبيق");
                intent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName());
                startActivity(Intent.createChooser(intent,"شارك مع"));
                break;


            case R.id.nav_policy:
                AdsUtility.destroyAds(interstitialAd, adView);
                Intent i = new Intent(MainActivity.this, Privacypolicy.class);
                startActivity(i);
                finish();
                break;

            case R.id.nav_rate:
                AdsUtility.destroyAds(interstitialAd, adView);
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName());
                Intent intent1 = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent1);

                break;

            case R.id.nav_exit:
                AdsUtility.destroyAds(interstitialAd, adView);
                finish();
        }
        drawerLayout.closeDrawer(START);
        return true;
    }

}
