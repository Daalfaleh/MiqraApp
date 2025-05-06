package islam.muslim.app.ui.quran.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import islam.muslim.app.R;
import islam.muslim.app.databinding.ItemVersesBinding;
import islam.muslim.app.models.Verses;
import islam.muslim.app.utils.Utils;

public class VersesAdapter extends RecyclerView.Adapter<VersesAdapter.VersesHolder> {

    Verses verses;
    AdapterView.OnItemClickListener onItemClickListener;
    Context mContext;
    Typeface arabicTypeFace;

    public VersesAdapter(Verses verses, AdapterView.OnItemClickListener onItemClickListener, Context mContext) {
        this.verses = verses;
        this.onItemClickListener = onItemClickListener;
        arabicTypeFace = Typeface.createFromAsset(mContext.getAssets(), "Font/surah.ttf");
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public VersesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VersesHolder(ItemVersesBinding
                .bind(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_verses, parent, false)));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull VersesHolder holder, int position) {

        holder.binding.tvSurahUrduName.setTypeface(arabicTypeFace);

        holder.binding.tvSurahEnglishName.setText(verses.getSurahEnglishName());
        holder.binding.tvSurahUrduName.setText(verses.getSurahUrduName());
        holder.binding.tvCounter.setText(String.valueOf(position + 1));

        holder.binding.getRoot().setOnClickListener(v -> {
            Utils.preventTwoClick(v);

            onItemClickListener.onItemClick(
                    null, holder.binding.getRoot(), holder.getAdapterPosition(), 0
            );
        });

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.list_view_anim);
        holder.binding.getRoot().setAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return verses.getTotalVerse();
    }

    static class VersesHolder extends RecyclerView.ViewHolder {

        ItemVersesBinding binding;

        public VersesHolder(@NonNull ItemVersesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
