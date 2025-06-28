package org.andrew.recipeappcmp.features.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow()

    fun login(
        email: String,
        password: String
    ){
        viewModelScope.launch {
            _loginState.value = LoginState.Idle

            try {

                //Mock API Call here
                delay(1500)

                if(email == "test@gmail.com" && password == "test"){
                    _loginState.value = LoginState.Success
                }else{
                    _loginState.value = LoginState.Error(
                        "Invalid email or password"
                    )
                }
            }catch (e: Exception){
                _loginState.value = LoginState.Error(
                    "Unexpected error in login"
                )
            }
        }
    }

    fun resetState(){
        _loginState.value = LoginState.Idle
    }

}