package com.tc.midtermproject.ui.navigation

import com.tc.midtermproject.ui.navigation.ScreenName.LOGIN_SCEEN
import com.tc.midtermproject.ui.navigation.ScreenName.LOGIN_SCREEN_HEADER
import com.tc.midtermproject.ui.navigation.ScreenName.SCHOOL_HEADER
import com.tc.midtermproject.ui.navigation.ScreenName.SCHOOL_ITEM
import com.tc.midtermproject.ui.navigation.ScreenName.SCHOOL_ITEM_HEADER
import com.tc.midtermproject.ui.navigation.ScreenName.SCHOOL_LIST
import com.tc.midtermproject.ui.navigation.ScreenName.SCHOOL_SEARCH
import com.tc.midtermproject.ui.navigation.ScreenName.SCHOOL_SEARCH_HEADER
import com.tc.midtermproject.ui.navigation.ScreenName.SIGNUP_SCREEN
import com.tc.midtermproject.ui.navigation.ScreenName.SIGNUP_SCREEN_HEADER

enum class Screens(
    val route: String,
    val header: String
) {
    SchoolList(route = SCHOOL_LIST, header = SCHOOL_HEADER),
    SchoolItem(route = SCHOOL_ITEM, header = SCHOOL_ITEM_HEADER),
    SchoolSearch(route = SCHOOL_SEARCH, header = SCHOOL_SEARCH_HEADER),
    LoginScreen(route = LOGIN_SCEEN, header = LOGIN_SCREEN_HEADER),
    SignupScreen(route = SIGNUP_SCREEN, header = SIGNUP_SCREEN_HEADER),
}

object ScreenName {
    const val SCHOOL_LIST: String = "school_list"
    const val SCHOOL_HEADER: String = "School List"
    const val SCHOOL_ITEM: String = "school_item"
    const val SCHOOL_ITEM_HEADER: String = "School Scores"
    const val SCHOOL_SEARCH: String = "school_search"
    const val SCHOOL_SEARCH_HEADER: String = "School Search"
    const val LOGIN_SCEEN: String = "login_screen"
    const val LOGIN_SCREEN_HEADER: String = "Login"
    const val SIGNUP_SCREEN: String = "signup_screen"
    const val SIGNUP_SCREEN_HEADER: String = "Signup"
}