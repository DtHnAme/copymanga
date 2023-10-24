package top.fumiama.copymanga.tools.api

import android.os.Bundle
import androidx.navigation.NavController

object Navigate {
    fun safeNavigateTo(navController: NavController, id: Int, bundle: Bundle? = null) {
        navController.currentDestination?.getAction(id)?.let {
            navController.navigate(id, bundle)
        }
    }
}