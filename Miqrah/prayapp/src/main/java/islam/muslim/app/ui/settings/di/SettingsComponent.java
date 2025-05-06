package islam.muslim.app.ui.settings.di;

import islam.muslim.app.ui.settings.SettingsFragment;
import islam.muslim.app.ui.settings.location.AutoCompleteTextPreferenceDialog;
import islam.muslim.app.ui.settings.method.CalculationMethodPreference;

import dagger.Subcomponent;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */
@Subcomponent
public interface SettingsComponent {

    @Subcomponent.Factory
    interface Factory {
        SettingsComponent create();
    }

    void inject(SettingsFragment settingsFragment);

    void inject(AutoCompleteTextPreferenceDialog autoCompleteTextPreferenceDialog);

    void inject(CalculationMethodPreference calculationMethodPreference);
}
