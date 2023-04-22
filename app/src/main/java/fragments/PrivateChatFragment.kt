package fragments

import adapters.PrivateMessageAdapter
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentPrivateChatBinding
import dialogs.InformationDialogFragment
import repositories.MessagesRepository
import utils.Constants
import utils.ItemDecorator
import viewModels.MessagesViewModel

class PrivateChatFragment: BaseFragment() {

    private lateinit var binding: FragmentPrivateChatBinding
    private lateinit var messagesViewModel: MessagesViewModel
    private lateinit var adapter: PrivateMessageAdapter

    private var contactId = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrivateChatBinding.inflate(layoutInflater)
        arguments?.let {
            contactId = it.getInt(Constants.CONTACT_ID)
            if(it.getString(Constants.CONTACT_INFO_AVATAR).isNullOrBlank()) {
                binding.contactImage.setImageDrawable(resources.getDrawable(R.drawable.user_default))
            } else {
                binding.contactImage.setImageURI(Uri.parse(it.getString(Constants.CONTACT_INFO_AVATAR)))
            }
            binding.userNameText.text = it.getString(Constants.CONTACT_INFO_NAME)
        }
        return binding.root
    }

    override fun initializeView() {
        messagesViewModel = MessagesViewModel(MessagesRepository(), baseViewModel)
        messagesViewModel.getPrivateMessages(contactId)

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                messagesViewModel.getPrivateMessages(contactId)
                mainHandler.postDelayed(this, 1000)
            }
        })

        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            messagesRecyclerView.apply {
                addItemDecoration(ItemDecorator(10))
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }

            sendButton.setOnClickListener {
                if(!messageInput.text.isNullOrEmpty()) {
                    messagesViewModel.sendPrivateMessage(messageInput.text.toString(), contactId)
                    messageInput.setText("")
                } else {
                    showInformationDialog(R.string.text_required, true)
                }
            }
        }
    }

    override fun subscribe() {
        messagesViewModel.mutableMessages.observe(this, Observer {
            adapter = PrivateMessageAdapter(it)
            binding.messagesRecyclerView.adapter = adapter
        })

        messagesViewModel.mutableMessageSent.observe(this, Observer {
            if(it) {
                messagesViewModel.getPrivateMessages(contactId)
            }
        })
    }
}