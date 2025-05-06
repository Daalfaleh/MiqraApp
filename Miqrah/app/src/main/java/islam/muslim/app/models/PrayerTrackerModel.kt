package islam.muslim.app.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text

@Entity
data class PrayerTrackerModel(
    @PrimaryKey
    val date: String,
    val fajar: Int,
    val duhur: Int,
    val asar: Int,
    val maghrib: Int,
    val isha: Int
)