package com.vinibelo.consultacep.ui.search.visualTransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.vinibelo.consultacep.utils.extensions.formatCep

class CepVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val formattedCep = text.text.formatCep()

        return TransformedText(
            AnnotatedString(formattedCep),
            CepOffsetMapping
        )
    }

    object CepOffsetMapping : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset > 5 -> offset + 1
                else -> offset
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when {
                offset > 5 -> offset - 1
                else -> offset
            }
        }
    }
}