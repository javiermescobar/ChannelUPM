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
import viewModels.MessagesViewModel

class AddGroupFragment: BaseFragment() {

    private lateinit var binding: FragmentAddGroupBinding
    private lateinit var messagesViewModel: MessagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddGroupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initializeView() {
        messagesViewModel = MessagesViewModel(MessagesRepository(), LoginRepository(), baseViewModel)

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
                    messagesViewModel.createGroupChat(titleInput.text.toString(), descriptionInput.text.toString(), urlInput.text.toString())
                }
            }
        }
    }

    override fun subscribe() {
        baseViewModel.appState.observe(this, Observer {
            if(it == AppState.SUCCESS) {
                showInformationDialog(R.string.group_added, false)
                findNavController().navigate(R.id.action_add_group_fragment_to_groups_fragment)
            }
        })
    }
}