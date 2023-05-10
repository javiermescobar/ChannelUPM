package fragments

import adapters.ParticipantsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentGroupParticipantsBinding
import repositories.LoginRepository
import repositories.MessagesRepository
import utils.Constants
import utils.ItemDecorator
import viewModels.MessagesViewModel

class GroupParticipantsFragment: BaseFragment() {

    private lateinit var binding: FragmentGroupParticipantsBinding
    private lateinit var messagesViewModel: MessagesViewModel

    private var groupId = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupParticipantsBinding.inflate(layoutInflater)

        arguments?.let {
            groupId = it.getInt(Constants.GROUP_ID)
        }

        return binding.root
    }

    override fun initializeView() {
        messagesViewModel = MessagesViewModel(MessagesRepository(), LoginRepository(), baseViewModel)
        messagesViewModel.getGroupParticipants(groupId)

        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            participantsRecyclerView.apply {
                layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(ItemDecorator(0))
            }
        }
    }

    override fun subscribe() {
        messagesViewModel.mutableGroupParticipants.observe(this, Observer {
            binding.participantsRecyclerView.adapter = ParticipantsAdapter(it){
                val navBundle = Bundle()
                navBundle.putSerializable(Constants.CONTACT_ID, it.UserId)
                findNavController().navigate(R.id.action_group_participants_fragment_to_contact_info_fragment, navBundle)
            }
        })
    }
}