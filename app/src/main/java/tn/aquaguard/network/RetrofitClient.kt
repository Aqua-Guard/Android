package tn.aquaguard.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor(context: Context) {

    companion object {
        private const val BASE_URL = "https://aquaguard-tux1.onrender.com"



        private lateinit var context: Context

        private val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .addHeader("Authorization", "Bearer " + SessionManager(context).getToken())
                        .method(original.method(), original.body())
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                .build()
        }

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        val discutionservice: DiscutionService by lazy { retrofit.create(DiscutionService::class.java) }
        val reclamationservice: ReclamationService by lazy { retrofit.create(ReclamationService::class.java) }
        val postService: PostService by lazy { retrofit.create(PostService::class.java) }
        val eventService: EventService by lazy { retrofit.create(EventService::class.java) }
        val actualiteService: ActualiteService by lazy { retrofit.create(ActualiteService::class.java) }
        val participationService: ParticipationService by lazy { retrofit.create(ParticipationService::class.java) }

        fun initialize(context: Context) {
            this.context = context.applicationContext
        }
    }
}
