package com.example.akintolaoluwaseun.popmovies;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by AKINTOLA OLUWASEUN on 7/26/2017.
 */

public class Settings extends PreferenceActivity {
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.settings);
    }
}
