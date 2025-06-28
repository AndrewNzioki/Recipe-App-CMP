package org.andrew.recipeappcmp.features.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.andrew.recipeappcmp.features.common.ui.components.ErrorContent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import recipeapp_cmp.composeapp.generated.resources.Res
import recipeapp_cmp.composeapp.generated.resources.app_name
import recipeapp_cmp.composeapp.generated.resources.recipe_app_logo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenModalBottomSheet(
    loginViewModel: LoginViewModel,
    showBottomSheet: Boolean,
    onClose: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { false }
    )

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var clearInputFields = {
        email = ""
        password = ""
    }

    val scope = rememberCoroutineScope()

    val loginState by loginViewModel.loginState.collectAsStateWithLifecycle()

    val onCloseIconClick: () -> Unit = {
        scope.launch {
            clearInputFields()
            bottomSheetState.hide()
        }.invokeOnCompletion {
            if (!bottomSheetState.isVisible) {
                onClose()
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            dragHandle = {},
            onDismissRequest = {
                onClose()
                clearInputFields
            },
            sheetState = bottomSheetState,
            properties = ModalBottomSheetProperties(
                shouldDismissOnBackPress = true
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = stringResource(Res.string.app_name),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        modifier = Modifier
                            .clickable { onCloseIconClick() },
                        contentDescription = "Close",
                        imageVector = Icons.Outlined.Close,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    //Image or Logo
                    Image(
                        painter = painterResource(Res.drawable.recipe_app_logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                            focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                            cursorColor = MaterialTheme.colorScheme.primaryContainer,
                            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        label = {
                            Text("Email")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                            focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                            cursorColor = MaterialTheme.colorScheme.primaryContainer,
                            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        label = {
                            Text("Password")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation()
                    )


                    Spacer(modifier = Modifier.height(24.dp))

                    when (loginState) {
                        is LoginState.Error -> {
                            ErrorContent(
                                (loginState as LoginState.Error).message
                            )
                        }

                        LoginState.Idle -> Unit
                        LoginState.Loading -> {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }

                        LoginState.Success -> {
                            LaunchedEffect(Unit) {
                                onLoginSuccess()
                                onCloseIconClick()
                            }
                        }
                    }

                    //Button
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp),
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.primaryContainer),
                        ),
                        onClick = {
                            loginViewModel.login(email, password)
                        },
                        enabled = loginState !is LoginState.Loading
                    ) {
                        Text(
                            "Login")

                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
        }
    }

}