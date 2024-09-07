package com.zzy.nasaapod.data.remote


import com.zzy.nasaapod.data.model.APOD
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/planetary/apod")
    suspend fun getAPODs(@Query("count") count: Int = 10, @Query("api_key") apiKey: String = "vBsK0zf1roZqAEd67rpTj5IoTYHPNzPTFNmsNrN2"): List<APOD>
}