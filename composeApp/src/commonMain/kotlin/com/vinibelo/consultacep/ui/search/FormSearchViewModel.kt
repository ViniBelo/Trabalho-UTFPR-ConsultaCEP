package com.vinibelo.consultacep.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import consultacep.composeapp.generated.resources.Res
import consultacep.composeapp.generated.resources.invalid_cep
import consultacep.composeapp.generated.resources.mandatory_cep
import org.jetbrains.compose.resources.StringResource

class FormSearchViewModel(): ViewModel() {
    var uiState: FormSearchUiState by mutableStateOf(FormSearchUiState())
        private set

    fun onCepChanged(value: String) {
        val newCep = value.replace("\\D".toRegex(), "")
        if (newCep.length <= 8 && uiState.formSearchState.cep.value != newCep) {
            val validationMessage = validateCep(newCep)
            uiState = uiState.copy(
                formSearchState = uiState.formSearchState.copy(
                    cep = FormSearchField(
                        value = newCep,
                        errorStringResource = validationMessage
                    )
                )
            )
        }
    }

    private fun validateCep(cep: String): StringResource? = if (cep.isBlank()) {
        Res.string.mandatory_cep
    } else if (cep.length != 8) {
        Res.string.invalid_cep
    } else {
        null
    }
}