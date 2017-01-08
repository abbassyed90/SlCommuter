package project.as224qc.dv606.slcommuter.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v7.app.AlertDialog;

import project.as224qc.dv606.slcommuter.R;
import project.as224qc.dv606.slcommuter.SQLiteContext;

/**
 * Settings
 *
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.fragment
 */
public class PreferenceFragment extends android.preference.PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        Preference clearButton = findPreference("clearButton");
        clearButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

                dialogBuilder.setTitle(getString(R.string.dialog_title_clear_data));
                dialogBuilder.setMessage(getString(R.string.msg_clear_data));
                dialogBuilder.setPositiveButton(getString(R.string.clear), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        SQLiteContext.getInstance().getController().clearAll();
                        dialog.dismiss();
                    }
                });
                dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                AlertDialog b = dialogBuilder.create();
                b.show();
                return true;
            }
        });
    }


}
