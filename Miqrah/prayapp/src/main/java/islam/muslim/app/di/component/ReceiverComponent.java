package islam.muslim.app.di.component;

import islam.muslim.app.di.module.AppModule;
import islam.muslim.app.di.module.NetworkModule;
import islam.muslim.app.di.module.WidgetModule;
import islam.muslim.app.notifier.DailyVerseReceiver;
import islam.muslim.app.notifier.ReminderReceiver;
import islam.muslim.app.notifier.NotificationDismissedReceiver;
import islam.muslim.app.notifier.NotifierActionReceiver;
import islam.muslim.app.notifier.NotifierReceiver;
import islam.muslim.app.notifier.SilenterReceiver;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */
@Singleton
@Component(modules =
        {
                AppModule.class,
                WidgetModule.class,
                NetworkModule.class
        })
public interface ReceiverComponent {

    void inject(NotificationDismissedReceiver notificationDismissedReceiver);

    void inject(NotifierActionReceiver notifierActionReceiver);

    void inject(NotifierReceiver notifierReceiver);

    void inject(ReminderReceiver beforeAdhanAlertReceiver);

    void inject(SilenterReceiver silenterReceiver);

    void inject(DailyVerseReceiver dailyVerseReceiver);
}
