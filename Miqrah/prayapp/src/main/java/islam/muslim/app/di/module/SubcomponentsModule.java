package islam.muslim.app.di.module;


import islam.muslim.app.ui.home.di.HomeComponent;
import islam.muslim.app.ui.settings.di.SettingsComponent;
import islam.muslim.app.ui.timingtable.di.TimingTableComponent;

import dagger.Module;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */
@Module(subcomponents = {
        HomeComponent.class,
        TimingTableComponent.class,
        SettingsComponent.class
})
public class SubcomponentsModule {

}
