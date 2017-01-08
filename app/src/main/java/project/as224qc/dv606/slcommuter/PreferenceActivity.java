package project.as224qc.dv606.slcommuter;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import project.as224qc.dv606.slcommuter.fragment.PreferenceFragment;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter
 */
public class PreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(android.R.id.content, new PreferenceFragment()).commit();
    }
}
