package islam.muslim.app.di.module;

import islam.muslim.app.di.factory.worker.ChildWorkerFactory;
import islam.muslim.app.di.factory.worker.WorkerKey;
import islam.muslim.app.job.InstantPrayerScheduler;
import islam.muslim.app.job.PrayerUpdater;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */
@Module
public interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(PrayerUpdater.class)
    ChildWorkerFactory bindPrayerUpdaterWorker(PrayerUpdater.Factory factory);

    @Binds
    @IntoMap
    @WorkerKey(InstantPrayerScheduler.class)
    ChildWorkerFactory bindInstantPrayerSchedulerWorker(InstantPrayerScheduler.Factory factory);
}
