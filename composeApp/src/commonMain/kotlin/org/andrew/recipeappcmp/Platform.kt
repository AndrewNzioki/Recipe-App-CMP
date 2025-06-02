package org.andrew.recipeappcmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform