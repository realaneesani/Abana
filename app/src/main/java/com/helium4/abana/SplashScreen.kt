package com.helium4.abana

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.core.Rive
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.daimajia.easing.Glider
import com.daimajia.easing.Skill
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import java.util.Timer
import kotlin.concurrent.schedule


class SplashScreen : AppCompatActivity() {
    lateinit var riveAnimationView: RiveAnimationView
    lateinit var originalBitmap : Bitmap
    lateinit var blurredBitmap : Bitmap
    val stateMachine ="Login Machine"
 //   val riveAnimationController

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Rive.init(this)

        val blurEffect = RenderEffect.createBlurEffect(200F, 200F
            , Shader.TileMode.MIRROR)
        riveAnimationView = findViewById(R.id.bg_anim)
        riveAnimationView.setRenderEffect(blurEffect)
        val getStarted = findViewById<TextView>(R.id.getStarted)
        YoYo.with(Techniques.Pulse)
            .duration(2000)
            .repeat(100)
            .playOn(getStarted);

        getStarted.setOnClickListener {
          //  getStarted.visibility = View.GONE
           // findViewById<LinearProgressIndicator>(R.id.progress_circular).visibility = View.VISIBLE

            getStarted.visibility = View.GONE
            openPopup()
//            val intent = Intent(this@SplashScreen, HomeActivity::class.java)
//            startActivity(intent)
        }

    }

    private fun openPopup() {

        val animShake: Animation = AnimationUtils.loadAnimation(this,R.anim.shake)

        val builder = AlertDialog.Builder(this,R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.login_popup,null)
      //
        val  bear = view.findViewById<RiveAnimationView>(R.id.bear)


val tv = view.findViewById<TextInputEditText>(R.id.login_phone_number_editText)
        val tvp = view.findViewById<TextInputEditText>(R.id.login_password_editText)

        tv.
        setOnFocusChangeListener { view,b ->
            if (b){
                bear.controller.setBooleanState(stateMachine, "isChecking",true)
            }else{
                bear.controller.setBooleanState(stateMachine, "isChecking",false)

            }
        }
        tvp.
        setOnFocusChangeListener { view,b ->
            if (b){
                bear.controller.setBooleanState(stateMachine, "isHandsUp",true)
            }else{
                bear.controller.setBooleanState(stateMachine, "isHandsUp",false)

            }
        }

        tv.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                try {
                    bear.controller.setNumberState(stateMachine,"numLook",s!!.length.toFloat())


                }catch (_:Exception){

                }
            }
        })


        view.findViewById<TextView>(R.id.btn_cntn).setOnClickListener { v->

            if(tv.text.toString() == ""){
                tv.error ="Username can't be null"
                tv.isFocused
                tv.startAnimation(animShake)
                tv.setText("")
            }
            else if (tvp.text.toString() != "1234") {
                tvp.error ="Wrong Pin"
                tvp.isFocused
                tvp.startAnimation(animShake)
                tvp.setText("")
            }else{
                val intent = Intent(this@SplashScreen, HomeActivity::class.java)
            startActivity(intent)

            }
        }


        builder.setView(view)
//        button.setOnClickListener {
//            builder.dismiss()
//        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }
}