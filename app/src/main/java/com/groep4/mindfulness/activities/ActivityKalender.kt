package com.groep4.mindfulness.activities

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import com.groep4.mindfulness.R
import com.groep4.mindfulness.adapters.ListTaskAdapter
import com.groep4.mindfulness.utils.NoScrollListView
import com.groep4.mindfulness.database.DBHelper
import com.groep4.mindfulness.utils.KalenderFunction
import java.util.ArrayList
import java.util.HashMap


class ActivityKalender : AppCompatActivity() {

    lateinit var activity: Activity
    lateinit var mydb: DBHelper
    lateinit var taskNoScrollListToday: NoScrollListView ; lateinit var taskNoScrollListTomorrow: NoScrollListView  ; lateinit var taskNoScrollListUpcoming: NoScrollListView
    lateinit var scrollView: NestedScrollView
    lateinit var todayText: TextView ; lateinit var tomorrowText: TextView ; lateinit var upcomingText: TextView
    var todayList = ArrayList<HashMap<String, String>>()
    var tomorrowList = ArrayList<HashMap<String, String>>()
    var upcomingList = ArrayList<HashMap<String, String>>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kalender)

        //database opstellen
        activity = this@ActivityKalender
        mydb = DBHelper(activity)


        scrollView = findViewById(R.id.scrollView) as NestedScrollView
        taskNoScrollListToday = findViewById(R.id.taskListToday) as NoScrollListView
        taskNoScrollListTomorrow = findViewById(R.id.taskListTomorrow) as NoScrollListView
        taskNoScrollListUpcoming = findViewById(R.id.taskListUpcoming) as NoScrollListView

        todayText = findViewById(R.id.todayText) as TextView
        tomorrowText = findViewById(R.id.tomorrowText) as TextView
        upcomingText = findViewById(R.id.upcomingText) as TextView

    }

    //Nieuwe AddTask Activity openen

    fun openAddTask(v: View) {
        val i = Intent(this, ActivityAddTask::class.java)
        startActivity(i)
    }

    fun populateData() {
        mydb = DBHelper(activity)
        scrollView!!.visibility = View.GONE
        val loadTask = LoadTask()
        loadTask.execute()
    }

    public override fun onResume() {
        super.onResume()
        populateData()
    }


    /**
     *  asynchroon ophalen van alle tasks
     */

    inner class LoadTask : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()

            todayList.clear()
            tomorrowList.clear()
            upcomingList.clear()
        }

        override fun doInBackground(vararg args: String): String {
            val xml = ""


            val today = mydb.dataToday
            loadDataList(today, todayList)

            val tomorrow = mydb.dataTomorrow
            loadDataList(tomorrow, tomorrowList)

            val upcoming = mydb.dataUpcoming
            loadDataList(upcoming, upcomingList)

            return xml
        }

        override fun onPostExecute(xml: String) {

            loadListView(taskNoScrollListToday, todayList)
            loadListView(taskNoScrollListTomorrow, tomorrowList)
            loadListView(taskNoScrollListUpcoming, upcomingList)


            /**
             * List tonen als het items bevat voor vandaag,morgen en opkomend
             */
            if (todayList.size > 0) {
                todayText.visibility = View.VISIBLE
            } else {
                todayText.visibility = View.GONE
            }

            if (tomorrowList.size > 0) {
                tomorrowText.visibility = View.VISIBLE
            } else {
                tomorrowText.visibility = View.GONE
            }

            if (upcomingList.size > 0) {
                upcomingText.visibility = View.VISIBLE
            } else {
                upcomingText.visibility = View.GONE
            }

            scrollView.visibility = View.VISIBLE
        }
    }

    /**
     *  ID , TASK , DATE ->> ophalen uit datebase
     *  Date naar leesbare string omzetten
     */
    fun loadDataList(cursor: Cursor?, dataList: ArrayList<HashMap<String, String>>) {
        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.isAfterLast == false) {

                val mapToday = HashMap<String, String>()
                mapToday[KEY_ID] = cursor.getString(0).toString()
                mapToday[KEY_TASK] = cursor.getString(1).toString()

                mapToday[KEY_DATE] = KalenderFunction().Epoch2DateString(cursor.getString(2).toString(), "dd-MM-yyyy")
                dataList.add(mapToday)
                cursor.moveToNext()
            }
        }
    }

    /**
     * Task update als er word op geklikt
     */
    fun loadListView(listView: ListView, dataList: ArrayList<HashMap<String, String>>) {

        val adapter = ListTaskAdapter(activity, dataList)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val i = Intent(activity, ActivityAddTask::class.java)
            i.putExtra("isUpdate", true)
            i.putExtra("id", dataList[+position][KEY_ID])
            startActivity(i)

        }
    }

    companion object {

        var KEY_ID = "id"
        var KEY_TASK = "task"
        var KEY_DATE = "date"
    }
}