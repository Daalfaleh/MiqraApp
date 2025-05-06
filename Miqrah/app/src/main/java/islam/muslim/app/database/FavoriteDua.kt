package islam.muslim.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteDuaTable")
data class FavoriteDua(
    @PrimaryKey(autoGenerate = true)
    var index: Int,
    var title: String,
    var category: String
)