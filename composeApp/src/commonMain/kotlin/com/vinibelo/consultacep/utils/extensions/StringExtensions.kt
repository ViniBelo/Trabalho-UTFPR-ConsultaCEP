package com.vinibelo.consultacep.utils.extensions

fun String.formatCep(): String = mapIndexed { index, char ->
    when (index) {
        5 -> "-$char"
        else -> char
    }
}.joinToString("")