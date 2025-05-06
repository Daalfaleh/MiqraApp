package islam.muslim.app.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import islam.muslim.app.utils.LocaleHelper;

import islam.muslim.app.FivePrayerApplication;
import islam.muslim.app.preferences.PreferencesHelper;

import javax.inject.Inject;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class BaseActivity extends AppCompatActivity {

    @Inject
    PreferencesHelper preferencesHelper;

    @Inject
    LocaleHelper localeUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((FivePrayerApplication) getApplicationContext())
                .defaultComponent
                .inject(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int theme = preferencesHelper.getThemePreferenceId();
            setTheme(theme);
        }

        localeUtils.refreshLocale(getBaseContext(), this);

        super.onCreate(savedInstanceState);
    }
}