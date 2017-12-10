package inteligenty_zamek.app_ik.beforeChange;

import android.content.res.TypedArray;
import android.os.Bundle;

import inteligenty_zamek.app_ik.Navigation.BaseActivity;
import inteligenty_zamek.app_ik.R;

public class GenerationCertyfikatForGuestActivity extends BaseActivity {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generation_certyfikat_for_guest);

        navMenuTitles= getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons =getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles,navMenuIcons);

    }
}
