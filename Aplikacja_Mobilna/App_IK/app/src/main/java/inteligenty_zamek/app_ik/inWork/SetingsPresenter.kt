package inteligenty_zamek.app_ik.inWork

import android.util.Base64
import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.HTTPRequestAPI
import inteligenty_zamek.app_ik.API.fileReadWriteApi
import org.json.JSONObject


/**
 * Created by Damian on 09.12.2017.
 */
class SetingsPresenter(val view: SetingsActivity)
{
        val model= SetingsModel(view)
    init{
        //TODO wypełnienie pól odnośćnie certyfikatu

    }

    fun changePassword(newPassword:String)
    {
        val passwd_hash = CyptographyApi.bin2hex(CyptographyApi.getHash("haslo"))
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
            model!!.pair = CyptographyApi.KeyPairGenerator()
        } catch (e: Exception) {
        }

        if (model!!.pair!!.public != null) {
            newPublicKey = Base64.encodeToString(model!!.pair!!.public.encoded, Base64.DEFAULT)

            val toSend: HashMap<String, String> = HashMap()
            toSend.put("login", model.login)
            toSend.put("token", model.token)
            //TODO odczytanie starego certyfikatu
            toSend.put("old_public_key", "do odczytanai z certyfikatu")
            toSend.put("new_public_key", newPublicKey)

            try {
                HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/adreeeeeeeesss", "changeKeyResult", toSend).execute()
            } catch (e: Exception) {
            }
        }
    }

    fun changeKeyResult(response:String)
    {
        ///TODO jakas walidacja
        fileReadWriteApi.writeToFile("klucz publiczny",view,"**"+model.login)
        //TODO wypełenieni pól w certyfikacie
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