package inteligenty_zamek.app_ik.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import inteligenty_zamek.app_ik.API.EnumChoice;
import inteligenty_zamek.app_ik.API.HTTPRequestAPI;
import inteligenty_zamek.app_ik.API.sharedPreferenceApi;
import inteligenty_zamek.app_ik.Views.Admin_PanelActivity;
import inteligenty_zamek.app_ik.Views.Managment_certyficationActivity;
import inteligenty_zamek.app_ik.R;
import inteligenty_zamek.app_ik.SetingsActivity;
import inteligenty_zamek.app_ik.Views.LoginActivity;
import inteligenty_zamek.app_ik.Views.MainActivity;
import inteligenty_zamek.app_ik.rest_class.GlobalClassContainer;
import inteligenty_zamek.app_ik.rest_class.GlobalContainer;
import inteligenty_zamek.app_ik.rest_class.User;

public class BaseActivity extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ArrayList<NavDrawerItem> navDrawerItems;
    NavDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
    }

    public void set(String[] navMenuTitles,TypedArray navMenuIcons) {
        mTitle = mDrawerTitle = getTitle();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items
        if(navMenuIcons==null){
            for(int i=0;i<navMenuTitles.length;i++){
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[i]));

            }}else{
            for(int i=0;i<navMenuTitles.length;i++){
                if(sharedPreferenceApi.INSTANCE.getBoolean(this,EnumChoice.isLogin)==false && i>1){i=navMenuTitles.length;
                    navDrawerItems.add(new NavDrawerItem("wyjdz",navMenuIcons.getResourceId(i, -1)));

                    break;}
                else
                    if(sharedPreferenceApi.INSTANCE.getBoolean(this,EnumChoice.isAdmin)==false && i==2){i++;}
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[i],navMenuIcons.getResourceId(i, -1)));
            }
        }
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.app_name,
                R.string.app_name
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);

                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);

    }


    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    private void displayView(int position) {
        GlobalContainer.menuSelectedNumber=position;

        switch (position) {
            case 0:
               Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case 1:

                Intent intent1 = new Intent(this, Managment_certyficationActivity.class);
                startActivity(intent1);
                finish();
                break;
            case 2:
                if(sharedPreferenceApi.INSTANCE.getBoolean(this,EnumChoice.isAdmin)==true) {
                    Intent intent2 = new Intent(this, Admin_PanelActivity.class);
                    startActivity(intent2);
                    finish();
                }
                else if(sharedPreferenceApi.INSTANCE.getBoolean(this,EnumChoice.isLogin)==true)
                {
                    Intent intent3 = new Intent(this, SetingsActivity.class);
                    startActivity(intent3);
                    finish();
                }
                else
                {
                    ((GlobalClassContainer) getApplication()).setDefaultValue();
                    Intent intent3 = new Intent(this, LoginActivity.class);
                    SharedPreferences sharedPref;
                    sharedPref = this.getSharedPreferences(this.getString(R.string.SPName), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("password", "");
                    editor.putString("token", "");
                    editor.putBoolean("isadmin",false);
                    editor.putBoolean("isLogin",false);
                    editor.commit();
                    startActivity(intent3);
                    finish();
                }
                break;
            case 3:
                if(sharedPreferenceApi.INSTANCE.getBoolean(this,EnumChoice.isAdmin)==true) {
                    Intent intent3 = new Intent(this, SetingsActivity.class);
                    startActivity(intent3);
                    finish();
                }
                else
                {
                    User user=GlobalContainer.getUser(this);
                    logout(user, sharedPreferenceApi.INSTANCE.getString(this,EnumChoice.ip));
                    break;
                }
                break;
            case 4:
                User user=GlobalContainer.getUser(this);
                logout(user, sharedPreferenceApi.INSTANCE.getString(this,EnumChoice.ip));
                break;
            default:
                break;
        }
        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

private void logout(User user,String ipserwer)
{
    HashMap toSend = new HashMap();
    toSend.put("login", user.getLogin());
    toSend.put("token", sharedPreferenceApi.INSTANCE.getString(this,EnumChoice.token));
    try {
        new HTTPRequestAPI(this, "http://" + ipserwer + ":8080/api/logout/","logoutresponse" , toSend).execute();
    }catch (Exception e)
    {

    }

}

public void logoutresponse(String response)
{

    JSONObject jObj = null;
    try {
        jObj = new JSONObject(response);
        if (jObj.getString("status").equals("logout") || jObj.getString("status").equals("invalid")) {
            sharedPreferenceApi.INSTANCE.set(this,false, EnumChoice.isAdmin);
            sharedPreferenceApi.INSTANCE.set(this,false,EnumChoice.isLogin);
            GlobalContainer.setdefault();
           HashMap<EnumChoice,String> value=new HashMap<>();
           value.put(EnumChoice.password,"");
           value.put(EnumChoice.token,"");
           sharedPreferenceApi.INSTANCE.set(this,value);

            // editor.commit();
            runOnUiThread(new Runnable() {
                public void run() {
                    ((GlobalClassContainer) getApplication()).setDefaultValue();
                    Intent intent4 = new Intent(BaseActivity.this, LoginActivity.class);
                    startActivity(intent4);
                    finish();
                }
            });
        }
        else
        {
            // TODO error z logoutu
        }
    } catch (JSONException e) {
    }
}












}

