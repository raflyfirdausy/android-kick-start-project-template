package com.frogobox.kickstart.source

import android.content.Context
import com.frogobox.kickstart.base.data.BaseDataSource
import com.frogobox.kickstart.mvvm.model.ArticleResponse
import com.frogobox.kickstart.mvvm.model.Favorite
import com.frogobox.kickstart.mvvm.model.Fashion
import com.frogobox.kickstart.mvvm.model.SourceResponse

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * BaseMusicPlayer
 * Copyright (C) 18/08/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.basemusicplayer.source
 *
 */
interface FrogoDataSource : BaseDataSource {

    // API Server ----------------------------------------------------------------------------------

    // Switch For Using Chuck Interceptor
    fun usingChuckInterceptor(context: Context)

    // Get Top Headline
    fun getTopHeadline(
        apiKey: String,
        q: String?,
        sources: String?,
        category: String?,
        country: String?,
        pageSize: Int?,
        page: Int?,
        callback: GetRemoteCallback<ArticleResponse>
    )

    // Get Everythings
    fun getEverythings(
        apiKey: String,
        q: String?,
        from: String?,
        to: String?,
        qInTitle: String?,
        sources: String?,
        domains: String?,
        excludeDomains: String?,
        language: String?,
        sortBy: String?,
        pageSize: Int?,
        page: Int?,
        callback: GetRemoteCallback<ArticleResponse>
    )

    // Get Sources
    fun getSources(
        apiKey: String,
        language: String,
        country: String,
        category: String,
        callback: GetRemoteCallback<SourceResponse>
    )

    // Room Database -------------------------------------------------------------------------------

    fun saveRoomFavorite(data: Favorite) : Boolean

    // Get
    fun getRoomData(callback: GetRoomDataCallBack<List<Fashion>>)
    fun getRoomFavorite(callback: GetRoomDataCallBack<List<Favorite>>)

    // Update
    fun updateRoomFavorite(tableId: Int, title: String, description: String, dateTime: String) : Boolean

    // Search
    fun searchRoomFavorite(scriptId: String, callback: GetRoomDataCallBack<List<Favorite>>)

    // Delete
    fun deleteRoomFavorite(tableId: Int) : Boolean

    // Nuke
    fun nukeRoomFavorite() : Boolean
    // ---------------------------------------------------------------------------------------------

    // Interface Helper ---------------------------------------------------------------------------

    // Response Callback
    interface GetRemoteCallback<T> : BaseDataSource.ResponseCallback<T>

    // Save
    interface SaveRoomDataCallBack<T>: BaseDataSource.ResponseCallback<T>
    interface SavePrefCallBack<T>: BaseDataSource.ResponseCallback<T>

    // Get
    interface GetRoomDataCallBack<T> : BaseDataSource.ResponseCallback<T>

    // Update
    interface UpdateRoomDataCallBack<T> : BaseDataSource.ResponseCallback<T>

    // Delete
    interface DeleteRoomDataCallBack<T> : BaseDataSource.ResponseCallback<T>
    // ---------------------------------------------------------------------------------------------

}