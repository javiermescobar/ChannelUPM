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
import com.javier.channelupm.databinding.FragmentUserInterestsBinding
import models.Category
import models.InteractiveCategory
import repositories.CategoriesRepository
import repositories.NewsRepository
import repositories.RegisterRepository
import utils.ItemDecorator
import viewModels.CategoriesViewModel
import viewModels.NewsViewModel
import viewModels.RegisterViewModel

class UserInterestsFragment: BaseFragment() {

    private lateinit var binding: FragmentUserInterestsBinding
    private lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var adapter: CategoryAdapter

    private var categories = mutableListOf<InteractiveCategory>()

    companion object {
        const val ITEM_SPACING = 10
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserInterestsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initializeView() {
        categoriesViewModel = CategoriesViewModel(CategoriesRepository(), baseViewModel)
        newsViewModel = NewsViewModel(NewsRepository(), baseViewModel)
        categoriesViewModel.getCategories()

        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            categoriesRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(ItemDecorator(ITEM_SPACING))
            }

            buttonConfirm.setOnClickListener {
                newsViewModel.removeUserInterests()
            }
        }

    }

    override fun subscribe() {
        categoriesViewModel.categories.observe(this, Observer {
            it.forEach { category ->
                categories.add(InteractiveCategory.mapCategoryToInteractiveCategory(category))
            }
            newsViewModel.getUserInterests()
        })

        newsViewModel.mutableUserInterests.observe(this, Observer {
            categories.forEach { category ->
                it.forEach { id ->
                    if(category.categoryId == id) {
                        category.selected = true
                    }
                }
            }

            adapter = CategoryAdapter(categories.toList().sortedBy { category -> category.categoryTitle }) {
                it.selected = !it.selected
                updateAdapter()
            }

            binding.categoriesRecyclerView.adapter = adapter
        })

        newsViewModel.mutableInterestsRemoved.observe(this, Observer {
            if(it) {
                val categoriesId = mutableListOf<Int>()
                categories.forEach { category ->
                    if(category.selected) {
                        categoriesId.add(category.categoryId)
                    }
                }
                newsViewModel.addUserInterests(categoriesId)
            }
        })

        newsViewModel.mutableUserInterestsUpdated.observe(this, Observer {
            if(it) {
                showInformationDialog(R.string.interests_updated, false)
                findNavController().popBackStack()
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateAdapter() {
        if(this::adapter.isInitialized) {
            adapter.notifyDataSetChanged()
        }
    }
}