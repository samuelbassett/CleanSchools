package com.tc.midtermproject.data.Util

object Validator {

    fun validateFirstName(firstName: String): ValidationResult {
        return ValidationResult(
            (!firstName.isNullOrEmpty() && firstName.length >= 2)
        )

    }

    fun validateLastName(lastName: String): ValidationResult {
        return ValidationResult(
            (!lastName.isNullOrEmpty() && lastName.length >= 2)
        )
    }

    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            (!email.isNullOrEmpty() && email.contains('@'))
        )
    }

    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            (!password.isNullOrEmpty() && password.length >= 8)
        )
    }

}

data class ValidationResult(
    val status: Boolean = false
)