package fragments

import adapters.ParticipantsAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentEditGroupPermissionsBinding
import repositories.LoginRepository
import repositories.MessagesRepository
import utils.Constants
import viewModels.MessagesViewModel

class EditGroupPermissionsFragment: BaseFragment() {

    private lateinit var binding: FragmentEditGroupPermissionsBinding
    private lateinit var messagesViewModel: MessagesViewModel

    private var groupId = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditGroupPermissionsBinding.inflate(layoutInflater)

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
            participantsRecyclerView.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun subscribe() {
        messagesViewModel.mutableGroupParticipants.observe(this, Observer { participants->
            binding.participantsRecyclerView.adapter = ParticipantsAdapter(participants){ user->
                when(user.UserId) {
                    Constants.currentUser.UserId -> {
                        showInformationDialog(R.string.cant_edit_own_permissions, true)
                    }
                    else ->{
                        if(user.Administrator == 1) {
                            messagesViewModel.updateUserGroup(groupId, user.UserId, 0)
                        } else {
                            messagesViewModel.updateUserGroup(groupId, user.UserId, 1)
                        }
                    }
                }
            }
        })
    }
}