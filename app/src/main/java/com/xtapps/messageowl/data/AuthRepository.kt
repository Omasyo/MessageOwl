package com.xtapps.messageowl.data

import com.xtapps.messageowl.data.model.User

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class AuthRepository(val dataSource: AuthDataSource) {

    // in-memory cache of the loggedInUser object
    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    ***REMOVED***

    fun logout() {
        user = null
        dataSource.logout()
    ***REMOVED***

    fun login(username: String, password: String): Result<User> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        ***REMOVED***

        return result
    ***REMOVED***

    private fun setLoggedInUser(user: User) {
        this.user = user
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    ***REMOVED***
***REMOVED***