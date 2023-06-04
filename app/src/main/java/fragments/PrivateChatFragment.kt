package fragments

import adapters.PrivateMessageAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentPrivateChatBinding
import com.squareup.picasso.Picasso
import models.User
import repositories.LoginRepository
import repositories.MessagesRepository
import utils.Constants
import utils.ItemDecorator
import viewModels.LoginViewModel
import viewModels.MessagesViewModel

class PrivateChatFragment: BaseFragment() {

    private lateinit var binding: FragmentPrivateChatBinding
    private lateinit var messagesViewModel: MessagesViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var adapter: PrivateMessageAdapter
    private lateinit var inputMethodManager: InputMethodManager
    private lateinit var contact: User
    private lateinit var mainHandler: Handler
    private lateinit var runnable: Runnable

    private var contactId = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrivateChatBinding.inflate(layoutInflater)
        arguments?.let {
            contactId = it.getInt(Constants.CONTACT_ID)
            if(!it.getString(Constants.CONTACT_INFO_AVATAR).isNullOrBlank()) {
                Picasso.with(context)
                    .load(it.getString(Constants.CONTACT_INFO_AVATAR))
                    .placeholder(R.drawable.user_default)
                    .resize(binding.contactImage.layoutParams.width, binding.contactImage.layoutParams.height)
                    .into(binding.contactImage)
            }
            binding.userNameText.text = it.getString(Constants.CONTACT_INFO_NAME)
        }
        return binding.root
    }

    override fun initializeView() {
        loginViewModel = LoginViewModel(LoginRepository(), baseViewModel)
        loginViewModel.getUserById(contactId)
        messagesViewModel = MessagesViewModel(MessagesRepository(),LoginRepository(), baseViewModel)

        inputMethodManager = activity?.let {
            it.getSystemService(Context.INPUT_METHOD_SERVICE)
        } as InputMethodManager

        mainHandler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                messagesViewModel.getPrivateMessages(contactId)
                mainHandler.postDelayed(this, 1000)
            }
        }
        mainHandler.post(runnable)

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
                    if(contactId == 100) {
                        showInformationDialog(R.string.cant_send_to_contact, true);
                    } else {
                        messagesViewModel.sendPrivateMessage(messageInput.text.toString(), contactId)
                    }
                    messageInput.setText("")
                    inputMethodManager.hideSoftInputFromWindow(root.windowToken, 0)
                } else {
                    showInformationDialog(R.string.text_required, true)
                }
            }

            userNameText.setOnClickListener {
                goToInfo()
            }
            contactImage.setOnClickListener {
                goToInfo()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun subscribe() {
        messagesViewModel.mutableMessages.observe(this, Observer {
            if(this::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            }
            adapter = PrivateMessageAdapter(it)
            binding.messagesRecyclerView.adapter = adapter
        })

        messagesViewModel.mutableMessageSent.observe(this, Observer {
            if(it) {
                messagesViewModel.getPrivateMessages(contactId)
            }
        })

        loginViewModel.currentUser.observe(this, Observer {
            contact = it
        })
    }

    private fun goToInfo() {
        if(this::contact.isInitialized) {
            val navBundle = Bundle()
            navBundle.putSerializable(Constants.CONTACT_INFO_NAME, contact.Name)
            navBundle.putSerializable(Constants.CONTACT_INFO_AVATAR, contact.AvatarImage)
            navBundle.putSerializable(Constants.CONTACT_INFO_MAIL, contact.Mail)
            navBundle.putSerializable(Constants.CONTACT_INFO_DESCRIPTION, contact.Description)
            navBundle.putSerializable(Constants.CONTACT_ID, contactId)
            findNavController().navigate(R.id.action_private_chat_fragment_to_contact_info_fragment, navBundle)
        }
    }

    override fun onDestroy() {
        mainHandler.removeCallbacks(runnable)
        super.onDestroy()
    }
}