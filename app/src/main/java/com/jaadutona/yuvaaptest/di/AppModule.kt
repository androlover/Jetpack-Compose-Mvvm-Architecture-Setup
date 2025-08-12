package com.jaadutona.yuvaaptest.di

import com.jaadutona.yuvaaptest.data.remote.ApiService
import com.jaadutona.yuvaaptest.data.repository.ApiRepository
import com.jaadutona.yuvaaptest.data.repository.ItemRepository
import com.jaadutona.yuvaaptest.ui.screen.home.HomeViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//val appModule = module {
//    single { ItemRepository() }
////    single { AuthRepository() }   { for others singleton library }
////    single { ProfileRepository() }
//    viewModel { HomeViewModel(get()) }
//
////    viewModel { AuthViewModel(get()) }         // get() → AuthRepository
////    viewModel { ProfileViewModel(get()) }
//}


// if you are using list

val homeModule = module {
    single { ItemRepository() }
    viewModel { HomeViewModel(get(),get()) }
}
val authModule = module {
    single { ItemRepository() }
    viewModel { HomeViewModel(get(),get()) }
}
val profileModule = module {
    single { ItemRepository() }
    viewModel { HomeViewModel(get(),get()) }
}
val networkModule = module {

    // Moshi instance
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory()) // Kotlin support
            .build()
    }

    // Retrofit with Moshi
    single {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(MoshiConverterFactory.create(get())) // get() → Moshi instance
            .build()
            .create(ApiService::class.java)
    }
    single { ApiRepository(get()) }
}