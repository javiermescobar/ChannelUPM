package fragments

import adapters.NewsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentNewsBinding
import models.New
import utils.ItemDecorator

class NewsFragment : BaseFragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var news: MutableList<New>
    private lateinit var newsAdapter: NewsAdapter

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
        news = mutableListOf()
        news.add(New.getNew("Prueba1", "Un NFT no es una imagen… hacer imagenes bonitas es facil, un NFT es una “llave” para acceder a algo, el crear ese “algo” es lo dificil. Puede ser una comunidad, un juego, metaversos, mas NFTs, una experiencia… Una vez se entiende esto, posibilidades son infinitas!!"))
        news.add(New.getNew("Prueba2", "Un NFT no es una imagen… hacer imagenes bonitas es facil, un NFT es una “llave” para acceder a algo, el crear ese “algo” es lo dificil. Puede ser una comunidad, un juego, metaversos, mas NFTs, una experiencia… Una vez se entiende esto, posibilidades son infinitas!!"))
        news.add(New.getNew("Prueba3", "Un NFT no es una imagen… hacer imagenes bonitas es facil, un NFT es una “llave” para acceder a algo, el crear ese “algo” es lo dificil. Puede ser una comunidad, un juego, metaversos, mas NFTs, una experiencia… Una vez se entiende esto, posibilidades son infinitas!!"))
        news.add(New.getNew("Prueba4", "Un NFT no es una imagen… hacer imagenes bonitas es facil, un NFT es una “llave” para acceder a algo, el crear ese “algo” es lo dificil. Puede ser una comunidad, un juego, metaversos, mas NFTs, una experiencia… Una vez se entiende esto, posibilidades son infinitas!!"))
        news.add(New.getNew("Prueba5", "Un NFT no es una imagen… hacer imagenes bonitas es facil, un NFT es una “llave” para acceder a algo, el crear ese “algo” es lo dificil. Puede ser una comunidad, un juego, metaversos, mas NFTs, una experiencia… Una vez se entiende esto, posibilidades son infinitas!!"))

        newsAdapter = NewsAdapter(news.toList())

        binding.newsRecyclerView.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL,false)
        binding.newsRecyclerView.adapter = newsAdapter
        binding.newsRecyclerView.addItemDecoration(ItemDecorator(ITEM_SPACING))
    }
}