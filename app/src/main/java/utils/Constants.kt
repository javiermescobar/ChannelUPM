package utils

class Constants {
    companion object {
        const val BASE_URL = "https://localhost:8080/"

        val ACCEPTED_DOMAINS = arrayListOf("upm.es","alumnos.upm.es")
        val MIN_PASSWORD_LENGHT = 8
        val MAX_PASSWORD_LENGHT = 20

        fun isAcceptedDomain(domain: String): Boolean {
            var accepted = false
            ACCEPTED_DOMAINS.forEach {
                if(domain == it) {
                    accepted = true
                }
            }
            return accepted
        }
    }
}