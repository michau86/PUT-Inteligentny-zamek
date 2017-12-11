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
    var position:Int=0
    var i: Byte=0
    var cs: CharSequence=""
    fun putKeys(context:Context, cs:CharSequence) {
        Keys=LinkedHashMap()
        try {
            var j:Int=0;
            for (i in 0 until user.getCertyficateList(context).size) {
                if (user.getCertyficateList(context)[i].lockName.toLowerCase().contains(cs.toString().toLowerCase())
                        ||
                        user.getCertyficateList(context)[i].lockLocalization.toLowerCase().contains(cs.toString().toLowerCase())
                        ) {
                    Keys!!.put(j, user.getCertyficateList(context)[i]);
                    j++
                }

            }
        }catch(exc:Exception){}
    }
}
