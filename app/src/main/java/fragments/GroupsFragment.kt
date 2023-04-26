package fragments

import adapters.GroupsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.javier.channelupm.databinding.FragmentsGroupsBinding
import repositories.LoginRepository
import repositories.MessagesRepository
import utils.ItemDecorator
import viewModels.MessagesViewModel

class GroupsFragment: BaseFragment() {

    private lateinit var binding: FragmentsGroupsBinding
    private lateinit var adapter: GroupsAdapter
    private lateinit var messagesViewModel: MessagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentsGroupsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initializeView() {
        messagesViewModel = MessagesViewModel(MessagesRepository(), LoginRepository(), baseViewModel)
        messagesViewModel.getUserGropus()

        binding.apply {
            addGroupButton.setOnClickListener {
                //go to create group fragment
            }

            groupsRecyclerView.apply {
                addItemDecoration(ItemDecorator(0))
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun subscribe() {
        messagesViewModel.mutableGroups.observe(this, Observer {
            adapter = GroupsAdapter(it){
                //go to group
            }
            binding.groupsRecyclerView.adapter = adapter
        })
    }
}