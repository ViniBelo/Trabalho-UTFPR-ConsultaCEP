package com.vinibelo.consultacep.ui.search

import org.jetbrains.compose.resources.StringResource

data class FormSearchField(
    val value: String = "",
    val errorStringResource: StringResource? = null
)

data class FormSearchState(
    val cep: FormSearchField = FormSearchField(),
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