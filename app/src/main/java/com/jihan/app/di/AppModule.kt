package com.jihan.app.di

import com.jihan.app.api.ChannelApi
import com.jihan.app.local.ChannelDatabase
import com.jihan.app.repository.ChannelRepository
import com.jihan.app.util.Constants.BASE_URL
import com.jihan.app.util.json
import com.jihan.app.viewmodel.CategoryDetailViewmodel
import com.jihan.app.viewmodel.ChannelViewmodel
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


val appModule = module {


    single {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            json.asConverterFactory(
                "application/json; charset=UTF8".toMediaType()
            )
        ).build()
    }

    single {
        get<Retrofit>().create(ChannelApi::class.java)
    }

    single {
        ChannelDatabase.getDatabase(androidContext()).getDao()
    }

    viewModelOf(::CategoryDetailViewmodel)

    singleOf(::ChannelRepository)

    viewModelOf(::ChannelViewmodel)


}