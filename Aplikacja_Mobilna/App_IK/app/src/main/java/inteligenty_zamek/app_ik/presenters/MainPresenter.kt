package inteligenty_zamek.app_ik.presenters

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import inteligenty_zamek.app_ik.API.Connect_and_send_message
import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.Views.MainActivity
import inteligenty_zamek.app_ik.models.MainModel
import inteligenty_zamek.app_ik.adapters.MainListAdapter
import inteligenty_zamek.app_ik.rest_class.Certyficat
import inteligenty_zamek.app_ik.rest_class.GlobalContainer

/**
 * Created by Damian on 26.10.2017.
 */

class MainPresenter ( val view: MainActivity) {


    val model = MainModel(GlobalContainer.getUser(view))

    fun chagneColorIco(icoColor:Byte)
    {
        view.changeIco(model.position,icoColor)
    }

    fun sendCertyficate(index:Int,cs: CharSequence)
    {
       model.position=index
       view.changeIco(index,1)

        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!mBluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            view.startActivity(enableBtIntent)
        }

        var signature = ""


        try {
            signature = CyptographyApi.sign(model.Keys!!.get(index)!!.lok_key, GlobalContainer.getPrivateKey(view))
      //dołożenie doo sygnatury certyfikatu
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
            return
        }

        val tosend = model.Keys!!.get(index)!!.idKey + ";" + model.user.login + ";" + signature
        Connect_and_send_message(model.Keys!!.get(index)!!.getMac_addres(), tosend,this).execute()


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





       fun  updateList(cs: CharSequence)//: MainListAdapter
        {
          model.putKeys(view,cs)
            view.setAdapter(model.Keys!!)

        }






    }


