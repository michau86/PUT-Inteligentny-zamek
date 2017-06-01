package inteligenty_zamek.app_ik;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class GenerateCertyficatRangeActivity extends Activity {
    final ArrayList<String> mondayList = new ArrayList<String>();
    final ArrayList<String> tuesdayList = new ArrayList<String>();
    final ArrayList<String> wednesdayList = new ArrayList<String>();
    final ArrayList<String> thurstdayList = new ArrayList<String>();
    final ArrayList<String> fridayList = new ArrayList<String>();
    final ArrayList<String> saturdayList = new ArrayList<String>();
    final ArrayList<String> sundayList = new ArrayList<String>();





    Spinner spiner1,spiner2,spiner3,spiner4,spiner5,spiner6,spiner7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_certyficat_range);

        spiner1 = (Spinner) findViewById(R.id.spinnerMonday);
        spiner2 = (Spinner) findViewById(R.id.spinnerTuesday);
        spiner3 = (Spinner) findViewById(R.id.spinnerSroda);
        spiner4 = (Spinner) findViewById(R.id.spinnerCzwartek);
        spiner5 = (Spinner) findViewById(R.id.spinnerPiatek);
        spiner6 = (Spinner) findViewById(R.id.spinnerSobota);
        spiner7 = (Spinner) findViewById(R.id.spinnerNiedziela);

        final SpinnerAdapter spinerMondayAdapter=new SpinnerAdapter(this, R.layout.spinner_row, mondayList, mondayList);
        final SpinnerAdapter spinerTuesdayAdapter=new SpinnerAdapter(this, R.layout.spinner_row, tuesdayList, tuesdayList);
        final SpinnerAdapter spinerWednesdayAdapter=new SpinnerAdapter(this, R.layout.spinner_row, wednesdayList, wednesdayList);
        final SpinnerAdapter spinerThurstdayAdapter=new SpinnerAdapter(this, R.layout.spinner_row, thurstdayList, thurstdayList);
        final SpinnerAdapter spinerFridayAdapter=new SpinnerAdapter(this, R.layout.spinner_row, fridayList, fridayList);
        final SpinnerAdapter spinerSaturdayAdapter=new SpinnerAdapter(this, R.layout.spinner_row, saturdayList, saturdayList);
        final SpinnerAdapter spinerSundayAdapter=new SpinnerAdapter(this, R.layout.spinner_row, sundayList, sundayList);


        spiner1.setAdapter(spinerMondayAdapter);
        spiner2.setAdapter(spinerTuesdayAdapter);
        spiner3.setAdapter(spinerWednesdayAdapter);
        spiner4.setAdapter(spinerThurstdayAdapter);
        spiner5.setAdapter(spinerFridayAdapter);
        spiner6.setAdapter(spinerSaturdayAdapter);
        spiner7.setAdapter(spinerSundayAdapter);


        //dodawniae nowej wartosci

        final Button monday = (Button) findViewById(R.id.buttonAddRangeMonday);
        monday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText from=(EditText)findViewById(R.id. inputfromPoniedzialek);
                EditText to=(EditText)findViewById(R.id. inputtoPoniedzialek);
                spinerMondayAdapter.add(from.getText().toString()+"-"+to.getText().toString());
                spinerMondayAdapter.notifyDataSetChanged();
            }
        });

        final Button tuesday = (Button) findViewById(R.id.buttonAddRangeTuesday);
        tuesday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText from=(EditText)findViewById(R.id. inputfromWtorek);
                EditText to=(EditText)findViewById(R.id. inputtoWtorek);
                spinerTuesdayAdapter.add(from.getText().toString()+"-"+to.getText().toString());
                spinerTuesdayAdapter.notifyDataSetChanged();
            }
        });

        final Button wednesday = (Button) findViewById(R.id.buttonAddRangeSroda);
        wednesday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText from=(EditText)findViewById(R.id. inputfromSroda);
                EditText to=(EditText)findViewById(R.id. inputtoSroda);
                spinerWednesdayAdapter.add(from.getText().toString()+"-"+to.getText().toString());
                spinerWednesdayAdapter.notifyDataSetChanged();
            }
        });

        final Button thurstday = (Button) findViewById(R.id.buttonAddRangeCzwartek);
       thurstday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText from=(EditText)findViewById(R.id. inputfromCzwartek);
                EditText to=(EditText)findViewById(R.id. inputtoCzwartek);
                spinerThurstdayAdapter.add(from.getText().toString()+"-"+to.getText().toString());
                spinerThurstdayAdapter.notifyDataSetChanged();
            }
        });

        final Button friday = (Button) findViewById(R.id.buttonAddRangePiatek);
        friday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText from=(EditText)findViewById(R.id. inputfrompiatek);
                EditText to=(EditText)findViewById(R.id. inputtopiatek);
                spinerFridayAdapter.add(from.getText().toString()+"-"+to.getText().toString());
                spinerFridayAdapter.notifyDataSetChanged();
            }
        });

        final Button saturday= (Button) findViewById(R.id.buttonAddRangeSobota);
        saturday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText from=(EditText)findViewById(R.id. inputfromSobota);
                EditText to=(EditText)findViewById(R.id. inputtoSobota);
                spinerSaturdayAdapter.add(from.getText().toString()+"-"+to.getText().toString());
                spinerSaturdayAdapter.notifyDataSetChanged();
            }
        });

        final Button sunday= (Button) findViewById(R.id.buttonAddRangeNiedziela);
        sunday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText from=(EditText)findViewById(R.id. inpiutfromNiedziela);
                EditText to=(EditText)findViewById(R.id. inputtoNiedziela);
                spinerSundayAdapter.add(from.getText().toString()+"-"+to.getText().toString());
                spinerSundayAdapter.notifyDataSetChanged();
            }
        });



    }

    @Override
    public void onBackPressed() {

        CheckBox monday = (CheckBox)findViewById(R.id.checkBoxPoniedzialek);
        if(monday.isEnabled()) {
            ((SessionContainer) getApplication()).setMondayList(mondayList);
        }
        else
        {
            ((SessionContainer) getApplication()).setMondayList(null);
        }

        CheckBox tuesday= (CheckBox)findViewById(R.id.checkBoxWtorek);
        if(tuesday.isEnabled()) {
            ((SessionContainer) getApplication()).setTuesdayList(tuesdayList);
        }
        else
        {
            ((SessionContainer) getApplication()).setTuesdayList(null);
        }


        CheckBox wednesday= (CheckBox)findViewById(R.id.checkBoxSroda);
        if(wednesday.isEnabled()) {
            ((SessionContainer) getApplication()).setWednesdayList(wednesdayList);
        }
        else
        {
            ((SessionContainer) getApplication()).setWednesdayList(null);
        }

        CheckBox thurstday= (CheckBox)findViewById(R.id.checkBoxCzwartek);
        if(thurstday.isEnabled()) {
            ((SessionContainer) getApplication()).setThurstdayList(thurstdayList);
        }
        else
        {
            ((SessionContainer) getApplication()).setThurstdayList(null);
        }


        CheckBox friday= (CheckBox)findViewById(R.id.checkBoxPiatek);
        if(friday.isEnabled()) {
            ((SessionContainer) getApplication()).setFridyList(fridayList);
        }
        else
        {
            ((SessionContainer) getApplication()).setFridyList(null);
        }


        CheckBox saturday= (CheckBox)findViewById(R.id.checkBoxSobota);
        if(saturday.isEnabled()) {
            ((SessionContainer) getApplication()).setSaturdayList(saturdayList);
        }
        else
        {
            ((SessionContainer) getApplication()).setSaturdayList(null);
        }


        CheckBox sunday= (CheckBox)findViewById(R.id.checkBoxNiedziela);
        if(sunday.isEnabled()) {
            ((SessionContainer) getApplication()).setSundayList(sundayList);
        }
        else
        {
            ((SessionContainer) getApplication()).setSundayList(null);
        }


        this.finish();
    }


}
