package inteligenty_zamek.app_ik.API

import android.content.Context
import android.content.SharedPreferences
import inteligenty_zamek.app_ik.R

/**
 * Created by Damian on 28.11.2017.
 */

enum class EnumChoice(val value:String)
{
    ip("ipserwer"),password("password"),token("sessionToken"),
    login("login"),nameuser("name"),surname("surname"),
    isLogin("isLogin"),isAdmin("isadmin"),choiceLogin("choiceLogin"),choiceLock("choiceLock"),
    publicKey("publicKey")
}


object sharedPreferenceApi {
    private var sharedPref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null



    fun getBoolean(context : Context, choise:EnumChoice): Boolean
    {
        sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
        return sharedPref!!.getBoolean(choise.value, false)
    }

    fun getString(context : Context, choise: EnumChoice): String
    {
        sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
        return sharedPref!!.getString(choise.value, "")
    }

    fun set(context : Context,  hashMapvalue: HashMap<EnumChoice,String>) {

        if(hashMapvalue==null){return}
        sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
        editor = sharedPref!!.edit()
        for ((key, value) in hashMapvalue) {
            editor!!.putString(key.value, value)
        }
        editor!!.commit()
    }

    fun set(context : Context, value: Boolean, choise: EnumChoice)
    {
        if(value==null){return}
        sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
        editor = sharedPref!!.edit()
        editor!!.putBoolean(choise.value, value)
            editor!!.commit()
    }

}