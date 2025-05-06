package islam.muslim.app.timings;

import android.location.Address;
import android.util.Log;

import islam.muslim.app.database.PrayerRegistry;
import islam.muslim.app.preferences.PreferencesHelper;
import islam.muslim.app.timings.calculations.CalculationMethodEnum;
import islam.muslim.app.timings.calculations.LatitudeAdjustmentMethod;
import islam.muslim.app.timings.calculations.MidnightModeAdjustmentMethod;
import islam.muslim.app.timings.calculations.SchoolAdjustmentMethod;
import islam.muslim.app.timings.offline.OfflineTimingsService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public abstract class AbstractTimingsService implements TimingsService {

    protected final PrayerRegistry prayerRegistry;
    private final OfflineTimingsService offlineTimingsService;
    protected final PreferencesHelper preferencesHelper;
    protected String TAG = "AbstractTimingsService";

    public AbstractTimingsService(PrayerRegistry prayerRegistry,
                                  OfflineTimingsService offlineTimingsService,
                                  PreferencesHelper preferencesHelper) {
        this.prayerRegistry = prayerRegistry;
        this.offlineTimingsService = offlineTimingsService;
        this.preferencesHelper = preferencesHelper;
    }

    protected abstract void retrieveAndSaveTimings(LocalDate localDate, Address address) throws IOException;

    protected abstract void retrieveAndSaveCalendar(Address address, int month, int year) throws IOException;

    public Single<DayPrayer> getTimingsByCity(final LocalDate localDate, final Address address) {

        return Single.create(emitter -> {
            Thread thread = new Thread(() -> {

                DayPrayer prayerTimings;

                prayerTimings = getSavedPrayerTimings(localDate, address);

                if (prayerTimings != null) {
                    emitter.onSuccess(prayerTimings);
                } else {
                    try {
                        retrieveAndSaveTimings(localDate, address);
                        prayerTimings = getSavedPrayerTimings(localDate, address);

                        if (prayerTimings != null) {
                            emitter.onSuccess(prayerTimings);
                        } else {
                            Log.i(TAG, "Offline timings calculation");
                            emitter.onSuccess(offlineTimingsService.getPrayerTimings(localDate, address, getTimingsPreferences()));
                        }

                    } catch (IOException e) {
                        Log.i(TAG, "Offline timings calculation", e);
                        emitter.onSuccess(offlineTimingsService.getPrayerTimings(localDate, address, getTimingsPreferences()));
                    }
                }
            });
            thread.start();
        });
    }

    public Single<List<DayPrayer>> getCalendarByCity(final Address address, int month, int year) {

        return Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                List<DayPrayer> prayerCalendar;

                prayerCalendar = getSavedPrayerCalendar(address, month, year);

                if (prayerCalendar != null && (prayerCalendar.size() == YearMonth.of(year, month).lengthOfMonth())) {
                    emitter.onSuccess(prayerCalendar);
                } else {
                    try {
                        retrieveAndSaveCalendar(address, month, year);
                        prayerCalendar = getSavedPrayerCalendar(address, month, year);

                        if (prayerCalendar != null && !prayerCalendar.isEmpty()) {
                            emitter.onSuccess(prayerCalendar);
                        } else {
                            Log.i(TAG, "Offline calendar calculation");
                            emitter.onSuccess(offlineTimingsService.getPrayerCalendar(address, month, year, getTimingsPreferences()));
                        }
                    } catch (IOException e) {
                        Log.i(TAG, "Offline calendar calculation", e);
                        emitter.onSuccess(offlineTimingsService.getPrayerCalendar(address, month, year, getTimingsPreferences()));
                    }
                }
            });
            thread.start();
        });
    }

    protected DayPrayer getSavedPrayerTimings(LocalDate localDate, Address address) {
        TimingsPreferences timingsPreferences = getTimingsPreferences();

        if (address.getLocality() != null && address.getCountryName() != null) {
            return prayerRegistry.getPrayerTimings(
                    localDate,
                    address.getLocality(),
                    address.getCountryName(),
                    timingsPreferences.getMethod(),
                    timingsPreferences.getLatitudeAdjustmentMethod(),
                    timingsPreferences.getSchoolAdjustmentMethod(),
                    timingsPreferences.getMidnightModeAdjustmentMethod(),
                    timingsPreferences.getHijriAdjustment(),
                    timingsPreferences.getTune()
            );
        }

        return null;
    }

    protected List<DayPrayer> getSavedPrayerCalendar(Address address, int month, int year) {
        TimingsPreferences timingsPreferences = getTimingsPreferences();

        if (address.getLocality() != null && address.getCountryName() != null) {

            return prayerRegistry.getPrayerCalendar(
                    address.getLocality(),
                    address.getCountryName(),
                    month, year,
                    timingsPreferences.getMethod(),
                    timingsPreferences.getLatitudeAdjustmentMethod(),
                    timingsPreferences.getSchoolAdjustmentMethod(),
                    timingsPreferences.getMidnightModeAdjustmentMethod(),
                    timingsPreferences.getHijriAdjustment(),
                    timingsPreferences.getTune()
            );
        }

        return null;
    }

    protected TimingsPreferences getTimingsPreferences() {
        CalculationMethodEnum method = preferencesHelper.getCalculationMethod();
        String tune = preferencesHelper.getTune();
        LatitudeAdjustmentMethod latitudeAdjustmentMethod = preferencesHelper.getLatitudeAdjustmentMethod();
        SchoolAdjustmentMethod schoolAdjustmentMethod = preferencesHelper.getSchoolAdjustmentMethod();
        MidnightModeAdjustmentMethod midnightModeAdjustmentMethod = preferencesHelper.getMidnightModeAdjustmentMethod();
        int hijriAdjustment = preferencesHelper.getHijriAdjustment();

        return new TimingsPreferences(method, tune, latitudeAdjustmentMethod, schoolAdjustmentMethod,
                midnightModeAdjustmentMethod, hijriAdjustment
        );
    }
}
