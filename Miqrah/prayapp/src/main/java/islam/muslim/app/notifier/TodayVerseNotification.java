package islam.muslim.app.notifier;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import islam.muslim.app.preferences.PreferencesHelper;
import islam.muslim.app.ui.dailyverse.DailyVerseActivity;

import com.yaseen.fiveprayers.R;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */
@Singleton
public class TodayVerseNotification extends BaseNotification {

    @Inject
    public TodayVerseNotification(PreferencesHelper preferencesHelper, Context context) {
        super(preferencesHelper, context);
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String id = NotifierConstants.TODAY_VERSE_NOTIFICATION_CHANNEL_ID;
            CharSequence name = NotifierConstants.TODAY_VERSE_NOTIFICATION_CHANNEL_NAME;
            String description = NotifierConstants.TODAY_VERSE_NOTIFICATION_CHANNEL_DESCRIPTION;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(id, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.WHITE);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }


    @Override
    protected int getNotificationIcon() {
        return R.drawable.ic_book_24dp;
    }

    private PendingIntent getNotificationIntent() {
        Intent notifyIntent = new Intent(context, DailyVerseActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        return PendingIntentCreator.getActivity(context, 0,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
