package inteligenty_zamek.app_ik.beforeTest

import android.content.Intent
import android.os.Bundle
import inteligenty_zamek.app_ik.rest_class.GlobalContainer
import inteligenty_zamek.app_ik.sampledata.certyficatActivity
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
        if (!model.flag)
        {
        Arrays.sort(GlobalContainer.getUser(view).getCertyficateList(view), Collections.reverseOrder())
        }
        else
        {
        Arrays.sort(GlobalContainer.getUser(view).getCertyficateList(view))
        }


        model.Keys = LinkedHashMap()
        try {
            for (i in 0 until GlobalContainer.getUser(view).getCertyficateList(view).size) {
                model.Keys!!.put(GlobalContainer.getUser(view).getCertyficateList(view)[i].getLockLocalization(), GlobalContainer.getUser(view).getCertyficateList(view)[i].getLockName())
            }
        } catch (e: Exception) {}
    }
    fun goToCertyficat(position:Int)
    {
                val myIntent = Intent(view, certyficatActivity::class.java)
                val b = Bundle()
                b.putString("lock", model.listItems!![position].values.toTypedArray()[0].toString())
                b.putString("name", model.listItems!![position].values.toTypedArray()[1].toString())
                myIntent.putExtras(b)
                view.startActivityForResult(myIntent, 0)
            }

    fun setValueSearch(cs:CharSequence)
    {
        model.cs=cs
    }
    fun setValueToAdapter()
    {
                    val it = model!!.Keys!!.entries.iterator()
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
        setKeys()
        model.flag=!model.flag
        setValueToAdapter()
    }




}