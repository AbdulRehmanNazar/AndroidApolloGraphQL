package com.app.countries.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.Observer
import com.app.countries.R
import com.app.countries.adapter.CountriesAdapter
import com.app.countries.data.model.Flag
import com.app.countries.util.ItemClickListener
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), ItemClickListener<Flag> {

    private val mainAcitivityViewModel: MainAcitivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_main)
        fetchCountries()
    }

    /**
     * Method to trigger to get the callBack with network calls
     */
    fun fetchCountries() {
        mainAcitivityViewModel.dataFetchState.observe(this, Observer {
            it.getContentIfNotHandled()?.let { fetchedData ->
                populateData(fetchedData)
            }
        })

        mainAcitivityViewModel.isFetching.observe(this, Observer { isFetching ->
            when (isFetching) {
                true -> {
                    progress_circular.visibility = View.VISIBLE
                }
                false -> {
                    progress_circular.visibility = View.GONE
                }
            }

        })

        mainAcitivityViewModel.fetchCountries()
    }

    /**
     *Method to initialize the adapter and assign data to it
     */
    fun populateData(listData: List<Flag>) {
        val adapter = CountriesAdapter(listData, this)
        recyclerViewCountry.adapter = adapter
    }

    /**
     * CallBack method when item of RecyclerView clicked
     */
    override fun onClick(flag: Flag) {
        Toast.makeText(
            this,
            flag.country?.name + "\n" + flag.country?.capital
            , Toast.LENGTH_LONG
        ).show()
    }
}