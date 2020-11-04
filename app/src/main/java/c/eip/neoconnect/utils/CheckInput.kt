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

    /**
     *  Vérifie lien facebook pendant le partage d'une offre
     */
    fun checkLinksFacebook(str: String): Boolean {
        return str.startsWith("https://www.facebook.com/") || str.startsWith("facebook.com")
    }

    /**
     *  Vérifie lien twitter pendant le partage d'une offre
     */
    fun checkLinksTwitter(str: String): Boolean {
        return str.startsWith("https://www.twitter.com/") || str.startsWith("twitter.com")
    }

    /**
     *  Vérifie lien instagram pendant le partage d'une offre
     */
    fun checkLinksInstagram(str: String): Boolean {
        return str.startsWith("https://www.instagram.com/") || str.startsWith("instagram.com")
    }

    /**
     *  Vérifie lien pinterest pendant le partage d'une offre
     */
    fun checkLinksPinterest(str: String): Boolean {
        return str.startsWith("https://www.pinterest.fr/") || str.startsWith("pinterest.fr")
    }

    /**
     *  Vérifie lien twitch pendant le partage d'une offre
     */
    fun checkLinksTwitch(str: String): Boolean {
        return str.startsWith("https://www.twitch.tv/") || str.startsWith("twitch.tv")
    }

    /**
     *  Vérifie lien youtube pendant le partage d'une offre
     */
    fun checkLinksYoutube(str: String): Boolean {
        return str.startsWith("https://www.youtube.com/") || str.startsWith("youtube.com")
    }

    /**
     *  Vérifie lien tiktok pendant le partage d'une offre
     */
    fun checkLinksTiktok(str: String): Boolean {
        return str.startsWith("https://www.tiktok.com/") || str.startsWith("tiktok.com")
    }
}