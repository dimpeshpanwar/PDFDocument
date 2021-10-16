package com.itamazons.domain.repositories

import com.sample.scopedstorage.activities.data.Result
import com.sample.scopedstorage.activities.data.model.LoggedInUser

/*
Interface describing the action of LoginRepository
 */

interface LoginRepository {
    var user: LoggedInUser?
    fun login(username: String, password: String): Result<LoggedInUser>
    fun logout()
}