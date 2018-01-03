# coding=utf-8

from django.http import JsonResponse  # zwracanie JSONow w odpowiedzi
from django.views.decorators.csrf import csrf_exempt  # wylaczenie CSRF tokenow
import MySQLdb
from Crypto.PublicKey import RSA
from Crypto import Random
from datetime import datetime

username = "root"
userpassword = "inteligentnyzamek"
databasename = "inteligentny_zamek_db"
databaseaddres = "127.0.0.1"

db = MySQLdb.connect(databaseaddres, username, userpassword, databasename)


# api do logowania
@csrf_exempt
def api_login(request):
    if request.method == 'POST':
        username = request.POST.get('username')
        password = request.POST.get('password')

        random_generator = Random.new().read
        key = RSA.generate(1024, random_generator).publickey().exportKey()
        token = ""

        for x in key.split("\n")[1:-1]:
            token += x
        try:
            cursor = db.cursor()
            cursor.execute("SELECT PASSWORD, IS_ADMIN, ISACTIVATED FROM USERS WHERE login='%s'" % username)
            data = cursor.fetchone()
            if data[2] == 0:
                print password
                print data[0]
                if data[0] == password:
                    cursor.execute("UPDATE USERS SET TOKEN = '%s' WHERE LOGIN = '%s'" % (token, username))
                    db.commit()
                    if data[1] == 1:
                        return JsonResponse({"status": "root", "token": token})
                    else:
                        return JsonResponse({"status": "ok", "token": token})
                else:
                    return JsonResponse({"status": "ERROR PASSWORD", "token": "invalid"})
            else:
                return JsonResponse({"status": "not activated", "token": "invalid"})
        except Exception:
            return JsonResponse({"status": "ERROR", "token": "Invalid"})


# api do rejestrowania
@csrf_exempt
def api_register(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        password = request.POST.get('password')
        name = request.POST.get('name')
        surname = request.POST.get('surname')
        publickkey = request.POST.get('publickkey')
        # prepare a cursor object using cursor() method
        cursor = db.cursor()
        # execute SQL query using execute() method.
        cursor.execute("SELECT LOGIN FROM USERS WHERE LOGIN='%s'" % login)
        # Fetch a single row using fetchone() method.
        data = cursor.fetchone()
        if data is not None:
            return JsonResponse({"status": "ERROR LOGIN"})
        else:
            try:
                random_generator = Random.new().read
                key = RSA.generate(1024, random_generator).publickey().exportKey()
                serial = ""
                for x in key.split("\n")[1:-1]:
                    serial += x
                record = [login, password, name, surname, '0', publickkey, '1', serial, datetime.now().replace(year=datetime.now().year + 1)]
                cursor.execute(
                    'INSERT INTO USERS (LOGIN,PASSWORD,NAME,SURNAME,IS_ADMIN,PUBLIC_KEY, ISACTIVATED, Serial_number, Validitiy_period) VALUES(%s,%s,%s,%s,%s,%s,%s,%s,%s)',
                    record)
                db.commit()
                cursor.execute(
                    "SELECT CONCAT(NAME, ' ', SURNAME) as User_Name, LOGIN as Issuer_name,  PUBLIC_KEY, Serial_number, Validitiy_period, Version, Signature_Algorithm_Identifier, Hash_Algorithm FROM `users` WHERE `LOGIN` = '%s'" % (
                    login))
                db.commit()
                dict_certificate = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row
                                    in cursor.fetchall()]
                if len(dict_certificate) == 0:
                    return JsonResponse({"status": "invalid"})
                else:
                    return JsonResponse({"status": "ok", "data": dict_certificate})
            except Exception:
                db.rollback()
                return JsonResponse({"status": "ERROR"})


