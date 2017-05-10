package inteligenty_zamek.app_ik;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ViewFlipper;

public class GenerationCertyficatActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    String[] items = { "Certyfikat dla użytkownika", "Certyfikat dla gościa"};
    ViewFlipper viewFlipper;
    Spinner spin,spin2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generation_certyficat);
        navMenuTitles= getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons =getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles,navMenuIcons);

        viewFlipper = (ViewFlipper) findViewById(R.id.myViewFlipper);

        viewFlipper.setDisplayedChild(0);
        spin = (Spinner) findViewById(R.id.spinnerChangeAdminGenerationCertyficat);
        spin2 = (Spinner) findViewById(R.id.spinnerChangeAdminGenerationCertyficat2);
        spin.setOnItemSelectedListener(this);
        spin2.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                items);

        ArrayAdapter aa2 = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                items);

        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        aa2.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(aa2);


    }

    public void onItemSelected(AdapterView<?> parent, View v, int position,
                               long id) {
if(position==1){spin2.setSelection(1);} else {spin.setSelection(0);}
        viewFlipper.setDisplayedChild(position);

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }


}
