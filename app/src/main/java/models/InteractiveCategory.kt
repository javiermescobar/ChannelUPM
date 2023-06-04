package models

class InteractiveCategory(
    val categoryId: Int,
    val categoryTitle: String,
    var selected: Boolean
) {
    companion object {
        fun getEmptyCategory(): InteractiveCategory {
            return InteractiveCategory(
                -1,
                "",
                false
            )
        }

        fun mapCategoryToInteractiveCategory(category: Category): InteractiveCategory {
            return InteractiveCategory(
                category.CategoryId,
                category.CategoryName,
                false
            )
        }
    }
}