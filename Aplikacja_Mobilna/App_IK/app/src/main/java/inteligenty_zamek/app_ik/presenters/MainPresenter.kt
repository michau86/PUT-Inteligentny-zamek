package inteligenty_zamek.app_ik.presenters

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import inteligenty_zamek.app_ik.*
import inteligenty_zamek.app_ik.API.Connect_and_send_message
import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.Views.MainActivity
import inteligenty_zamek.app_ik.models.MainModel
import inteligenty_zamek.app_ik.rest_class.GlobalClassContainer
import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.rest_class.Certyficat
import inteligenty_zamek.app_ik.rest_class.GlobalContainer

/**
 * Created by Damian on 26.10.2017.
 */

class MainPresenter ( val view: MainActivity) {
    var model: MainModel
    var csk: CharSequence = ""

    init {
        model = MainModel(GlobalContainer.getUser(view))
    }

    fun sendCertyficate(index:Int,cs: CharSequence)
    {
       chagneColorIco(index,1,cs);

        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!mBluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            view.startActivity(enableBtIntent)
        }

        var signature = ""


        try {
            signature = CyptographyApi.sign(model!!.Keys!!.get(index)!!.getLok_key(), GlobalContainer.getPrivateKey(view))
        } catch (e: Exception) {
            val toast = Toast.makeText(view, "Brak klucza prywatnego", Toast.LENGTH_LONG)
            toast.show()
            object : CountDownTimer(2000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    toast.show()
                }

                override fun onFinish() {
                    toast.show()
                }
            }.start()
            return;
        }

        val tosend = model!!.Keys!!.get(index)!!.getIdKey() + ";" + model.user.login + ";" + signature
        Log.i("HHHH Dane BT",model!!.Keys!!.get(index)!!.getMac_addres())
        Connect_and_send_message(model!!.Keys!!.get(index)!!.getMac_addres(), tosend,this).execute()          //B8:27:EB:FC:73:A2  64:B3:10:B4:81:DD
        val toast = Toast.makeText(view, "Wysłano certyfikat", Toast.LENGTH_LONG)
        toast.show()
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                toast.show()
            }

            override fun onFinish() {
                toast.show()
            }
        }.start()
    }


       fun  updateList(cs: CharSequence): MainListAdapter
        {
          model!!.putKeys(view,cs);
         return MainListAdapter(view, model);
        }

    fun chagneColorIco(position:Int, i: Byte,cs: CharSequence)
    {
        val a:Certyficat? = model.Keys!!.get(position)
        a!!.status=i
        model.Keys!!.put(position,  a )
        view.resultsListView!!.adapter= updateList(cs)
        model.position=position
        model.i=i
        model.cs=cs
    }

    fun chagneColorIco(i: Byte)
    {
        val a:Certyficat? = model.Keys!!.get(model.position)
        a!!.status=i
        model.Keys!!.put(model.position,  a )
        view.resultsListView!!.adapter= updateList(model.cs)
    }


    }


