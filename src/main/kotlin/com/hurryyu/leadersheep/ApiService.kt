package com.hurryyu.leadersheep

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers(
        "Host: cat-match.easygame2021.com",
        "Connection: keep-alive",
        "user-agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 MicroMessenger/7.0.4.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat/WMPF",
        "Accept: */*",
        "Referer: https://servicewechat.com/wx141bfb9b73c970a9/19/index.html"
    )
    @GET("game_over")
    suspend fun endGame(
        @Query("rank_time") endTime: Int,
        @Header("t") t: String,
        @Query("skin") skin: Int = 1,
        @Query("rank_role") rankRole: Int = 1,
        @Query("rank_score") rankScore: Int = 1,
        @Query("rank_state") rankState: Int = 1
    ): ResponseData
}