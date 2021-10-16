package com.itamazons.data

import com.itamazons.domain.repositories.LoginRepository
import com.sample.scopedstorage.activities.data.LoginDataSource
import com.sample.scopedstorage.activities.data.Result
import com.sample.scopedstorage.activities.data.model.LoggedInUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepositoryImp(val dataSource: LoginDataSource): LoginRepository {

    // in-memory cache of the loggedInUser object
    override var user: LoggedInUser? = null
        set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    override fun logout() {
        user = null
        dataSource.logout()
    }

    override fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}