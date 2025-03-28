package com.vinibelo.consultacep.ui.search

import com.vinibelo.consultacep.data.model.Cep
import org.jetbrains.compose.resources.StringResource

data class FormSearchField(
    val value: String = "",
    val errorStringResource: StringResource? = null
)

data class FormSearchState(
    val cep: FormSearchField = FormSearchField(),
    val endereco: Cep = Cep(),
    val fetchingCep: Boolean = false
) {
    val isValid get(): Boolean = cep.errorStringResource == null
}

data class FormSearchUiState (
    val formSearchState: FormSearchState = FormSearchState(),
    val errorWhileFetching: Boolean = false,
    val fetchedSuccessfully: Boolean = false
) {
    val successFetching get(): Boolean = !errorWhileFetching
}