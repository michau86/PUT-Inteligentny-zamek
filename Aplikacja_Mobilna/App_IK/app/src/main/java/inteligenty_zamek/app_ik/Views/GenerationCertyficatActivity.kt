package inteligenty_zamek.app_ik.Views

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.view.View
import android.widget.*
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.rest_class.User
import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.rest_class.Lock
import inteligenty_zamek.app_ik.presenters.GenerationCertyficatPresenter
import android.app.DatePickerDialog
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.support.v4.content.res.ResourcesCompat
import android.view.ViewGroup
import android.widget.EditText
import inteligenty_zamek.app_ik.adapters.SpinnerWithoutDeleteAdapter
import java.text.SimpleDateFormat
import java.util.*




class GenerationCertyficatActivity : BaseActivity() {

    private var navMenuTitles: Array<String>? = null
    private var navMenuIcons: TypedArray? = null

    var items = arrayOf("Certyfikat dla użytkownika", "Certyfikat dla gościa")
    var viewFlipper: ViewFlipper?=null
    var spin: Spinner?=null
    var spin2: Spinner?=null
    var lock = ""
    var login = ""
    var presenter: GenerationCertyficatPresenter?=null
    var context: Context?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generation_certyficat)
        navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)

        spin = findViewById(R.id.generationCertyficatspinnerUser) as Spinner

        presenter = GenerationCertyficatPresenter(this)

        val arrayAdapterFroLoginUser = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        arrayAdapterFroLoginUser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin!!.adapter = arrayAdapterFroLoginUser
        context=this

        //pobranie listy zamków oraz użytkowników



        val fontFamily = Typeface.createFromAsset(this.assets, "fonts/fontawesome.ttf")
        var sampleText = this.findViewById(R.id.generationCertificateTextViewErrorIco) as TextView
        sampleText.typeface = fontFamily




        val addRange = findViewById(R.id.generationCertyficatButtonRange) as Button
        addRange.setOnClickListener {
            val value = HashMap<EnumChoice, String>()
            value.put(EnumChoice.choiceLogin, presenter!!.getUserLogin((findViewById(R.id.generationCertyficatspinnerUser) as Spinner).selectedItemPosition))
            value.put(EnumChoice.choiceLock, presenter!!.getLockName((findViewById(R.id.generationCertificateSpinnerLoks) as Spinner).selectedItemPosition))
            sharedPreferenceApi.set(this, value)
            val intent = Intent(this@GenerationCertyficatActivity, GenerateCertyficatRangeActivity::class.java)
            startActivity(intent)
        }

        val generate = findViewById(R.id.generationCertificateButtonSend) as Button
        generate.setOnClickListener {
            val fromDate = (findViewById(R.id.generationCertificateEditTextFrom) as EditText).text.toString()
            val toDate = (findViewById(R.id.generationCertificateEditTextTo) as EditText).text.toString()
            val lockposition = (findViewById(R.id.generationCertificateSpinnerLoks) as Spinner).selectedItemPosition
            val userposition = (findViewById(R.id.generationCertyficatspinnerUser) as Spinner).selectedItemPosition
            val userName = (this.findViewById(R.id.generationCertificateEditTextName) as TextView).text.toString()
            val surname = (this.findViewById(R.id.generationCertificateEditTextSurname) as TextView).text.toString()
            presenter!!.generateCertyficat(fromDate, toDate, lockposition, userposition, userName, surname)
        }


        val myCalendar = Calendar.getInstance()

        val from = findViewById(R.id.generationCertificateEditTextFrom) as EditText
        val dateFrom = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "yyyy-MM-dd"; //In which you need put here
            val sdf = SimpleDateFormat(myFormat, Locale.GERMAN);
            from.setText(sdf.format(myCalendar.getTime()));

        }



        from.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                // TODO Auto-generated method stub
          val data=    DatePickerDialog(this@GenerationCertyficatActivity, dateFrom, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))

                      data.datePicker.minDate=System.currentTimeMillis() - 1000
                      data.show()
                from.background.setColorFilter(ResourcesCompat.getColor(resources,
                        R.color.inpuLneColor, context!!.theme), PorterDuff.Mode.SRC_ATOP)
            }

        })



        val to = findViewById(R.id.generationCertificateEditTextTo) as EditText
        val dateTo = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "yyyy-MM-dd"; //In which you need put here
            val sdf = SimpleDateFormat(myFormat, Locale.GERMAN);
            to.setText(sdf.format(myCalendar.getTime()));

        }


        to.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                // TODO Auto-generated method stub
                val data=    DatePickerDialog(this@GenerationCertyficatActivity, dateTo, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))

                data.datePicker.minDate=System.currentTimeMillis() - 1000
                data.show()
                to.background.setColorFilter(ResourcesCompat.getColor(resources,
                        R.color.inpuLneColor, context!!.theme), PorterDuff.Mode.SRC_ATOP)
            }
        })



        presenter!!.downloadKey()
        presenter!!.downloadUsers()

    }

    fun setDefaultValueFromCertificate(userName:String , userSurname:String, from:String )
    {
        (findViewById(R.id.generationCertificateEditTextFrom) as EditText).setText(from)
        (findViewById(R.id.generationCertificateEditTextName) as EditText).setText(userName)
        (findViewById(R.id.generationCertificateEditTextSurname) as EditText).setText(userSurname)
    }
    fun showMessage(message:String)
    {
        val toast = Toast.makeText(this@GenerationCertyficatActivity, message, Toast.LENGTH_LONG)
        toast.show()
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                toast.show()
            }
            override fun onFinish() {
                toast.show()
            }
        }.start()
    }

