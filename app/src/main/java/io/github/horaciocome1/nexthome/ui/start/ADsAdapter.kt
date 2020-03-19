package io.github.horaciocome1.nexthome.ui.start

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.horaciocome1.nexthome.data.ad.AD
import io.github.horaciocome1.nexthome.databinding.ItemAdBinding

class ADsAdapter(
    private val onClickListener: (view: View, adId: String) -> Unit
) : RecyclerView.Adapter<ADsAdapter.MyViewHolder>() {

    var ads = mutableListOf<AD>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private lateinit var binding: ItemAdBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemAdBinding.inflate(inflater, parent, false)
        return MyViewHolder(view = binding.root)
    }

    override fun getItemCount() = ads.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ad = ads[position]
        binding.nomeProprietarioTextView.text = ad.proprietario.name
        binding.descriptionTextView.text = ad.buildADDescription()
        binding.zonaTextView.text = ad.zona
        binding.openADButton.text = ad.price.toString()
        binding.openADButton.setOnClickListener { onClickListener(it, ad.id) }

    }

    private fun AD.buildADDescription(): String {
        var description = "${quartos}x Quartos; "
        if (suites > 0)
            description += "${suites}x Suites; "
        if (wcs > 0)
            description += "${wcs}x WCs; "
        description += "\n"
        if (hasAgua)
            description += "Com água canalizada; "
        if (hasLuz)
            description += "Tem luz; "
        if (isMobilada)
            description += "Está mobilada"
        return description
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
