package com.vinibelo.consultacep.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinibelo.consultacep.data.model.Cep
import com.vinibelo.consultacep.data.repository.CepRepository
import consultacep.composeapp.generated.resources.Res
import consultacep.composeapp.generated.resources.error_loading_cep
import consultacep.composeapp.generated.resources.invalid_cep
import consultacep.composeapp.generated.resources.mandatory_cep
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource

class FormSearchViewModel(): ViewModel() {
    private val tag: String = "FormPessoaViewModel"
    private val cepRepository: CepRepository = CepRepository()
    var uiState: FormSearchUiState by mutableStateOf(FormSearchUiState())
        private set

    fun fetchCep() {
        viewModelScope.launch {
            uiState = uiState.copy(
                formSearchState = uiState.formSearchState.copy(
                    endereco = uiState.formSearchState.endereco.copy(
                        cep = "",
                        logradouro = "",
                        bairro = "",
                        cidade = "",
                        uf = ""
                    ),
                    fetchingCep = true
                )
            )
            try {
                val returnedCep: Cep = cepRepository.fetchCep(uiState.formSearchState.cep.value)
                println(returnedCep)
                uiState = uiState.copy(
                    formSearchState = uiState.formSearchState.copy(
                        endereco = uiState.formSearchState.endereco.copy(
                            cep = returnedCep.cep,
                            logradouro = returnedCep.logradouro,
                            bairro = returnedCep.bairro,
                            cidade = returnedCep.cidade,
                            uf = returnedCep.uf
                        ),
                        fetchingCep = false
                    )
                )
            } catch (ex: Exception) {
                println("[$tag]: Error fetching CEP $uiState.formSearchState.cep.value")
                ex.printStackTrace()
                uiState.copy(
                    formSearchState = uiState.formSearchState.copy(
                        fetchingCep = false,
                        cep = uiState.formSearchState.cep.copy(
                            errorStringResource = Res.string.error_loading_cep
                        )
                    )
                )
            }
        }
    }

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