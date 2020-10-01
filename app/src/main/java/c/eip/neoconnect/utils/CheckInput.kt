package c.eip.neoconnect.utils

class CheckInput {
    /**
     *  Vérifie taille du pseudo (entre 4 et 12 caractères)
     */
    fun checkPseudo(pseudo: String): Boolean {
        return !(pseudo.isEmpty() || pseudo.length < 4 || pseudo.length > 12)
    }

    /**
     *  Vérifie format de l'adresse mail
     */
    fun checkEmail(email: String): Boolean {
        return !(email.isEmpty() || email.startsWith("@") || email.endsWith("@"))
    }

    /**
     *  Vérifie format du mot de passe
     */
    fun checkPassword(password: String, passwordCheck: String): Int {
        return when {
            password.isEmpty() -> 1
            passwordCheck.isEmpty() -> 2
            password != passwordCheck -> 3
            !checkFormat(password) -> 4
            else -> 0
        }
    }

    /**
     *  Vérifie :
     *      Taille entre 4 et 12 caractères
     *      Présence d'au moins 1 Majuscule
     *      Présence d'au moins 1 Minuscule
     *      Présence d'au moins 1 Chiffre
     */
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