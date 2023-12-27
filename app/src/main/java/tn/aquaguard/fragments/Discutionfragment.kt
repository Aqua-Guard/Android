import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import okhttp3.RequestBody
import tn.aquaguard.adapters.DiscutionAdapter
import tn.aquaguard.adapters.ReclamationAdapter
import tn.aquaguard.databinding.ActivityDiscutionBinding
import tn.aquaguard.viewmodel.DiscutionViewModel

class Discutionfragment : BottomSheetDialogFragment() {
    private lateinit var binding: ActivityDiscutionBinding
    private val viewModel: DiscutionViewModel by viewModels()

    private lateinit var reclamationIdreq: RequestBody

    companion object {
        private const val RECLAMATION_ID_KEY = "reclamation_id"
        private const val RECLAMATION_NAME_KEY= "reclamation_name"
        fun newInstance(reclamationId: String,reclamationname :String): Discutionfragment {
            val fragment = Discutionfragment()
            val args = Bundle()
            args.putString(RECLAMATION_NAME_KEY,reclamationname)
            args.putString(RECLAMATION_ID_KEY, reclamationId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityDiscutionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val reclamationname =arguments?.getString(RECLAMATION_NAME_KEY)
        val reclamationId = arguments?.getString(RECLAMATION_ID_KEY)

        binding.recyclerViewMessages.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMessages.adapter = ReclamationAdapter(emptyList())

binding.buttonSendMessage.setOnClickListener {
    val message = binding.editTextMessage.text.toString()
    if (message.isEmpty()){
        showSnackbar("You should type a message to send it ")
    }
    else {

        viewModel.sendmessage(reclamationId!!, message)
        binding.editTextMessage.setText("")
    }

}

        viewModel.getallmessagesofthisreclamation(reclamationId!!)
            .observe(viewLifecycleOwner) { discutionList ->
                println("Reclamation API: $discutionList")
                binding.recyclerViewMessages.adapter = DiscutionAdapter(discutionList,viewModel)
            }

        if (!reclamationId.isNullOrEmpty()) {
            binding.reclamationcontext.text = reclamationname
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}
