package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentGroupChatBinding
import com.squareup.picasso.Picasso
import utils.Constants

class GroupChatFragment: BaseFragment() {

    private lateinit var binding: FragmentGroupChatBinding

    private var groupId = -1
    private var groupName = ""
    private var groupAvatar = ""

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

        binding.apply {
            groupNameText.text = groupName
            Picasso.with(root.context)
                .load(groupAvatar)
                .resize(groupImage.layoutParams.width, groupImage.layoutParams.height)
                .placeholder(R.drawable.user_default)
                .into(groupImage)

            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            groupNameText.setOnClickListener {
                navigateToInfo()
            }
            groupImage.setOnClickListener {
                navigateToInfo()
            }
        }
    }

    private fun navigateToInfo() {
        val navBundle = Bundle()
        navBundle.putSerializable(Constants.GROUP_ID, groupId)

        findNavController().navigate(R.id.action_group_chat_fragment_to_group_info_fragment, navBundle)
    }
}