package com.meshdesh.trifler.login.data.entity

sealed class LoginResponse {
    data class Success(
        val status: String,
        val message: String,
        val user: User
    ) {
        data class User(
            private val password: String,
            val email: String,
            val name: String,
            val userId: String
        )
    }

    data class Failure(
        val status: String,
        val message: String
    )
}