package com.fajarxfce.feature.splash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarxfce.core.datastore.NiaPreferencesDataSource
import com.fajarxfce.core.model.data.DarkThemeConfig
import com.fajarxfce.core.model.data.ThemeBrand
import com.fajarxfce.core.model.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    preferencesDataSource: NiaPreferencesDataSource
) : ViewModel() {

    val userDataState: StateFlow<UserData> = preferencesDataSource.userData
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserData(
                bookmarkedNewsResources = emptySet(),
                viewedNewsResources = emptySet(),
                followedTopics = emptySet(),
                themeBrand = ThemeBrand.DEFAULT,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                useDynamicColor = false,
                shouldHideOnboarding = false,
                isLoggedIn = false,
                token = ""
            )
        )
}