package c.eip.neoconnect.utils


data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    /**
     * Réponses des requêtes API avec un Status (SUCCESS ou ERROR),
     * les données en cas de succès et un message de succès ou d'erreur
     */
    companion object {
        fun <T> success(data: T, message: String): Resource<T> =
            Resource(
                status = Status.SUCCESS,
                data = data,
                message = message
            )

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(
                status = Status.ERROR,
                data = data,
                message = message
            )
    }
}