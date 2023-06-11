package fragments

import adapters.CategoryAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentAddCategoriesNewsBinding
import models.InteractiveCategory
import repositories.CategoriesRepository
import repositories.NewsRepository
import utils.AppState
import utils.Constants
import utils.ItemDecorator
import viewModels.CategoriesViewModel
import viewModels.NewsViewModel

class AddCategoriesNewsItemFragment: BaseFragment(){

    private lateinit var binding: FragmentAddCategoriesNewsBinding
    private lateinit var categoriesAdapter: CategoryAdapter
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var selectedCategory: InteractiveCategory

    private var newsItemTitle = ""
    private var newsItemDescription = ""
    private var isEditing = false
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
            newsItemTitle = it.getString(Constants.TITLE_NAV_ARG)!!
            newsItemDescription = it.getString(Constants.DESCRIPTION_NAV_ARG)!!
            isEditing = it.getBoolean(Constants.EDITING_NAV_ARG)
        }

        binding = FragmentAddCategoriesNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initializeView() {

        newsViewModel = NewsViewModel(NewsRepository(), baseViewModel)

        categoriesViewModel = CategoriesViewModel(CategoriesRepository(), baseViewModel)
        categoriesViewModel.getCategories()

        selectedCategory = InteractiveCategory.getEmptyCategory()

        binding.backButton.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.buttonConfirm.setOnClickListener {
            if(selectedCategory.categoryId == -1) {
                showInformationDialog(R.string.need_select_category, true)
            } else {
                creatingNew = true
                if(isEditing) {
                    newsViewModel.editNew(Constants.currentNewsItem.NewsItemId, newsItemTitle, newsItemDescription, selectedCategory.categoryId)
                } else {
                    newsViewModel.addNew(Constants.currentUser.UserId, newsItemTitle, newsItemDescription, selectedCategory)
                }
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
                        creatingNew = false
                        newsViewModel.sendNewsNotifications(selectedCategory.categoryId, selectedCategory.categoryTitle)
                    }
                }
                else -> {}
            }
        })

        categoriesViewModel.categories.observe(this, Observer {

            val mutableCategories = mutableListOf<InteractiveCategory>()

            it.forEach { category ->
                mutableCategories.add(InteractiveCategory.mapCategoryToInteractiveCategory(category))
            }

            categoriesAdapter = CategoryAdapter(mutableCategories) { interactiveCategory ->
                if(interactiveCategory.selected) {
                    selectedCategory = InteractiveCategory.getEmptyCategory()
                    mutableCategories.forEach { it.selected = false }
                } else {
                    mutableCategories.filter { category -> category.selected }
                        .forEach { filteredCategory ->
                            filteredCategory.selected = false
                        }
                    selectedCategory = interactiveCategory
                    interactiveCategory.selected = true
                }
                updateAdapter()
            }

            binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)
            binding.categoriesRecyclerView.addItemDecoration(ItemDecorator(ITEM_SPACING))
            binding.categoriesRecyclerView.adapter = categoriesAdapter
        })

        newsViewModel.mutableNotificationsSend.observe(this, Observer {
            if(it) {
                if(isEditing) {
                    showInformationDialog(R.string.newsItem_edited, false)
                } else {
                    showInformationDialog(R.string.newsItem_created, false)
                }
                findNavController().navigate(R.id.action_add_categories_news_fragment_to_news_fragment)
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateAdapter() {
        if(this::categoriesAdapter.isInitialized) {
            categoriesAdapter.notifyDataSetChanged()
        }
    }
}