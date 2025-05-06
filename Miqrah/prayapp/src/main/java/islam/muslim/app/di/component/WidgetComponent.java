package islam.muslim.app.di.component;

import islam.muslim.app.di.module.AppModule;
import islam.muslim.app.di.module.NetworkModule;
import islam.muslim.app.di.module.WidgetModule;
import islam.muslim.app.ui.widget.ClockHomeScreenWidgetProvider;
import islam.muslim.app.ui.widget.HomeScreenWidgetProvider;
import islam.muslim.app.ui.widget.NextPrayerHomeScreenWidgetProvider;

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
public interface WidgetComponent {

    void inject(HomeScreenWidgetProvider homeScreenWidgetProvider);

    void inject(NextPrayerHomeScreenWidgetProvider nextPrayerHomeScreenWidgetProvider);

    void inject(ClockHomeScreenWidgetProvider clockHomeScreenWidgetProvider);
}
