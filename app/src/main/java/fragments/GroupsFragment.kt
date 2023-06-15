package fragments

import adapters.GroupsAdapter
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
import repositories.LoginRepository
import repositories.MessagesRepository
import utils.Constants
import utils.ItemDecorator
import viewModels.MessagesViewModel

class GroupsFragment: BaseFragment() {

    private lateinit var binding: FragmentGroupsBinding
    private lateinit var adapter: GroupsAdapter
    private lateinit var messagesViewModel: MessagesViewModel
    private lateinit var currentGroups: List<GroupChat>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initializeView() {
        messagesViewModel = MessagesViewModel(MessagesRepository(), LoginRepository(), baseViewModel)
        messagesViewModel.getUserGropus()

        binding.apply {

            binding.searchInput.addTextChangedListener {
                it?.let {
                    if(it.isNotEmpty()) {
                        messagesViewModel.searchGroups(it.toString())
                    } else {
                        messagesViewModel.getUserGropus()
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
            currentGroups = groups
            setAdapter(currentGroups)
        })

        messagesViewModel.mutableSearchedGroups.observe(this, Observer { searchedGroups ->
            currentGroups = searchedGroups
            setAdapter(currentGroups)
        })
    }

    private fun setAdapter(groups: List<GroupChat>) {
        adapter = GroupsAdapter(groups.sortedBy { group -> group.GroupName }){
            val navBundle = Bundle()
            navBundle.putSerializable(Constants.GROUP_ID, it.GroupChatId)
            navBundle.putSerializable(Constants.GROUP_NAME, it.GroupName)
            navBundle.putSerializable(Constants.GROUP_AVATAR, it.AvatarImage)

            findNavController().navigate(R.id.action_groups_fragment_to_group_chat_fragment, navBundle)
        }
        binding.groupsRecyclerView.adapter = adapter

        if(groups.isEmpty()) {
            binding.noGroupsText.visibility = View.VISIBLE
        }
    }
}