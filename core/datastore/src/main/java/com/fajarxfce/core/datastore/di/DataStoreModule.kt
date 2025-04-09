package com.fajarxfce.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.fajarxfce.core.datastore.IntToStringIdsMigration
import com.fajarxfce.core.datastore.UserPreferences
import com.fajarxfce.core.datastore.UserPreferencesSerializer
import com.fajarxfce.core.network.Dispatcher
import com.fajarxfce.core.network.NiaDispatchers
import com.fajarxfce.core.network.di.ApplicationScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    internal fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(NiaDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        userPreferencesSerializer: UserPreferencesSerializer,
    ): DataStore<UserPreferences> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
            migrations = listOf(
                IntToStringIdsMigration,
            ),
        ) {
            context.dataStoreFile("user_preferences.pb")
        }
}
