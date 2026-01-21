package com.ks.culinario.network.dto

private interface UserFields {
    val email: String
    val name: String
}

data class NewUserDTO(
    val password: String,
    override val email: String,
    override val name: String
) : UserFields

data class UserDTO(
    val id: Long,
    override val email: String,
    override val name: String
) : UserFields
