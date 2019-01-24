package com.example.ricindigus.newsapp;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        public static final String KEY_PREF_TAG = "tag";
        @Override
        public void onCreate( Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences_layout);
            Preference preference = findPreference(KEY_PREF_TAG);
            preference.setOnPreferenceChangeListener(this);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());

            String stringTag = sharedPreferences.getString(KEY_PREF_TAG,"");
            onPreferenceChange(preference, stringTag);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String valor = newValue.toString();
            if (preference instanceof ListPreference){
                ListPreference listPreference = (ListPreference) preference;
                int seleccion = listPreference.findIndexOfValue(valor);
                CharSequence[] labels = listPreference.getEntries();
                preference.setSummary(labels[seleccion]);
            }
            return true;
        }
    }


}
