package inteligenty_zamek.app_ik.Views

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.view.View
import android.widget.*
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi

import java.util.ArrayList

import inteligenty_zamek.app_ik.rest_class.User
import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.SpinnerAdapter
import inteligenty_zamek.app_ik.rest_class.Lock
import inteligenty_zamek.app_ik.sampledata.GenerateCertyficatRangeActivity
import inteligenty_zamek.app_ik.presenters.GenerationCertyficatPresenter
import java.util.HashMap

class GenerationCertyficatActivity : BaseActivity(), AdapterView.OnItemSelectedListener {

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
        viewFlipper = findViewById(R.id.myViewFlipper) as ViewFlipper

        viewFlipper!!.displayedChild = 0
        spin = findViewById(R.id.spinnerChangeAdminGenerationCertyficat) as Spinner
        spin2 = findViewById(R.id.spinnerChangeAdminGenerationCertyficat2) as Spinner
        spin!!.onItemSelectedListener = this
        spin2!!.onItemSelectedListener = this

        presenter= GenerationCertyficatPresenter(this)

        val arrayAdapterFroLoginUser = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        val arrayAdapterForGuestUser = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        arrayAdapterFroLoginUser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin!!.adapter = arrayAdapterFroLoginUser
        arrayAdapterForGuestUser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin2!!.adapter = arrayAdapterForGuestUser


        //pobranie listy zamków oraz użytkowników
        presenter!!.downloadKey()
        presenter!!.downloadUsers()


        val addRange = findViewById(R.id.buttonRangeGenerateCertyficat) as Button
        addRange.setOnClickListener {
            val value = HashMap<EnumChoice, String>()
            value.put(EnumChoice.choiceLogin, presenter!!.getUserLogin((findViewById(R.id.spinnerUser) as Spinner).selectedItemPosition) )
            value.put(EnumChoice.choiceLock, presenter!!.getLockName((findViewById(R.id.spinnerLoks) as Spinner).selectedItemPosition))
           sharedPreferenceApi.set(this,value)
            val intent = Intent(this@GenerationCertyficatActivity, GenerateCertyficatRangeActivity::class.java)
            startActivity(intent)
        }

        val generate = findViewById(R.id.generujAdmin) as Button
        generate.setOnClickListener {
            val fromDate = (findViewById(R.id.inputfromAdmingenerate) as EditText).toString()
            val toDate = (findViewById(R.id.inputtoadminGenerateKey) as EditText).toString()
            val lockposition = (findViewById(R.id.spinnerLoks) as Spinner).selectedItemPosition
            val userposition = (findViewById(R.id.spinnerUser) as Spinner).selectedItemPosition
            val nameCertyficat = (findViewById(R.id.inputName) as EditText).text.toString()
            val userName = (this.findViewById(R.id.editText7) as TextView).text.toString()
            val surname = (this.findViewById(R.id.editText8) as TextView).text.toString()
            presenter!!.generateCertyficat(fromDate,toDate ,lockposition,userposition,userName,surname)
        }
    }


    override fun onItemSelected(parent: AdapterView<*>, v: View, position: Int,
                                id: Long) {
        if (position == 1) {
           spin2!!.setSelection(1)
        } else {
            spin!!.setSelection(0)
        }
        viewFlipper!!.displayedChild = position
    }

    override fun onNothingSelected(parent: AdapterView<*>) {}

    fun setSpinnerLockList(locklist:Array<Lock?>? )
        {

            runOnUiThread {
                  lock = sharedPreferenceApi.getString(this,EnumChoice.choiceLock)

                val spiner1 = findViewById(R.id.spinnerLoks) as Spinner
                val spinner2=findViewById(R.id.spinnerloks2) as Spinner
                val listkey = ArrayList<String>()
                val spinerListKeyAdapter = SpinnerAdapter(this@GenerationCertyficatActivity, R.layout.spinner_row, listkey, listkey)
                spiner1.adapter = spinerListKeyAdapter
                spinner2.adapter=spinerListKeyAdapter
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

                val spiner1 = findViewById(R.id.spinnerUser) as Spinner
                val listkey = ArrayList<String>()
                val spinerListKeyAdapter = SpinnerAdapter(this@GenerationCertyficatActivity, R.layout.spinner_row, listkey, listkey)
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