package com.helium4.abana

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.helium4.abana.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val animShake: Animation = AnimationUtils.loadAnimation(this,R.anim.shake)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.etMpin.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

//                if (s != null) {
//                    if (s.length == 4){
//                        if (s.equals("0000")){
//                            val intent = Intent(this@LoginActivity,MainActivity::class.java)
//                            startActivity(intent)
//                        }else{
//                            binding.etMpin.error ="Wrong Pin"
//                            binding.etMpin.isFocused
//                        }
//                    }
//
//
//                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.length == 4){
                        if (s.toString() == "0000"){
                            val intent = Intent(this@LoginActivity,HomeActivity::class.java)
                            startActivity(intent)
                        }else{
                            binding.etMpin.error ="Wrong Pin"
                            binding.etMpin.isFocused
                            binding.etMpin.startAnimation(animShake)
                            binding.etMpin.setText("")

                        }
                    }


                }            }

        })


    }
}