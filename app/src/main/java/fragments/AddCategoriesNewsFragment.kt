package fragments

import adapters.CategoryAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.javier.channelupm.databinding.FragmentAddCategoriesNewsBinding
import models.Category
import models.InteractableCategory
import utils.ItemDecorator

class AddCategoriesNewsFragment: BaseFragment(){

    private lateinit var binding: FragmentAddCategoriesNewsBinding
    private lateinit var categoriesAdapter: CategoryAdapter

    companion object {
        const val ITEM_SPACING = 20
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCategoriesNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initializeView() {

        var mutableCategories = mutableListOf<InteractableCategory>()
        mutableCategories.add(InteractableCategory.mapCategoryToInteractableCategory(Category.newCategory("Categoría 1")))
        mutableCategories.add(InteractableCategory.mapCategoryToInteractableCategory(Category.newCategory("Categoría 2")))
        mutableCategories.add(InteractableCategory.mapCategoryToInteractableCategory(Category.newCategory("Categoría 3")))
        mutableCategories.add(InteractableCategory.mapCategoryToInteractableCategory(Category.newCategory("Categoría 4")))
        mutableCategories.add(InteractableCategory.mapCategoryToInteractableCategory(Category.newCategory("Categoría 5")))
        mutableCategories.add(InteractableCategory.mapCategoryToInteractableCategory(Category.newCategory("Categoría 6")))
        mutableCategories.add(InteractableCategory.mapCategoryToInteractableCategory(Category.newCategory("Categoría 7")))
        mutableCategories.add(InteractableCategory.mapCategoryToInteractableCategory(Category.newCategory("Categoría 8")))

        categoriesAdapter = CategoryAdapter(mutableCategories.toList()) {
            if(it.selected) {
                mutableCategories.forEach { it.selected = false }
            } else {
                mutableCategories.filter { category -> category.selected }
                    .forEach { filteredCategory ->
                        filteredCategory.selected = false
                    }
                it.selected = true
            }
            updateAdapter()
        }

        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)
        binding.categoriesRecyclerView.addItemDecoration(ItemDecorator(ITEM_SPACING))
        binding.categoriesRecyclerView.adapter = categoriesAdapter

        binding.backButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    private fun updateAdapter() {
        if(this::categoriesAdapter.isInitialized) {
            categoriesAdapter.notifyDataSetChanged()
        }
    }
}