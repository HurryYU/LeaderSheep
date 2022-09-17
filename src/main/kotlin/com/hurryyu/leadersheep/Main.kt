package com.hurryyu.leadersheep

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://cat-match.easygame2021.com/sheep/v1/game/"

/**
 * 通关次数
 */
private const val REQUEST_COUNT = 3

/**
 * 通关间隔毫秒数
 */
private const val REQUEST_INTERVAL_TIME = 3000L

/**
 * 通关用时,单位秒
 */
private const val COMPLETE_TIME = 1 * 60

private val api by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}

fun main(args: Array<String>) {
    var currentCount = 0
    args.getOrNull(0)?.also {
        runBlocking {
            while (currentCount < REQUEST_COUNT) {
                runCatching {
                    println("正执行第 ${currentCount + 1} 次闯关")
                    endGame(it)
                    println("间隔 ${REQUEST_INTERVAL_TIME / 1000} 秒进行下一次闯关")
                    delay(REQUEST_INTERVAL_TIME)
                    println("-----------------------------------------")
                }.onFailure {
                    println("网络错误,请稍后尝试!")
                    println(it.message)
                    println("-----------------------------------------")
                }
                currentCount++
            }
            println("闯关已经全部完成!")
        }
    } ?: print("请携带 t 参数使用")
}

private suspend fun endGame(t: String) {
    val responseData = api.endGame(COMPLETE_TIME, t)
    if (responseData.err_code == 0) {
        println("闯关成功!")
    } else {
        println("闯关失败!请检查 t 设置")
        println(responseData.toString())
    }
}