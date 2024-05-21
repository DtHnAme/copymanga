package top.fumiama.copymanga.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.fumiama.copymanga.MainActivity
import top.fumiama.copymanga.tools.ui.UITools
import top.fumiama.copymanga.views.AutoHideEditTextPreferenceDialogFragmentCompat
import top.fumiama.dmzj.copymanga.R

class SettingsFragment: PreferenceFragmentCompat() {
    private var isSettingsChanged = false
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                delay(300)
                withContext(Dispatchers.Main) {
                    setPreferencesFromResource(R.xml.pref_setting, rootKey)
                }
            }
        }
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        isSettingsChanged = true
        return super.onPreferenceTreeClick(preference)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (isSettingsChanged) {
            MainActivity.mainWeakReference?.get()?.recreate()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { c ->
            view.apply { post { setPadding(0, 0, 0, UITools.getNavigationBarHeight(c)) } }
        }
    }

    override fun onDisplayPreferenceDialog(preference: Preference) {
        if (preference is EditTextPreference) {
            Log.d("MySF", "preference is EditTextPreference")
            val f = view?.let { AutoHideEditTextPreferenceDialogFragmentCompat.newInstance(it, preference.key) }?:return
            f.setTargetFragment(this, 0)
            f.show(parentFragmentManager, null)
            return
        }
        super.onDisplayPreferenceDialog(preference)
    }
}
