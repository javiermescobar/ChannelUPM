package fragments

import adapters.ContactMessageAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentGroupsBinding
import models.GroupChat
import models.MessageContact
import repositories.LoginRepository
import repositories.MessagesRepository
import utils.Constants
import utils.ItemDecorator
import viewModels.MessagesViewModel

class GroupsFragment: BaseFragment() {

    private lateinit var binding: FragmentGroupsBinding
    private lateinit var messagesViewModel: MessagesViewModel

    private var groups = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initializeView() {
        messagesViewModel = MessagesViewModel(MessagesRepository(), baseViewModel)
        messagesViewModel.getUserGroups()

        binding.apply {

            binding.searchInput.addTextChangedListener {
                it?.let {
                    if(it.isNotEmpty()) {
                        messagesViewModel.searchGroups(it.toString())
                    } else {
                        messagesViewModel.getUserGroups()
                    }
                }
            }

            addGroupButton.setOnClickListener {
                findNavController().navigate(R.id.action_groups_fragment_to_add_group_fragment)
            }

            groupsRecyclerView.apply {
                addItemDecoration(ItemDecorator(0))
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun subscribe() {
        messagesViewModel.mutableGroups.observe(this, Observer { groups ->
            this.groups = groups.size
            if(this.groups == 0) {
                binding.noGroupsText.visibility = View.VISIBLE
            }
            groups.forEach { group -> messagesViewModel.getLastGroupMessage(group) }
        })

        messagesViewModel.mutableMessageGroup.observe(this, Observer { messageGroupHashMap ->
            if(messageGroupHashMap.size == groups) {
                val mutableMessageGroups = mutableListOf<MessageContact>()
                messageGroupHashMap.forEach { (group, message) ->
                    mutableMessageGroups.add(MessageContact(group.GroupChatId, group.AvatarImage, group.GroupName, message))
                }

                binding.groupsRecyclerView.adapter = ContactMessageAdapter(
                    mutableMessageGroups.toList().sortedBy { group -> group.Name }) {
                    val navBundle = Bundle()
                    navBundle.putSerializable(Constants.GROUP_ID, it.UserId)
                    navBundle.putSerializable(Constants.GROUP_NAME, it.Name)
                    navBundle.putSerializable(Constants.GROUP_AVATAR, it.AvatarImage)

                    findNavController().navigate(R.id.action_groups_fragment_to_group_chat_fragment, navBundle)
                }
            }
        })
    }
}