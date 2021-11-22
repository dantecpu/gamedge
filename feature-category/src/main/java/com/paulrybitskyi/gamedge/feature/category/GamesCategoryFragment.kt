/*
 * Copyright 2020 Paul Rybitskyi, paul.rybitskyi.work@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paulrybitskyi.gamedge.feature.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.viewModels
import com.paulrybitskyi.gamedge.commons.ui.base.BaseComposeFragment
import com.paulrybitskyi.gamedge.commons.ui.base.events.Route
import com.paulrybitskyi.gamedge.commons.ui.defaultWindowAnimationDuration
import com.paulrybitskyi.gamedge.feature.category.widgets.GamesCategory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class GamesCategoryFragment : BaseComposeFragment<GamesCategoryViewModel, GamesCategoryNavigator>() {

    override val viewModel by viewModels<GamesCategoryViewModel>()

    override fun getContent() = @Composable {
        GamesCategory(
            viewState = viewModel.viewState.collectAsState().value,
            onBackButtonClicked = viewModel::onToolbarLeftButtonClicked,
            onGameClicked = viewModel::onGameClicked,
            onBottomReached = viewModel::onBottomReached,
        )
    }


/*    private fun initToolbar() = with(viewBinding.toolbar) {
        applyWindowTopInsetAsPadding()
        onLeftButtonClickListener = { viewModel.onToolbarLeftButtonClicked() }
    }

    private fun initGamesCategoryView() = with(viewBinding.gamesCategoryView) {
        applyWindowBottomInsetAsMargin()
        onGameClicked = viewModel::onGameClicked
        onBottomReached = viewModel::onBottomReached
    }*/

/*    override fun onBindViewModel() {
        super.onBindViewModel()

        observeToolbarTitle()
        observeUiState()
    }

    private fun observeToolbarTitle() {
        viewModel.toolbarTitle
            .onEach { viewBinding.toolbar.titleText = it }
            .observeIn(this)
    }

    private fun observeUiState() {
        viewModel.uiState
            .onEach { viewBinding.gamesCategoryView.uiState = it }
            .observeIn(this)
    }*/

    override fun onLoadData() {
        super.onLoadData()

        viewModel.loadData(requireContext().defaultWindowAnimationDuration())
    }

    override fun onRoute(route: Route) {
        super.onRoute(route)

        when (route) {
            is GamesCategoryRoute.Info -> navigator.goToInfo(route.gameId)
            is GamesCategoryRoute.Back -> navigator.goBack()
        }
    }
}
