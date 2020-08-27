package com.app.countries.data.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import com.app.countries.data.model.Country
import com.app.countries.data.model.Flag
import com.example.graphql.GetCountryListQuery
import com.app.countries.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class CountriesRemoteDataSourceImpl(
    private val apolloClient: ApolloClient,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CountriesRemoteDataSource {

    val countriesList = mutableListOf<Flag>()

    override suspend fun fetchCountry(): Result<List<Flag>> = withContext(ioDispatcher) {
        val getCountryListQuery: GetCountryListQuery = GetCountryListQuery.builder().build()
        return@withContext try {
            val deferred = apolloClient.query(getCountryListQuery).toDeferred()
            val response = deferred.await()
            if (!response.hasErrors() && response.data != null) {
                val data = response.data?.Flag()

                data?.forEach { flag ->
                    countriesList.add(
                        Flag(
                            flag.svgFile(),
                            Country(flag.country()?.name(), flag.country()?.capital())
                        )
                    )
                }

            }

            Result.Success(countriesList)
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}