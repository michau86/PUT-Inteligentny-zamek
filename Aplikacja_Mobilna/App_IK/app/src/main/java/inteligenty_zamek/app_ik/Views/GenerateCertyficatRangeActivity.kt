package inteligenty_zamek.app_ik.Views

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.os.Bundle
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.adapters.SpinnerAdapter
import inteligenty_zamek.app_ik.presenters.GenerateCertyficatRangePresenter
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.*
import inteligenty_zamek.app_ik.API.Valdiation
import java.util.*


class GenerateCertyficatRangeActivity : Activity()   {


    var presenter: GenerateCertyficatRangePresenter?=null
    val context:Context=this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_certyficat_range)
        val spiner1 = findViewById(R.id.generationCertificateRangeSpinnerMonday) as Spinner
        val spiner2 = findViewById(R.id.generationCertificateRangeSpinnerTuesday) as Spinner
        val spiner3 = findViewById(R.id.generationCertificateRangeSpinnerWednesday) as Spinner
        val spiner4 = findViewById(R.id.generationCertificateRangeSpinnerThursday) as Spinner
        val spiner5 = findViewById(R.id.generationCertificateRangeSpinnerFriday) as Spinner
        val spiner6 = findViewById(R.id.generationCertificateRangeSpinnerSaturday) as Spinner
        val spiner7 = findViewById(R.id.generationCertificateRangeSpinnerSunday) as Spinner

        presenter= GenerateCertyficatRangePresenter(this)
        val spinerMondayAdapter = SpinnerAdapter(this, R.layout.spinner_row,
                presenter!!.getDayList(0), presenter!!.getDayList(0))
        val spinerTuesdayAdapter = SpinnerAdapter(this, R.layout.spinner_row,
                presenter!!.getDayList(1), presenter!!.getDayList(1))
        val spinerWednesdayAdapter = SpinnerAdapter(this, R.layout.spinner_row,
                presenter!!.getDayList(2), presenter!!.getDayList(2))
        val spinerThurstdayAdapter = SpinnerAdapter(this, R.layout.spinner_row,
                presenter!!.getDayList(3), presenter!!.getDayList(3))
        val spinerFridayAdapter = SpinnerAdapter(this, R.layout.spinner_row,
                presenter!!.getDayList(4), presenter!!.getDayList(4))
        val spinerSaturdayAdapter = SpinnerAdapter(this, R.layout.spinner_row,
                presenter!!.getDayList(5), presenter!!.getDayList(5))
        val spinerSundayAdapter = SpinnerAdapter(this, R.layout.spinner_row,
                presenter!!.getDayList(6), presenter!!.getDayList(6))


        spiner1.adapter = spinerMondayAdapter
        spiner2.adapter = spinerTuesdayAdapter
        spiner3.adapter = spinerWednesdayAdapter
        spiner4.adapter = spinerThurstdayAdapter
        spiner5.adapter = spinerFridayAdapter
        spiner6.adapter = spinerSaturdayAdapter
        spiner7.adapter = spinerSundayAdapter
        //dodawniae nowej wartosci




        val fontFamily = Typeface.createFromAsset(this.assets, "fonts/fontawesome.ttf")
        var textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoMonday) as TextView
        textView.typeface = fontFamily
        textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoTuesday) as TextView
        textView.typeface = fontFamily
        textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoWednesday) as TextView
        textView.typeface = fontFamily
        textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoThursday) as TextView
        textView.typeface = fontFamily
        textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoFriday) as TextView
        textView.typeface = fontFamily
        textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoSaturday) as TextView
        textView.typeface = fontFamily
        textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoSunday) as TextView
        textView.typeface = fontFamily






        //MONDAY
        val monday = findViewById(R.id.generationCertificateRangeButtonAddRangeMonday) as Button
        monday.setOnClickListener {

            val from = findViewById(R.id.generationCertificateRangeEditTextFromMonday) as EditText
            val to = findViewById(R.id.generationCertificateRangeEditTextToMonday) as EditText
            if(Valdiation.biggerThanTime(from.text.toString(),to.text.toString())) {
                spinerMondayAdapter.add(from.text.toString() + "-" + to.text.toString())
                spinerMondayAdapter.notifyDataSetChanged()

                from.setText("")
                to.setText("")
            }
            else
            {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextMonday) as TextView
                textView.visibility = View.VISIBLE
                val params = textView.layoutParams
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoMonday) as TextView
                textView.visibility = View.VISIBLE
                textView.layoutParams = params
            }
        }
        (findViewById(R.id.generationCertificateRangeEditTextFromMonday) as EditText)  .setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextMonday) as TextView
                textView.visibility = View.INVISIBLE
                val params = textView.layoutParams
                params.height = 0
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoMonday) as TextView
                textView.visibility = View.INVISIBLE
                textView.layoutParams = params
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute -> (findViewById(R.id.generationCertificateRangeEditTextFromMonday) as EditText) .setText(selectedHour.toString()+":"+selectedMinute.toString() ) }, hour, minute, true)
                mTimePicker.setTitle("wybierz godzine")
                mTimePicker.show()

            }
        })

        (findViewById(R.id.generationCertificateRangeEditTextToMonday) as EditText)  .setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextMonday) as TextView
                textView.visibility = View.INVISIBLE
                val params = textView.layoutParams
                params.height = 0
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoMonday) as TextView
                textView.visibility = View.INVISIBLE
                textView.layoutParams = params
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener
                { timePicker, selectedHour, selectedMinute ->
                    (findViewById(R.id.generationCertificateRangeEditTextToMonday) as EditText)
                        .setText(selectedHour.toString()+":"+selectedMinute.toString() ) }, hour, minute, true)
                mTimePicker.setTitle("wybierz godzine")
                mTimePicker.show()

            }
        })
        //TUESDAY
        val tuesday = findViewById(R.id.generationCertificateRangeButtonAddRangeTuesday) as Button
        tuesday.setOnClickListener {

            val from = findViewById(R.id.generationCertificateRangeEditTextFromTuesday) as EditText
            val to = findViewById(R.id.generationCertificateRangeEditTextToTuesday) as EditText
            if(Valdiation.biggerThanTime(from.text.toString(),to.text.toString())) {
                spinerTuesdayAdapter.add(from.text.toString() + "-" + to.text.toString())
                spinerTuesdayAdapter.notifyDataSetChanged()

                from.setText("")
                to.setText("")
            }
            else
            {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextTuesday) as TextView
                textView.visibility = View.VISIBLE
                val params = textView.layoutParams
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoTuesday) as TextView
                textView.visibility = View.VISIBLE
                textView.layoutParams = params
            }
        }
        (findViewById(R.id.generationCertificateRangeEditTextFromTuesday) as EditText)  .setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextTuesday) as TextView
                textView.visibility = View.INVISIBLE
                val params = textView.layoutParams
                params.height = 0
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoTuesday) as TextView
                textView.visibility = View.INVISIBLE
                textView.layoutParams = params
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute -> (findViewById(R.id.generationCertificateRangeEditTextFromTuesday) as EditText) .setText(selectedHour.toString()+":"+selectedMinute.toString() ) }, hour, minute, true)
                mTimePicker.setTitle("wybierz godzine")
                mTimePicker.show()

            }
        })

        (findViewById(R.id.generationCertificateRangeEditTextToTuesday) as EditText)  .setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextTuesday) as TextView
                textView.visibility = View.INVISIBLE
                val params = textView.layoutParams
                params.height = 0
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoTuesday) as TextView
                textView.visibility = View.INVISIBLE
                textView.layoutParams = params
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener
                { timePicker, selectedHour, selectedMinute ->
                    (findViewById(R.id.generationCertificateRangeEditTextToTuesday) as EditText)
                            .setText(selectedHour.toString()+":"+selectedMinute.toString() ) }, hour, minute, true)
                mTimePicker.setTitle("wybierz godzine")
                mTimePicker.show()

            }
        })

        //WEDNESDAY
        val wednesday = findViewById(R.id.generationCertificateRangeButtonAddRangeWednesday) as Button
        wednesday.setOnClickListener {

            val from = findViewById(R.id.generationCertificateRangeEditTextFromWednesday) as EditText
            val to = findViewById(R.id.generationCertificateRangeEditTextToWednesday) as EditText
            if(Valdiation.biggerThanTime(from.text.toString(),to.text.toString())) {
                spinerWednesdayAdapter.add(from.text.toString() + "-" + to.text.toString())
                spinerWednesdayAdapter.notifyDataSetChanged()

                from.setText("")
                to.setText("")
            }
            else
            {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextWednesday) as TextView
                textView.visibility = View.VISIBLE
                val params = textView.layoutParams
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoWednesday) as TextView
                textView.visibility = View.VISIBLE
                textView.layoutParams = params
            }
        }
        (findViewById(R.id.generationCertificateRangeEditTextFromWednesday) as EditText)  .setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextWednesday) as TextView
                textView.visibility = View.INVISIBLE
                val params = textView.layoutParams
                params.height = 0
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoWednesday) as TextView
                textView.visibility = View.INVISIBLE
                textView.layoutParams = params
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute -> (findViewById(R.id.generationCertificateRangeEditTextFromWednesday) as EditText) .setText(selectedHour.toString()+":"+selectedMinute.toString() ) }, hour, minute, true)
                mTimePicker.setTitle("wybierz godzine")
                mTimePicker.show()

            }
        })

        (findViewById(R.id.generationCertificateRangeEditTextToWednesday) as EditText)  .setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextWednesday) as TextView
                textView.visibility = View.INVISIBLE
                val params = textView.layoutParams
                params.height = 0
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoWednesday) as TextView
                textView.visibility = View.INVISIBLE
                textView.layoutParams = params
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener
                { timePicker, selectedHour, selectedMinute ->
                    (findViewById(R.id.generationCertificateRangeEditTextToWednesday) as EditText)
                            .setText(selectedHour.toString()+":"+selectedMinute.toString() ) }, hour, minute, true)
                mTimePicker.setTitle("wybierz godzine")
                mTimePicker.show()

            }
        })

        //THURSTDAY
        val thursday = findViewById(R.id.generationCertificateRangeButtonAddRangeThursday) as Button
        thursday.setOnClickListener {

            val from = findViewById(R.id.generationCertificateRangeEditTextFromThursday) as EditText
            val to = findViewById(R.id.generationCertificateRangeEditTextToThursday) as EditText
            if(Valdiation.biggerThanTime(from.text.toString(),to.text.toString())) {
                spinerThurstdayAdapter.add(from.text.toString() + "-" + to.text.toString())
                spinerThurstdayAdapter.notifyDataSetChanged()

                from.setText("")
                to.setText("")
            }
            else
            {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextThursday) as TextView
                textView.visibility = View.VISIBLE
                val params = textView.layoutParams
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoThursday) as TextView
                textView.visibility = View.VISIBLE
                textView.layoutParams = params
            }
        }
        (findViewById(R.id.generationCertificateRangeEditTextFromThursday) as EditText)  .setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextThursday) as TextView
                textView.visibility = View.INVISIBLE
                val params = textView.layoutParams
                params.height = 0
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoThursday) as TextView
                textView.visibility = View.INVISIBLE
                textView.layoutParams = params
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute -> (findViewById(R.id.generationCertificateRangeEditTextFromThursday) as EditText) .setText(selectedHour.toString()+":"+selectedMinute.toString() ) }, hour, minute, true)
                mTimePicker.setTitle("wybierz godzine")
                mTimePicker.show()

            }
        })

        (findViewById(R.id.generationCertificateRangeEditTextToThursday) as EditText)  .setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextThursday) as TextView
                textView.visibility = View.INVISIBLE
                val params = textView.layoutParams
                params.height = 0
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoThursday) as TextView
                textView.visibility = View.INVISIBLE
                textView.layoutParams = params
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener
                { timePicker, selectedHour, selectedMinute ->
                    (findViewById(R.id.generationCertificateRangeEditTextToThursday) as EditText)
                            .setText(selectedHour.toString()+":"+selectedMinute.toString() ) }, hour, minute, true)
                mTimePicker.setTitle("wybierz godzine")
                mTimePicker.show()

            }
        })

        //FRIDAY
        val friday = findViewById(R.id.generationCertificateRangeButtonAddRangeFriday) as Button
       friday.setOnClickListener {

            val from = findViewById(R.id.generationCertificateRangeEditTextFromFriday) as EditText
            val to = findViewById(R.id.generationCertificateRangeEditTextToFriday) as EditText
            if(Valdiation.biggerThanTime(from.text.toString(),to.text.toString())) {
                spinerFridayAdapter.add(from.text.toString() + "-" + to.text.toString())
                spinerFridayAdapter.notifyDataSetChanged()

                from.setText("")
                to.setText("")
            }
            else
            {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextFriday) as TextView
                textView.visibility = View.VISIBLE
                val params = textView.layoutParams
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoFriday) as TextView
                textView.visibility = View.VISIBLE
                textView.layoutParams = params
            }
        }
        (findViewById(R.id.generationCertificateRangeEditTextFromFriday) as EditText)  .setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextFriday) as TextView
                textView.visibility = View.INVISIBLE
                val params = textView.layoutParams
                params.height = 0
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoFriday) as TextView
                textView.visibility = View.INVISIBLE
                textView.layoutParams = params
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute -> (findViewById(R.id.generationCertificateRangeEditTextFromFriday) as EditText) .setText(selectedHour.toString()+":"+selectedMinute.toString() ) }, hour, minute, true)
                mTimePicker.setTitle("wybierz godzine")
                mTimePicker.show()

            }
        })

        (findViewById(R.id.generationCertificateRangeEditTextToFriday) as EditText)  .setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextFriday) as TextView
                textView.visibility = View.INVISIBLE
                val params = textView.layoutParams
                params.height = 0
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoFriday) as TextView
                textView.visibility = View.INVISIBLE
                textView.layoutParams = params
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener
                { timePicker, selectedHour, selectedMinute ->
                    (findViewById(R.id.generationCertificateRangeEditTextToFriday) as EditText)
                            .setText(selectedHour.toString()+":"+selectedMinute.toString() ) }, hour, minute, true)
                mTimePicker.setTitle("wybierz godzine")
                mTimePicker.show()

            }
        })

        //SATURDAY
        val saturday = findViewById(R.id.generationCertificateRangeButtonAddRangeSaturday) as Button
        saturday.setOnClickListener {

            val from = findViewById(R.id.generationCertificateRangeEditTextFromSaturday) as EditText
            val to = findViewById(R.id.generationCertificateRangeEditTextToSaturday) as EditText
            if(Valdiation.biggerThanTime(from.text.toString(),to.text.toString())) {
                spinerSaturdayAdapter.add(from.text.toString() + "-" + to.text.toString())
                spinerSaturdayAdapter.notifyDataSetChanged()

                from.setText("")
                to.setText("")
            }
            else
            {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextSaturday) as TextView
                textView.visibility = View.VISIBLE
                val params = textView.layoutParams
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoSaturday) as TextView
                textView.visibility = View.VISIBLE
                textView.layoutParams = params
            }
        }
        (findViewById(R.id.generationCertificateRangeEditTextFromSaturday) as EditText)  .setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextSaturday) as TextView
                textView.visibility = View.INVISIBLE
                val params = textView.layoutParams
                params.height = 0
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoSaturday) as TextView
                textView.visibility = View.INVISIBLE
                textView.layoutParams = params
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute -> (findViewById(R.id.generationCertificateRangeEditTextFromSaturday) as EditText) .setText(selectedHour.toString()+":"+selectedMinute.toString() ) }, hour, minute, true)
                mTimePicker.setTitle("wybierz godzine")
                mTimePicker.show()

            }
        })

        (findViewById(R.id.generationCertificateRangeEditTextToSaturday) as EditText)  .setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextSaturday) as TextView
                textView.visibility = View.INVISIBLE
                val params = textView.layoutParams
                params.height = 0
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoSaturday) as TextView
                textView.visibility = View.INVISIBLE
                textView.layoutParams = params
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener
                { timePicker, selectedHour, selectedMinute ->
                    (findViewById(R.id.generationCertificateRangeEditTextToSaturday) as EditText)
                            .setText(selectedHour.toString()+":"+selectedMinute.toString() ) }, hour, minute, true)
                mTimePicker.setTitle("wybierz godzine")
                mTimePicker.show()

            }
        })


        //SUNDAY
        val sunday = findViewById(R.id.generationCertificateRangeButtonAddRangeSunday) as Button
        sunday.setOnClickListener {

            val from = findViewById(R.id.generationCertificateRangeEditTextFromSunday) as EditText
            val to = findViewById(R.id.generationCertificateRangeEditTextToSunday) as EditText
            if(Valdiation.biggerThanTime(from.text.toString(),to.text.toString())) {
                spinerSundayAdapter.add(from.text.toString() + "-" + to.text.toString())
                spinerSundayAdapter.notifyDataSetChanged()

                from.setText("")
                to.setText("")
            }
            else
            {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextSunday) as TextView
                textView.visibility = View.VISIBLE
                val params = textView.layoutParams
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoSunday) as TextView
                textView.visibility = View.VISIBLE
                textView.layoutParams = params
            }
        }
        (findViewById(R.id.generationCertificateRangeEditTextFromSunday) as EditText)  .setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextSunday) as TextView
                textView.visibility = View.INVISIBLE
                val params = textView.layoutParams
                params.height = 0
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoSunday) as TextView
                textView.visibility = View.INVISIBLE
                textView.layoutParams = params
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute -> (findViewById(R.id.generationCertificateRangeEditTextFromSunday) as EditText) .setText(selectedHour.toString()+":"+selectedMinute.toString() ) }, hour, minute, true)
                mTimePicker.setTitle("wybierz godzine")
                mTimePicker.show()

            }
        })

        (findViewById(R.id.generationCertificateRangeEditTextToSunday) as EditText)  .setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                var textView = findViewById(R.id.generationCertificateRangeTextViewErrorTextSunday) as TextView
                textView.visibility = View.INVISIBLE
                val params = textView.layoutParams
                params.height = 0
                textView.layoutParams = params
                textView = findViewById(R.id.generationCertificateRangeTextViewErrorIcoSunday) as TextView
                textView.visibility = View.INVISIBLE
                textView.layoutParams = params
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener
                { timePicker, selectedHour, selectedMinute ->
                    (findViewById(R.id.generationCertificateRangeEditTextToSunday) as EditText)
                            .setText(selectedHour.toString()+":"+selectedMinute.toString() ) }, hour, minute, true)
                mTimePicker.setTitle("wybierz godzine")
                mTimePicker.show()

            }
        })


    }

    override fun onBackPressed() {
        val value:BooleanArray= BooleanArray(7)
        val monday = findViewById(R.id.generationCertificateRangeCheckBoxMonday) as CheckBox
        value[0]=monday.isEnabled
        val tuesday = findViewById(R.id.generationCertificateRangeCheckBoxTuesday) as CheckBox
        value[1]=tuesday.isEnabled
        val wednesday = findViewById(R.id.generationCertificateRangeCheckBoxWednesday) as CheckBox
        value[2]=wednesday.isEnabled
        val thurstday = findViewById(R.id.generationCertificateRangeCheckBoxThursday) as CheckBox
        value[3]=thurstday.isEnabled
        val friday = findViewById(R.id.generationCertificateRangeCheckBoxFriday) as CheckBox
        value[4]=friday.isEnabled
        val saturday = findViewById(R.id.generationCertificateRangeCheckBoxSaturday) as CheckBox
        value[5]=saturday.isEnabled
        val sunday = findViewById(R.id.generationCertificateRangeCheckBoxSunday) as CheckBox
        value[6]=sunday.isEnabled
        presenter!!.setValue(value)
        this.finish()
    }


}
