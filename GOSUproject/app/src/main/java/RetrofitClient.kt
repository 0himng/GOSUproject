import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// RetrofitClient 객체는 싱글톤으로 생성
object RetrofitClient {
    private const val BASE_URL = "http://yourserver.com/"  // 여기에 서버의 기본 URL을 입력

    // Retrofit 인스턴스를 lazy로 초기화
    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // GSON 변환기 추가
            .build()

        retrofit.create(ApiService::class.java)  // ApiService 인터페이스 생성
    }
}
