package models

class InteractableCategory(
    val categoryId: Int,
    val categoryTitle: String,
    var selected: Boolean
) {
    companion object {
        fun mapCategoryToInteractableCategory(category: Category): InteractableCategory {
            return InteractableCategory(
                category.CategoryId,
                category.Title,
                false
            )
        }
    }
}