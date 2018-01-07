package inteligenty_zamek.app_ik.inWork

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Damian on 07.01.2018.
 */
class UserPresenter(val view:UserActivity)
{
    val model=UserModel(view)

    fun chekDate()
    {
        val myCalendar = Calendar.getInstance()
        val tim=myCalendar.time

        val myFormat = "yyyy-MM-dd hh:mm:ss"; //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.GERMAN);


        Log.i("HHHH", sdf.format(myCalendar.getTime()))

        val date1 = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(sdf.format(myCalendar.getTime()))
        val date2 = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(model.user.validitiy_period.replace("T"," "))

        if (date1.compareTo(date2) > 0) {
        view.blockButton()
        }
    }


}