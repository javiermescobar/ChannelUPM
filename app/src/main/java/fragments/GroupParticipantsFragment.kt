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
        messagesViewModel.mutableGroupParticipants.observe(this, Observer { participants ->
            binding.apply {
                participantsRecyclerView.adapter = ParticipantsAdapter(participants){ contact ->
                    val navBundle = Bundle()
                    navBundle.putSerializable(Constants.CONTACT_ID, contact.UserId)
                    findNavController().navigate(R.id.action_group_participants_fragment_to_contact_info_fragment, navBundle)
                }

                addParticipantsButton.setOnClickListener {
                    if(participants.any { participant -> participant.UserId == Constants.currentUser.UserId &&
                                    participant.Administrator == 1 }) {
                        val navBundle = Bundle()
                        navBundle.putSerializable(Constants.GROUP_ID, groupId)
                        findNavController().navigate(R.id.action_group_participants_fragment_to_add_participant_fragment, navBundle)
                    } else {
                        showInformationDialog(R.string.need_to_be_admin_add_participants, true)
                    }
                }

                editAdministratorsButton.setOnClickListener {
                    if(participants.any { participant -> participant.UserId == Constants.currentUser.UserId &&
                                    participant.Administrator == 1 }) {
                        val navBundle = Bundle()
                        navBundle.putSerializable(Constants.GROUP_ID, groupId)
                        findNavController().navigate(R.id.action_group_participants_fragment_to_edit_group_permissions_fragment, navBundle)
                    } else {
                        showInformationDialog(R.string.need_to_be_admin_edit_participants, true)
                    }
                }

                editInfoButton.setOnClickListener {
                    if(participants.any { participant -> participant.UserId == Constants.currentUser.UserId &&
                                    participant.Administrator == 1 }) {
                        val navBundle = Bundle()
                        navBundle.putSerializable(Constants.GROUP_ID, groupId)
                        findNavController().navigate(R.id.action_group_participants_fragment_to_add_group_fragment, navBundle)
                    } else {
                        showInformationDialog(R.string.need_to_be_admin_edit_info, true)
                    }
                }
            }
        })
    }
}