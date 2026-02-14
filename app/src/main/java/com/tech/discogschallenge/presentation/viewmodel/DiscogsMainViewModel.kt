package com.tech.discogschallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.tech.core.route.DiscogsAppDestination
import com.tech.core.route.SearchArtist
import com.tech.design_system.common.model.DiscogsUiText
import com.tech.discogschallenge.presentation.navigation.extension.toTopBarTitle
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

data class MainState(
    val currentRoute: DiscogsAppDestination = SearchArtist,
    val topBarTitle: DiscogsUiText = DiscogsUiText.Dynamic(""),
    val showBottomBar: Boolean = false
)

sealed class MainIntent {
    data class RouteChanged(val route: DiscogsAppDestination?) : MainIntent()
    data object BackLongPressed : MainIntent()
    data object ExitConfirmed : MainIntent()
}

sealed interface MainSideEffect {
    data class ShowMessage(
        val message: String
    ) : MainSideEffect
    data object ShowExitDialog : MainSideEffect
    data object ExitApp : MainSideEffect
}

class DiscogsMainViewModel : ViewModel(),
    ContainerHost<MainState, MainSideEffect> {

    override val container = container<MainState, MainSideEffect>(
        MainState()
    )

    fun onIntent(intent: MainIntent) = intent {
        when (intent) {

            is MainIntent.RouteChanged -> {
                val safeRoute = intent.route ?: SearchArtist
                reduce {
                    state.copy(
                        currentRoute = safeRoute,
                        topBarTitle = safeRoute.toTopBarTitle()
                    )
                }
            }

            MainIntent.BackLongPressed -> {
                if (state.currentRoute is SearchArtist) {
                    postSideEffect(MainSideEffect.ShowExitDialog)
                }
            }

            MainIntent.ExitConfirmed -> {
                postSideEffect(MainSideEffect.ExitApp)
            }
        }
    }
}