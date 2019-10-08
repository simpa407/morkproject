package com.example.tokyoartbeat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.log_in.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private var isLogin = false
    private lateinit var userAccess: DatabaseAccess
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userAccess = DatabaseAccess(this)
    }
    fun eventClick(view : View){
        when(view.id){
            R.id.btnSignUp -> {
                var userName = this.signupFullName.text.toString()
                var userEmail = this.signupEmail.text.toString()
                var userPassword = this.signupPassword.text.toString()

                var usersList = userAccess.getAllUsers()

                if(userName.isNotEmpty() && userEmail.isNotEmpty() && userPassword.isNotEmpty() ){
                    if(isEmail(userEmail) && userPassword.length in 6..16){
                        var isChecked = true
                        for (value in usersList) {
                            if(value.email == userEmail){
                                isChecked = false
                                Toast.makeText(this, "Email đã tồn tại", Toast.LENGTH_LONG).show()
                                break
                            }
                        }
                        if(isChecked){
                            userAccess.insertUser(UserModel(userName, userEmail, userPassword))
                            this.signupEmail.setText("")
                            this.signupFullName.setText("")
                            this.signupPassword.setText("")
                            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
                            setContentView(R.layout.log_in)
                        }
                    } else {
                        if (!isEmail(userEmail)) {
                            Toast.makeText(this, "Định dạng email không hợp lệ ", Toast.LENGTH_LONG).show()
                        }
                        if (userPassword.length !in 6..16) {
                            Toast.makeText(this, "Độ dài mật khẩu phải từ 6 - 16 kí tự", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Phải nhập đầy đủ thông tin", Toast.LENGTH_LONG).show()
                }
            }
            R.id.toLogIn -> setContentView(R.layout.log_in)
            R.id.btnLogin -> {
                var email = this.loginEmail.text.toString()
                var password = this.loginPassword.text.toString()
                var usersList = userAccess.getAllUsers()
                var checked = false
                for (value in usersList){
                    if (value.email == email && value.password == password) {
                        checked = true
                        isLogin = true
                        Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MeActivity::class.java)
                        startActivity(intent)
                        break
                    }
                }
                if (!checked) {
                    Toast.makeText(this,"Sai email đăng nhập hoặc mật khẩu ", Toast.LENGTH_LONG).show()
                }
            }
            R.id.btnBackSignup -> setContentView(R.layout.activity_main)
            R.id.btnBackLogin -> setContentView(R.layout.log_in)
            R.id.btnForgetPassword -> setContentView(R.layout.forget_password)
            R.id.btnResetPassword -> {

                Toast.makeText(this, "Đã thay đổi mật khẩu", Toast.LENGTH_LONG).show()
                val email  = findViewById<TextView>(R.id.resetEmail)
                val password = findViewById<TextView>(R.id.resetPassword)
                userAccess.changePassword(email.toString(), password.toString())
                setContentView(R.layout.log_in)
            }
        }
    }
    fun isEmail(email: String): Boolean {
        var emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$"
        var pattern = Pattern.compile(emailRegex)
        return pattern.matcher(email).matches()
    }
}
