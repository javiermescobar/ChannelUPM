package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentGroupInfoBinding
import com.squareup.picasso.Picasso
import models.UserInGroup
import repositories.LoginRepository
import repositories.MessagesRepository
import utils.Constants
import viewModels.MessagesViewModel

class GroupInfoFragment: BaseFragment() {

    private lateinit var binding: FragmentGroupInfoBinding
    private lateinit var messagesViewModel: MessagesViewModel
    private lateinit var participants: List<UserInGroup>

    private var groupId = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupInfoBinding.inflate(layoutInflater)

        arguments?.let {
            groupId = it.getInt(Constants.GROUP_ID)
        }

        return binding.root
    }

    override fun initializeView() {
        messagesViewModel = MessagesViewModel(MessagesRepository(), LoginRepository(), baseViewModel)
        messagesViewModel.getGroupById(groupId)
        messagesViewModel.getGroupParticipants(groupId)

        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            groupParticipantsButton.setOnClickListener {
                val navBundle = Bundle()
                navBundle.putSerializable(Constants.GROUP_ID, groupId)
                findNavController().navigate(R.id.action_group_info_fragment_to_group_participants_fragment, navBundle)
            }

            exitGroupButton.setOnClickListener {
                if(participants.isEmpty()) {
                    showInformationDialog(R.string.cant_exit_not_enough_admin, true)
                } else {
                    messagesViewModel.removeUserGroup(groupId)
                }
            }
        }
    }

    override fun subscribe() {
        messagesViewModel.mutableCurrentGroup.observe(this, Observer { groupChat ->
            binding.apply {
                if(groupChat.AvatarImage.isNotEmpty()) {
                    Picasso.with(root.context)
                        .load(groupChat.AvatarImage)
                        .resize(groupImage.layoutParams.width, groupImage.layoutParams.height)
                        .placeholder(R.drawable.user_default)
                        .into(groupImage)
                }

                groupNameText.text = groupChat.GroupName
                groupDescritionText.keyListener = null
                groupDescritionText.setText(if(groupChat.Description.isEmpty()) {
                    resources.getText(R.string.contact_info_no_description)
                } else {
                    groupChat.Description
                })
            }
        })

        messagesViewModel.mutableGroupParticipants.observe(this, Observer {
            participants = it.filter { participant -> participant.Administrator == 1
                    && participant.UserId != Constants.currentUser.UserId}
            binding.exitGroupButton.isEnabled = true
        })

        messagesViewModel.mutableUserInGroupRemoved.observe(this, Observer {
            if(it) {
                findNavController().navigate(R.id.action_group_info_fragment_to_groups_fragment)
                showInformationDialog(R.string.exited_from_group, false)
            }
        })
    }
}