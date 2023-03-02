package dialogs

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.javier.channelupm.databinding.DialogErrorBinding

class ErrorDialogFragment(
    private val activity: Activity
): DialogFragment() {

    private lateinit var binding: DialogErrorBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogErrorBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(activity)
        builder.setView(binding.root)
        val dialog = builder.create()

        binding.errorConfirmationButton.setOnClickListener {
            dialog.dismiss()
        }

        return dialog
    }
}