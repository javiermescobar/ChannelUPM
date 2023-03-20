package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentAddNewsBinding
import repositories.NewsRepository
import utils.Constants
import viewModels.NewsViewModel

class AddNewsItemFragment: BaseFragment() {

    private lateinit var binding: FragmentAddNewsBinding
    private lateinit var newsViewModel: NewsViewModel

    private var isEditing = false
    private var placeHolderTitle = ""
    private var placeHolderDescription = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        arguments?.let {
            isEditing = it.getBoolean(Constants.EDITING_NAV_ARG)
            if(isEditing) {
                placeHolderTitle = it.getString(Constants.TITLE_NAV_ARG)!!
                placeHolderDescription = it.getString(Constants.DESCRIPTION_NAV_ARG)!!
            }
        }

        binding = FragmentAddNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun initializeView() {
        val newsRepository = NewsRepository()
        newsViewModel = NewsViewModel(newsRepository, baseViewModel)

        if(isEditing) {
            binding.titleText.text = resources.getText(R.string.edit_newsItem_fragment_title)
            binding.titleInput.setText(placeHolderTitle)
            binding.descriptionInput.setText(placeHolderDescription)
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.addNewButton.setOnClickListener {

            if(binding.titleInput.text.isEmpty() || binding.descriptionInput.text.isNullOrEmpty()) {
                this.activity?.let {
                    showInformationDialog(R.string.enter_all_fields, true)
                }
            } else {
                val navBundle = Bundle()
                navBundle.putSerializable(Constants.TITLE_NAV_ARG, binding.titleInput.text.toString())
                navBundle.putSerializable(Constants.DESCRIPTION_NAV_ARG, binding.descriptionInput.text.toString())
                navBundle.putSerializable(Constants.EDITING_NAV_ARG, isEditing)
                findNavController().navigate(R.id.action_add_news_fragment_to_add_categories_news_fragment, navBundle)
            }
        }
    }
}