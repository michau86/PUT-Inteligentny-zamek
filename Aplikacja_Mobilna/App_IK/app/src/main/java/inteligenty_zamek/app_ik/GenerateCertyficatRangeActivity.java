package inteligenty_zamek.app_ik;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;

import java.util.ArrayList;

public class GenerateCertyficatRangeActivity extends Activity {

    Spinner spiner1,spiner2,spiner3,spiner4,spiner5,spiner6,spiner7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_certyficat_range);

        spiner1 = (Spinner) findViewById(R.id.spinnerMonday);
        spiner2 = (Spinner) findViewById(R.id.spinnerTuesday);
        ArrayList<String> mondayList = new ArrayList<String>();
        ArrayList<String> tuesdayList = new ArrayList<String>();

        SpinnerAdapter spinerMondayAdapter=new SpinnerAdapter(this, R.layout.spinner_row, mondayList, mondayList);
        SpinnerAdapter spinerTuesdayAdapter=new SpinnerAdapter(this, R.layout.spinner_row, tuesdayList, tuesdayList);
        spiner1.setAdapter(spinerMondayAdapter);
        spiner2.setAdapter(spinerTuesdayAdapter);

        //dodawniae nowej wartosci
        spinerMondayAdapter.add("18:00-21:00");
        spinerMondayAdapter.notifyDataSetChanged();

        spinerTuesdayAdapter.add("1:00-4:00");
        spinerTuesdayAdapter.add("7:00-13:00");
    }



}
