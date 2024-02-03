import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import okhttp3.RequestBody
import tn.aquaguard.R
import tn.aquaguard.adapters.DiscutionAdapter
import tn.aquaguard.adapters.ReclamationAdapter
import tn.aquaguard.databinding.ActivityDiscutionBinding
import tn.aquaguard.databinding.FragmentReclamationBinding
import tn.aquaguard.ui.DetailActualite
import tn.aquaguard.viewmodel.DiscutionViewModel

class Discutionfragment : BottomSheetDialogFragment() {
    private lateinit var binding: ActivityDiscutionBinding
    private val viewModel: DiscutionViewModel by viewModels()
    private lateinit var sendcomment: MediaPlayer

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
       // sendcomment = MediaPlayer.create(this, R.raw.sendmessagesound)
        binding.recyclerViewMessages.layoutManager = LinearLayoutManager(requireContext())
        viewModel.discutionList.observe(viewLifecycleOwner) { discutionList ->
            println("Updated Discution List: $discutionList")
            binding.recyclerViewMessages.adapter = DiscutionAdapter(discutionList, viewModel)
        }

binding.buttonSendMessage.setOnClickListener {
    val message = binding.editTextMessage.text.toString()
    if (message.isEmpty()){
        showSnackbar("You should type a message to send it ")
    }
    else {

        viewModel.sendmessage(reclamationId!!, message)
        binding.editTextMessage.setText("")
        replaceFragment(ReclamationFragment())
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
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}
