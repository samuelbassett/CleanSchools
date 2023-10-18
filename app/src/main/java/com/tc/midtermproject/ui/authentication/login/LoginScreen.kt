package com.tc.midtermproject.ui.authentication.login

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tc.midtermproject.R
import com.tc.midtermproject.ui.navigation.Screens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }

    val viewModel = hiltViewModel<LoginViewModel>()
    val loginState by viewModel.loginState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val navigateToList: (String) -> Unit = {
        navController.navigate(Screens.SchoolList.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome,",
            modifier = Modifier.padding(top = 80.dp, start = 8.dp, end = 8.dp),
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Start,
        )
        OutlinedTextField(value = email,
            onValueChange = {
                email = it
                isButtonEnabled = isLoginValid(email, password)
            },
            label = { Text("Enter Email") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(value = password,
            onValueChange = {
                password = it
                isButtonEnabled = isLoginValid(email, password)
            },
            label = { Text("Enter Password") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Button(
            onClick = {
                scope.launch {
                    viewModel.loginWithEmailAndPassword(email, password)
                }
            }, enabled = isButtonEnabled, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Login")
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 36.dp),
            color = Color.Gray,
            thickness = 1.dp
        )

        Text(
            text = "or sign in with",
            modifier = Modifier.padding(8.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Start,
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 36.dp),
            color = Color.Gray,
            thickness = 1.dp
        )

        Row {
            IconButton(onClick = {

            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "googleButton",
                    modifier = Modifier.size(36.dp),
                    tint = Color.Unspecified
                )
            }

            IconButton(onClick = {

            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_facebook),
                    contentDescription = "facebookButton",
                    modifier = Modifier.size(50.dp),
                    tint = Color.Unspecified
                )
            }
        }

        Button(
            onClick = {
                navController.navigate(Screens.SignupScreen.route)
            }, enabled = true, modifier = Modifier
                .padding(8.dp)
        ) {
            Text("Don't have an account? Sign up here!")
        }

        LaunchedEffect(key1 = loginState.isSuccess) {
            scope.launch {
                if (loginState.isSuccess?.isNotEmpty() == true) {
                    val success = loginState.isSuccess
                    Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
                    navController.navigate(Screens.SchoolList.route)
                    navigateToList
                }
            }
        }

        LaunchedEffect(key1 = loginState.isError) {
            scope.launch {
                if (loginState.isError?.isNotEmpty() == true) {
                    val error = loginState.isError
                    Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

private fun isLoginValid(email: String, password: String): Boolean {
    val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = password.length >= 8
    return isEmailValid && isPasswordValid
}