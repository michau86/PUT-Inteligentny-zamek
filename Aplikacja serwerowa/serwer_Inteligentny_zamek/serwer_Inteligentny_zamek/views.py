# coding=utf-8

from django.http import JsonResponse  # zwracanie JSONow w odpowiedzi
from django.views.decorators.csrf import csrf_exempt  # wylaczenie CSRF tokenow
import MySQLdb
from Crypto.PublicKey import RSA
from Crypto import Random
from datetime import datetime
import datetime as dt
from datetime import date

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
        # trzeba zapisac do BD
        try:
            cursor = db.cursor()
            cursor.execute("SELECT PASSWORD, IS_ADMIN, ISACTIVATED FROM USERS WHERE login='%s'" % username)
            data = cursor.fetchone()
            if data[2] == 0:
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
            print "aa"
            return JsonResponse({"status": "ERROR LOGIN"})
        else:
            try:
                record = [login, password, name, surname, '0', publickkey, '1']
                cursor.execute(
                    'INSERT INTO USERS (LOGIN,PASSWORD,NAME,SURNAME,IS_ADMIN,PUBLIC_KEY, ISACTIVATED) VALUES(%s,%s,%s,%s,%s,%s,%s)',
                    record)
                db.commit()
                return JsonResponse({"status": "REGISTER OK"})
            except Exception:
                db.rollback()
                print  publickkey
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

    #try:
        cursor = db.cursor()
        cursor.execute("SELECT TOKEN  FROM USERS WHERE login='%s'" % login)
        data = cursor.fetchone()[0]

        # jezeli token jest poprawny to nastepuje wylogowanie
        if (data == token and token != None):
            random_generator = Random.new().read
            key = RSA.generate(1024, random_generator).publickey().exportKey()
            serial = ""

            for x in key.split("\n")[1:-1]:
                serial += x
            cursor.execute("UPDATE USERS SET PUBLIC_KEY = '%s', Serial_number = '%s', Validitiy_period = '%s' WHERE LOGIN = '%s' AND PUBLIC_KEY = '%s' " % (new_public_key, serial, datetime.now() , login, old_public_key))
            db.commit()
            cursor.execute("SELECT CONCAT(NAME, SURNAME) as User_Name, LOGIN as Issuer_name,  PUBLIC_KEY, Serial_number, Validitiy_period, Version, Signature_Algorithm_Identifier, Hash_Algorithm FROM `users` WHERE `LOGIN` = '%s' AND `TOKEN` = '%s'" % (login, token))
            db.commit()
            dict_certificate = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row
                                    in cursor.fetchall()]
            if len(dict_certificate) == 0:
                return JsonResponse({"status": "invalid"})
            else:
                return JsonResponse({"status": "ok", "data": dict_certificate})
        else:
            return JsonResponse({"status": "invalid"})
    #except Exception:
#    return JsonResponse({"status": "Invalid"})

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
                    "SELECT LOGIN,ID_USER FROM USERS  ")
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
                "SELECT LOCK_KEY, FROM_DATE, TO_DATE, ISACTUAL, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY, IS_PERNAMENT FROM LOCKS_KEYS WHERE ID_KEY='%s' and  ID_LOCK=(SELECT ID_LOCK FROM LOCKS WHERE MAC_ADDRESS='%s')" % (
                    certificate_id, RPI_MAC))
            dict_all_certificate = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row
                                    in cursor.fetchall()]
            cursor2 = db.cursor()
            cursor2.execute("SELECT PUBLIC_KEY FROM USERS WHERE LOGIN='%s'" % login_user)
            public_key = cursor2.fetchone()

            if len(dict_all_certificate) == 0:
                return JsonResponse({"data": "invalid"})
            else:
                return JsonResponse({"data": dict_all_certificate, "public_key": public_key})
        except Exception:
            print "Error: api_RPI_download_cetificate"
            return JsonResponse({"data": "invalid"})


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
        print "aaa"
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


def Check_access(day_access, time):
    if len(day_access) > 0:
        for x in day_access:
            x = x.split("-")
            if int(x[0]) <= int(time) < int(x[1]):
                return True
    return False


