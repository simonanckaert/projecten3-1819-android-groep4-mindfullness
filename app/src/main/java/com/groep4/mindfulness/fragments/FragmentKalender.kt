package com.groep4.mindfulness.fragments

import android.database.Cursor
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.groep4.mindfulness.R
import com.groep4.mindfulness.activities.MainActivity
import com.groep4.mindfulness.adapters.ListTaskAdapter
import com.groep4.mindfulness.utils.NoScrollListView
import com.groep4.mindfulness.database.DBHelper
import com.groep4.mindfulness.utils.KalenderFunction
import kotlinx.android.synthetic.main.fragment_kalender.*
import java.util.ArrayList
import java.util.HashMap


class FragmentKalender : Fragment()
{

    lateinit var mydb: DBHelper
    lateinit var taskNoScrollListToday: NoScrollListView ; lateinit var taskNoScrollListTomorrow: NoScrollListView  ; lateinit var taskNoScrollListUpcoming: NoScrollListView
    lateinit var scrollView: NestedScrollView
    lateinit var todayText: TextView ; lateinit var tomorrowText: TextView ; lateinit var upcomingText: TextView

    var todayList = ArrayList<HashMap<String, String>>()
    var tomorrowList = ArrayList<HashMap<String, String>>()
    var upcomingList = ArrayList<HashMap<String, String>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_kalender, container, false)

        scrollView = view.findViewById(R.id.scrollView) as NestedScrollView

        taskNoScrollListToday = view.findViewById(R.id.taskListToday) as NoScrollListView
        taskNoScrollListTomorrow = view.findViewById(R.id.taskListTomorrow) as NoScrollListView
        taskNoScrollListUpcoming = view.findViewById(R.id.taskListUpcoming) as NoScrollListView

        todayText = view.findViewById(R.id.todayText) as TextView
        tomorrowText = view.findViewById(R.id.tomorrowText) as TextView
        upcomingText = view.findViewById(R.id.upcomingText) as TextView

        //Fragment knop taak toevoegen
        val addTaskButton = view.findViewById(R.id.addTaskButton) as ImageView
        addTaskButton.setOnClickListener {

            //Een nieuwe fragment aanmaken
            var f = FragmentAddTask()
            val bundle = Bundle()

            //isUpdate = false aangezien dit een nieuwe taak is en geen aanpassing
            bundle.putBoolean("isUpdate", false)
            f.arguments = bundle
            f.arguments = bundle

            //Launch fragment
            activity?.supportFragmentManager!!
                    .beginTransaction()
                    .replace(R.id.fragment_holder_main, f, "pageContent")
                    .addToBackStack("root_fragment")
                    .commit()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        //database opstellen
        mydb = DBHelper(activity!!)
        populateData()
    }

    fun populateData() {
        mydb = DBHelper(activity!!)
        scrollView!!.visibility = View.GONE
        val loadTask = LoadTask()
        loadTask.execute()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar!!.hide()
        populateData()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar!!.show()
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

        val adapter = ListTaskAdapter(activity!!, dataList)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            var f = FragmentAddTask()
            val bundle = Bundle()
            bundle.putBoolean("isUpdate", true)
            bundle.putString("id", dataList[+position][KEY_ID])
            f.arguments = bundle
            activity?.supportFragmentManager!!
                    .beginTransaction()
                    .replace(R.id.fragment_holder_main, f, "pageContent")
                    .addToBackStack("root_fragment")
                    .commit()
        }
    }

    companion object {

        var KEY_ID = "id"
        var KEY_TASK = "task"
        var KEY_DATE = "date"
    }
}