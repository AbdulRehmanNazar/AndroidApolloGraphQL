package com.app.countries.data.remote

import com.app.countries.data.model.Flag
import com.app.countries.util.Result

interface CountriesRemoteDataSource {
    suspend fun fetchCountry(): Result<List<Flag>>
}