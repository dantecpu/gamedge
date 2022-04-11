/*
 * Copyright 2021 Paul Rybitskyi, paul.rybitskyi.work@gmail.com
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

package com.paulrybitskyi.gamedge.feature.news.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.paulrybitskyi.gamedge.commons.ui.theme.GamedgeTheme
import com.paulrybitskyi.gamedge.commons.ui.widgets.AnimatedContentContainer
import com.paulrybitskyi.gamedge.commons.ui.widgets.FiniteUiState
import com.paulrybitskyi.gamedge.commons.ui.widgets.GamedgeProgressIndicator
import com.paulrybitskyi.gamedge.commons.ui.widgets.Info
import com.paulrybitskyi.gamedge.commons.ui.widgets.RefreshableContent
import com.paulrybitskyi.gamedge.feature.news.R

@Composable
internal fun GamingNews(
    uiState: GamingNewsUiState,
    onNewsItemClicked: (GamingNewsItemModel) -> Unit,
    onRefreshRequested: () -> Unit,
) {
    AnimatedContentContainer(uiState.finiteUiState) { finiteUiState ->
        when (finiteUiState) {
            FiniteUiState.LOADING -> LoadingState(Modifier.align(Alignment.Center))
            else -> {
                RefreshableContent(
                    isRefreshing = uiState.isRefreshing,
                    modifier = Modifier.matchParentSize(),
                    onRefreshRequested = onRefreshRequested,
                ) {
                    if (finiteUiState == FiniteUiState.EMPTY) {
                        EmptyState(Modifier.matchParentSize())
                    } else {
                        SuccessState(
                            news = uiState.news,
                            onNewsItemClicked = onNewsItemClicked,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier) {
    GamedgeProgressIndicator(modifier)
}

@Composable
private fun EmptyState(modifier: Modifier) {
    Column(
        // verticalScroll is to enable SwipeRefresh to work
        // when the screen is in empty state
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Info(
            icon = painterResource(R.drawable.newspaper_variant_outline),
            title = stringResource(R.string.gaming_news_info_view_title),
            modifier = Modifier.padding(
                horizontal = GamedgeTheme.spaces.spacing_7_5,
            ),
        )
    }
}

@Composable
private fun SuccessState(
    news: List<GamingNewsItemModel>,
    onNewsItemClicked: (GamingNewsItemModel) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(GamedgeTheme.spaces.spacing_3_5),
    ) {
        items(news, key = GamingNewsItemModel::id) { itemModel ->
            GamingNewsItem(
                model = itemModel,
                onClick = { onNewsItemClicked(itemModel) }
            )
        }
    }
}

@Preview
@Composable
internal fun GamingNewsSuccessStatePreview() {
    val news = listOf(
        GamingNewsItemModel(
            id = 1,
            imageUrl = "",
            title = "Halo Infinite Season 1 Will Run Until May 2022",
            lede = "Season 1 has been extended until May 2020, which " +
                    "might mean campaign co-op and Forge are coming even later than expected.",
            publicationDate = "3 mins ago",
            siteDetailUrl = "url",
        ),
        GamingNewsItemModel(
            id = 2,
            imageUrl = "",
            title = "Call of Duty: Vanguard's UK Launch Sales are Down 40% From Last Year",
            lede = "Call of Duty: Vanguard's launch sales are down about 40% compared to last year's " +
                "Call of Duty: Black Ops Cold War in the UK.",
            publicationDate = "an hour ago",
            siteDetailUrl = "url",
        ),
        GamingNewsItemModel(
            id = 3,
            imageUrl = null,
            title = "WoW Classic Season of Mastery: Full List of Changes",
            lede = "World of Warcraft Classic's first season is nearly here, and Blizzard has " +
                "detailed all the changes players can expect.",
            publicationDate = "2 hours ago",
            siteDetailUrl = "url",
        ),
    )

    GamedgeTheme {
        GamingNews(
            uiState = GamingNewsUiState(
                news = news,
            ),
            onNewsItemClicked = {},
            onRefreshRequested = {},
        )
    }
}

@Preview
@Composable
internal fun GamingNewsEmptyStatePreview() {
    GamedgeTheme {
        GamingNews(
            uiState = GamingNewsUiState(),
            onNewsItemClicked = {},
            onRefreshRequested = {},
        )
    }
}

@Preview
@Composable
internal fun GamingNewsLoadingStatePreview() {
    GamedgeTheme {
        GamingNews(
            uiState = GamingNewsUiState(isLoading = true),
            onNewsItemClicked = {},
            onRefreshRequested = {},
        )
    }
}
