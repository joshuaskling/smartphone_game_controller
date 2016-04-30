package edu.csumb.gamecontroller;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceActivity;
<<<<<<< HEAD
import android.preference.PreferenceFragment;
=======
>>>>>>> 01ef4e7646051b289110c424693e9f2685168e42
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.preference.PreferenceManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toolbar;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
=======

>>>>>>> 01ef4e7646051b289110c424693e9f2685168e42
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment()).commit();

    }
<<<<<<< HEAD
    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
        }
    }
=======



>>>>>>> 01ef4e7646051b289110c424693e9f2685168e42
}
