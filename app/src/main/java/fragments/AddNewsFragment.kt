package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentAddNewsBinding
import repositories.NewsRepository
import utils.Constants
import viewModels.NewsViewModel

class AddNewsFragment: BaseFragment() {

    private lateinit var binding: FragmentAddNewsBinding
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun initializeView() {
        val newsRepository = NewsRepository()
        newsViewModel = NewsViewModel(newsRepository, baseViewModel)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}