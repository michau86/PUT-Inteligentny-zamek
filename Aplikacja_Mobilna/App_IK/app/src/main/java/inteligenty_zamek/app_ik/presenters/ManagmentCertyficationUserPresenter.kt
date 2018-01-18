package inteligenty_zamek.app_ik.presenters

import android.content.Intent
import android.os.Bundle
import inteligenty_zamek.app_ik.API.HTTPRequestAPI
import inteligenty_zamek.app_ik.Views.ManagmentCertyficationUserActivity
import inteligenty_zamek.app_ik.models.ManagmentCertyficationUserModel
import inteligenty_zamek.app_ik.Views.certyficatActivity
import inteligenty_zamek.app_ik.models.Certyficat
import inteligenty_zamek.app_ik.models.GlobalContainer
import org.json.JSONException
import org.json.JSONObject
import java.util.Map
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

/**
 * Created by Damian on 09.12.2017.
 */
class ManagmentCertyficationUserPresenter(val view: ManagmentCertyficationUserActivity)
{

    val model= ManagmentCertyficationUserModel(view)
    fun initActivity() {

        val toSend: HashMap<String, String> = HashMap()
        toSend.put("login", model.login)
        toSend.put("token", model.token)
        try {
            HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/admin/download/all_certificate/", "initResult", toSend).execute()
        } catch (e: Exception) { }
    }

    fun initResult(response:String) {
        try {

            if (JSONObject(response).getString("data") != "empty") {
                val arrJson = JSONObject(response).getJSONArray("data")
                model.Keys = LinkedHashMap()
                for (i in 0 until arrJson.length()) {
                    try {

                        val cert= Certyficat(
                                arrJson.getJSONObject(i).getString("ISACTUAL"),
                                arrJson.getJSONObject(i).getString("IS_PERNAMENT"),
                                arrJson.getJSONObject(i).getString("MONDAY"),
                                arrJson.getJSONObject(i).getString("TUESDAY"),
                                arrJson.getJSONObject(i).getString("WEDNESDAY"),
                                arrJson.getJSONObject(i).getString("THURSDAY"),
                                arrJson.getJSONObject(i).getString("FRIDAY"),
                                arrJson.getJSONObject(i).getString("SUNDAY"),
                                arrJson.getJSONObject(i).getString("SATURDAY"),
                                arrJson.getJSONObject(i).getString("FROM_DATE"),
                                arrJson.getJSONObject(i).getString("TO_DATE"),
                                arrJson.getJSONObject(i).getString("NAME"),
                                arrJson.getJSONObject(i).getString("SURNAME"),
                                arrJson.getJSONObject(i).getString("ID_KEY"),
                                arrJson.getJSONObject(i).getString("LOCK_NAME"),
                                arrJson.getJSONObject(i).getString("LOCALIZATION"),
                                arrJson.getJSONObject(i).getString("ID_LOCK"),
                                arrJson.getJSONObject(i).getString("LOCK_KEY"),
                                arrJson.getJSONObject(i).getString("ID_USER"),
                                arrJson.getJSONObject(i).getString("MAC_ADDRESS")
                        )
                            cert.userName=arrJson.getJSONObject(i).getString("USER_NAME")
                            cert.userSurname=arrJson.getJSONObject(i).getString("USER_SURNAME")
                        model.Keys!!.put(i,cert)


                    } catch (ignored: JSONException) {
                    }

                }

                model.Keys= LinkedHashMap(model.Keys!!.toList().sortedBy { (key, value) -> value.lockName}.toMap())
                updateList("")
            }
        } catch (exc: Exception) {
        }

    }


    fun sortList()
    {



        if(model.flag) {
            model.Keys= LinkedHashMap(model.Keys!!.toList().sortedBy { (key, value) -> value.userName }.toMap())
        }
        else {
            model.Keys= LinkedHashMap(model.Keys!!.toList().sortedByDescending { (key, value) -> value.userName }.toMap())
        }
        model.flag=!model.flag




        updateList(model.cs)

    }




    fun goToCertificate(position:Int)
    {
        GlobalContainer.obj=model.Keys!!.get(model.keysPosition!!.get(position))

        val myIntent = Intent(view, certyficatActivity::class.java)
        val b = Bundle()
        b.putInt("position", -1)
        myIntent.putExtras(b)
        view.startActivityForResult(myIntent, 0)
    }

    fun updateList(cs:CharSequence)
    {
        model.cs=cs
        model.listItems = ArrayList()
        model.keysPosition= ArrayList()
        var inKey=0
        var inList=0
        val it = model.Keys!!.entries.iterator()
        while (it.hasNext()) {
            val resultsMap: LinkedHashMap<String, String> = LinkedHashMap()
            val pair = it.next() as Map.Entry<*, *>
            val certificate=pair.value as Certyficat

            if (certificate.userName.toLowerCase().contains(cs.toString().toLowerCase()) ||
                    certificate.userSurname.toString().toLowerCase().contains(cs.toString().toLowerCase()) ||
                    certificate.lockName.toString().toLowerCase().contains(cs.toString().toLowerCase()) ||
                    certificate.lockLocalization.toString().toLowerCase().contains(cs.toString().toLowerCase())
                    ) {
                model.keysPosition!!.add(inList,inKey)
                inList++
                var value= "Certyfikat użytkownika:\n"+certificate.userName+" "+certificate.userSurname
                resultsMap.put("First Line", value)
                value= "Dla zamka:\n"+certificate.lockName+" "+certificate.lockLocalization
                resultsMap.put("Second Line", value)
                model.listItems.add(resultsMap)
            } else if (cs.toString() === "") {
                model.keysPosition!!.add(inList,inKey)
                inList++
                var value= "Certyfikat użytkownika:\n"+certificate.userName+" "+certificate.userSurname
                resultsMap.put("First Line", value)
                value= "Dla zamka:\n"+certificate.lockName+" "+certificate.lockLocalization
                resultsMap.put("Second Line", value)
                model.listItems.add(resultsMap)
            }
            inKey++
        }
       view.setAdapter()
    }


}
