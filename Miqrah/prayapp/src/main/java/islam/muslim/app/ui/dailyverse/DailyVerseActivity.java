package islam.muslim.app.ui.dailyverse;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.core.os.ConfigurationCompat;
import islam.muslim.app.utils.CustomTypefaceSpan;

import islam.muslim.app.FivePrayerApplication;
import com.yaseen.fiveprayers.R;
import islam.muslim.app.preferences.PreferencesHelper;

import islam.muslim.app.ui.BaseActivity;

import java.text.NumberFormat;
import java.util.Objects;

/**
 * @author Hicham Bouzidi Idrissi
 * Github : https://github.com/Five-Prayers/five-prayers-android
 * licenced under GPLv3 : https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class DailyVerseActivity extends BaseActivity {

    @Override
    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        ((FivePrayerApplication) getApplicationContext())
                .defaultComponent
                .inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_verse);


        PreferencesHelper preferencesHelper = new PreferencesHelper(this);
        NumberFormat numberFormat = NumberFormat.getInstance();

        String systemLanguage = Objects.requireNonNull(ConfigurationCompat
                .getLocales(Resources.getSystem().getConfiguration())
                .get(0)).getLanguage();



        TextView baslamaTextView = findViewById(R.id.baslama_text_view);
        baslamaTextView.setText(getIslamicPhrase("6"));

        TextView sadakaTextView = findViewById(R.id.sadaka_text_view);
        sadakaTextView.setText(getIslamicPhrase("S"));

        TextView separatorView = findViewById(R.id.ornaments_separator_text_view);
        separatorView.setText(getIslamicPhrase("XXXXXXX"));

          }

    private String getIslamicPhrase(String text) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.aga_islamic_phrases);
        builder.append(text);
        builder.setSpan(new CustomTypefaceSpan(typeface), 0, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new RelativeSizeSpan(2.5f), 0, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder.toString();
    }
}