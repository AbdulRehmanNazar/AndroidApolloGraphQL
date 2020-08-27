package com.app.countries.data.repository

import com.app.countries.data.model.Flag
import com.app.countries.util.Result

interface CountryRepository {
    suspend fun fetchCountries(): Result<List<Flag>>
}