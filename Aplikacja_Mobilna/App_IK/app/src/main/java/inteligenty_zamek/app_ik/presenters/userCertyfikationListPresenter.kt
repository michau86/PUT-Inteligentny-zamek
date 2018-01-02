package inteligenty_zamek.app_ik.presenters

import android.content.Intent
import android.os.Bundle
import inteligenty_zamek.app_ik.Views.userCertyfikationListActivity
import inteligenty_zamek.app_ik.Views.certyficatActivity
import inteligenty_zamek.app_ik.models.userCertyfikationListModel
import inteligenty_zamek.app_ik.rest_class.GlobalContainer
import java.util.*

/**
 * Created by Damian on 08.12.2017.
 */
class userCertyfikationListPresenter(val view: userCertyfikationListActivity)
{
        val model: userCertyfikationListModel = userCertyfikationListModel()

    init{
        setKeys()
    }
    fun setKeys()
    {
        model.Keys = LinkedHashMap()
        if(GlobalContainer.getUser(view).getCertyficateList(view)==null) {return;}
            if (!model.flag) {
                Arrays.sort(GlobalContainer.getUser(view).getCertyficateList(view), Collections.reverseOrder())
            } else {
                Arrays.sort(GlobalContainer.getUser(view).getCertyficateList(view))
            }



        try {
            for (i in 0 until GlobalContainer.getUser(view).getCertyficateList(view).size) {
                model.Keys!!.put(GlobalContainer.getUser(view).getCertyficateList(view)[i].lockLocalization, GlobalContainer.getUser(view).getCertyficateList(view)[i].lockName)
            }
        } catch (e: Exception) {}
    }
    fun goToCertyficat(position:Int)
    {
                val myIntent = Intent(view, certyficatActivity::class.java)
                val b = Bundle()
                b.putInt("position", position)
                myIntent.putExtras(b)
                view.startActivityForResult(myIntent, 0)
            }

    fun setValueSearch(cs:CharSequence)
    {
        model.cs=cs
    }
    fun setValueToAdapter()
    {
                    val it = model.Keys!!.entries.iterator()
                    model.listItems= ArrayList()
                    var resultsMap: LinkedHashMap<String, String>

                    while (it.hasNext()) {
                        resultsMap = LinkedHashMap()
                        val pair = it.next() as Map.Entry<*,*>


                        if (pair.key.toString().toLowerCase().contains(model.cs.toString().toLowerCase()) || pair.value.toString().toLowerCase().contains(model.cs.toString().toLowerCase())) {
                            resultsMap.put("First Line", pair.key.toString())
                            resultsMap.put("Second Line", pair.value.toString())
                            model.listItems!!.add(resultsMap)
                        } else if (model.cs.toString() === "") {
                            resultsMap.put("First Line", pair.key.toString())
                            resultsMap.put("Second Line", pair.value.toString())
                            model.listItems!!.add(resultsMap)
                        }
                    }

                }

    fun sortList()
    {
        model.flag=!model.flag
        setKeys()
        setValueToAdapter()
    }




}