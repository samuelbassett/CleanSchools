package com.tc.midtermproject.ui.scores

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tc.midtermproject.R
import com.tc.midtermproject.data.model.sat.SatItemModel

@Composable
fun ScoresScreen(
    schoolId: String
) {
    val viewModel = hiltViewModel<ScoresViewModel>()
    val schoolScore by viewModel.schoolScores.collectAsState()

    viewModel.getScores(schoolId)
    SchoolScoresScreen(schoolScore)
}

@Composable
fun SchoolScoresScreen(schoolScores: SatItemModel) {
    if(schoolScores.schoolName == stringResource(R.string.school_not_found)) {
        Column(modifier = Modifier.padding(top = 128.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "No Data for selected school, or there were zero students who took the SAT.",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp))
        }
    } else {
        Column(modifier = Modifier.padding(top = 64.dp)) {
            Card(
                elevation = CardDefaults.cardElevation(10.dp),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column() {
                    Row(
                        modifier = Modifier
                            .height(99.dp)
                            .background(color = Color(0xFFD1D1D1))
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${schoolScores.dbn}",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(start = 10.dp),
                            fontSize = 20.sp
                        )
                        Text(
                            text = "${schoolScores.schoolName}",
                            color = Color.Black,
                            modifier = Modifier
                                .weight(3f)
                                .padding(end = 5.dp),
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
            Column() {
                Text(
                    text = "Number of SAT Test Takers: ${schoolScores.numOfSatTestTakers}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Text(
                    text = "Average Math Score: ${schoolScores.satMathAvgScore}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Text(
                    text = "Average Writing Score: ${schoolScores.satWritingAvgScore}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Text(
                    text = "Average Reading Score: ${schoolScores.satCriticalReadingAvgScore}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}