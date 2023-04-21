package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.javier.channelupm.databinding.FragmentPrivateChatBinding

class PrivateChatFragment: BaseFragment() {

    private lateinit var binding: FragmentPrivateChatBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrivateChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initializeView() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}