fun setDateError()
{
    val et1=findViewById(R.id.generationCertificateEditTextFrom)
    val drawable: Drawable = et1.background // get current EditText drawable
    drawable.setColorFilter(ResourcesCompat.getColor(resources, R.color.errorColor, this.theme), PorterDuff.Mode.SRC_ATOP) // change the drawable color
    et1.background = drawable

    val et2=findViewById(R.id.generationCertificateEditTextTo)
    val drawable2: Drawable = et2.background // get current EditText drawable
    drawable2.setColorFilter(ResourcesCompat.getColor(resources, R.color.errorColor, this.theme), PorterDuff.Mode.SRC_ATOP) // change the drawable color
    et2.background = drawable2


    var textView = findViewById(R.id.generationCertificateTextViewErrorText) as TextView
    textView.visibility = View.VISIBLE


    val params = textView.layoutParams
    params.height = ViewGroup.LayoutParams.WRAP_CONTENT
    textView.layoutParams = params

    textView = findViewById(R.id.generationCertificateTextViewErrorIco) as TextView
    textView.visibility = View.VISIBLE
    textView.layoutParams = params



}





    fun setSpinnerLockList(locklist:Array<Lock?>? )
        {

            runOnUiThread {
                  lock = sharedPreferenceApi.getString(this,EnumChoice.choiceLock)

                val spiner1 = findViewById(R.id.generationCertificateSpinnerLoks) as Spinner
                val listkey = ArrayList<String>()
                val spinerListKeyAdapter = SpinnerWithoutDeleteAdapter(this@GenerationCertyficatActivity, R.layout.spinner_row, listkey, listkey)
                spiner1.adapter = spinerListKeyAdapter
                val list = locklist
                for (i in list!!.indices) {
                    try {
                        if (lock == list[i]!!.name.toString()) {
                            spiner1.setSelection(i)
                        }
                    } catch (e: Exception) {
                    }
                    spinerListKeyAdapter.add(list[i]!!.name)
                    spinerListKeyAdapter.notifyDataSetChanged()
                }
            }
        }

        fun setSpinnerUsersList(userlist:Array<User?>? )
        {

            runOnUiThread {
                login =  sharedPreferenceApi.getString(this,EnumChoice.choiceLogin)

                val spiner1 = findViewById(R.id.generationCertyficatspinnerUser) as Spinner
                val listkey = ArrayList<String>()


                val spinerListKeyAdapter = SpinnerWithoutDeleteAdapter (this@GenerationCertyficatActivity, R.layout.spinner_row2, listkey, listkey)
                spiner1.adapter = spinerListKeyAdapter
                val list = userlist
                for (i in list!!.indices) {
                    try {
                        if (login == list[i]!!.name.toString()) {
                            spiner1.setSelection(i)
                        }
                    } catch (e: Exception) {
                    }
                    spinerListKeyAdapter.add(list[i]!!.login)
                    spinerListKeyAdapter.notifyDataSetChanged()
                }
            }
        }
}