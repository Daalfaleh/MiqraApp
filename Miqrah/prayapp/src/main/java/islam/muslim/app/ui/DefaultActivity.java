package islam.muslim.app.ui;

import android.content.Intent;
import android.os.Bundle;

import islam.muslim.app.FivePrayerApplication;
import islam.muslim.app.preferences.PreferencesHelper;
import islam.muslim.app.ui.appintro.IntroActivity;
import islam.muslim.app.ui.splashscreen.SplashScreenActivity;

import javax.inject.Inject;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class DefaultActivity extends BaseActivity {

    @Inject
    PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((FivePrayerApplication) getApplicationContext())
                .defaultComponent
                .inject(this);

        super.onCreate(savedInstanceState);

        if (preferencesHelper.isFirstLaunch()) {
            Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
            startActivity(intent);
        }
    }
}