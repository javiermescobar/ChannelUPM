package models

data class Category(
    val CategoryId: Int,
    val Title: String
) {
    companion object {
        fun newCategory(title: String): Category {
            return Category(-1, title)
        }
    }
}