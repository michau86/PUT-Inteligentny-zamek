package inteligenty_zamek.app_ik;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class GenerateCertyficatRangeActivity extends Activity {
    final ArrayList<String> mondayList = new ArrayList<String>();
    Spinner spiner1,spiner2,spiner3,spiner4,spiner5,spiner6,spiner7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_certyficat_range);

        spiner1 = (Spinner) findViewById(R.id.spinnerMonday);
        spiner2 = (Spinner) findViewById(R.id.spinnerTuesday);

        ArrayList<String> tuesdayList = new ArrayList<String>();

        final SpinnerAdapter spinerMondayAdapter=new SpinnerAdapter(this, R.layout.spinner_row, mondayList, mondayList);
        SpinnerAdapter spinerTuesdayAdapter=new SpinnerAdapter(this, R.layout.spinner_row, tuesdayList, tuesdayList);
        spiner1.setAdapter(spinerMondayAdapter);
        spiner2.setAdapter(spinerTuesdayAdapter);

        //dodawniae nowej wartosci

        final Button login = (Button) findViewById(R.id.buttonAddRangeMonday);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText from=(EditText)findViewById(R.id. inputfromPoniedzialek);
                EditText to=(EditText)findViewById(R.id. inputtoPoniedzialek);
                spinerMondayAdapter.add(from.getText().toString()+"-"+to.getText().toString());
                spinerMondayAdapter.notifyDataSetChanged();


//in on bakc presedbutton

            }
        });


        spinerTuesdayAdapter.add("1:00-4:00");
        spinerTuesdayAdapter.add("7:00-13:00");
    }

    @Override
    public void onBackPressed() {

        String listString = "";
        for (String s : mondayList)
        {
            listString += s + "\t";
        }
        ((SessionContainer) getApplication()).setCertyficatadminlist(listString);
        this.finish();
    }

}
