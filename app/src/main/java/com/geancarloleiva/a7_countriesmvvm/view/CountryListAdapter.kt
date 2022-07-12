package com.geancarloleiva.a7_countriesmvvm.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geancarloleiva.a7_countriesmvvm.Country
import com.geancarloleiva.a7_countriesmvvm.R
import com.geancarloleiva.a7_countriesmvvm.util.getProgressDrawable
import com.geancarloleiva.a7_countriesmvvm.util.loadImage
import java.util.ArrayList

class CountryListAdapter(private val context: Context,
                         private val lstCountry: ArrayList<Country>,
                         val itemClick: (Country) -> Unit)
    : RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries: List<Country>){
        lstCountry.clear()
        lstCountry.addAll(newCountries)
        notifyDataSetChanged()
    }

    inner class CountryViewHolder(
        itemView: View
        // , itemClick: (Country) -> Unit
    )
        : RecyclerView.ViewHolder(itemView){

        private val iviCountryFlag: ImageView = itemView.findViewById(R.id.iviCountryFlag)
        private val lblCountryName: TextView = itemView.findViewById(R.id.lblCountryName)
        private val lblCountryCapital: TextView = itemView.findViewById(R.id.lblCountryCapital)
        //Variable to manage the progressBar to load the images
        private val progressDrawable = getProgressDrawable(context)

        fun bind(country: Country){
            //Loading the image with an extension class (in Util.kt)
            iviCountryFlag.loadImage(country.flag, progressDrawable)
            lblCountryName.text = country.name
            lblCountryCapital.text = country.capital

            itemView.setOnClickListener{itemClick(country)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(itemView/*, itemClick*/)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(lstCountry[position])
    }

    override fun getItemCount(): Int {
        return lstCountry.size
    }
}