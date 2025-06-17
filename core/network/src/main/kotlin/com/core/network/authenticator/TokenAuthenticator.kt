package com.core.network.authenticator

import com.core.datastore.DataStoreManager
import com.core.network.BuildConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val loggingInterceptor: HttpLoggingInterceptor,
    private val converterFactory: Converter.Factory,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.responseCount >= 3) {
            return null
        }
        val refreshToken = runBlocking {
            dataStoreManager.getRefreshToken().first()
        }
        return runBlocking {
            val request = getNewToken(refreshToken)
            request.body()?.let {
                dataStoreManager.saveAccessToken(it.data.accessToken)
                dataStoreManager.saveRefreshToken(it.data.refreshToken)
                response.request.newBuilder()
                    .header(name = "Authorization", value =  "Bearer ${it.data.accessToken}")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String): retrofit2.Response<RefreshTokenResponseDTO> {
        val okHttpClient = OkHttpClient().newBuilder()
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(loggingInterceptor)
        }
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(converterFactory)
        retrofitBuilder.client(okHttpClient.build())
        val retrofit = retrofitBuilder.build()
        val service = retrofit.create(RefreshTokenService::class.java)
        // TODO: Replace [Unit] with service request body
        return service.getRefreshedToken(Unit)
    }
    private val Response.responseCount: Int
        get() = generateSequence(this) { it.priorResponse }.count()
}
