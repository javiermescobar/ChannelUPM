package fragments

import adapters.CategoryAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentAddCategoriesNewsBinding
import models.Category
import models.InteractableCategory
import repositories.CategoriesRepository
import repositories.NewsRepository
import utils.AppState
import utils.Constants
import utils.ItemDecorator
import viewModels.CategoriesViewModel
import viewModels.NewsViewModel

class AddCategoriesNewsFragment: BaseFragment(){

    private lateinit var binding: FragmentAddCategoriesNewsBinding
    private lateinit var categoriesAdapter: CategoryAdapter
    private lateinit var newsViewModel: NewsViewModel

    private var selectedCategory: InteractableCategory? = null
    private var newTitle = ""
    private var newDescription = ""
    private var creatingNew = false

    companion object {
        const val ITEM_SPACING = 20
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        arguments?.let {
            newTitle = it.getString(Constants.TITLE_NAV_ARG)!!
            newDescription = it.getString(Constants.DESCRIPTION_NAV_ARG)!!
        }

        binding = FragmentAddCategoriesNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initializeView() {

        newsViewModel = NewsViewModel(NewsRepository(), baseViewModel)

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
                selectedCategory = null
                mutableCategories.forEach { it.selected = false }
            } else {
                mutableCategories.filter { category -> category.selected }
                    .forEach { filteredCategory ->
                        filteredCategory.selected = false
                    }
                selectedCategory = it
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

        binding.buttonConfirm.setOnClickListener {
            if(selectedCategory == null) {
                showInformationDialog(R.string.need_select_category)
            } else {
                creatingNew = true
                newsViewModel.addNew(Constants.currentUser.UserId, newTitle, newDescription, selectedCategory!!.categoryId)
            }
        }
    }

    override fun subscribe() {
        baseViewModel.appState.observe(this, Observer {
            when (it) {
                AppState.ERROR -> {
                    creatingNew = false
                }
                AppState.SUCCESS -> {
                    if(creatingNew) {
                        showInformationDialog(R.string.new_created)
                        findNavController().navigate(R.id.action_add_categories_news_fragment_to_news_fragment)
                    }
                }
                else -> {}
            }
        })
    }

    private fun updateAdapter() {
        if(this::categoriesAdapter.isInitialized) {
            categoriesAdapter.notifyDataSetChanged()
        }
    }
}