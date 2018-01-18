package inteligenty_zamek.app_ik.models

import java.security.KeyPair

/**
 * Created by Damian on 07.11.2017.
 */
class RegisterModel{
var user: User?=null
var ip:String?=null
    var pair: KeyPair? = null
   val toastDelay = 4000


    fun setRegisterValue(login:String,password:String,name:String,surname:String,ip:String)
    {
    this.ip=ip
        this.user= User()
        this.user!!.login=login
        this.user!!.password=password
        this.user!!.name=name
        this.user!!.surname=surname
    }
}
