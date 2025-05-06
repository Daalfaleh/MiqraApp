package islam.muslim.app.timings;

import islam.muslim.app.timings.calculations.CalculationMethodEnum;
import islam.muslim.app.timings.calculations.LatitudeAdjustmentMethod;
import islam.muslim.app.timings.calculations.MidnightModeAdjustmentMethod;
import islam.muslim.app.timings.calculations.SchoolAdjustmentMethod;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class TimingsPreferences {

    private CalculationMethodEnum method;
    private String tune;
    private LatitudeAdjustmentMethod latitudeAdjustmentMethod;
    private SchoolAdjustmentMethod schoolAdjustmentMethod;
    private MidnightModeAdjustmentMethod midnightModeAdjustmentMethod;
    private int hijriAdjustment;

    public TimingsPreferences(CalculationMethodEnum method,
                              String tune,
                              LatitudeAdjustmentMethod latitudeAdjustmentMethod,
                              SchoolAdjustmentMethod schoolAdjustmentMethod,
                              MidnightModeAdjustmentMethod midnightModeAdjustmentMethod,
                              int hijriAdjustment) {
        this.method = method;
        this.tune = tune;
        this.latitudeAdjustmentMethod = latitudeAdjustmentMethod;
        this.schoolAdjustmentMethod = schoolAdjustmentMethod;
        this.midnightModeAdjustmentMethod = midnightModeAdjustmentMethod;
        this.hijriAdjustment = hijriAdjustment;
    }

    public CalculationMethodEnum getMethod() {
        return method;
    }

    public String getTune() {
        return tune;
    }

    public LatitudeAdjustmentMethod getLatitudeAdjustmentMethod() {
        return latitudeAdjustmentMethod;
    }

    public SchoolAdjustmentMethod getSchoolAdjustmentMethod() {
        return schoolAdjustmentMethod;
    }

    public MidnightModeAdjustmentMethod getMidnightModeAdjustmentMethod() {
        return midnightModeAdjustmentMethod;
    }

    public int getHijriAdjustment() {
        return hijriAdjustment;
    }
}
