package inteligenty_zamek.app_ik.Views

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.SpinnerAdapter
import inteligenty_zamek.app_ik.presenters.GenerateCertyficatRangePresenter

class GenerateCertyficatRangeActivity : Activity() {


    var presenter: GenerateCertyficatRangePresenter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_certyficat_range)
        val spiner1 = findViewById(R.id.spinnerMonday) as Spinner
        val spiner2 = findViewById(R.id.spinnerTuesday) as Spinner
        val spiner3 = findViewById(R.id.spinnerSroda) as Spinner
        val spiner4 = findViewById(R.id.spinnerCzwartek) as Spinner
        val spiner5 = findViewById(R.id.spinnerPiatek) as Spinner
        val spiner6 = findViewById(R.id.spinnerSobota) as Spinner
        val spiner7 = findViewById(R.id.spinnerNiedziela) as Spinner

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

        val monday = findViewById(R.id.buttonAddRangeMonday) as Button
        monday.setOnClickListener {
            val from = findViewById(R.id.inputfromPoniedzialek) as EditText
            val to = findViewById(R.id.inputtoPoniedzialek) as EditText
            spinerMondayAdapter.add(from.text.toString() + "-" + to.text.toString())
            spinerMondayAdapter.notifyDataSetChanged()
        }

        val tuesday = findViewById(R.id.buttonAddRangeTuesday) as Button
        tuesday.setOnClickListener {
            val from = findViewById(R.id.inputfromWtorek) as EditText
            val to = findViewById(R.id.inputtoWtorek) as EditText
            spinerTuesdayAdapter.add(from.text.toString() + "-" + to.text.toString())
            spinerTuesdayAdapter.notifyDataSetChanged()
        }

        val wednesday = findViewById(R.id.buttonAddRangeSroda) as Button
        wednesday.setOnClickListener {
            val from = findViewById(R.id.inputfromSroda) as EditText
            val to = findViewById(R.id.inputtoSroda) as EditText
            spinerWednesdayAdapter.add(from.text.toString() + "-" + to.text.toString())
            spinerWednesdayAdapter.notifyDataSetChanged()
        }

        val thurstday = findViewById(R.id.buttonAddRangeCzwartek) as Button
        thurstday.setOnClickListener {
            val from = findViewById(R.id.inputfromCzwartek) as EditText
            val to = findViewById(R.id.inputtoCzwartek) as EditText
            spinerThurstdayAdapter.add(from.text.toString() + "-" + to.text.toString())
            spinerThurstdayAdapter.notifyDataSetChanged()
        }

        val friday = findViewById(R.id.buttonAddRangePiatek) as Button
        friday.setOnClickListener {
            val from = findViewById(R.id.inputfrompiatek) as EditText
            val to = findViewById(R.id.inputtopiatek) as EditText
            spinerFridayAdapter.add(from.text.toString() + "-" + to.text.toString())
            spinerFridayAdapter.notifyDataSetChanged()
        }

        val saturday = findViewById(R.id.buttonAddRangeSobota) as Button
        saturday.setOnClickListener {
            val from = findViewById(R.id.inputfromSobota) as EditText
            val to = findViewById(R.id.inputtoSobota) as EditText
            spinerSaturdayAdapter.add(from.text.toString() + "-" + to.text.toString())
            spinerSaturdayAdapter.notifyDataSetChanged()
        }

        val sunday = findViewById(R.id.buttonAddRangeNiedziela) as Button
        sunday.setOnClickListener {
            val from = findViewById(R.id.inpiutfromNiedziela) as EditText
            val to = findViewById(R.id.inputtoNiedziela) as EditText
            spinerSundayAdapter.add(from.text.toString() + "-" + to.text.toString())
            spinerSundayAdapter.notifyDataSetChanged()
        }
    }

    override fun onBackPressed() {
        val value:BooleanArray= BooleanArray(7)
        val monday = findViewById(R.id.checkBoxPoniedzialek) as CheckBox
        value[0]=monday.isEnabled
        val tuesday = findViewById(R.id.checkBoxWtorek) as CheckBox
        value[1]=tuesday.isEnabled
        val wednesday = findViewById(R.id.checkBoxSroda) as CheckBox
        value[2]=wednesday.isEnabled
        val thurstday = findViewById(R.id.checkBoxCzwartek) as CheckBox
        value[3]=thurstday.isEnabled
        val friday = findViewById(R.id.checkBoxPiatek) as CheckBox
        value[4]=friday.isEnabled
        val saturday = findViewById(R.id.checkBoxSobota) as CheckBox
        value[5]=saturday.isEnabled
        val sunday = findViewById(R.id.checkBoxNiedziela) as CheckBox
        value[6]=sunday.isEnabled
        presenter!!.setValue(value)
        this.finish()
    }


}
