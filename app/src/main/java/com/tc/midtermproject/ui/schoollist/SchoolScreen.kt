package com.tc.midtermproject.ui.schoollist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tc.midtermproject.data.model.schools.SchoolItemModel
import com.tc.midtermproject.ui.navigation.Screens

@Composable
fun SchoolScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<SchoolsViewModel>()
    val schoolData by (viewModel.schoolData.collectAsState())

    val navigateToItem: (String) -> Unit = { schoolId ->
        navController.navigate("${Screens.SchoolItem.route}/$schoolId")
    }

    LazyColumn(modifier = Modifier.padding(top = 64.dp)) {
        items(schoolData) {school ->
            SchoolItem(school, navigateToItem)
        }
    }
}

@Composable
fun SchoolItem(school: SchoolItemModel, navigateToScore: (String) -> Unit) {

    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxHeight()
            .clickable { navigateToScore(school.dbn.toString()) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(99.dp)
                .background(color = Color(0xFFD1D1D1))
                .padding(10.dp),
        ) {
            Text(
                text = "${school.schoolName}",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 5.dp),
                fontSize = 20.sp,
                textAlign = TextAlign.Start
            )
            Text(
                text = "${school.primaryAddressLine1}",
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 5.dp, top = 5.dp),
                textAlign = TextAlign.Start
            )
        }
    }

}