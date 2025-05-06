package islam.muslim.app.di.component;

import islam.muslim.app.di.factory.worker.WorkerProviderFactory;
import islam.muslim.app.di.module.AppModule;
import islam.muslim.app.di.module.NetworkModule;
import islam.muslim.app.di.module.SubcomponentsModule;
import islam.muslim.app.di.module.ViewModelFactoryModule;
import islam.muslim.app.di.module.WorkerBindingModule;
import islam.muslim.app.ui.home.di.HomeComponent;

import islam.muslim.app.ui.settings.di.SettingsComponent;
import islam.muslim.app.ui.timingtable.di.TimingTableComponent;

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
                NetworkModule.class,
                ViewModelFactoryModule.class,
                WorkerBindingModule.class,
                SubcomponentsModule.class
        })
public interface ApplicationComponent {

    HomeComponent.Factory homeComponent();

    TimingTableComponent.Factory timingTableComponent();


    SettingsComponent.Factory settingsComponent();

    WorkerProviderFactory workerProviderFactory();
}
