package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.javier.channelupm.databinding.FragmentsGroupsBinding

class GroupsFragment: Fragment() {

    private lateinit var binding: FragmentsGroupsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentsGroupsBinding.inflate(layoutInflater)
        return binding.root
    }
}