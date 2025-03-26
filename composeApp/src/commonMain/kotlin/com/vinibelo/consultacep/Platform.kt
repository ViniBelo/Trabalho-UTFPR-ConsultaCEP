package com.vinibelo.consultacep

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform