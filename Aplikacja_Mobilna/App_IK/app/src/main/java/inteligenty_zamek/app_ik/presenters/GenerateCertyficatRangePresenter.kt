package inteligenty_zamek.app_ik.presenters

import inteligenty_zamek.app_ik.Views.GenerateCertyficatRangeActivity
import inteligenty_zamek.app_ik.rest_class.GlobalContainer
import inteligenty_zamek.app_ik.models.GenerateCertyficatRangeModel
import java.util.ArrayList

/**
 * Created by Damian on 07.12.2017.
 */
class GenerateCertyficatRangePresenter(view: GenerateCertyficatRangeActivity)
{
   val model: GenerateCertyficatRangeModel = GenerateCertyficatRangeModel()

    fun getDayList(day:Int):ArrayList<String>?
    {
        when(day)
        {
            0->
            {
                return model.mondayList
            }
            1->
            {
                return model.tuesdayList
            }
            2->
            {
                return model.wednesdayList
            }
            3->
            {
                return model.thurstdayList
            }
            4->
            {
                return model.fridayList
            }
            5->
            {
                return model.saturdayList
            }
            6->
            {
                return model.sundayList
            }
        }
        return null

    }

    fun setValue(value:BooleanArray)
    {


        for(i in value.indices)
        if(value[i])
        {
        GlobalContainer.setDay(i,getDayList(i))
        }
        else
        {
            GlobalContainer.setDay(i,null)
        }

    }

    fun getDefaultValue()
    {
        if(GlobalContainer.mondayList!=null)
        {
            model.mondayList=GlobalContainer.mondayList
        }
    }


}