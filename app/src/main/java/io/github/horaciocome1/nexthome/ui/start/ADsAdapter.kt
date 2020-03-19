package io.github.horaciocome1.nexthome.ui.start

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.horaciocome1.nexthome.data.ad.AD
import io.github.horaciocome1.nexthome.databinding.ItemAdBinding

class ADsAdapter(
    private val onClickListener: View.OnClickListener
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
        var description = "${ad.quartos}x Quartos; "
        if (ad.suites > 0)
            description += "${ad.suites}x Suites; "
        if (ad.wcs > 0)
            description += "${ad.wcs}x WCs; "
        description += "\n"
        if (ad.hasAgua)
            description += "Com água canalizada; "
        if (ad.hasLuz)
            description += "Tem luz; "
        if (ad.isMobilada)
            description += "Está mobilada"
        binding.nomeProprietarioTextView.text = ad.proprietario.name
        binding.descriptionTextView.text = description
        binding.zonaTextView.text = ad.zona
        binding.openADButton.text = ad.price.toString()
        binding.openADButton.setOnClickListener(onClickListener)

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
