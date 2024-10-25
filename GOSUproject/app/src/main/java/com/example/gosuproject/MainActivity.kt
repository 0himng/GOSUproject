package com.example.gosuproject

import ApiService
import Question
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.Toast
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import com.example.gosuproject.databinding.ActivityMainBinding
import com.github.barteksc.pdfviewer.PDFView
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setContentView(R.layout.content_main)


        // PDFView 객체 찾기
        val pdfView = findViewById<PDFView>(R.id.pdfView)

        // assets 폴더에 있는 PDF 파일 로드
        pdfView.fromAsset("su1.pdf")
            .load()


        val auth = FirebaseAuth.getInstance()


        // 로그인 버튼 클릭 시
        val loginButton: Button = findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            val email = "user@example.com" // 실제 사용자의 이메일
            val password = "password123" // 실제 사용자의 비밀번호

            signInWithEmail(email, password)
        }

        // Retrofit API 호출
        RetrofitClient.instance.getUsers().enqueue(object : Callback<List<Question>> {

            override fun onResponse(call:Call<List<Question>>, response: Response<List<Question>>) {

            }

            override fun onFailure(call: Call<List<Question>>, t: Throwable) {
                Log.e("MainActivity", "Failed to fetch users", t)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun signInWithEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    // 로그인 성공 시 행동
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                } else {
                    // 로그인 실패 시
                    Toast.makeText(this, "로그인 실패: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

}