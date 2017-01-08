package project.as224qc.dv606.slcommuter.util;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

/**
 * Singleton utility class
 *
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.util
 */
public class Utils {

    private static Utils instance = new Utils();

    private Utils() {

    }

    public static Utils getInstance() {
        return instance;
    }

    /**
     * Create title format that will be used in the toolbar
     *
     * @param title
     * @return
     */
    public CharSequence createToolbarTitle(String title) {
        SpannableString spannableString = new SpannableString(title);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), 0, title.length(), 0);
        return spannableString;
    }
}
