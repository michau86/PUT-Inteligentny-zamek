package inteligenty_zamek.app_ik.beforeTest

import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.HTTPRequestAPI
import inteligenty_zamek.app_ik.beforeTest.SetingsActivity
import inteligenty_zamek.app_ik.beforeTest.SetingsModel
import org.json.JSONObject

/**
 * Created by Damian on 09.12.2017.
 */
class SetingsPresenter(val view: SetingsActivity)
{
        val model= SetingsModel(view)
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

    fun changePasswordResult(response:String)
    {
        try {
            if (JSONObject(response).getString("status") == "ok") {
             view.showMessage("Zmieniono hasło")
                //TODO zmienić szyfrowanie klucza prywatnego (odczytać starym hasłem i zaszyfrować nowym)
                //natepnie zmienić hasło w sharedPreference
            } else {
                view.showMessage("Nie powiodła się zmiana hasła")
            }
        } catch (e: Exception) { }
    }

}