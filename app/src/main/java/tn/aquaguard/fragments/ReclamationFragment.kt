import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.R
import tn.aquaguard.adapters.ActualitesAdapter
import tn.aquaguard.adapters.ReclamationAdapter
import tn.aquaguard.databinding.FragmentHomeBinding
import tn.aquaguard.databinding.FragmentReclamationBinding
import tn.aquaguard.models.Reclamation
import tn.aquaguard.viewmodel.ActualiteViewModel
import tn.aquaguard.viewmodel.ReclamationViewModel

class ReclamationFragment : Fragment() {

    private val viewModel: ReclamationViewModel by viewModels()
    private lateinit var binding: FragmentReclamationBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentReclamationBinding.inflate(layoutInflater)
        binding.recyclerViewMessages.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMessages.adapter= ReclamationAdapter(emptyList())
        // Initialize your messageList with sample data



        viewModel.getReclamationsByUserId().observe(viewLifecycleOwner) {ReclamationList ->
            println("reclamation API :"+ReclamationList.toString())
            binding.recyclerViewMessages.adapter=ReclamationAdapter(ReclamationList)

binding.NoReclamation.visibility=View.GONE




            }
        return binding.root
    }



}