package com.tc.midtermproject.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.ContentAlpha
import com.tc.midtermproject.ui.navigation.Screens
import com.tc.midtermproject.ui.schoollist.SchoolItem

@Composable
fun SearchScreen(
    navController: NavController,
    query: String
) {
    val viewModel = hiltViewModel<SearchViewModel>()
    val searchData by viewModel.searchData.collectAsState()

    viewModel.getSearch(query)

    val navigateToItem: (String) -> Unit = { schoolId ->
        navController.navigate("${Screens.SchoolItem.route}/$schoolId")
    }

    Column {
        Text(
            text = "Showing results for \"${query}\"",
            modifier = Modifier
                .padding(top = 64.dp, bottom = 4.dp, start = 8.dp)
                .alpha(ContentAlpha.medium),
            textAlign = TextAlign.Center,
        )

        LazyColumn {
            items(searchData) { school ->
                SchoolItem(school, navigateToItem)
            }
        }
    }
}