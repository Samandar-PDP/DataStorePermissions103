package uz.digital.datastore103.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.digital.datastore103.model.User
import uz.digital.datastore103.util.Constants

class DataStoreManager(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences>
    by preferencesDataStore(name = "Ketmon")

    companion object {
        val NAME = stringPreferencesKey("name")
        val LAST_NAME = stringPreferencesKey("last_name")
        val AGE = intPreferencesKey("age")
    }

    suspend fun saveUser(user: User) {
        context.dataStore.edit {
            it[NAME] = user.name
            it[LAST_NAME] = user.lastName
            it[AGE] = user.age
        }
    }

    fun getUser(): Flow<User> {
        return context.dataStore.data.map {
            User(
                it[NAME] ?: "",
                it[LAST_NAME] ?: "",
                it[AGE] ?: -1
            )
        }
    }
}