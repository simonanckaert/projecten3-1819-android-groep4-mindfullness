package com.groep4.mindfulness.activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.groep4.mindfulness.R
import com.groep4.mindfulness.database.DBHelper
import com.groep4.mindfulness.utils.KalenderFunction
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ActivityAddTask : AppCompatActivity(), com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {


    lateinit var mydb: DBHelper
    lateinit var dpd: com.wdullaer.materialdatetimepicker.date.DatePickerDialog
    var startYear = 0 ;var startMonth = 0 ;var startDay = 0
    var dateFinal: String = ""
    var nameFinal: String = ""
    var isUpdate: Boolean = false
    var id: String = ""
    var yearNow = 0
    var monthNow = 0
    var dayNow = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kalender_add)

        mydb = DBHelper(this)
        intent = getIntent()

        // Als er een Task moet worden bijgewerkt word dit true
        isUpdate = intent.getBooleanExtra("isUpdate", false)


        //Calendar instance
        dateFinal = todayDateString()
        val your_date = Date()
        val cal = Calendar.getInstance()
        cal.setTime(your_date)
        startYear = cal.get(Calendar.YEAR)
        startMonth = cal.get(Calendar.MONTH)
        startDay = cal.get(Calendar.DAY_OF_MONTH)
        yearNow = cal.get(Calendar.YEAR)
        monthNow = cal.get(Calendar.MONTH)
        dayNow = cal.get(Calendar.DAY_OF_MONTH)

        if (isUpdate!!) {
            init_update()
        }
    }

    /**
     *  Word opgeroepen wanneer een Task moet worden bijgewerkt
     */
    fun init_update() {

        id = intent.getStringExtra("id")
        val toolbar_task_add_title = findViewById(R.id.toolbar_task_add_title) as TextView
        val task_name = findViewById(R.id.task_name) as EditText
        val task_date = findViewById(R.id.task_date) as EditText

        //title veranderen toolbar
        toolbar_task_add_title.setText("Update")
        val task = mydb.getDataSpecific(id)

        if (task != null) {
            task!!.moveToFirst()

            task_name.setText(task!!.getString(1).toString())
            val cal = KalenderFunction().Epoch2Calender(task!!.getString(2).toString())

            startYear = cal.get(Calendar.YEAR)
            startMonth = cal.get(Calendar.MONTH)
            startDay = cal.get(Calendar.DAY_OF_MONTH)

            task_date.setText(KalenderFunction().Epoch2DateString(task!!.getString(2).toString(), "dd/MM/yyyy"))

        }

    }


    fun todayDateString(): String {
        val dateFormat = SimpleDateFormat(
                "dd/MM/yyyy", Locale.getDefault())

        return dateFormat.toString()

    }

    fun closeAddTask(v: View) {
        finish()
    }


    fun doneAddTask(v: View) {
        var errorStep = 0
        val task_name = findViewById(R.id.task_name) as EditText
        val task_date = findViewById(R.id.task_date) as EditText
        nameFinal = task_name.getText().toString()
        dateFinal = task_date.getText().toString()


        /**
         *  Validatie input
         */

        if (nameFinal.trim { it <= ' ' }.length < 1) {
            errorStep++
            task_name.setError("Geef een taaknaam op.")
        }
        if (dateFinal.trim { it <= ' ' }.length < 4) {
            errorStep++
            task_date.setError("Geef een specifieke datum op.")
        }
        else {
            if(startYear < yearNow) {
                errorStep++
                Toast.makeText(applicationContext, "Datum mag niet in het verleden liggen", Toast.LENGTH_SHORT).show()
            }
            else if(startYear == yearNow && startMonth < monthNow) {
                errorStep++
                Toast.makeText(applicationContext, "Datum mag niet in het verleden liggen", Toast.LENGTH_SHORT).show()
            }
            else if(startYear == yearNow && startMonth == monthNow && startDay < dayNow) {
                errorStep++
                Toast.makeText(applicationContext, "Datum mag niet in het verleden liggen", Toast.LENGTH_SHORT).show()
            }
        }



        if (errorStep == 0) {
            if (isUpdate!!) {
                mydb.updateTask(id, nameFinal, dateFinal)
                Toast.makeText(applicationContext, "Taak bijgewerkt.", Toast.LENGTH_SHORT).show()
            } else {
                mydb.insertTask(nameFinal, dateFinal)
                Toast.makeText(applicationContext, "Taak toegevoegd.", Toast.LENGTH_SHORT).show()
            }

            finish()
        } else {

        }

    }


    public override fun onResume() {
        super.onResume()

    }


    /**
     *  Datum tonen die je hebt gekozen
     */

    override fun onDateSet(view: com.wdullaer.materialdatetimepicker.date.DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        startYear = year
        startMonth = monthOfYear
        startDay = dayOfMonth
        val monthAddOne = startMonth + 1
        val date = (if (startDay < 10) "0$startDay" else "" + startDay) + "/" +
                (if (monthAddOne < 10) "0$monthAddOne" else "" + monthAddOne) + "/" +
                startYear
        val task_date = findViewById(R.id.task_date) as EditText
        task_date.setText(date)
    }

    /**
     *  DatePickerDialog fragment openen
     */

    fun showStartDatePicker(v: View) {

        dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance { view, year, monthOfYear, dayOfMonth -> this@ActivityAddTask; startYear; startMonth; startDay }
        dpd!!.setOnDateSetListener(this)
        dpd!!.show(fragmentManager, "startDatepickerdialog")
    }


}