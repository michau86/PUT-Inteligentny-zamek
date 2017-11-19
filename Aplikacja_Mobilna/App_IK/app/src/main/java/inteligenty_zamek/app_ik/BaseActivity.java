package inteligenty_zamek.app_ik;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;

import inteligenty_zamek.app_ik.Views.LoginActivity;
import inteligenty_zamek.app_ik.Views.MainActivity;
import inteligenty_zamek.app_ik.rest_class.GlobalClassContainer;
import inteligenty_zamek.app_ik.rest_class.User;

import static java.nio.charset.StandardCharsets.UTF_8;

public class BaseActivity extends ActionBarActivity
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
                if(((GlobalClassContainer) getApplication()).getIsadmin()<0 && i>1){i=navMenuTitles.length;
                    navDrawerItems.add(new NavDrawerItem("wyjdz",navMenuIcons.getResourceId(i, -1)));

                    break;}
                else
                    if(((GlobalClassContainer) getApplication()).getIsadmin()==0 && i==2){i++;}
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
        mDrawerLayout.setDrawerListener(mDrawerToggle);

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
                if(((GlobalClassContainer) getApplication()).getIsadmin()>0) {
                    Intent intent2 = new Intent(this, Admin_PanelActivity.class);
                    startActivity(intent2);
                    finish();
                }
                else if(((GlobalClassContainer) getApplication()).getIsadmin()==0)
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
                          editor.putString("ipserwer", "");
                      editor.putString("login", "");
                          editor.putString("password", "");
                    editor.putString("token", "");
                    editor.commit();
                    Log.i("HHH","tutaj base");
                    startActivity(intent3);
                    finish();
                }
                break;
            case 3:
                if(((GlobalClassContainer) getApplication()).getIsadmin()>0) {
                    Intent intent3 = new Intent(this, SetingsActivity.class);
                    startActivity(intent3);
                    finish();
                }
                else if(((GlobalClassContainer) getApplication()).getIsadmin()==0)
                {
                    User user=((GlobalClassContainer) getApplication()).getUser();
                    new HTTPRequest(user).execute();
                    break;
                }
                break;
            case 4:
                User user=((GlobalClassContainer) getApplication()).getUser();
                new HTTPRequest(user).execute();
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

    public class HTTPRequest extends AsyncTask<Void, Void, String> {
        User user;
        boolean choise;
        public HTTPRequest(User x){
            user=x;
        }
        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpclient = new DefaultHttpClient();
            String adres="http://"+ ((GlobalClassContainer) getApplication()).getSerwerIP()+":8080/api/logout/";
            HttpPost httppost = new HttpPost(adres);

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("login", user.getLogin()));
                nameValuePairs.add(new BasicNameValuePair("token", ((GlobalClassContainer) getApplication()).getSession()));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");

                return responseString;
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return null;
        }

        //akcja po otrzyman iu odpowiedzi z serwera
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            JSONObject jObj = null;
            try {
                jObj = new JSONObject(response);
                if (jObj.getString("status").equals("logout")) {
                    ((GlobalClassContainer) getApplication()).setSession("");
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

    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(UTF_8));
        byte[] signature = privateSignature.sign();
        return Base64.encodeToString(signature,Base64.DEFAULT);
    }

    /*
    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(UTF_8));
        byte[] signatureBytes = Base64.decode(signature,Base64.DEFAULT);
        return publicSignature.verify(signatureBytes);
    }

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
        return pair;
    }
*/
}

