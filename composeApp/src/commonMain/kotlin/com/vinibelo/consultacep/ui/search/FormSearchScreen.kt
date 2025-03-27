package com.vinibelo.consultacep.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vinibelo.consultacep.ui.search.visualTransformation.CepVisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormSearchScreen(
    modifier: Modifier = Modifier,
    viewModel: FormSearchViewModel = FormSearchViewModel()
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        FormSearch(
            modifier = Modifier.padding(innerPadding),
            formSearchState = viewModel.uiState.formSearchState,
            onCepChanged = viewModel::onCepChanged
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormSearch(
    modifier: Modifier = Modifier,
    formSearchState: FormSearchState,
    onCepChanged: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth()
                .padding(bottom = 12.dp),
            value = formSearchState.cep.value,
            visualTransformation = CepVisualTransformation(),
            onValueChange = onCepChanged,
            label = { Text("CEP") }
        )
        ElevatedButton(
            modifier = modifier.fillMaxWidth(),
            onClick = {}
        ) {
            Text("Search")
        }
    }
}