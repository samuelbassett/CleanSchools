package com.tc.midtermproject.ui.navigation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SensorDoor
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.ContentAlpha
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.tc.midtermproject.ui.authentication.login.LoginScreen
import com.tc.midtermproject.ui.authentication.login.LoginViewModel
import com.tc.midtermproject.ui.authentication.signup.SignUpScreen
import com.tc.midtermproject.ui.schoollist.SchoolItem
import com.tc.midtermproject.ui.schoollist.SchoolScreen
import com.tc.midtermproject.ui.scores.ScoresScreen
import com.tc.midtermproject.ui.search.SearchBarState
import com.tc.midtermproject.ui.search.SearchScreen
import com.tc.midtermproject.ui.search.SearchViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val targetScreen = rememberSaveable { mutableStateOf(Screens.LoginScreen) }
    val viewModel = hiltViewModel<AppNavViewModel>()
    val searchBarState by viewModel.searchWidgetState
    val searchTextState by viewModel.searchTextState

    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    Scaffold(topBar = {
        Toolbar(
            targetScreen = targetScreen.value,
            navController = navController,
            searchBarState = searchBarState,
            searchTextState = searchTextState,
            viewModel = viewModel
        )
    }) {
        NavHost(
            navController = navController, startDestination = if (currentUser == null) {
                Screens.LoginScreen.route
            } else {
                Toast.makeText(LocalContext.current, "Hello, ${currentUser.displayName}", Toast.LENGTH_LONG).show()
                Screens.SchoolList.route
            }
        ) {
            composable(Screens.LoginScreen.route) {
                targetScreen.value = Screens.LoginScreen
                LoginScreen(navController)
            }
            composable(Screens.SignupScreen.route) {
                targetScreen.value = Screens.SignupScreen
                SignUpScreen(navController)
            }
            composable(Screens.SchoolList.route) {
                targetScreen.value = Screens.SchoolList
                SchoolScreen(navController)
            }
            composable("${Screens.SchoolItem.route}/{schoolId}") { backStackEntry ->
                targetScreen.value = Screens.SchoolItem
                ScoresScreen(
                    backStackEntry.arguments?.getString("schoolId")!!
                )
            }
            composable("${Screens.SchoolSearch.route}/{query}") { backStackEntry ->
                targetScreen.value = Screens.SchoolSearch
                SearchScreen(
                    navController, backStackEntry.arguments?.getString("query")!!
                )
            }

        }
    }
}

// Toolbar integration
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    targetScreen: Screens,
    navController: NavController,
    searchBarState: SearchBarState,
    searchTextState: String,
    viewModel: AppNavViewModel
) {
    when(targetScreen) {
        Screens.SchoolList -> {
            TopAppBar(title = { Text(text = targetScreen.header, color = Color.White) },
                navigationIcon = { BackButton(navController = navController) },
                colors = TopAppBarDefaults.smallTopAppBarColors(Color(0xFF6F7FF7))
            )
        }
        Screens.LoginScreen -> {
//            TopAppBar(title = { Text(text = targetScreen.header, color = Color.White) },
//                navigationIcon = {  },
//                colors = TopAppBarDefaults.smallTopAppBarColors(Color(0xFF6F7FF7))
//            )
        }
        else -> {

            SearchBar(
                navController = navController,
                searchBarState = searchBarState,
                searchTextState = searchTextState,
                onTextChange = {
                    viewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    viewModel.updateSearchWidgetState(newValue = SearchBarState.COLLAPSED)
                },
                onSearchClicked = {
                    navController.navigate("${Screens.SchoolSearch.route}/${viewModel.searchTextState.value}")
                },
                onSearchTriggered = {
                    viewModel.updateSearchWidgetState(newValue = SearchBarState.EXPANDED)
                })
        }
//            (targetScreen != Screens.SchoolList && targetScreen != Screens.LoginScreen) {
//
    }
}

// Back button integration
@Composable
fun BackButton(navController: NavController) {
    IconButton(onClick = { navController.popBackStack() }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White
        )
    }
}

@Composable
fun SearchBar(
    navController: NavController,
    searchBarState: SearchBarState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchBarState) {
        SearchBarState.COLLAPSED -> {
            DefaultAppBar(
                navController, onSearchClicked = onSearchTriggered
            )
        }

        SearchBarState.EXPANDED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    navController: NavController, onSearchClicked: () -> Unit
) {
    TopAppBar(title = {
        Text(
            text = "School List", color = Color.White
        )
    }, colors = TopAppBarDefaults.smallTopAppBarColors(Color(0xFF6F7FF7)), actions = {
        IconButton(onClick = { onSearchClicked() }) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
                tint = Color.White
            )
        }
        IconButton(onClick = {
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            navController.navigate(
                Screens.LoginScreen.route
            )
        }) {
            Icon(
                imageVector = Icons.Filled.SensorDoor,
                contentDescription = "Log Out",
                tint = Color(0xFFEF0000)
            )
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp), color = Color(0xFF6F7FF7)
    ) {
        TextField(modifier = Modifier.fillMaxWidth(), value = text, onValueChange = {
            onTextChange(it)
        }, placeholder = {
            Text(
                modifier = Modifier.alpha(ContentAlpha.medium),
                text = "Search here...",
                color = Color.White
            )
        }, textStyle = TextStyle(
            fontSize = 18.sp, color = Color.White
        ), singleLine = true, leadingIcon = {
            IconButton(modifier = Modifier.alpha(ContentAlpha.medium), onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
        }, trailingIcon = {
            IconButton(onClick = {
                if (text.isNotEmpty()) {
                    onTextChange("")
                } else {
                    onCloseClicked()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                    tint = Color.White
                )
            }
        }, keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ), keyboardActions = KeyboardActions(onSearch = {
            onSearchClicked(text)
        }), colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
        ))
    }
}


@Composable
@Preview
fun DefaultAppBarPreview() {
    DefaultAppBar(navController = rememberNavController(), onSearchClicked = {})
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(text = "Some random text",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {})
}