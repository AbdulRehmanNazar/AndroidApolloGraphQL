package com.app.countries.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.countries.data.model.Flag
import com.app.countries.data.repository.CountryRepository
import com.app.countries.util.Event
import kotlinx.coroutines.launch
import com.app.countries.util.Result


class MainAcitivityViewModel(private val countryRepository: CountryRepository) : ViewModel() {

    val dataFetchState: MutableLiveData<Event<List<Flag>>> = MutableLiveData()
    val isFetching: MutableLiveData<Boolean> = MutableLiveData()

    fun fetchCountries() = viewModelScope.launch {
        isFetching.value = (true)
        when (val result = countryRepository.fetchCountries()) {
            is Result.Success -> {
                dataFetchState.value = Event(result.data)
            }
            is Result.Error -> {
                dataFetchState.value = Event(null)
            }
        }
        isFetching.value = (false)
    }

}