package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private var time = 0
    private var timerTask: Timer?=null



    lateinit var secTextView: TextView
    lateinit var milliTextView: TextView
    lateinit var timerSettingButton: Button
    lateinit var inputEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        secTextView=findViewById(R.id.secTextView)
        milliTextView=findViewById(R.id.milliTextView)
        timerSettingButton=findViewById(R.id.timerSettingButton)
        inputEditText=findViewById(R.id.inputEditText)

        timerSettingButton.setOnClickListener{
            if(inputEditText.text.toString().toInt() != 0){
                //타이머
                time=inputEditText.text.toString().toInt()*100
                timerTask = timer(period = 10) {
                    //워커스레드 (UI조작불가)
                    time--
                    val sec = time / 100
                    val milli = time %100
                    runOnUiThread { // 메인스레드 (UI조작 가능)
                        // 특정 동작을 ui스레드에서 동작하도록 함
                        // 하지만 현재 스레드가 ui스레드가 아니면 취소?
                        secTextView.text = "$sec"
                        milliTextView.text = "$milli"
                    }
                    if (time == 0) {

                        runOnUiThread {
                            secTextView.text = "0"
                            milliTextView.text = "0"
                            timerTask?.cancel() // 타이머 취소
                            toast("타이머 종료됨!!")
                        }
                    }
                }
            }
        }
    }
    fun toast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}