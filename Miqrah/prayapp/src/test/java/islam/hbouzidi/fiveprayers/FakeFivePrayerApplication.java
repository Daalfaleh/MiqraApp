package islam.hbouzidi.fiveprayers;

import android.os.Build;

import androidx.multidex.MultiDexApplication;

import islam.muslim.app.common.api.TLSSocketFactoryCompat;
import islam.muslim.app.di.component.ApplicationComponent;
import com.yaseen.fiveprayers.di.component.DaggerApplicationComponent;
import com.yaseen.fiveprayers.di.component.DaggerWidgetComponent;
import islam.muslim.app.di.component.WidgetComponent;
import islam.muslim.app.di.module.AppModule;
import islam.muslim.app.di.module.WidgetModule;
import islam.muslim.app.ui.report.ErrorActivity;

import cat.ereza.customactivityoncrash.config.CaocConfig;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class FakeFivePrayerApplication extends MultiDexApplication {

    public ApplicationComponent appComponent = DaggerApplicationComponent
            .builder()
            .appModule(new AppModule(this))
            .build();

    public WidgetComponent widgetComponent = DaggerWidgetComponent
            .builder()
            .appModule(new AppModule(this))
            .widgetModule(new WidgetModule())
            .build();

    @Override
    public void onCreate() {
        super.onCreate();

        // enable TLS1.1/1.2 for kitkat devices
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            TLSSocketFactoryCompat.setAsDefault();
        }

        CaocConfig
                .Builder
                .create()
                .errorActivity(ErrorActivity.class)
                .apply();
    }
}
