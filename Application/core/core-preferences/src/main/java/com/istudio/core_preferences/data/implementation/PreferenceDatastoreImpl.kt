package com.istudio.core_preferences.data.implementation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import com.istudio.core_preferences.data.implementation.utilities.KeysPreferences
import com.istudio.core_preferences.domain.InAppReviewPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class PreferenceDatastoreImpl(
    private val dataStore: DataStore<Preferences>
) : InAppReviewPreferences {

    companion object {
        private val keyRefHasUserRatedTheApp = booleanPreferencesKey(KeysPreferences.keyHasUserRatedApp)
        private val keyRefHasUserChosenRateLater = booleanPreferencesKey(KeysPreferences.keyHasUserChosenRateLater)
        private val keyRefGetRateLaterTime = longPreferencesKey(KeysPreferences.keyGetRateLaterTime)
        private val keyNoOfDistanceTracked = intPreferencesKey(KeysPreferences.keyNoOfDistanceTracked)
        private val keyUiModeOfApp = intPreferencesKey(KeysPreferences.keyUiModeOfApp)
    }


    override suspend fun getUiModeForApp(): Flow<Int> {
        return dataStore.getValueFlow(keyUiModeOfApp,0)
    }

    override suspend fun setUiModeForApp(mode: Int) {
        dataStore.edit { it[keyUiModeOfApp] = mode }
    }

    /** *************************************************************** **/
    override suspend fun hasUserRatedApp(): Flow<Boolean> {
        return dataStore.getValueFlow(keyRefHasUserRatedTheApp, false)
    }

    override suspend fun setUserRatedApp(hasRated: Boolean) {
        dataStore.edit { it[keyRefHasUserRatedTheApp] = hasRated }
    }
    /** *************************************************************** **/

    /** *************************************************************** **/
    override suspend fun hasUserChosenRateLater(): Flow<Boolean> {
        return dataStore.getValueFlow(keyRefHasUserChosenRateLater, false)
    }

    override suspend fun setUserChosenRateLater(hasChosenRateLater: Boolean) {
        dataStore.edit { it[keyRefHasUserChosenRateLater] = hasChosenRateLater }
    }
    /** *************************************************************** **/

    /** *************************************************************** **/
    override suspend fun getRateLaterTime(): Flow<Long> {
        return dataStore.getValueFlow(keyRefGetRateLaterTime, defaultValue = 0)
    }

    override suspend fun setRateLater(time: Long) {
        dataStore.edit { it[keyRefGetRateLaterTime] = time }
    }
    /** *************************************************************** **/

    /** *************************************************************** **/
    override suspend fun noOfDistanceTracked(): Flow<Int> {
        return dataStore.getValueFlow(keyNoOfDistanceTracked, defaultValue = 0)
    }

    override suspend fun setNoOfDistanceTracked(number: Int) {
        dataStore.edit { it[keyNoOfDistanceTracked] = number }
    }
    /** *************************************************************** **/

    /** *************************************************************** **/
    override suspend fun clearIfUserDidNotRate() { dataStore.edit { it.clear() } }
    /** *************************************************************** **/


    private fun <T> DataStore<Preferences>.getValueFlow(
        key: Preferences.Key<T>,
        defaultValue: T,
    ): Flow<T> {
        return this.data
            .catch { exception ->
                if (exception is IOException) { emit(emptyPreferences()) } else { throw exception }
            }.map { preferences ->
                preferences[key] ?: defaultValue
            }
    }
}
