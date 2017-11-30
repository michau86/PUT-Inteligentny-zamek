package inteligenty_zamek.app_ik.presenters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.HTTPRequestAPI
import inteligenty_zamek.app_ik.API.fileReadWriteApi
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.sampledata.CertificationaskActivity
import inteligenty_zamek.app_ik.GenerationCertyfikatForGuestActivity
import inteligenty_zamek.app_ik.models.Managment_certyficationModel
import inteligenty_zamek.app_ik.rest_class.GlobalContainer
import inteligenty_zamek.app_ik.userCertyfikationListActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

/**
 * Created by Damian on 28.11.2017.
 */
class Managment_certyficationPresenter( val view: Context) {
    var model: Managment_certyficationModel=Managment_certyficationModel(view)


        fun getAnotherActivity(position : Int) {
            if (position == 0) {
                val myIntent = Intent(view, userCertyfikationListActivity::class.java)
                view.startActivity(myIntent)
            }
            if (sharedPreferenceApi.getBoolean(view, EnumChoice.isLogin)) {
                if (position == 1) {
                    val myIntent = Intent(view, CertificationaskActivity::class.java)
                    view.startActivity(myIntent)                }
                if (position == 2) {
                    val myIntent = Intent(view, GenerationCertyfikatForGuestActivity::class.java)
                    view.startActivity(myIntent)                }
            }
        }

    fun readfromfile(requestCode:Int,resultCode:Int,data: Intent)
    {
        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            model.selectedfile = data.data
            model.arrJson = null
            try {
                val jObj = JSONObject(fileReadWriteApi.readFromFile(model.selectedfile!!.toString(),view))
                val readcertyficat = fileReadWriteApi.readFromFile(GlobalContainer.getUser(view).login,view)
                model.arrJson = JSONArray(readcertyficat)
                model!!.arrJson!!.put(jObj)

            } catch (e: Exception) {
            }
            fileReadWriteApi.writeToFile(model.arrJson!!.toString(), view, GlobalContainer.getUser(view).login)
        }

    }

    fun isLogin():Boolean
    {
        return model.islogin
    }

    fun setAdapter(): ArrayAdapter<String>
    {
        return model.adapter
    }

    fun getCertyficat()
    {
        val toSend: HashMap<String, String> = HashMap()
        toSend.put("login", model.login)
        toSend.put("token", model.token)
        try {
            HTTPRequestAPI(this, "http://" + model.ipaddres + ":8080/api/download/all_certifacate/", "downloadResult", toSend).execute()
        } catch (e: Exception) { }

    }

    fun downloadResult(response:String)
    {
        try {
            val jObj: JSONObject = JSONObject(response)
            val arrJson = jObj.getJSONArray("data")
            fileReadWriteApi.writeToFile(arrJson.toString(), view, model.login)
            GlobalContainer.getUser(view).addCertyficatList(arrJson)

            val toast = Toast.makeText(view, "dodano certyfikaty", Toast.LENGTH_LONG)
            toast.show()
            object : CountDownTimer(4000.toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    toast.show()
                }
                override fun onFinish() {
                    toast.show()
                }
            }.start()

        } catch (e: JSONException) {

            val toast = Toast.makeText(view, "brak certyfikatow", Toast.LENGTH_LONG)
            toast.show()
            object : CountDownTimer(4000.toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    toast.show()
                }
                override fun onFinish() {
                    toast.show()
                }
            }.start()

    }

}

}

