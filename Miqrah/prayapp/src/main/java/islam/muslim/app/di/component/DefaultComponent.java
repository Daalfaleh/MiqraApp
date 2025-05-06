package islam.muslim.app.di.component;

import islam.muslim.app.di.module.AppModule;
import islam.muslim.app.di.module.NetworkModule;
import islam.muslim.app.ui.BaseActivity;
import islam.muslim.app.ui.DefaultActivity;
import islam.muslim.app.ui.MainActivity;

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
                NetworkModule.class
        })
public interface DefaultComponent {

    void inject(MainActivity mainActivity);

    void inject(DefaultActivity defaultActivity);

    void inject(BaseActivity baseActivity);
}
