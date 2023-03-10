package models

data class User(
    val UserId: Int,
    val Name: String,
    val Mail: String,
    val UserPassword: String,
    val Description: String,
    val AvatarImage: String,
    val Administrator: Int
) {
    companion object {
        fun emptyUser(): User {
            return User(
                -1,
                "",
                "",
                "",
                "",
                "",
                0
            )
        }

        fun emptyAdminUser(): User {
            return User(
                -1,
                "",
                "",
                "",
                "",
                "",
                1
            )
        }
    }
}