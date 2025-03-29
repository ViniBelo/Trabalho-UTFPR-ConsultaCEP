package com.vinibelo.consultacep.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vinibelo.consultacep.ui.search.visualTransformation.CepVisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormSearchScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    val viewModel: FormSearchViewModel = viewModel()
    LaunchedEffect(snackbarHostState, viewModel.uiState.errorWhileFetching) {
        if (viewModel.uiState.errorWhileFetching) {
            snackbarHostState.showSnackbar(
                message = "Error fetching CEP."
            )
        }
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        FormSearch(
            modifier = Modifier.padding(innerPadding),
            formSearchState = viewModel.uiState.formSearchState,
            onCepChanged = viewModel::onCepChanged,
            onClick = viewModel::fetchCep,
            fetchingCep = viewModel.uiState.formSearchState.fetchingCep,
            cepText = viewModel.uiState.formSearchState.endereco.cep.toString(),
            logradouroText = viewModel.uiState.formSearchState.endereco.logradouro,
            bairroText = viewModel.uiState.formSearchState.endereco.bairro,
            localidadeText = viewModel.uiState.formSearchState.endereco.cidade,
            ufText = viewModel.uiState.formSearchState.endereco.uf
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormSearch(
    modifier: Modifier = Modifier,
    formSearchState: FormSearchState,
    onCepChanged: (String) -> Unit,
    onClick: () -> Unit,
    fetchingCep: Boolean,
    cepText: String = "",
    logradouroText: String = "",
    bairroText: String = "",
    localidadeText: String = "",
    ufText: String = "",
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth()
                .padding(bottom = 12.dp),
            value = formSearchState.cep.value,
            visualTransformation = CepVisualTransformation(),
            enabled = !fetchingCep,
            onValueChange = onCepChanged,
            label = { Text("Digite o CEP") }
        )
        ElevatedButton(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            enabled = formSearchState.cep.value.isNotEmpty() && !fetchingCep && formSearchState.cep.errorStringResource == null,
            onClick = onClick
        ) {
            if (fetchingCep) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }
            } else {
                Text("Search")
            }
        }
        Text("CEP: $cepText")
        Text("Logradouro: $logradouroText")
        Text("Bairro: $bairroText")
        Text("Localidade: $localidadeText")
        Text("UF: $ufText")
    }
}