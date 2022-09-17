import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val REQUEST_COUNT = 3
private const val BASE_URL = "https://cat-match.easygame2021.com/sheep/v1/game/"
private const val REQUEST_INTERVAL_TIME = 3000L

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
        }
    } ?: print("请携带 t 参数使用")
}

private suspend fun endGame(t: String) {
    val endTime = 1 * 60
    val responseData = api.endGame(endTime, t)
    if (responseData.err_code == 0) {
        println("闯关成功!")
    } else {
        println("闯关失败!请检查 t 设置")
        println(responseData.toString())
    }
}