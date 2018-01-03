package inteligenty_zamek.app_ik.models

import inteligenty_zamek.app_ik.rest_class.Certyficat

/**
 * Created by Damian on 02.01.2018.
 */
class HistoryListElement() : Comparable<HistoryListElement>
{
    var Title=""

    var text=""
    var ico=""
    var key=""
    var date=""
    var dateHour=""
    var name=""
    var surname=""

    override
     fun compareTo(o: HistoryListElement): Int {
        return key.compareTo(o.key)
    }
}