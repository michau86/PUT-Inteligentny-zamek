package inteligenty_zamek.app_ik;

import android.content.res.TypedArray;
import android.os.Bundle;

import inteligenty_zamek.app_ik.rest_class.Certyficat;

public class certyficatActivity extends BaseActivity {


    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    String value="";
    Certyficat cert=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certyficat);

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles, navMenuIcons);
/*
        Bundle b = getIntent().getExtras();
         value = ""; // nazwa certyfikatu
        if(b != null)
        {value = b.getString("name");}
        Log.i("aaa",value);
        //wyszukanie certyfikatu po nazwie
        int index=((GlobalClassContainer) getApplication()).searchcertyficat(value);
        //jezeli znalazl to ustawia wartosci:
        if(index>=0) {
            cert = ((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[index];
            TextView tx1 = (TextView) this.findViewById(R.id.textView20);
            tx1.setText("czas wygasniecia: "+ cert.getTo());
            TextView tx2 = (TextView) this.findViewById(R.id. textView22);
            tx2.setText("klucz przypisany do użytkownika: "+cert.getName() +" "+ cert.getSurname());

            TextView tx3 = (TextView) this.findViewById(R.id. textView24);
            String val="";
            if(!cert.getMonday().equals("null")){val=val+"poniedziałek: "+cert.getMonday();}
            if(!cert.getTuesday().equals("null")){val=val+"\nwtorek: "+cert.getTuesday();}
            if(!cert.getWednesday().equals("null")){val=val+"\nsroda: "+cert.getWednesday();}
            if(!cert.getThurstday().equals("null")){val=val+"\nczwartek: "+cert.getThurstday();}
            if(!cert.getFriday().equals("null")){val=val+"\npiatek: "+cert.getFriday();}
            if(!cert.getSaturday().equals("null")){val=val+"\nsobota: "+cert.getSaturday();}
            if(!cert.getSunday().equals("null")){val=val+"\nniedziela: "+cert.getSunday();}
            tx3.setText(val);

        }
        else
        {
            Button   button= (Button) findViewById(R.id.extend_cert);
            button.setVisibility(View.INVISIBLE);
        }
    //akcja po nacisniecu usun certyfikat
        final Button delete = (Button) findViewById(R.id.delete_cert);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               User user= ((GlobalClassContainer) getApplication()).getUser();



                if (((GlobalClassContainer) getApplication()).getIsadmin()>=0) {
                     new HTTPRequest(user.getLogin(), ((GlobalClassContainer) getApplication()).getSession(), cert.getIdKey()).execute();
                }
                else
                {
                    try {
                        String readcertyficat = ((GlobalClassContainer) getApplication()).readFromFile(certyficatActivity.this, ((GlobalClassContainer) getApplication()).getUser().getLogin());
                        JSONArray arrJson = new JSONArray(readcertyficat);
                        if (arrJson != null) {
                            for (int i = 0; i < arrJson.length(); i++) {
                                JSONObject jsonObj = arrJson.getJSONObject(i);
                                String k = jsonObj.getString("LOCK_NAME");
                                if (k.equals(value)) {
                                    arrJson.remove(i);
                                    break;
                                }
                            }
                            ((GlobalClassContainer) getApplication()).getUser().setCertyficateList(null);
                            ((GlobalClassContainer) getApplication()).getUser().addCertyficatList(arrJson);
                            ((GlobalClassContainer) getApplication()).writeToFile(arrJson.toString(), certyficatActivity.this, ((GlobalClassContainer) getApplication()).getUser().getLogin());
                            Intent intent = new Intent(certyficatActivity.this,Managment_certyficationActivity.class);
                            startActivity(intent);
                        }
                    }catch (Exception e){}

                }


            }
        });


    //akcja po nacisnieciu przedluż certyfikat
        final Button extend = (Button) findViewById(R.id.extend_cert);
        extend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                User user= ((GlobalClassContainer) getApplication()).getUser();
                new HTTPRequestext(user.getLogin(), ((GlobalClassContainer) getApplication()).getSession(), cert.getId_lock()).execute();
            }
        });

    }

    public class HTTPRequest extends AsyncTask<Void, Void, String> {
        String login, token, certyficat_id;
        int toastDelay = 4000;
        boolean choise;

        public HTTPRequest(String login, String token, String id) {
            this.login = login;
            this.token = token;
            this.certyficat_id = id;

        }

        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpclient = new DefaultHttpClient();

            String adres = "http://" + ((GlobalClassContainer) getApplication()).getSerwerIP() + ":8080/api/deactivation/";

            HttpPost httppost = new HttpPost(adres);

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("login", login));
                nameValuePairs.add(new BasicNameValuePair("token", token));
                nameValuePairs.add(new BasicNameValuePair("certificate_id", certyficat_id));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");

                return responseString;
            } catch (ClientProtocolException e) {
                final Toast toast = Toast.makeText(certyficatActivity.this, "wystapił problem podczas połącenia z serwerem", Toast.LENGTH_LONG);
                toast.show();
                new CountDownTimer(toastDelay, 1000) {
                    public void onTick(long millisUntilFinished) {
                        toast.show();
                    }

                    public void onFinish() {
                        toast.show();
                    }
                }.start();
            } catch (IOException e) {
                final Toast toast = Toast.makeText(certyficatActivity.this, "wystapił problem podczas połącenia z serwerem", Toast.LENGTH_LONG);
                toast.show();
                new CountDownTimer(toastDelay, 1000) {
                    public void onTick(long millisUntilFinished) {
                        toast.show();
                    }

                    public void onFinish() {
                        toast.show();
                    }
                }.start();
            }
            return null;
        }

        //akcja po otrzymaniu odpowiedzi z serwera
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            JSONObject jObj = null;

            try {
                jObj = new JSONObject(response);
                if(jObj.getString("status").equals("ok"))
                {
                    String readcertyficat=((GlobalClassContainer) getApplication()).readFromFile(certyficatActivity.this,((GlobalClassContainer) getApplication()).getUser().getLogin());
                        JSONArray  arrJson = new JSONArray(readcertyficat);
                        // delete item from jsonarray save jsonarray to file  run toast
                        if( arrJson !=null)
                        {
                            for (int i=0;i<arrJson.length();i++){
                                JSONObject jsonObj = arrJson.getJSONObject(i);
                                String k = jsonObj.getString("LOCK_NAME");
                                Log.i("aaaaa",k);
                                Log.i("aaaaaa",value);
                                if(k.equals(value))
                                {
                                    arrJson.remove(i);
                                    break;
                                }
                            }
                            ((GlobalClassContainer) getApplication()).getUser().setCertyficateList(null);
                            ((GlobalClassContainer) getApplication()).getUser().addCertyficatList(arrJson);
                        ((GlobalClassContainer) getApplication()).writeToFile(arrJson.toString(),certyficatActivity.this, ((GlobalClassContainer) getApplication()).getUser().getLogin());
                            Intent intent = new Intent(certyficatActivity.this,Managment_certyficationActivity.class);
                            startActivity(intent);
                        }

                }
                else
                {
                    final Toast toast = Toast.makeText(certyficatActivity.this, "Nie powiodło się usunięcie certyfikatu", Toast.LENGTH_LONG);
                    toast.show();
                    new CountDownTimer(toastDelay, 1000) {
                        public void onTick(long millisUntilFinished) {
                            toast.show();
                        }

                        public void onFinish() {
                            toast.show();
                        }
                    }.start();
                }
            } catch (Exception e) {
            }
        }

    }



    public class HTTPRequestext extends AsyncTask<Void, Void, String> {
        String login, token, lock_id;
        int toastDelay = 4000;
        boolean choise;

        public HTTPRequestext(String login, String token, String id) {
            this.login = login;
            this.token = token;
            this.lock_id = id;

        }
        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpclient = new DefaultHttpClient();

            String adres = "http://" + ((GlobalClassContainer) getApplication()).getSerwerIP() + ":8080/api/request_new_certificate/";

            HttpPost httppost = new HttpPost(adres);

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("login", login));
                nameValuePairs.add(new BasicNameValuePair("token", token));
                nameValuePairs.add(new BasicNameValuePair("lock_id", lock_id));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");

                return responseString;
            } catch (ClientProtocolException e) {
                final Toast toast = Toast.makeText(certyficatActivity.this, "wystapił problem podczas połącenia z serwerem", Toast.LENGTH_LONG);
                toast.show();
                new CountDownTimer(toastDelay, 1000) {
                    public void onTick(long millisUntilFinished) {
                        toast.show();
                    }

                    public void onFinish() {
                        toast.show();
                    }
                }.start();
            } catch (IOException e) {
                final Toast toast = Toast.makeText(certyficatActivity.this, "wystapił problem podczas połącenia z serwerem", Toast.LENGTH_LONG);
                toast.show();
                new CountDownTimer(toastDelay, 1000) {
                    public void onTick(long millisUntilFinished) {
                        toast.show();
                    }
                    public void onFinish() {
                        toast.show();
                    }
                }.start();
            }
            return null;
        }

        //akcja po otrzymaniu odpowiedzi z serwera
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            JSONObject jObj = null;

            try {
                jObj = new JSONObject(response);
                if(jObj.getString("status").equals("ok"))
                {
                    final Toast toast = Toast.makeText(certyficatActivity.this, "wysłano prośbę o przedłużenie certyfikatu", Toast.LENGTH_LONG);
                    toast.show();
                    new CountDownTimer(toastDelay, 1000) {
                        public void onTick(long millisUntilFinished) {
                            toast.show();
                        }
                        public void onFinish() {
                            toast.show();
                        }
                    }.start();
                }
                else
                {
                    final Toast toast = Toast.makeText(certyficatActivity.this, "Nie powiodło się usunięcie certyfikatu", Toast.LENGTH_LONG);
                    toast.show();
                    new CountDownTimer(toastDelay, 1000) {
                        public void onTick(long millisUntilFinished) {
                            toast.show();
                        }

                        public void onFinish() {
                            toast.show();
                        }
                    }.start();
                }
            } catch (Exception e) {
            }
        }
    }
*/
    }

}
