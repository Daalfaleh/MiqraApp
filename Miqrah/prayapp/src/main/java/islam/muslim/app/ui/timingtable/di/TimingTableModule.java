package islam.muslim.app.ui.timingtable.di;


import androidx.lifecycle.ViewModel;

import islam.muslim.app.di.factory.viewmodel.ViewModelKey;
import islam.muslim.app.ui.timingtable.TimingTableViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */
@Module
public abstract class TimingTableModule {

    @Binds
    @IntoMap
    @ViewModelKey(TimingTableViewModel.class)
    public abstract ViewModel bindsTimingTableViewModel(TimingTableViewModel timingTableViewModel);
}
