package tn.aquaguard.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        private const val BASE_URL = "http://10.0.2.2:9090/"

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()

                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NTRkZjE4YjUzNWVjMDRlZmVkYWJiMGIiLCJ1c2VybmFtZSI6Im1hbGVrIiwiaWF0IjoxNzAwNjA3NzIyLCJleHAiOjE3MDA2MTQ5MjJ9.KxjwOqnaN5n4FSheTfLSwEJ19_lzACwcRBwMXWMlSuA")
                    .method(original.method(), original.body())
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()


        val postService: PostService = retrofit.create(PostService::class.java)
        val eventService: EventService = retrofit.create(EventService::class.java)
        val actualiteService : ActualiteService = retrofit.create(ActualiteService::class.java)
        val participationService: ParticipationService = retrofit.create(ParticipationService::class.java)
    }
}