@csrf_exempt
def api_generate_new_quest_certificate(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')
        lock_id = request.POST.get('lock_id')
        guest_name = request.POST.get('guest_name')
        guest_surname = request.POST.get('guest_surname')
        from_date = request.POST.get('from_date')
        period = request.POST.get('period')

        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN, NAME, SURNAME, ID_USER, IS_ADMIN FROM USERS WHERE login='%s'" % login)
            data_from_DB = cursor.fetchone()
            token_db = data_from_DB[0]
            id_user = data_from_DB[3]
            is_admin = data_from_DB[4]
            if (token_db == token and token != None):
                cursor = db.cursor()
                cursor.execute(
                    "SELECT FROM_DATE, TO_DATE, ISACTUAL, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY, IS_PERNAMENT FROM locks_keys WHERE ID_LOCK='%s' and ID_USER='%s'" % (
                        lock_id, id_user))
                certificate_actual = cursor.fetchone()
                from_date_db = certificate_actual[0]
                to_date_db = certificate_actual[1]
                is_actual = certificate_actual[2]
                access_table = [certificate_actual[3], certificate_actual[4], certificate_actual[5],
                                certificate_actual[6], certificate_actual[7], certificate_actual[8],
                                certificate_actual[9]]
                is_pernament = certificate_actual[10]
                if is_actual is not None or is_admin == 1:
                    if is_pernament == 1 or is_admin == 1:
                        random_generator = Random.new().read
                        key = RSA.generate(1024, random_generator).publickey().exportKey()
                        lock_key = ""
                        for x in key.split("\n")[1:-1]:
                            lock_key += x
                        if period <= 5:
                            period = period * 60
                        to_date = (datetime.strptime(from_date, '%Y-%m-%dT%H:%M:%S') + dt.timedelta(
                            minutes=period)).strftime('%Y-%m-%d %H:%M:%S')
                        if datetime.strptime(from_date_db, '%Y-%m-%dT%H:%M:%S') <= datetime.strptime(to_date,
                                                                                                     '%Y-%m-%dT%H:%M:%S') <= datetime.strptime(
                                to_date_db, '%Y-%m-%dT%H:%M:%S') or is_admin == 1:
                            record = [lock_id, 132, lock_key, datetime.now().strftime('%Y-%m-%d %H:%M:%S'), to_date, 1,
                                      guest_name, guest_surname]
                            cursor.execute(
                                'INSERT INTO locks_keys (ID_LOCK, ID_USER, LOCK_KEY, FROM_DATE, TO_DATE, IS_PERNAMENT, NAME, SURNAME) VALUES (%s,%s,%s,%s,%s,%s,%s,%s)',
                                record)
                            db.commit()
                            cursor.execute(
                                "SELECT LOCKS_KEYS.*, LOCKS.NAME AS LOCK_NAME, LOCKS.LOCALIZATION, MAC_ADDRESS FROM LOCKS_KEYS  RIGHT JOIN LOCKS  ON LOCKS.ID_LOCK=LOCKS_KEYS.ID_LOCK  WHERE LOCK_KEY ='%s'" % lock_key)
                            dict_certificate = [
                                dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row
                                in cursor.fetchall()]
                            if len(dict_certificate) == 0:
                                return JsonResponse({"status": "ok", "data": ""})
                            else:
                                return JsonResponse({"status": "ok", "data": dict_certificate})
                    else:
                        random_generator = Random.new().read
                        key = RSA.generate(1024, random_generator).publickey().exportKey()
                        lock_key = ""
                        for x in key.split("\n")[1:-1]:
                            lock_key += x
                        if period <= 5:
                            period = period * 60
                        to_date = (datetime.strptime(from_date, '%Y-%m-%dT%H:%M:%S') + dt.timedelta(
                            minutes=period)).strftime('%Y-%m-%d %H:%M:%S')
                        day_access = access_table[datetime.strptime(to_date, '%Y-%m-%dT%H:%M:%S').weekday()].split(";")
                        to_date_time = int(to_date.strftime('%H'))
                        if datetime.strptime(from_date_db, '%Y-%m-%dT%H:%M:%S') <= datetime.strptime(to_date,
                                                                                                     '%Y-%m-%dT%H:%M:%S') <= datetime.strptime(
                                to_date_db, '%Y-%m-%dT%H:%M:%S') and Check_access(day_access, to_date_time):
                            record = [lock_id, 132, lock_key, from_date, to_date, 1, guest_name, guest_surname]
                            cursor.execute(
                                'INSERT INTO locks_keys (ID_LOCK, ID_USER, LOCK_KEY, FROM_DATE, TO_DATE, IS_PERNAMENT, NAME, SURNAME) VALUES (%s,%s,%s,%s,%s,%s,%s,%s)',
                                record)
                            db.commit()
                            cursor.execute(
                                "SELECT LOCKS_KEYS.*, LOCKS.NAME AS LOCK_NAME, LOCKS.LOCALIZATION, MAC_ADDRESS FROM LOCKS_KEYS  RIGHT JOIN LOCKS  ON LOCKS.ID_LOCK=LOCKS_KEYS.ID_LOCK  WHERE LOCK_KEY ='%s'" % lock_key)
                            dict_certificate = [
                                dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row
                                in cursor.fetchall()]
                            if len(dict_certificate) == 0:
                                return JsonResponse({"status": "ok", "data": ""})
                            else:
                                return JsonResponse({"status": "ok", "data": dict_certificate})
                return JsonResponse({"status": "denied"})
            else:
                return JsonResponse({"status": "invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})
