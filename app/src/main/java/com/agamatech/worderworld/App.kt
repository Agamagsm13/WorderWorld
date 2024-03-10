package com.agamatech.worderworld

import android.app.Application
import android.content.Context
import com.agamatech.worderworld.domain.data.AppLocalStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@HiltAndroidApp
class App: Application() {

}

@Module
@InstallIn(SingletonComponent::class)
class AppDep {
    @Singleton
    @Provides
    fun provideAppLocalStorage(@ApplicationContext context: Context): AppLocalStorage {
        return AppLocalStorage(context.getSharedPreferences("appStorage", Application.MODE_PRIVATE))
    }
}
