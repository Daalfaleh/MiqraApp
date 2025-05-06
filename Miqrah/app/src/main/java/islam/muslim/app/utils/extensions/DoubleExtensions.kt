package islam.muslim.app.utils.extensions

object DoubleExtensions {
    fun Double.limitTo2Decimal():Double{
        return String.format("%.2f", this).toDouble()
    }

    fun Double.limitTo1Decimal():Double{
        return String.format("%.1f", this).toDouble()
    }
}