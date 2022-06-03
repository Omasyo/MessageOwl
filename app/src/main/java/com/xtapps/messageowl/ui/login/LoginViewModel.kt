package com.xtapps.messageowl.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.xtapps.messageowl.data.AuthRepository
import com.xtapps.messageowl.data.Result

import com.xtapps.messageowl.R

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = authRepository.login(username, password)

        if (result is Result.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        ***REMOVED*** else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        ***REMOVED***
    ***REMOVED***

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        ***REMOVED*** else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        ***REMOVED*** else {
            _loginForm.value = LoginFormState(isDataValid = true)
        ***REMOVED***
    ***REMOVED***

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        ***REMOVED*** else {
            username.isNotBlank()
        ***REMOVED***
    ***REMOVED***

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    ***REMOVED***
***REMOVED***