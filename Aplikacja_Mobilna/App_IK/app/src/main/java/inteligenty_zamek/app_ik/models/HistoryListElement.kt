package inteligenty_zamek.app_ik.models

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