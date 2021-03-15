package com.example.backgroundtasksapplication.JobScheduler

import android.app.Notification
import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import com.example.backgroundtasksapplication.BackgroundTasks2Activity
import com.example.backgroundtasksapplication.MainActivity
import java.util.concurrent.Executors
//import java.util.logging.Handler
import javax.security.auth.callback.Callback


class MyJobScheduler : JobService() {

    private val myJobHandler : Handler = Handler(object  : Handler.Callback {
        override fun handleMessage(msg : Message) : Boolean{
            Log.e("Job Scheduler", "Job running")
            jobFinished(msg.obj as JobParameters, false)
            return true
        }
    })

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.e("onStartJob", "Job started")
        myJobHandler.sendMessage(Message.obtain(myJobHandler, 1, params))
//        Executors.newSingleThreadExecutor().execute {
//            Log.e("inside thread2", "Job started...")
//            for (i in 1..10){
//                println(i)
//            }
//        }


        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.e("onStopJob", "Job stopped")
        myJobHandler.removeMessages(1)
        return false
    }
}