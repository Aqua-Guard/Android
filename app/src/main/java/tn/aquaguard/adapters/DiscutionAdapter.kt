package tn.aquaguard.adapters





import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.databinding.DiscutionMessageItemBinding
import tn.aquaguard.models.Discution
import tn.aquaguard.viewHolders.DiscutionViewHolder
import tn.aquaguard.viewmodel.DiscutionViewModel



class DiscutionAdapter(private val DiscutionList: List<Discution>,private val viewModel: DiscutionViewModel) : RecyclerView.Adapter<DiscutionViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscutionViewHolder {

        val v = 0
        val binding =
            DiscutionMessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.bottomHalf.visibility = View.GONE
        binding.topHalf.setOnClickListener {
            binding.bottomHalf.visibility = View.VISIBLE
            val handler = android.os.Handler(Looper.getMainLooper())
            handler.postDelayed({
                binding.bottomHalf.visibility = View.GONE
            }, 3000) // 2000 milliseconds (2 seconds)
        }
//        binding.topHalf.


        return DiscutionViewHolder(parent.context, binding,viewModel,this)
    }

    override fun getItemCount() = DiscutionList.size

    override fun onBindViewHolder(holder: DiscutionViewHolder, position: Int) {
        DiscutionList[position]?.let { holder.setData(it) }
    }


}