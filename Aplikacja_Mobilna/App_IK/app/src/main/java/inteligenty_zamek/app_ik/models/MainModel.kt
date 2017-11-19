package inteligenty_zamek.app_ik.models

import android.content.Context
import inteligenty_zamek.app_ik.rest_class.Certyficat
import inteligenty_zamek.app_ik.rest_class.User
import java.util.ArrayList
import java.util.HashMap
import java.util.LinkedHashMap

/**
 * Created by Damian on 26.10.2017.
 */

class MainModel(val user: User)
{
    public var Keys: LinkedHashMap<Int, Certyficat>?=LinkedHashMap()
    public var resultsMap: LinkedHashMap<String, String>?=null
    public var listItems: MutableList<HashMap<String, String>>?= ArrayList()
    public fun  getCertyficateList(context :Context): Array<Certyficat>
    {
        return user.getCertyficateList(context)
    }
    public fun putKeys(context:Context, cs:CharSequence) {

        Keys=LinkedHashMap();
        for (i in 0 until user.getCertyficateList(context).size) {
            if(user.getCertyficateList(context)[i].lockName.toLowerCase().contains(cs.toString().toLowerCase())
                    ||
                    user.getCertyficateList(context)[i].lockLocalization.toLowerCase().contains(cs.toString().toLowerCase())
                    )
            {
                Keys!!.put(i, user.getCertyficateList(context)[i]);
            }

        }

    }
}
