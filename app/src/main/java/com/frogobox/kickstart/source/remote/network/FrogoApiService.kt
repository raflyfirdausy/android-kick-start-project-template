package com.frogobox.kickstart.source.remote.network

import android.content.Context
import com.frogobox.kickstart.mvvm.model.ArticleResponse
import com.frogobox.kickstart.mvvm.model.SourceResponse
import com.frogobox.kickstart.util.helper.ConstHelper
import com.readystatesoftware.chuck.ChuckInterceptor
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * PublicSpeakingBooster
 * Copyright (C) 16/08/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.publicspeakingbooster.source.remote.network
 *
 */
interface FrogoApiService {

    // Get Top Headline
    @GET(ConstHelper.ApiUrl.NEWS_URL_TOP_HEADLINE)
    fun getTopHeadline(
        @Query(ConstHelper.NewsConstant.QUERY_API_KEY) apiKey: String,
        @Query(ConstHelper.NewsConstant.QUERY_Q) q: String?,
        @Query(ConstHelper.NewsConstant.QUERY_SOURCES) sources: String?,
        @Query(ConstHelper.NewsConstant.QUERY_CATEGORY) category: String?,
        @Query(ConstHelper.NewsConstant.QUERY_COUNTRY) country: String?,
        @Query(ConstHelper.NewsConstant.QUERY_PAGE_SIZE) pageSize: Int?,
        @Query(ConstHelper.NewsConstant.QUERY_PAGE) page: Int?
    ): Observable<ArticleResponse>

    // Get Everythings
    @GET(ConstHelper.ApiUrl.NEWS_URL_EVERYTHING)
    fun getEverythings(
        @Query(ConstHelper.NewsConstant.QUERY_API_KEY) apiKey: String,
        @Query(ConstHelper.NewsConstant.QUERY_Q) q: String?,
        @Query(ConstHelper.NewsConstant.QUERY_FROM) from: String?,
        @Query(ConstHelper.NewsConstant.QUERY_TO) to: String?,
        @Query(ConstHelper.NewsConstant.QUERY_Q_IN_TITLE) qInTitle: String?,
        @Query(ConstHelper.NewsConstant.QUERY_SOURCES) sources: String?,
        @Query(ConstHelper.NewsConstant.QUERY_DOMAINS) domains: String?,
        @Query(ConstHelper.NewsConstant.QUERY_EXCLUDE_DOMAINS) excludeDomains: String?,
        @Query(ConstHelper.NewsConstant.QUERY_LANGUAGE) language: String?,
        @Query(ConstHelper.NewsConstant.QUERY_SORT_BY) sortBy: String?,
        @Query(ConstHelper.NewsConstant.QUERY_PAGE_SIZE) pageSize: Int?,
        @Query(ConstHelper.NewsConstant.QUERY_PAGE) page: Int?
    ): Observable<ArticleResponse>

    // Get Sources
    @GET(ConstHelper.ApiUrl.NEWS_URL_SOURCES)
    fun getSources(
        @Query(ConstHelper.NewsConstant.QUERY_API_KEY) apiKey: String,
        @Query(ConstHelper.NewsConstant.QUERY_LANGUAGE) language: String,
        @Query(ConstHelper.NewsConstant.QUERY_COUNTRY) country: String,
        @Query(ConstHelper.NewsConstant.QUERY_CATEGORY) category: String
    ): Observable<SourceResponse>

    companion object Factory {

        private var isUsingChuckInterceptor = false
        private lateinit var context: Context

        fun usingChuckInterceptor(context: Context) {
            isUsingChuckInterceptor = true
            this.context = context
        }

        val getApiService: FrogoApiService by lazy {
            val mLoggingInterceptor = HttpLoggingInterceptor()
            mLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val mClient = if (isUsingChuckInterceptor) {
                OkHttpClient.Builder()
                    .addInterceptor(mLoggingInterceptor)
                    .addInterceptor(ChuckInterceptor(context))
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build()
            } else {
                OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build()
            }

            val mRetrofit = Retrofit.Builder()
                .baseUrl(ConstHelper.ApiUrl.NEWS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mClient)
                .build()

            mRetrofit.create(FrogoApiService::class.java)
        }
    }

}