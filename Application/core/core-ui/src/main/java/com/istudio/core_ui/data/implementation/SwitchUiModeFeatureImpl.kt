package com.istudio.core_ui.data.implementation

import android.app.Activity
import androidx.appcompat.app.AppCompatDelegate
import com.istudio.core_preferences.domain.InAppReviewPreferences
import com.istudio.core_ui.data.models.Mode
import com.istudio.core_ui.domain.SwitchUiModeFeature
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SwitchUiModeFeatureImpl @Inject constructor(
    // Preferences used to update the rate app prompt flags.
    var preferences: InAppReviewPreferences
) : SwitchUiModeFeature {

    /**
     * How this works:
     * ***************
     * If the dark mode is currently present in app return light mode value else dark mode value
     */
    override suspend fun toggleUiMode(): Int {
        return if(isDarkMode()){
            // Set light mode
            AppCompatDelegate.MODE_NIGHT_NO
        }else {
            // Set dark mode
            AppCompatDelegate.MODE_NIGHT_YES
        }
    }

    /**
     * This will return true if the current mode is dark mode
     * else if false in case the current mode is dark mode
     */
    override suspend fun isDarkMode(): Boolean {
        val currentMode = preferences.getUiModeForApp().first()
        if (currentMode == Mode.LIGHT.ordinal){ return false }
        else { return true }
    }

    /**
     * How this works:
     * ***************
     * User would have initiated -> Change UI mode, So new UI mode will be the changed one
     * Thus if its dark-mode we set the flag new mode as light mode else dark mode
     */
    override suspend fun saveToggledUiMode() {
        if(isDarkMode()){
            // Set light mode
            preferences.setUiModeForApp(Mode.LIGHT.ordinal)
        }else {
            // Set dark mode
            preferences.setUiModeForApp(Mode.DARK.ordinal)
        }
    }

    /**
     * How this works:
     * ***************
     * When this is invoked, Activity gets restarted and animation takes place
     */
    override fun animateAndRestartApplication(activity: Activity) {
        activity.apply {
            finish()
            val fadeIn = com.istudio.core_ui.R.anim.fade_in
            val fadeOut = com.istudio.core_ui.R.anim.fade_out
            overridePendingTransition(fadeIn, fadeOut);
            startActivity(intent);
            overridePendingTransition(fadeIn, fadeOut);
        }
    }
}