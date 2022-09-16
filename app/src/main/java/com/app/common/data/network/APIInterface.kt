package com.app.common.data.network

import com.app.common.model.LoginModel

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIInterface {

    //Corutine
    @FormUrlEncoded
    @POST("/api/users")
    suspend fun getUserRxJava(
        @Field("first_name") first_name: String?,
        @Field("avatar") avatar: String?,
    ): LoginModel

    //RxJava
    /*@FormUrlEncoded
    @POST("/api/users")
    fun getUserRxJava(
        @Field("name") name: String?,
        @Field("job") job: String?
    ):Observable<LoginModel>*/
}