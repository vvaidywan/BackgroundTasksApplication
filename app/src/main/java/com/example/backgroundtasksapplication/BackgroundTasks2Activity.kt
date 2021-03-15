package com.example.backgroundtasksapplication

import android.annotation.SuppressLint
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.solver.state.State
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
//import androidx.work.OneTimeWorkRequestBuilder
import com.example.backgroundtasksapplication.JobScheduler.MyJobScheduler
import com.example.backgroundtasksapplication.WorkManager.MyWorker

class BackgroundTasks2Activity : AppCompatActivity() {
    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background_tasks2)

        //Ques 1 : A demo for job scheduler
        val myJobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val jobInfo = JobInfo.Builder(1,
            ComponentName(this, MyJobScheduler::class.java))

        val job = jobInfo.setMinimumLatency(1000)
            //.setPeriodic(5000)
            //.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .build()

        myJobScheduler.schedule(job)


        //Ques 2 : A demo for Work manager
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        var request = OneTimeWorkRequestBuilder<MyWorker>()
            .setConstraints(constraints)
            .build()

        var btnClick = findViewById<Button>(R.id.btnClick)
        btnClick.setOnClickListener {

            WorkManager.getInstance(this).enqueue(request)
        }

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.id)
            .observe(this, Observer {

                val status: String = it.state.name
                Toast.makeText(this,status, Toast.LENGTH_SHORT).show()
            })


    }
}