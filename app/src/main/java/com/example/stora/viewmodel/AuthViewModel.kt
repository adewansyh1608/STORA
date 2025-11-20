package com.example.stora.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stora.data.AuthResponse
import com.example.stora.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val authResponse: AuthResponse? = null,
    val token: String? = null,
    val isLoggedIn: Boolean = false
)

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()
    
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            authRepository.login(email, password)
                .onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        authResponse = response,
                        token = response.token,
                        isLoggedIn = true
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        errorMessage = exception.message
                    )
                }
        }
    }
    
    fun signup(name: String, email: String, password: String, passwordConfirmation: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            authRepository.signup(name, email, password, passwordConfirmation)
                .onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        authResponse = response,
                        token = response.token,
                        isLoggedIn = true
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        errorMessage = exception.message
                    )
                }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            val currentToken = _uiState.value.token
            if (currentToken != null) {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                
                authRepository.logout(currentToken)
                    .onSuccess { response ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isSuccess = true,
                            authResponse = response,
                            token = null,
                            isLoggedIn = false
                        )
                    }
                    .onFailure { exception ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isSuccess = false,
                            errorMessage = exception.message,
                            token = null,
                            isLoggedIn = false
                        )
                    }
            } else {
                // No token, just clear state
                _uiState.value = _uiState.value.copy(
                    token = null,
                    isLoggedIn = false,
                    authResponse = null
                )
            }
        }
    }
    
    fun getProfile() {
        viewModelScope.launch {
            val currentToken = _uiState.value.token
            if (currentToken != null) {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                
                authRepository.getProfile(currentToken)
                    .onSuccess { response ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isSuccess = true,
                            authResponse = response
                        )
                    }
                    .onFailure { exception ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isSuccess = false,
                            errorMessage = exception.message
                        )
                    }
            } else {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "No authentication token found"
                )
            }
        }
    }
    
    fun updateProfile(name: String?, email: String?) {
        viewModelScope.launch {
            val currentToken = _uiState.value.token
            if (currentToken != null) {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                
                authRepository.updateProfile(currentToken, name, email)
                    .onSuccess { response ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isSuccess = true,
                            authResponse = response
                        )
                    }
                    .onFailure { exception ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isSuccess = false,
                            errorMessage = exception.message
                        )
                    }
            } else {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "No authentication token found"
                )
            }
        }
    }
    
    fun clearState() {
        _uiState.value = AuthUiState()
    }
}
