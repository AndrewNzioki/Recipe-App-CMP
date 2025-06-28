package org.andrew.recipeappcmp.preferences

import com.russhwolf.settings.Settings

expect class MultiplatformSettingsFactory {
    fun getSettings(): Settings
}