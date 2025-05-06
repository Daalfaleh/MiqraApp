package islam.muslim.app.timings.londonprayertimes;

import android.location.Address;

import islam.muslim.app.database.PrayerRegistry;
import islam.muslim.app.preferences.PreferencesHelper;
import islam.muslim.app.timings.AbstractTimingsService;
import islam.muslim.app.timings.TimingsPreferences;
import islam.muslim.app.timings.aladhan.AladhanAPIService;
import islam.muslim.app.timings.aladhan.AladhanCalendarResponse;
import islam.muslim.app.timings.aladhan.AladhanData;
import islam.muslim.app.timings.aladhan.AladhanTimings;
import islam.muslim.app.timings.aladhan.AladhanTodayTimingsResponse;
import islam.muslim.app.timings.calculations.SchoolAdjustmentMethod;
import islam.muslim.app.timings.offline.OfflineTimingsService;
import islam.muslim.app.utils.TimingUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */
@Singleton
public class LondonUnifiedPrayerTimingsService extends AbstractTimingsService {

    private final AladhanAPIService aladhanAPIService;
    private final LondonUnifiedPrayerAPIService londonUnifiedPrayerAPIService;
    protected String TAG = "LondonUnifiedPrayerTimingsService";

    @Inject
    public LondonUnifiedPrayerTimingsService(AladhanAPIService aladhanAPIService,
                                             LondonUnifiedPrayerAPIService londonUnifiedPrayerAPIService,
                                             PrayerRegistry prayerRegistry,
                                             OfflineTimingsService offlineTimingsService,
                                             PreferencesHelper preferencesHelper
    ) {
        super(prayerRegistry, offlineTimingsService, preferencesHelper);
        this.aladhanAPIService = aladhanAPIService;
        this.londonUnifiedPrayerAPIService = londonUnifiedPrayerAPIService;
    }


    protected void retrieveAndSaveTimings(LocalDate localDate, Address address) throws IOException {
        TimingsPreferences timingsPreferences = getTimingsPreferences();

        AladhanTodayTimingsResponse timingsByCity =
                aladhanAPIService.getTimingsByLatLong(
                        localDate,
                        address.getLatitude(),
                        address.getLongitude(),
                        timingsPreferences.getMethod(),
                        timingsPreferences.getLatitudeAdjustmentMethod(),
                        timingsPreferences.getSchoolAdjustmentMethod(),
                        timingsPreferences.getMidnightModeAdjustmentMethod(),
                        timingsPreferences.getHijriAdjustment(),
                        timingsPreferences.getTune());

        LondonUnifiedTimingsResponse londonTimings = londonUnifiedPrayerAPIService.getLondonTimings();

        if (timingsByCity != null && londonTimings != null) {
            AladhanData aladhanData = createTimingsData(timingsPreferences.getSchoolAdjustmentMethod(), timingsByCity.getData(), londonTimings);

            prayerRegistry.savePrayerTiming(localDate,
                    address.getLocality(),
                    address.getCountryName(),
                    timingsPreferences.getMethod(),
                    timingsPreferences.getLatitudeAdjustmentMethod(),
                    timingsPreferences.getSchoolAdjustmentMethod(),
                    timingsPreferences.getMidnightModeAdjustmentMethod(),
                    timingsPreferences.getHijriAdjustment(),
                    timingsPreferences.getTune(),
                    aladhanData
            );
        }
    }

    protected void retrieveAndSaveCalendar(Address address, int month, int year) throws IOException {
        List<AladhanData> aladhanCalendarData = new ArrayList<>();

        TimingsPreferences timingsPreferences = getTimingsPreferences();

        AladhanCalendarResponse calendarByCity =
                aladhanAPIService.getCalendarByLatLong(
                        address.getLatitude(),
                        address.getLongitude(),
                        month, year,
                        timingsPreferences.getMethod(),
                        timingsPreferences.getLatitudeAdjustmentMethod(),
                        timingsPreferences.getSchoolAdjustmentMethod(),
                        timingsPreferences.getMidnightModeAdjustmentMethod(),
                        timingsPreferences.getHijriAdjustment(),
                        timingsPreferences.getTune());

        LondonUnifiedCalendarResponse londonCalendar = londonUnifiedPrayerAPIService.getLondonCalendar(month, year);

        if (calendarByCity != null && londonCalendar != null) {

            for (AladhanData aladhanData : calendarByCity.getData()) {
                String londonDate = TimingUtils.ConvertAlAdhanFormatToLUT(aladhanData.getDate().getGregorian().getDate());
                LondonUnifiedTimingsResponse londonUnifiedTimingsResponse = londonCalendar.getTimes().get(londonDate);

                if (londonUnifiedTimingsResponse != null) {
                    aladhanCalendarData.add(createTimingsData(timingsPreferences.getSchoolAdjustmentMethod(), aladhanData, londonUnifiedTimingsResponse));
                }
            }

            prayerRegistry.saveCalendar(
                    address.getLocality(),
                    address.getCountryName(),
                    timingsPreferences.getMethod(),
                    timingsPreferences.getLatitudeAdjustmentMethod(),
                    timingsPreferences.getSchoolAdjustmentMethod(),
                    timingsPreferences.getMidnightModeAdjustmentMethod(),
                    timingsPreferences.getHijriAdjustment(),
                    timingsPreferences.getTune(),
                    aladhanCalendarData);
        }
    }

    private AladhanData createTimingsData(SchoolAdjustmentMethod schoolAdjustmentMethod, AladhanData data, LondonUnifiedTimingsResponse londonTimings) {
        AladhanData aladhanData = new AladhanData();
        aladhanData.setDate(data.getDate());
        aladhanData.setMeta(data.getMeta());

        AladhanTimings aladhanTimings = new AladhanTimings();
        aladhanTimings.setFajr(londonTimings.getFajr());
        aladhanTimings.setDhuhr(londonTimings.getDhuhr());
        aladhanTimings.setAsr(SchoolAdjustmentMethod.getDefault().equals(schoolAdjustmentMethod) ? londonTimings.getAsr() : londonTimings.getAsrTwo());
        aladhanTimings.setMaghrib(londonTimings.getMagrib());
        aladhanTimings.setIsha(londonTimings.getIsha());
        aladhanTimings.setImsak(data.getTimings().getImsak());
        aladhanTimings.setMidnight(data.getTimings().getMidnight());
        aladhanTimings.setSunrise(londonTimings.getSunrise());
        aladhanTimings.setSunset(data.getTimings().getSunset());

        aladhanData.setTimings(aladhanTimings);
        return aladhanData;
    }
}
