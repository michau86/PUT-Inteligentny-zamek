package inteligenty_zamek.app_ik.models

import inteligenty_zamek.app_ik.rest_class.GlobalContainer
import java.util.ArrayList

/**
 * Created by Damian on 07.12.2017.
 */
class GenerateCertyficatRangeModel {


    var mondayList = ArrayList<String>()
    var tuesdayList = ArrayList<String>()
    var wednesdayList = ArrayList<String>()
    var thurstdayList = ArrayList<String>()
    var fridayList = ArrayList<String>()
    var saturdayList = ArrayList<String>()
    var sundayList = ArrayList<String>()

    init{
        if(GlobalContainer.mondayList!=null) { mondayList=GlobalContainer.mondayList }
        if(GlobalContainer.tuesdayList!=null) { tuesdayList=GlobalContainer.tuesdayList }
        if(GlobalContainer.wednesdayList!=null) { wednesdayList=GlobalContainer.wednesdayList }
        if(GlobalContainer.thurstdayList!=null) { thurstdayList=GlobalContainer.thurstdayList }
        if(GlobalContainer.fridyList!=null) { fridayList=GlobalContainer.fridyList }
        if(GlobalContainer.saturdayList!=null) { saturdayList=GlobalContainer.saturdayList }
        if(GlobalContainer.sundayList!=null) { sundayList=GlobalContainer.sundayList }
    }
}