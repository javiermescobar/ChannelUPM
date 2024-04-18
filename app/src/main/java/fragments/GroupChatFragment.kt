package fragments

import adapters.GroupMessageAdapter
import android.annotation.SuppressLint
import android.content.Context
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
import com.javier.channelupm.databinding.FragmentGroupChatBinding
import com.squareup.picasso.Picasso
import models.GroupMessage
import repositories.MessagesRepository
import utils.Constants
import utils.DateUtils
import utils.ItemDecorator
import viewModels.MessagesViewModel

class GroupChatFragment: BaseFragment() {

    private lateinit var binding: FragmentGroupChatBinding
    private lateinit var messagesViewModel: MessagesViewModel
    private lateinit var inputMethodManager: InputMethodManager
    private lateinit var messageAdapter: GroupMessageAdapter
    private lateinit var mainHandler: Handler
    private lateinit var runnable: Runnable


    private var groupId = -1
    private var groupName = ""
    private var groupAvatar = ""
    private var currentMessages = listOf<GroupMessage>()
    private var doneFirstScroll = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupChatBinding.inflate(layoutInflater)
        arguments?.let {
            groupId = it.getInt(Constants.GROUP_ID)
            groupName = it.getString(Constants.GROUP_NAME, "")
            groupAvatar = it.getString(Constants.GROUP_AVATAR, "")
        }
        return binding.root
    }

    override fun initializeView() {
        messagesViewModel = MessagesViewModel(MessagesRepository(), baseViewModel)
        messagesViewModel.getGroupMessages(groupId)
        messagesViewModel.getGroupParticipants(groupId)

        inputMethodManager = activity?.let {
            it.getSystemService(Context.INPUT_METHOD_SERVICE)
        } as InputMethodManager

        mainHandler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                messagesViewModel.getGroupMessages(groupId)
                mainHandler.postDelayed(this, 1000)
            }
        }
        mainHandler.post(runnable)

        binding.apply {
            groupNameText.text = groupName

            if(groupAvatar.isNotEmpty()) {
                Picasso.with(root.context)
                    .load(groupAvatar)
                    .resize(groupImage.layoutParams.width, groupImage.layoutParams.height)
                    .placeholder(R.drawable.user_default)
                    .into(groupImage)
            }

            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            groupNameText.setOnClickListener {
                navigateToInfo()
            }
            groupImage.setOnClickListener {
                navigateToInfo()
            }

            messagesRecyclerView.apply {
                addItemDecoration(ItemDecorator(10))
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                messageAdapter = GroupMessageAdapter(emptyList())
                adapter = messageAdapter
            }

            sendButton.setOnClickListener {
                if(messageInput.text.isNullOrEmpty()) {
                    showInformationDialog(R.string.text_required, true)
                } else {
                    messagesViewModel.sendGroupMessage(groupId, messageInput.text.toString())
                    messageInput.setText("")
                    inputMethodManager.hideSoftInputFromWindow(root.windowToken, 0)
                }
            }
        }
    }

    private fun navigateToInfo() {
        val navBundle = Bundle()
        navBundle.putSerializable(Constants.GROUP_ID, groupId)

        findNavController().navigate(R.id.action_group_chat_fragment_to_group_info_fragment, navBundle)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun subscribe() {
        messagesViewModel.mutableGroupMessages.observe(this, Observer {
            if(it.size != currentMessages.size) {
                messageAdapter.updateItems(processMessages(it))
                messageAdapter.notifyDataSetChanged()
                currentMessages = it
            }

            binding.messagesRecyclerView.adapter?.let {adapter ->
                if(adapter.itemCount != 0 && !doneFirstScroll) {
                    doneFirstScroll = true
                    binding.messagesRecyclerView.layoutManager?.scrollToPosition(adapter.itemCount - 1)
                }
            }
        })

        messagesViewModel.mutableMessageSent.observe(this, Observer {
            if(it) {
                messagesViewModel.getGroupMessages(groupId)
            }
        })
    }

    override fun onDestroy() {
        mainHandler.removeCallbacks(runnable)
        super.onDestroy()
    }

    private fun processMessages(items: List<GroupMessage>): List<GroupMessage> {
        if(items.isEmpty()) return emptyList()

        var mutableList = mutableListOf<GroupMessage>()
        var lastPrivateMessage = GroupMessage(-1, "", DateUtils.getDateString(items[0].SendDate), -1, -1, "", "")
        mutableList.add(lastPrivateMessage)

        items.forEach {
            if(mutableList.size > 1 && lastPrivateMessage.GroupChatId != -1 && !DateUtils.haveSameDates(lastPrivateMessage.SendDate, it.SendDate)) {
                mutableList.add(GroupMessage(-1, "", DateUtils.getDateString(it.SendDate), -1, -1, "", ""))
            }
            mutableList.add(it)
            lastPrivateMessage = it
        }

        return mutableList.toList()
    }
}