package fragments

import adapters.NewsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentNewsBinding
import models.New
import repositories.NewsRepository
import utils.Constants
import utils.ItemDecorator
import viewModels.NewsViewModel

class NewsFragment : BaseFragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var news: MutableList<New>
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel

    companion object {
        const val ITEM_SPACING = 20
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initializeView() {
        newsViewModel = NewsViewModel(NewsRepository(), baseViewModel)
        newsViewModel.getNews(Constants.currentUser.UserId)

        news = mutableListOf()
        news.add(New.getNew("Prueba1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"))
        news.add(New.getNew("Prueba2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"))
        news.add(New.getNew("Prueba3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"))
        news.add(New.getNew("Prueba4", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"))
        news.add(New.getNew("Prueba5", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"))

        newsAdapter = NewsAdapter(news.toList())

        binding.newsRecyclerView.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL,false)
        binding.newsRecyclerView.adapter = newsAdapter
        binding.newsRecyclerView.addItemDecoration(ItemDecorator(ITEM_SPACING))

        binding.addNewsButton.apply {
            visibility = if(Constants.currentUser.Administrator == 1) {
                View.VISIBLE
            } else {
                View.GONE
            }

            setOnClickListener {
                findNavController().navigate(R.id.action_news_fragment_to_add_news_fragment)
            }
        }
    }

    override fun subscribe() {
        newsViewModel.mutableNews.observe(this, Observer {
            newsAdapter = NewsAdapter(it)

            binding.newsRecyclerView.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL,false)
            binding.newsRecyclerView.adapter = newsAdapter
            binding.newsRecyclerView.addItemDecoration(ItemDecorator(ITEM_SPACING))
        })
    }
}