# api do wylogowania
@csrf_exempt
def api_logout(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')

        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN FROM USERS WHERE login='%s'" % login)
            data = cursor.fetchone()[0]

            # jezeli token jest poprawny to nastepuje wylogowanie
            if (data == token and token != None):
                # aktualizacja tokena na pusty
                cursor.execute("UPDATE USERS SET TOKEN = NULL WHERE LOGIN = '%s'" % (login))
                db.commit()
                return JsonResponse({"status": "logout"})
            else:
                return JsonResponse({"status": "invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})

@csrf_exempt
def api_replace_certificate(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')
        old_public_key = request.POST.get('old_public_key')
        new_public_key = request.POST.get('new_public_key')

        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN  FROM USERS WHERE login='%s'" % login)
            data = cursor.fetchone()[0]
            if (data == token and token != None):
                random_generator = Random.new().read
                key = RSA.generate(1024, random_generator).publickey().exportKey()
                serial = ""
                for x in key.split("\n")[1:-1]:
                    serial += x
                cursor.execute("UPDATE USERS SET PUBLIC_KEY = '%s', Serial_number = '%s', Validitiy_period = '%s' WHERE LOGIN = '%s' AND PUBLIC_KEY = '%s' " % (new_public_key, serial, datetime.now().replace(year=datetime.now().year + 1), login, old_public_key))
                db.commit()
                cursor.execute("SELECT CONCAT(NAME, ' ', SURNAME) as User_Name, LOGIN as Issuer_name,  PUBLIC_KEY, Serial_number, Validitiy_period, Version, Signature_Algorithm_Identifier, Hash_Algorithm FROM `users` WHERE `LOGIN` = '%s' AND `TOKEN` = '%s'" % (login, token))
                db.commit()
                dict_certificate = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row
                                        in cursor.fetchall()]
                if len(dict_certificate) == 0:
                    return JsonResponse({"status": "invalid"})
                else:
                    return JsonResponse({"status": "ok", "data": dict_certificate})
            else:
                return JsonResponse({"status": "invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})

@csrf_exempt
def api_download_all_certificate(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')

        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN FROM USERS WHERE login='%s'" % login)
            token_from_DB = cursor.fetchone()[0]

            if (token_from_DB == token and token != None):
                cursor.execute(
                    "SELECT LOCKS_KEYS.*, LOCKS.NAME AS LOCK_NAME, LOCKS.LOCALIZATION, MAC_ADDRESS FROM LOCKS_KEYS  RIGHT JOIN LOCKS  ON LOCKS.ID_LOCK=LOCKS_KEYS.ID_LOCK  WHERE ID_USER=(SELECT ID_USER FROM USERS WHERE LOGIN='%s') and (NOW() BETWEEN FROM_DATE AND TO_DATE) and ISACTUAL is NULL" % login)
                dict_all_certificate = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row
                                        in cursor.fetchall()]
                if len(dict_all_certificate) == 0:
                    return JsonResponse({"data": ""})
                else:
                    return JsonResponse({"data": dict_all_certificate})
            else:
                return JsonResponse({"status": "invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})


@csrf_exempt
def api_download_all_locks(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')
        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN FROM USERS WHERE login='%s'" % login)
            token_from_DB = cursor.fetchone()[0]
            if (token_from_DB == token and token != None):
                cursor.execute(
                    "SELECT NAME, LOCKS.LOCALIZATION, MAC_ADDRESS, ID_LOCK FROM LOCKS  ")
                dict_all_locks = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row
                                  in cursor.fetchall()]
                if len(dict_all_locks) == 0:
                    return JsonResponse({"data": "empty"})
                else:
                    return JsonResponse({"data": dict_all_locks})

            return JsonResponse({"status": "Invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})


@csrf_exempt
def api_download_all_user(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')
        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN FROM USERS WHERE login='%s'" % login)
            token_from_DB = cursor.fetchone()[0]
            if (token_from_DB == token and token != None):
                cursor.execute(
                    "SELECT LOGIN,ID_USER, CONCAT(NAME, ' ', SURNAME) as Name FROM USERS  ")
                dict_all_locks = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row
                                  in cursor.fetchall()]
                if len(dict_all_locks) == 0:
                    return JsonResponse({"data": "invalid"})
                else:
                    return JsonResponse({"data": dict_all_locks})

            return JsonResponse({"status": "Invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})


@csrf_exempt
def api_RPI_download_cetificate(request):
    if request.method == 'POST':
        certificate_id = request.POST.get('certificate_id')
        RPI_MAC = request.POST.get('mac')
        login_user = request.POST.get('login')

        try:
            cursor = db.cursor()
            cursor.execute(
                "SELECT LOCK_KEY, FROM_DATE, TO_DATE, ISACTUAL, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY, IS_PERNAMENT  FROM LOCKS_KEYS WHERE ID_KEY='%s' and  ID_LOCK=(SELECT ID_LOCK FROM LOCKS WHERE MAC_ADDRESS='%s')" % (
                    certificate_id, RPI_MAC))
            dict_all_certificate = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row
                                    in cursor.fetchall()]
            cursor2 = db.cursor()
            cursor2.execute("SELECT PUBLIC_KEY, LOGIN as Issuer_name, CONCAT(NAME, ' ', SURNAME) as User_Name, Serial_number, Validitiy_period, Version, Signature_Algorithm_Identifier, Hash_Algorithm FROM USERS WHERE LOGIN='%s'" % login_user)
            certificate_pki = [dict((cursor2.description[i][0], value) for i, value in enumerate(row)) for row
                                    in cursor2.fetchall()]

            if len(dict_all_certificate) == 0:
                return JsonResponse({"data": "invalid"})
            else:
                return JsonResponse({"data": dict_all_certificate, "certificatePKI": certificate_pki})
        except Exception:
            print "Error: api_RPI_download_cetificate"
            return JsonResponse({"data": "invalid"})

@csrf_exempt
def api_RPI_people_counter(request):
    if request.method == 'POST':
        counter = request.POST.get('counter')
        RPI_MAC = request.POST.get('mac')

        try:
            cursor = db.cursor()
            cursor.execute(
                "UPDATE locks SET People_inside='%s' WHERE MAC_ADDRESS='%s'" % (
                    counter, RPI_MAC))
            db.commit()
            return JsonResponse({"status": "ok"})
        except Exception:
            print "Error: api_RPI_people_counter"
            return JsonResponse({"status": "invalid"})


@csrf_exempt
def api_RPI_access_decision(request):
    if request.method == 'POST':
        desicion = request.POST.get('desicion')
        certificate_id = request.POST.get('certificate_id')
        try:
            cursor = db.cursor()
            cursor.execute(
                "INSERT INTO ACCESS_TO_LOCKS(ID_KEY, DATE, ACCESS) VALUES (%s,'%s',%s)" % (
                    certificate_id, datetime.now().strftime('%Y-%m-%d %H:%M:%S'), desicion))
            db.commit()
            return JsonResponse({"status": "ok"})
        except Exception:
            return JsonResponse({"status": "Invalid"})


@csrf_exempt
def api_deactivation(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')
        certificate_id = request.POST.get('certificate_id')

        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN FROM USERS WHERE LOGIN='%s'" % login)
            token_from_DB = cursor.fetchone()[0]
            if (token_from_DB == token and token != None):
                cursor = db.cursor()
                cursor.execute(
                    "UPDATE LOCKS_KEYS SET ISACTUAL='%s' WHERE ID_USER=(SELECT ID_USER FROM USERS WHERE LOGIN='%s') and ID_KEY='%s' " % (
                        datetime.now().strftime('%Y-%m-%d %H:%M:%S'), login, certificate_id))
                db.commit()
                return JsonResponse({"status": "ok"})
            else:
                return JsonResponse({"status": "invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})


@csrf_exempt
def api_request_new_certificate(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')
        lock_id = request.POST.get('lock_id')

        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN FROM USERS WHERE login='%s'" % login)
            token_from_DB = cursor.fetchone()[0]
            if (token_from_DB == token and token != None):
                cursor.execute(
                    "INSERT INTO WAIT_LOCKS_KEYS(ID_LOCK, ID_USER) VALUES ('%s',(SELECT ID_USER FROM USERS WHERE LOGIN='%s'))" % (
                        lock_id, login))
                db.commit()
                return JsonResponse({"status": "ok"})
                print "ok"
            else:
                return JsonResponse({"status": "invalid"})

        except Exception:
            return JsonResponse({"status": "Invalid"})



@csrf_exempt
def api_admin_block_certificate(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')
        user_login = request.POST.get('user_login')
        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN, IS_ADMIN FROM USERS WHERE login='%s'" % login)
            data = cursor.fetchone()
            for row in data:
                token_db = row[0]
                isadmin = row[1]
            if (token_db == token and token != None and isadmin == 1):
                cursor.execute("UPDATE USERS SET PUBLIC_KEY = NULL, Serial_number = NULL, Validitiy_period = NULL WHERE LOGIN = '%s' " % (user_login))
                db.commit()
                return JsonResponse({"status": "ok"})
            else:
                return JsonResponse({"status": "invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})


@csrf_exempt
def api_admin_generate_new_certificate(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')
        user_id = request.POST.get('user_id')
        lock_id = request.POST.get('lock_id')
        from_date = request.POST.get('from_date')
        to_date = request.POST.get('to_date')
        monday = request.POST.get('monday')
        tuesday = request.POST.get('tuesday')
        wednesday = request.POST.get('wednesday')
        thursday = request.POST.get('thursday')
        friday = request.POST.get('friday')
        saturday = request.POST.get('saturday')
        sunday = request.POST.get('sunday')
        is_pernament = request.POST.get('is_pernament')
        name = request.POST.get('name')
        surname = request.POST.get('surname')
        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN, IS_ADMIN, ID_USER FROM USERS WHERE login='%s'" % login)
            token_from_DB = cursor.fetchall()
            for row in token_from_DB:
                token_db = row[0]
                admin = row[1]
                admin_id = row[2]
            if (token_db == token and token != None) and admin == 1:
                random_generator = Random.new().read
                key = RSA.generate(1024, random_generator).publickey().exportKey()
                lock_key = ""

                for x in key.split("\n")[1:-1]:
                    lock_key += x
                #cursor.execute("SELECT * FROM USERS WHERE USER_ID='%s' and ID_LOCK='%s' and ISACTUAL n" % (user_id, lock_id))
                cursor.execute(
                    "INSERT INTO `locks_keys`(`ID_LOCK`, `ID_USER`, `LOCK_KEY`, `FROM_DATE`, `TO_DATE`, `MONDAY`, `TUESDAY`, `WEDNESDAY`, `THURSDAY`, `FRIDAY`, `SATURDAY`, `SUNDAY`, `IS_PERNAMENT`, `NAME`, `SURNAME`) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')" % (
                        lock_id, user_id, lock_key, from_date, to_date, monday, tuesday, wednesday, thursday, friday,
                        saturday,
                        sunday, is_pernament, name, surname))
                db.commit()
                return JsonResponse({"status": "ok"})
            else:
                return JsonResponse({"status": "invalid"})
        except Exception:
            return JsonResponse({"status": "invalid"})


@csrf_exempt
def api_admin_history(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')

        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN, IS_ADMIN FROM USERS WHERE login='%s'" % login)
            token_from_DB = cursor.fetchall()
            for row in token_from_DB:
                token_db = row[0]
                admin = row[1]
            if (token_db == token and token != None) and admin == 1:
                cursor = db.cursor()
                cursor.execute(
                    "SELECT DATE, ACCESS, locks_keys.NAME, locks_keys.SURNAME, locks.NAME AS 'ZAMEK' FROM access_to_locks, locks_keys, locks WHERE locks_keys.ID_KEY = access_to_locks.ID_KEY AND locks.ID_LOCK = access_to_locks.ID_KEY ORDER BY DATE DESC")
                dict_history = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row
                                in cursor.fetchall()]
                if len(dict_history) == 0:
                    return JsonResponse({"data": "invalid"})
            return JsonResponse({"data": dict_history})
        except Exception:
            return JsonResponse({"data": "invalid"})


@csrf_exempt
def api_admin_download_all_certificate(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')

        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN, IS_ADMIN FROM USERS WHERE login='%s'" % login)
            token_from_DB = cursor.fetchall()
            for row in token_from_DB:
                token_db = row[0]
                admin = row[1]
            if (token_db == token and token != None) and admin == 1:
                cursor.execute(
                    "SELECT LOCKS_KEYS.*, USERS.NAME AS USER_NAME, USERS.SURNAME AS USER_SURNAME, LOCKS.NAME AS LOCK_NAME, LOCKS.LOCALIZATION, MAC_ADDRESS FROM LOCKS_KEYS LEFT JOIN LOCKS ON LOCKS.ID_LOCK=LOCKS_KEYS.ID_LOCK LEFT JOIN users ON locks_keys.ID_USER = users.ID_USER ORDER BY LOCK_NAME")
                dict_all_certificate = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row
                                        in cursor.fetchall()]
                if len(dict_all_certificate) > 0:
                    return JsonResponse({"data": dict_all_certificate})
                else:
                    return JsonResponse({"data": "empty"})
            else:
                return JsonResponse({"status": "invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})


@csrf_exempt
def api_admin_deactivation(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')
        certificate_id = request.POST.get('certificate_id')

        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN, IS_ADMIN FROM USERS WHERE login='%s'" % login)
            token_from_DB = cursor.fetchall()
            for row in token_from_DB:
                token_db = row[0]
                admin = row[1]
            if (token_db == token and token != None) and admin == 1:
                cursor = db.cursor()
                cursor.execute(
                    "UPDATE LOCKS_KEYS SET ISACTUAL='%s' WHERE ID_KEY='%s' " % (
                        datetime.now().strftime('%Y-%m-%d %H:%M:%S'), certificate_id))
                db.commit()
                return JsonResponse({"status": "ok"})
            else:
                return JsonResponse({"status": "invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})

@csrf_exempt
def api_admin_deactivation_user_account(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')
        user_id = request.POST.get('user_id')

        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN, IS_ADMIN FROM USERS WHERE login='%s'" % login)
            token_from_DB = cursor.fetchall()
            for row in token_from_DB:
                token_db = row[0]
                admin = row[1]
            if (token_db == token and token != None) and admin == 1:
                cursor = db.cursor()
                cursor.execute(
                    "UPDATE USERS SET ISACTIVATED='2' WHERE ID_USER='%s' " % (user_id))
                db.commit()
                return JsonResponse({"status": "ok"})
            else:
                return JsonResponse({"status": "invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})

@csrf_exempt
def api_admin_deactivation_user_certificate(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')
        user_id = request.POST.get('user_id')

        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN, IS_ADMIN FROM USERS WHERE login='%s'" % login)
            token_from_DB = cursor.fetchall()
            for row in token_from_DB:
                token_db = row[0]
                admin = row[1]
            if (token_db == token and token != None) and admin == 1:
                cursor = db.cursor()
                cursor.execute(
                    "UPDATE USERS SET Validitiy_period='%s' WHERE ID_USER='%s' " % (datetime.now().strftime('%Y-%m-%d %H:%M:%S'), user_id))
                db.commit()
                return JsonResponse({"status": "ok"})
            else:
                return JsonResponse({"status": "invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})

@csrf_exempt
def api_admin_register_waiting(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')
        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN, IS_ADMIN FROM USERS WHERE login='%s'" % login)
            token_from_DB = cursor.fetchall()
            for row in token_from_DB:
                token_db = row[0]
                admin = row[1]
            if (token_db == token and token != None) and admin == 1:
                cursor.execute(
                    "SELECT users.LOGIN, users.NAME, users.SURNAME FROM users WHERE users.ISACTIVATED=1")
                dict_all_certificate = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row
                                        in cursor.fetchall()]
                if len(dict_all_certificate) > 0:
                    return JsonResponse({"data": dict_all_certificate})
                else:
                    return JsonResponse({"data": "empty"})
            else:
                return JsonResponse({"status": "invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})


@csrf_exempt
def api_admin_register_decision(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')
        user_login = request.POST.get('user_login')
        decision = request.POST.get('decision')
        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN, IS_ADMIN FROM USERS WHERE login='%s'" % login)
            token_from_DB = cursor.fetchall()
            for row in token_from_DB:
                token_db = row[0]
                admin = row[1]
            if (token_db == token and token != None) and admin == 1:
                cursor = db.cursor()
                cursor.execute("SELECT LOGIN FROM USERS WHERE login='%s' and ISACTIVATED=1" % user_login)
                login_from_DB = cursor.fetchall()
                if len(login_from_DB) > 0:
                    cursor = db.cursor()
                    if decision == "1":
                        cursor.execute(
                            "UPDATE USERS SET ISACTIVATED=0 WHERE LOGIN='%s' " % (user_login))
                    elif decision == "0":
                        cursor.execute("DELETE FROM users WHERE LOGIN='%s'" % user_login)
                    else:
                        return JsonResponse({"status": "invalid"})
                    db.commit()
                    return JsonResponse({"status": "ok"})
            return JsonResponse({"status": "invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})


@csrf_exempt
def api_admin_cetificate_waiting(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')
        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN, IS_ADMIN FROM USERS WHERE login='%s'" % login)
            token_from_DB = cursor.fetchall()
            for row in token_from_DB:
                token_db = row[0]
                admin = row[1]
            if (token_db == token and token != None) and admin == 1:
                cursor.execute(
                    "SELECT wait_locks_keys.ID_KEY, locks.NAME AS 'LOCK_NAME', users.ID_USER, users.LOGIN AS 'LOGIN',users.NAME AS 'USER_NAME',users.SURNAME AS 'USER_SUERNAME' FROM wait_locks_keys LEFT JOIN locks ON locks.ID_LOCK=wait_locks_keys.ID_LOCK LEFT JOIN users ON users.ID_USER=wait_locks_keys.ID_USER")
                dict_all_certificate = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row
                                        in cursor.fetchall()]
                if len(dict_all_certificate) > 0:
                    print dict_all_certificate
                    return JsonResponse({"data": dict_all_certificate})
                else:
                    return JsonResponse({"data": "empty"})
            else:
                return JsonResponse({"status": "invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})


@csrf_exempt
def api_admin_cetificate_decision(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')
        cetificate_id = request.POST.get('cetificate_id')
        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN, IS_ADMIN FROM USERS WHERE login='%s'" % login)
            token_from_DB = cursor.fetchall()
            for row in token_from_DB:
                token_db = row[0]
                admin = row[1]
            if (token_db == token and token != None) and admin == 1:
                cursor = db.cursor()
                cursor.execute("SELECT ID_KEY FROM wait_locks_keys WHERE ID_KEY='%s'" % cetificate_id)
                login_from_DB = cursor.fetchall()
                if len(login_from_DB) > 0:
                    cursor = db.cursor()
                    cursor.execute("DELETE FROM wait_locks_keys WHERE ID_KEY='%s'" % cetificate_id)
                    db.commit()
                    return JsonResponse({"status": "ok"})
            return JsonResponse({"status": "invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})


@csrf_exempt
def api_change_password(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')
        passwd = request.POST.get('passwd')
        newpasswd = request.POST.get('newpasswd')
        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN, PASSWORD FROM USERS WHERE login='%s'" % login)
            token_from_DB = cursor.fetchall()
            for row in token_from_DB:
                token_db = row[0]
                passwd_db = row[1]

            if (token_db == token and token != None) and passwd_db == passwd:
                cursor = db.cursor()
                cursor.execute(
                    "UPDATE USERS SET PASSWORD='%s' WHERE LOGIN='%s' " % (
                        newpasswd, login))
                db.commit()
                return JsonResponse({"status": "ok"})
            else:
                return JsonResponse({"status": "invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})
