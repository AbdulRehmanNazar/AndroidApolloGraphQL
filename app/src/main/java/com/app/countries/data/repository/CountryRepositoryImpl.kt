package com.app.countries.data.repository

import com.app.countries.data.model.Flag
import com.app.countries.data.remote.CountriesRemoteDataSource
import com.app.countries.util.Result

class CountryRepositoryImpl(private val countriesRemoteDataSource: CountriesRemoteDataSource) :
    CountryRepository {

    override suspend fun fetchCountries(): Result<List<Flag>> {

        return when (val result = countriesRemoteDataSource.fetchCountry()) {
            is Result.Success -> {
                val countries = result.data
                Result.Success(countries)
            }
            is Result.Error -> {
                Result.Error(result.exception)

            }
            else -> {
                Result.Success(null)
            }
        }
    }
}
