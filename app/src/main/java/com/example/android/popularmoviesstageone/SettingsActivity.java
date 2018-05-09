package com.example.android.popularmoviesstageone;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by lianavklt on 21/02/2018.
 */

public class SettingsActivity extends AppCompatActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.action_settings);
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    PreferenceManager.setDefaultValues(this, R.xml.pref_main, false);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int itemId = item.getItemId();
    if (itemId == android.R.id.home) {
      onBackPressed();
    }

    return super.onOptionsItemSelected(item);
  }

}
