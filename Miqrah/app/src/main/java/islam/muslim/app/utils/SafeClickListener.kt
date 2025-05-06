package islam.muslim.app.utils

import islam.muslim.app.utils.common.constants.GeneralConstants.DOUBLE_CLICK_DELAY
import android.os.SystemClock
import android.view.View

class SafeClickListener(
    private var defaultInterval: Int = DOUBLE_CLICK_DELAY,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}