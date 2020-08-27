package com.app.countries.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.countries.R
import com.app.countries.data.model.Flag
import com.app.countries.util.ItemClickListener
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.row_country.view.*

/**
 * Adapter class to populate data of Countries
 */
class CountriesAdapter(
    private val countriesList: List<Flag>,
    private val itemClickListener: ItemClickListener<Flag>
) :
    RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {


    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(flag: Flag, itemClickListener: ItemClickListener<Flag>) {
            itemView.countryName.text = flag.country?.name
            itemView.countryCapital.text = flag.country?.capital

            GlideToVectorYou
                .init()
                .with(itemView.context)
                .load(Uri.parse(flag.svgFile), itemView.imageView)

            itemView.setOnClickListener {
                itemClickListener.onClick(flag)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_country, parent, false)
        )
    }

    override fun getItemCount(): Int = countriesList.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countriesList[position], itemClickListener)
    }
}