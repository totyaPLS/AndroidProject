package com.app.nutritionalsupplements;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.app.nutritionalsupplements.activities.NoInternetConnectionActivity;

public class Device {
    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (!(activeNetwork != null && activeNetwork.isConnected())) {
            Intent intent = new Intent(context, NoInternetConnectionActivity.class);
            context.startActivity(intent);
            return false;
        }

        return true;
    }
}
