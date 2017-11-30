package inteligenty_zamek.app_ik.API

import android.content.Context
import android.content.SharedPreferences
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.rest_class.GlobalContainer

/**
 * Created by Damian on 28.11.2017.
 */




object sharedPreferenceApi {
    private var sharedPref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    enum class choise
    {
        ip,password,token,login,nameuser,surname,isLogin,isAdmin
    }

    /**
     *
     * This function i using to get value from Shared PReferences
     *
     * @param context
     * @param choise is a number using to get concrete value
     * 1->ip addres,
     * 2->password,
     * 3->token,
     * 5-> Login
     *6->name
     * 7->surname
     */
    fun getString(context : Context, choise: choise): String
    {
        sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
        when(choise) {
            sharedPreferenceApi.choise.ip -> {
                return sharedPref!!.getString("ipserwer", "")
            }
            sharedPreferenceApi.choise.password -> {
                return CyptographyApi.decrypt(sharedPref!!.getString("password", ""));
            }
            sharedPreferenceApi.choise.token -> {
                try {
                    return CyptographyApi.decrypt(sharedPref!!.getString("sessionToken", ""), GlobalContainer.getUser(context)!!.password);
                }catch(ex:Exception ){return "";}
                }

            sharedPreferenceApi.choise.login-> {
                return sharedPref!!.getString("login", "");
            }
            sharedPreferenceApi.choise.nameuser->{
                return sharedPref!!.getString("name", "");
            }
            sharedPreferenceApi.choise.surname->{
                return sharedPref!!.getString("surname", "");
            }
        }
        return "ERRROR"

    }

    /**
 *
 * This function i using to get value from Shared PReferences
 *
 * @param context
 * @param choise is a number using to get concrete value
 * 1->user is login
 * 2->user is admin,
 */
    fun getBoolean(context : Context, choise:choise): Boolean
    {
        when(choise) {
            sharedPreferenceApi.choise.isLogin -> {
                sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
                return sharedPref!!.getBoolean("isLogin", false);
            }

            sharedPreferenceApi.choise.isAdmin -> {
                sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
                return sharedPref!!.getBoolean("isadmin", false);
            }
        }

        return false
    }

    /**
     *
     * This function i using to set value to Shared PReferences
     *
     * @param context
     * @param value is a hashmap with vlaue where key is
     * 1->ip addres,
     * 2->password,
     * 3->token,
     * 5-> Login
     * 6->name
     * 7->surname
     *
     */
    public fun set(context : Context,  hashMapvalue: HashMap<choise,String>) {

        if(hashMapvalue==null){return}

        sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
        editor = sharedPref!!.edit()
        var key:Int=1

        for ((key, value) in hashMapvalue) {
            when (key) {
                sharedPreferenceApi.choise.ip -> {
                    editor!!.putString("ipserwer", value)
                }
                sharedPreferenceApi.choise.password -> {
                    editor!!.putString("password", CyptographyApi.encrypt(value))
                }
                sharedPreferenceApi.choise.token -> {
                    editor!!.putString("sessionToken", CyptographyApi.encrypt(value, GlobalContainer.getUser(context)!!.password))
                }

                sharedPreferenceApi.choise.login -> {
                    editor!!.putString("login", value)
                }
                sharedPreferenceApi.choise.nameuser -> {
                    editor!!.putString("name", value)
                }
                sharedPreferenceApi.choise.surname -> {
                    editor!!.putString("surname", value)
                }
            }
        }


        editor!!.commit()
    }

    /**
     *
     * This function i using to set value to Shared PReferences
     *
     * @param context
     * @param value value to send
     * @param choise is a number using to get concrete value
     * 1->user is login
     * 2->user is admin,
     */
    public fun set(context : Context, value: Boolean, choise: choise)
    {
        if(value==null){return}

        when(choise) {
            sharedPreferenceApi.choise.isLogin-> {
                sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
                editor = sharedPref!!.edit()
                editor!!.putBoolean("isLogin", value)
            }
            sharedPreferenceApi.choise.isAdmin->{
                sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
                editor = sharedPref!!.edit()
                editor!!.putBoolean("isadmin", value)
            }
            }
            editor!!.commit()

    }

}