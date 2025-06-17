package com.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "pref_datastore")
private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN"
private const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN"

class DataStoreManager @Inject constructor(
    @ApplicationContext context: Context,
    private val cipherWrapper: CipherWrapper,
) {
    private val dataStore = context.dataStore
    private val json = Json {
        encodeDefaults = true
        isLenient = true
    }

    fun getDataStore(): DataStore<Preferences> = dataStore

    suspend fun hasKey(key: Preferences.Key<*>) = dataStore.edit { it.contains(key) }

    suspend fun setString(key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        dataStore.edit {
            it[prefKey] = value
        }
    }

    fun getString(key: String): Flow<String?> {
        val prefKey = stringPreferencesKey(key)
        return dataStore.data.map {
            it[prefKey]
        }
    }

    suspend fun setEncryptedString(key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        val encryptedValue = cipherWrapper.encryptData(Json.encodeToString(value))
        dataStore.edit { prefs ->
            prefs[prefKey] = encryptedValue
        }
    }

    fun getEncryptedString(key: String): Flow<String> {
        return getString(key = key).map { encryptedToken ->
            if (encryptedToken.isNullOrBlank()) {
                ""
            } else {
                val decodedString = cipherWrapper.decryptData(data = encryptedToken)
                decodedString
            }
        }
    }

    suspend fun setBoolean(key: String, value: Boolean) {
        val prefKey = booleanPreferencesKey(key)
        dataStore.edit {
            it[prefKey] = value
        }
    }

    fun getBoolean(key: String): Flow<Boolean?> {
        val prefKey = booleanPreferencesKey(key)
        return dataStore.data.map {
            it[prefKey]
        }
    }

    suspend fun setInt(key: String, value: Int) {
        val prefKey = intPreferencesKey(key)
        dataStore.edit {
            it[prefKey] = value
        }
    }

    fun getInt(key: String): Flow<Int?> {
        val prefKey = intPreferencesKey(key)
        return dataStore.data.map {
            it[prefKey]
        }
    }

    suspend fun setLong(key: String, value: Long) {
        val prefKey = longPreferencesKey(key)
        dataStore.edit {
            it[prefKey] = value
        }
    }

    fun getLong(key: String): Flow<Long?> {
        val prefKey = longPreferencesKey(key)
        return dataStore.data.map {
            it[prefKey]
        }
    }

    suspend fun setFloat(key: String, value: Float) {
        val prefKey = floatPreferencesKey(key)
        dataStore.edit {
            it[prefKey] = value
        }
    }

    fun getFloat(key: String): Flow<Float?> {
        val prefKey = floatPreferencesKey(key)
        return dataStore.data.map {
            it[prefKey]
        }
    }

    suspend fun setDouble(key: String, value: Double) {
        val prefKey = doublePreferencesKey(key)
        dataStore.edit {
            it[prefKey] = value
        }
    }

    fun getDouble(key: String): Flow<Double?> {
        val prefKey = doublePreferencesKey(key)
        return dataStore.data.map {
            it[prefKey]
        }
    }

    suspend fun saveAccessToken(token: String) {
        val encryptedValue = cipherWrapper.encryptData(text = token)
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey(name = ACCESS_TOKEN_KEY)] = encryptedValue
        }
    }

    fun getAccessToken(): Flow<String> {
        return getString(key = ACCESS_TOKEN_KEY).map { encryptedToken ->
            if (encryptedToken.isNullOrBlank()) {
                ""
            } else {
                val decodedString = cipherWrapper.decryptData(data = encryptedToken)
                decodedString
            }
        }
    }

    suspend fun removeAccessToken() {
        dataStore.edit {
            it.remove(stringPreferencesKey(name = ACCESS_TOKEN_KEY))
        }
    }

    suspend fun saveRefreshToken(token: String) {
        val encryptedValue = cipherWrapper.encryptData(text = token)
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey(name = REFRESH_TOKEN_KEY)] = encryptedValue
        }
    }

    fun getRefreshToken(): Flow<String> {
        return getString(key = REFRESH_TOKEN_KEY).map { encryptedToken ->
            if (encryptedToken.isNullOrBlank()) {
                ""
            } else {
                val decodedString = cipherWrapper.decryptData(data = encryptedToken)
                decodedString
            }
        }
    }

    suspend fun removeRefreshToken() {
        dataStore.edit {
            it.remove(stringPreferencesKey(name = REFRESH_TOKEN_KEY))
        }
    }

    suspend fun clearPreference() {
        dataStore.edit {
            it.clear()
        }
    }
}