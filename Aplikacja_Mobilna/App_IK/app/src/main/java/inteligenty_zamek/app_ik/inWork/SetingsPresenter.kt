package inteligenty_zamek.app_ik.inWork

import android.util.Base64
import android.util.Log
import inteligenty_zamek.app_ik.API.*
import inteligenty_zamek.app_ik.rest_class.GlobalContainer
import org.json.JSONObject


/**
 * Created by Damian on 09.12.2017.
 */
class SetingsPresenter(val view: SetingsActivity)
{
        val model= SetingsModel(view)
    init{
        updateCertyficat()
    }
    fun changeIP(ip:String)
    {
        if(!Valdiation.isCorrectIP(ip))
        {
          view.showErrorIp()
            return
        }

        val value = java.util.HashMap<EnumChoice, String>()
        value.put(EnumChoice.ip,ip)
        sharedPreferenceApi.set(view,value)
    }

    fun updateCertyficat()
    {
        val cert=GlobalContainer.getPublicKey(view)
        if(cert!=null) {
            view.updateCertyficat(cert.User_Name, cert.Issuer_name, cert.Validitiy_period, cert.Version, cert.Serial_number, cert.Hash_Algorithm, cert.Signature_Algorithm_Identifier, cert.PUBLIC_KEY)
        }
        }

    fun changePassword(newPassword:String, oldPassword:String)
    {

        if(!Valdiation.isCorrectPassword(newPassword))
        {
         return
        }
        val passwd_hash = CyptographyApi.bin2hex(CyptographyApi.getHash(oldPassword))
        val new_passwd_hash= CyptographyApi.bin2hex(CyptographyApi.getHash(newPassword))

        val toSend: HashMap<String, String> = HashMap()
        toSend.put("login", model.login)
        toSend.put("token", model.token)
        toSend.put("passwd",passwd_hash)
        toSend.put("newpasswd",new_passwd_hash)
        try {
            HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/change_password/", "changePasswordResult", toSend).execute()
        } catch (e: Exception) { }
    }

    fun changeKey()
    {

        var newPublicKey = ""
        try {
            model.pair = CyptographyApi.KeyPairGenerator()
        } catch (e: Exception) {
        }

        if (model.pair!!.public != null) {
            newPublicKey = Base64.encodeToString(model.pair!!.public.encoded, Base64.DEFAULT)

            val toSend: HashMap<String, String> = HashMap()
            toSend.put("login", model.login)
            toSend.put("token", model.token)
            toSend.put("old_public_key", model.publicKey)
            toSend.put("new_public_key", newPublicKey)

            try {
                HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/replace_certificate/", "changeKeyResult", toSend).execute()
            } catch (e: Exception) {
            }
        }
    }

    fun changeKeyResult(response:String)
    {


        if (JSONObject(response).getString("data") != "empty") {
            val arrJson = JSONObject(response).getJSONArray("data")
            try {
                fileReadWriteApi.writeToFile(
                        arrJson.getJSONObject(0).toString(), view, "**" + model.login)
                val value = java.util.HashMap<EnumChoice, String>()
                value.put(EnumChoice.publicKey, arrJson.toString(1))
                sharedPreferenceApi.set(view, value)



            } catch (e: Exception) { }

            try{
                val stringKey = Base64.encodeToString(model.pair!!.private.encoded, Base64.DEFAULT)
                fileReadWriteApi.writeToFile(
                        CyptographyApi.encrypt(stringKey,model.password), view, "*" + model.login)
                GlobalContainer.setPrivateKey(model.pair!!.private)
            }catch (ex:Exception){}

        }

        GlobalContainer.publicKeyReset()
        updateCertyficat()
    }



    fun changePasswordResult(response:String)
    {
        try {
            if (JSONObject(response).getString("status") == "ok") {
             view.showMessage("Zmieniono hasło")
                val privateKey=fileReadWriteApi.readFromFile("*"+model.login,view)
                val hashPrivateKey=
                        CyptographyApi.encrypt(privateKey,model.password)
                fileReadWriteApi.writeToFile(privateKey,view,"*"+model.login)

            } else {
                view.showMessage("Nie powiodła się zmiana hasła")
            }
        } catch (e: Exception) { }
    }

}