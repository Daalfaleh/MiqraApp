package islam.muslim.app.notifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import islam.muslim.app.FivePrayerApplication;
import islam.muslim.app.preferences.PreferencesHelper;


import javax.inject.Inject;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class DailyVerseReceiver extends BroadcastReceiver {

    @Inject
    TodayVerseNotification todayVerseNotification;

    @Inject
    PreferencesHelper preferencesHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        ((FivePrayerApplication) context.getApplicationContext())
                .receiverComponent
                .inject(this);

    }


}