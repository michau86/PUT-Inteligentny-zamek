package inteligenty_zamek.app_ik.API

import android.content.Context
import android.content.SharedPreferences
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.rest_class.GlobalContainer

/**
 * Created by Damian on 28.11.2017.
 */
object sharedPreferenceApi
{
    private var sharedPref: SharedPreferences? = null
    private var editor:SharedPreferences.Editor? =null


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
    fun getString(context : Context, choise: Int): String
    {
        sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
        when(choise) {
            1 -> {
                return sharedPref!!.getString("ipserwer", "")
            }
            2 -> {
                return CyptographyApi.decrypt(sharedPref!!.getString("password", ""));
            }
            3 -> {
                try {
                    return CyptographyApi.decrypt(sharedPref!!.getString("sessionToken", ""), GlobalContainer.getUser(context)!!.password);
                }catch(ex:Exception ){return "";}
                }

            5-> {
                return sharedPref!!.getString("login", "");
            }
            6->{
                return sharedPref!!.getString("name", "");
            }
            7->{
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
    fun getBoolean(context : Context, choise:Int): Boolean
    {
        when(choise) {
            1 -> {
                sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
                return sharedPref!!.getBoolean("isLogin", false);
            }

            2 -> {
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
    public fun set(context : Context,  value: HashMap<Int,String>) {

        if(value==null){return}

        sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
        editor = sharedPref!!.edit()
        var key:Int=1
        while (key<8)
        {
        if(value[key]!=null)
        {
            when(key) {
                1 -> {
                    editor!!.putString("ipserwer", value[key])
                }
                2 -> {
                    editor!!.putString("password", CyptographyApi.encrypt(value[key]))
                }
                3 -> {
                    editor!!.putString("sessionToken", CyptographyApi.encrypt(value[key],GlobalContainer.getUser(context)!!.password))
                }

                5-> {
                    editor!!.putString("login", value[key])
                }
                6->{
                    editor!!.putString("name", value[key])
                }
                7->{
                    editor!!.putString("surname", value[key])
                }
            }

        }
            key++
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
    public fun set(context : Context, value: Boolean, choise: Int)
    {
        if(value==null){return}

        when(choise) {
            1-> {
                sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
                editor = sharedPref!!.edit()
                editor!!.putBoolean("isLogin", value)
            }
            2->{
                sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
                editor = sharedPref!!.edit()
                editor!!.putBoolean("isadmin", value)
            }
            }
            editor!!.commit()

    }

}