package com.prof18.filmatik

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform