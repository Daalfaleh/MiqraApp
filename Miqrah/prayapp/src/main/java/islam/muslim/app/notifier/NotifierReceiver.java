package islam.muslim.app.notifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import islam.muslim.app.FivePrayerApplication;
import islam.muslim.app.preferences.PreferencesHelper;
import islam.muslim.app.ui.widget.WidgetUpdater;

import javax.inject.Inject;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */

public class NotifierReceiver extends BroadcastReceiver {

    @Inject
    PrayerNotification prayerNotification;

    @Inject
    WidgetUpdater widgetUpdater;

    @Inject
    PreferencesHelper preferencesHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        ((FivePrayerApplication) context.getApplicationContext())
                .receiverComponent
                .inject(this);

        if(preferencesHelper.isNotificationsEnabled()) {
            prayerNotification.createNotificationChannel();
            prayerNotification.createNotification(intent);
        }

        widgetUpdater.updateHomeScreenWidgets(context);
    }
}