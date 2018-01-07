package inteligenty_zamek.app_ik.inWork

import inteligenty_zamek.app_ik.rest_class.GlobalContainer
import inteligenty_zamek.app_ik.rest_class.User

/**
 * Created by Damian on 07.01.2018.
 */
class UserModel(val view:UserActivity)
{
    val user:User=GlobalContainer.obj as User

}