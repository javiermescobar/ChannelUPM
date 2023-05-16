package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentAddGroupBinding
import com.squareup.picasso.Picasso
import repositories.LoginRepository
import repositories.MessagesRepository
import utils.AppState
import utils.Constants
import viewModels.MessagesViewModel

class AddGroupFragment: BaseFragment() {

    private lateinit var binding: FragmentAddGroupBinding
    private lateinit var messagesViewModel: MessagesViewModel

    private var groupId = -1
    private var editing = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddGroupBinding.inflate(layoutInflater)

        arguments?.let {
            groupId = it.getInt(Constants.GROUP_ID)
        }

        return binding.root
    }

    override fun initializeView() {
        messagesViewModel = MessagesViewModel(MessagesRepository(), LoginRepository(), baseViewModel)
        if(groupId != -1) {
            binding.titleText.text = resources.getText(R.string.group_edit)
            messagesViewModel.getGroupById(groupId)
        }

        binding.apply {

            urlInput.doAfterTextChanged {
                if(!it.isNullOrEmpty()) {
                    try {
                        Picasso.with(root.context)
                            .load(it.toString())
                            .placeholder(R.drawable.user_default)
                            .resize(200,200)
                            .centerCrop()
                            .into(groupImage)
                    } catch (e: Exception) {
                        groupImage.setImageResource(R.drawable.user_default)
                    }
                }
            }

            continueButton.setOnClickListener {
                if(titleInput.text.isNullOrEmpty() || descriptionInput.text.isNullOrEmpty()) {
                    showInformationDialog(R.string.enter_all_fields, true)
                } else {
                    editing = true
                    if(groupId != -1) {
                        messagesViewModel.updateGroupChat(groupId, titleInput.text.toString(),
                            descriptionInput.text.toString(), urlInput.text.toString())
                    } else {
                        messagesViewModel.createGroupChat(titleInput.text.toString(),
                            descriptionInput.text.toString(), urlInput.text.toString())
                    }
                }
            }
        }
    }

    override fun subscribe() {
        baseViewModel.appState.observe(this, Observer {
            if(it == AppState.SUCCESS && editing) {
                if(groupId != -1) {
                    showInformationDialog(R.string.group_edited, false)
                } else {
                    showInformationDialog(R.string.group_added, false)
                }
                findNavController().navigate(R.id.action_add_group_fragment_to_groups_fragment)
            }
        })
        messagesViewModel.mutableCurrentGroup.observe(this, Observer {
            binding.apply {
                titleInput.setText(it.GroupName)
                descriptionInput.setText(it.Description)
                urlInput.setText(it.AvatarImage)
            }
        })
    }
}