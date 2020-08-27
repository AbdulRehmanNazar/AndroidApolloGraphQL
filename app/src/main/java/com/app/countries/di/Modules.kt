package com.example.rickandmortyapp.di

import com.apollographql.apollo.ApolloClient
import com.app.countries.data.remote.CountriesRemoteDataSource
import com.app.countries.data.remote.CountriesRemoteDataSourceImpl
import com.app.countries.data.repository.CountryRepository
import com.app.countries.data.repository.CountryRepositoryImpl
import com.app.countries.ui.MainAcitivityViewModel
import com.app.countries.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

/**
 * Class that give Dependency Injection for application like network, viewModel
 */
val networkModules = module {
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder().okHttpClient(okHttpClient)
            .serverUrl(Constants.BASE_GRAPHQL_URL).build()
    }

    single { provideOkHttpClient() }
    single { provideApolloClient(okHttpClient = get()) }
}

val dataSourceModules = module {
    single<CountriesRemoteDataSource> { CountriesRemoteDataSourceImpl(apolloClient = get()) }
}

val repositoryModules = module {
    single<CountryRepository> {
        CountryRepositoryImpl(
            countriesRemoteDataSource = get()
        )
    }
}

val viewModelModules = module {
    viewModel {
        MainAcitivityViewModel(countryRepository = get())
    }


}
