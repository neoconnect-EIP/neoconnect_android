package c.eip.neoconnect.utils

class CheckInput {
    fun checkPseudo(pseudo: String): Boolean {
        return !(pseudo.isEmpty() || pseudo.length < 4 || pseudo.length > 12)
    }

    fun checkEmail(email: String): Boolean {
        return !(email.isEmpty() || email.startsWith("@") || email.endsWith("@"))
    }

    fun checkPassword(password: String, passwordCheck: String): Int {
        return when {
            password.isEmpty() -> 1
            passwordCheck.isEmpty() -> 2
            password != passwordCheck -> 3
            !checkFormat(password) -> 4
            else -> 0
        }
    }

    private fun checkFormat(str: String): Boolean {
        var checkDigit = false
        var checkLower = false
        var checkUpper = false
        var checkLength = false
        if (str.length in 4..12) {
            checkLength = true
        }
        str.forEach {
            when {
                it.isDigit() -> checkDigit = true
                it.isLowerCase() -> checkLower = true
                it.isUpperCase() -> checkUpper = true
            }
        }

        return checkDigit && checkLower && checkUpper && checkLength
    }
}