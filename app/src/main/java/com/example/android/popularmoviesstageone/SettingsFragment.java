package com.example.android.popularmoviesstageone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

/**
 * Created by lianavklt on 21/02/2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements
    SharedPreferences.OnSharedPreferenceChangeListener {


  @Override
  public void onStart() {
    super.onStart();
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void onPause() {
    super.onPause();


  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    Preference preference = findPreference(key);
    if (null != preference) {
      if (!(preference instanceof CheckBoxPreference)) {
        String value = sharedPreferences.getString(preference.getKey(), "");
        sharedPreferences.edit().putString("sort_order", value).apply();
        Intent settingsIntent = getActivity().getIntent();
        settingsIntent.putExtra(getString(R.string.changed_settings), true);
        getActivity().setResult(Activity.RESULT_OK, settingsIntent);
        setPreferenceSummary(preference, value);
      }
    }
  }

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    addPreferencesFromResource(R.xml.pref_main);
    SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
    PreferenceScreen preferenceScreen = getPreferenceScreen();
    int count = preferenceScreen.getPreferenceCount();
    for (int i = 0; i < count; i++) {
      Preference preference = preferenceScreen.getPreference(i);
      if (!(preference instanceof CheckBoxPreference)) {
        String value = sharedPreferences.getString(preference.getKey(), "");
        setPreferenceSummary(preference, value);

      }
    }
  }

  private void setPreferenceSummary(Preference preference, String value) {
    if (preference instanceof ListPreference) {

      ListPreference listPreference = (ListPreference) preference;
      int prefIndex = listPreference.findIndexOfValue(value);
      if (prefIndex >= 0) {
        preference.setSummary(listPreference.getEntries()[prefIndex]);
      }
    }
  }
}
