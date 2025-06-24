package org.andrew.recipeappcmp.features.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.andrew.recipeappcmp.features.profile.data.User

class ProfileViewModel: ViewModel() {

    private val _profileUiState = MutableStateFlow(ProfileScreenUiState())
    val profileUiState = _profileUiState.asStateFlow()

    private fun getUserInfo(){
        viewModelScope.launch {
            _profileUiState.value = profileUiState.value.copy(
                isLoading = true
            )

            //Mock API Call
            delay(1000)
            _profileUiState.value = profileUiState.value.copy(
                userInfo = User(
                    id = 1,
                    name = "John Doe",
                    email = "johndoe@gmail.com",
                    myRecipeCount = 20,
                    favoriteRecipeCount = 10,
                    followers = 140
                ),
                isLoading = false
            )


        }
    }

    fun logIn(){
        viewModelScope.launch {
            _profileUiState.value = _profileUiState.value.copy(
                isLoggedIn = true
            )
            getUserInfo()
        }
    }

    fun logOut(){
        viewModelScope.launch {
            _profileUiState.value = _profileUiState.value.copy(
                isLoggedIn = false
            )
            getUserInfo()
        }
    }
}