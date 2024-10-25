import retrofit2.Call
import retrofit2.http.GET

// 데이터 클래스 정의 (API에서 반환하는 JSON 구조에 맞게)
data class Question(
    val id: Int,
    val name: String,
    val email: String
)

// Retrofit 인터페이스 정의
interface ApiService {
    @GET("database.php")  // 여기에 서버의 API 엔드포인트 경로를 입력
    fun getUsers(): Call<List<Question>>
}
