package inteligenty_zamek.app_ik.inWork

import android.util.Log
import inteligenty_zamek.app_ik.API.HTTPRequestAPI
import inteligenty_zamek.app_ik.rest_class.GlobalContainer
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 * Created by Damian on 06.01.2018.
 */
class UserListPresenter(val view:UserListActivity)
{
    val model=UserListModel(view)



    fun downloadUsers()
    {
        val toSend: HashMap<String, String> = HashMap()
        toSend.put("login", model.login)
        toSend.put("token", model.token)
        try {

            HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/download/all_user/", "downloadUsersResult", toSend).execute()
        } catch (e: Exception) {
        }
    }

    fun downloadUsersResult(result:String)
    {
        try {
            val arrJson = JSONObject(result).getJSONArray("data")
            model.addUserList(arrJson)
            setValueToAdapter()
        } catch (e: JSONException) {}


    }


    fun sort(sort:Boolean)
    {
        if(sort) {
           Collections.sort(model.userlist2!!)
        }
        else
        {
            //Collections.sort(model.userlist2!!), Collections.reverseOrder<Any>())
                     Collections.reverse(model.userlist2);
        }
        setValueToAdapter()
    }

    fun setValueToAdapter()
    {
    view.setAdapter()
    }

    fun setIsActive(isActive:Boolean)
    {
      model.isActive=isActive
        search(model.cs)
    }
    fun search(cs:CharSequence)
    {
        model.cs=cs
        model.userlist2= ArrayList()
        for(item in model.userlist!! )
        {
            if (item!!.login.toLowerCase().contains(cs.toString().toLowerCase())
                    || item.name.toLowerCase().contains(cs.toString().toLowerCase()) )
            {
                if(model.isActive) {
                 if(item.isActive) {
                     model.userlist2!!.add(item)
                 }
                }
                else
                {
                    model.userlist2!!.add(item)
                }
            }
            else if(cs=="")
            {
                if(model.isActive) {
                    if(item.isActive) {
                        model.userlist2!!.add(item)
                    }
               }
                else
                {
                    model.userlist2!!.add(item)
                }
            }

        }
        setValueToAdapter()
    }



    fun goToUserView(position:Int)
    {
    GlobalContainer.obj=model.userlist2!!.get(position)
        ////przejscie do widoku XD
    }

}