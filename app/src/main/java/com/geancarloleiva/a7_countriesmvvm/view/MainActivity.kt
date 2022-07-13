package com.geancarloleiva.a7_countriesmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.geancarloleiva.a7_countriesmvvm.Country
import com.geancarloleiva.a7_countriesmvvm.R
import com.geancarloleiva.a7_countriesmvvm.viewmodel.ListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ListViewModel
    private lateinit var countryAdapter: CountryListAdapter
    private lateinit var rviCountries: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initializing the ViewModel
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        //Initializing the adapter
        countryAdapter = CountryListAdapter(this, arrayListOf()) { country ->
            //itemClick events
            Toast.makeText(this, "You clicked on ${country.name}", Toast.LENGTH_SHORT).show()
        }

        //Giving the behaviour to the RecyclerView
        rviCountries = findViewById(R.id.rviCountries)
        rviCountries.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countryAdapter
        }

        //Managing the swipe in SwipeLayout
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            //Hiding the SwipeLayout spinner
            swipeRefreshLayout.isRefreshing = false
            //Refreshing the data
            viewModel.refresh()
        }

        observeViewModel()
    }

    //Implementing the observer pattern (reactivity)
    private fun observeViewModel(){
        val lblErrorMessage: TextView = findViewById(R.id.lblErrorMessage)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        //Observing the list of countries
        viewModel.lstCountry.observe(this, Observer { lstCountry ->
            lstCountry?.let {
                rviCountries.visibility = View.VISIBLE
                countryAdapter.updateCountries(it)
                progressBar.visibility = View.GONE
            }
        })

        //Observing the LoadError
        viewModel.countryLoadError.observe(this, Observer { isError ->
            isError?.let {
                lblErrorMessage.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        //Observing the Loading
        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                progressBar.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    lblErrorMessage.visibility = View.GONE
                    rviCountries.visibility = View.GONE
                }
            }
        })
    }
}