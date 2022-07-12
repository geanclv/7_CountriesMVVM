package com.geancarloleiva.a7_countriesmvvm.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geancarloleiva.a7_countriesmvvm.Country
import com.geancarloleiva.a7_countriesmvvm.R
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
        itemView: View,
        itemClick: (Country) -> Unit)
        : RecyclerView.ViewHolder(itemView){

        private val lblCountryName: TextView = itemView.findViewById(R.id.lblCountryName)

        fun bind(country: Country){
            lblCountryName.text = country.name

            itemView.setOnClickListener{itemClick(country)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(itemView, itemClick)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(lstCountry[position])
    }

    override fun getItemCount(): Int {
        return lstCountry.size
    }
}