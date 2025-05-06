package islam.muslim.app.job;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import islam.muslim.app.di.factory.worker.ChildWorkerFactory;
import islam.muslim.app.location.address.AddressHelper;
import islam.muslim.app.location.tracker.LocationHelper;
import islam.muslim.app.notifier.PrayerAlarmScheduler;
import islam.muslim.app.preferences.PreferencesHelper;
import islam.muslim.app.timings.DayPrayer;
import islam.muslim.app.timings.TimingServiceFactory;
import islam.muslim.app.timings.TimingsService;
import islam.muslim.app.ui.widget.WidgetUpdater;

import java.time.LocalDate;

import javax.inject.Inject;
import javax.inject.Provider;

import io.reactivex.rxjava3.core.Single;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class PrayerUpdater extends RxWorker {

    private static final String TAG = "PrayerUpdater";
    private final Context context;
    private final LocationHelper locationHelper;
    private final AddressHelper addressHelper;
    private final TimingServiceFactory timingServiceFactory;
    private final PrayerAlarmScheduler prayerAlarmScheduler;
    private final PreferencesHelper preferencesHelper;
    private final WidgetUpdater widgetUpdater;

    private int runAttemptCount = 0;

    @Inject
    public PrayerUpdater(@NonNull Context context,
                         @NonNull WorkerParameters params,
                         @NonNull LocationHelper locationHelper,
                         @NonNull AddressHelper addressHelper,
                         @NonNull TimingServiceFactory timingServiceFactory,
                         @NonNull PrayerAlarmScheduler prayerAlarmScheduler,
                         @NonNull PreferencesHelper PreferencesHelper,
                         @NonNull WidgetUpdater widgetUpdater
    ) {
        super(context, params);
        this.context = context;
        this.locationHelper = locationHelper;
        this.addressHelper = addressHelper;
        this.timingServiceFactory = timingServiceFactory;
        this.prayerAlarmScheduler = prayerAlarmScheduler;
        this.preferencesHelper = PreferencesHelper;
        this.widgetUpdater = widgetUpdater;

        Log.i(TAG, "Prayer Updater Initialized");
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        Log.i(TAG, "Starting Create Prayer Updater Work");

        TimingsService timingsService = timingServiceFactory.create(preferencesHelper.getCalculationMethod());

        Single<DayPrayer> dayPrayerSingle =
                locationHelper.getLocation()
                        .flatMap(addressHelper::getAddressFromLocation)
                        .flatMap(address ->
                                timingsService.getTimingsByCity(LocalDate.now(), address));

        return dayPrayerSingle
                .doOnSuccess(prayerAlarmScheduler::scheduleAlarmsAndReminders)
                .doAfterSuccess(dayPrayer -> widgetUpdater.updateHomeScreenWidgets(context))
                .map(dayPrayer -> {
                    Log.i(TAG, "Prayers alarm updated successfully");
                    return Result.success();
                })
                .onErrorReturn(error -> {
                    Log.e(TAG, "Prayer Updater Failure", error);

                    if (runAttemptCount > 3) {
                        Log.e(TAG, "Cancel Prayer Updater and return failure");
                        return Result.failure();
                    } else {
                        runAttemptCount++;
                        Log.e(TAG, "Retry Prayer Updater, runAttemptCount=" + runAttemptCount);
                        return Result.retry();
                    }
                });
    }

    public static class Factory implements ChildWorkerFactory {

        private final Provider<LocationHelper> locationHelperProvider;
        private final Provider<AddressHelper> addressHelperProvider;
        private final Provider<TimingServiceFactory> timingServiceFactoryProvider;
        private final Provider<PrayerAlarmScheduler> prayerAlarmSchedulerProvider;
        private final Provider<PreferencesHelper> preferencesHelperProvider;
        private final Provider<WidgetUpdater> widgetUpdaterProvider;

        @Inject
        public Factory(Provider<LocationHelper> locationHelperProvider,
                       Provider<AddressHelper> addressHelperProvider,
                       Provider<TimingServiceFactory> timingServiceFactoryProvider,
                       Provider<PrayerAlarmScheduler> prayerAlarmSchedulerProvider,
                       Provider<PreferencesHelper> preferencesHelperProvider,
                       Provider<WidgetUpdater> widgetUpdaterProvider
        ) {

            this.locationHelperProvider = locationHelperProvider;
            this.addressHelperProvider = addressHelperProvider;
            this.timingServiceFactoryProvider = timingServiceFactoryProvider;
            this.prayerAlarmSchedulerProvider = prayerAlarmSchedulerProvider;
            this.preferencesHelperProvider = preferencesHelperProvider;
            this.widgetUpdaterProvider = widgetUpdaterProvider;
        }

        @Override
        public ListenableWorker create(Context context, WorkerParameters workerParameters) {
            return new PrayerUpdater(context,
                    workerParameters,
                    locationHelperProvider.get(),
                    addressHelperProvider.get(),
                    timingServiceFactoryProvider.get(),
                    prayerAlarmSchedulerProvider.get(),
                    preferencesHelperProvider.get(),
                    widgetUpdaterProvider.get()
            );
        }
    }
}
