package islam.muslim.app.openweathermap;

import islam.muslim.app.utils.LocaleHelper;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import retrofit2.Retrofit;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */
@Singleton
public class OpenWeatherMapAPIService {

    protected String TAG = "OPEN_WEATHER_MAP_API_SERVICE";

    private final Retrofit retrofit;
    private final LocaleHelper localeHelper;

    @Inject
    public OpenWeatherMapAPIService(@Named("open_weather_map_api") Retrofit retrofit, LocaleHelper localeHelper) {
        this.retrofit = retrofit;
        this.localeHelper = localeHelper;
    }

}