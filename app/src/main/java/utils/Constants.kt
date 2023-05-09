package utils

import models.NewsItem
import models.User

class Constants {
    companion object {
        const val BASE_URL = "https://localhost:8080/"
        const val ACCEPTED_CODE = 200
        const val IMAGE_REQUEST_CODE = 100
        const val MIN_PASSWORD_LENGHT = 8
        const val MAX_PASSWORD_LENGHT = 20

        const val TITLE_NAV_ARG = "newsItem_title"
        const val DESCRIPTION_NAV_ARG = "newsItem_description"
        const val EDITING_NAV_ARG = "is_editing"

        const val CONTACT_INFO_NAME = "contact_name"
        const val CONTACT_INFO_MAIL = "contact_mail"
        const val CONTACT_INFO_DESCRIPTION = "contact_description"
        const val CONTACT_INFO_AVATAR = "contact_avatar"
        const val CONTACT_ID = "contact_id"

        const val GROUP_ID = "group_id"
        const val GROUP_NAME = "group_name"
        const val GROUP_AVATAR = "group_avatar"


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
        lateinit var currentNewsItem: NewsItem
    }
}