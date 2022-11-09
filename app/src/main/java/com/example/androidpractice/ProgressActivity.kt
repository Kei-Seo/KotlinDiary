package com.example.androidpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.TestLooperManager
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

class ProgressActivity : AppCompatActivity() {

    var isStarted = false
    var progressStatus = 0
    var handler: Handler? = null
    var secondaryHandler: Handler? = Handler()
    var primaryProgressStatus = 0
    var secondaryProgressStatus = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)


        handler = Handler(Handler.Callback {
            if (isStarted) {
                progressStatus++
            }
            val progressBarHorizontal = findViewById<ProgressBar>(R.id.progressBarHorizontal)
            progressBarHorizontal.progress = progressStatus
            findViewById<TextView>(R.id.textViewHorizontalProgress).text = "${progressStatus}/${progressBarHorizontal.max}"
            handler?.sendEmptyMessageDelayed(0, 100)

            true
        })

        handler?.sendEmptyMessage(0)



        findViewById<Button>(R.id.btnProgressBarSecondary).setOnClickListener {
            primaryProgressStatus = 0
            secondaryProgressStatus = 0

            Thread(Runnable {
                while (primaryProgressStatus < 100) {
                    primaryProgressStatus += 1

                    try {
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                    startSecondaryProgress()
                    secondaryProgressStatus = 0

                    val textViewPrimary = findViewById<TextView>(R.id.textViewPrimary)
                    secondaryHandler?.post {
                        findViewById<ProgressBar>(R.id.progressBarSecondary).progress = primaryProgressStatus
                        textViewPrimary.text = "Complete $primaryProgressStatus% of 100"

                        if (primaryProgressStatus == 100) {
                            textViewPrimary.text = "All tasks completed"
                        }
                    }
                }
            }).start()
        }

    }

    fun startSecondaryProgress() {
        Thread(Runnable {
            while (secondaryProgressStatus < 100) {
                secondaryProgressStatus += 1

                try {
                    Thread.sleep(10)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                secondaryHandler?.post {
                    findViewById<ProgressBar>(R.id.progressBarSecondary).setSecondaryProgress(secondaryProgressStatus)
                    findViewById<TextView>(R.id.textViewSecondary).setText("Current task progress\n$secondaryProgressStatus% of 100")

                    if (secondaryProgressStatus == 100) {
                        findViewById<TextView>(R.id.textViewSecondary).setText("Single task complete.")
                    }
                }
            }
        }).start()
    }

    fun horizontalDeterminate(view: View) {
        isStarted = !isStarted
    }
}