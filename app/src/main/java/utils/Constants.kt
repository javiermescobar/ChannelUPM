package utils

import models.User

class Constants {
    companion object {
        const val BASE_URL = "https://localhost:8080/"
        const val ACCEPTED_CODE = 200
        const val MIN_PASSWORD_LENGHT = 8
        const val MAX_PASSWORD_LENGHT = 20
        const val TITLE_NAV_ARG = "news_title"
        const val DESCRIPTION_NAV_ARG = "description_title"

        val ACCEPTED_DOMAINS = arrayListOf("upm.es","alumnos.upm.es")

        fun isAcceptedDomain(domain: String): Boolean {
            var accepted = false
            ACCEPTED_DOMAINS.forEach {
                if(domain == it) {
                    accepted = true
                }
            }
            return accepted
        }

        lateinit var currentUser: User
    }
}