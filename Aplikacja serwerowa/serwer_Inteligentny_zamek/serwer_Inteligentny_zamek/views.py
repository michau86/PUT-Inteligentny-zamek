# coding=utf-8

from django.http import JsonResponse  # zwracanie JSONow w odpowiedzi
from django.views.decorators.csrf import csrf_exempt  # wylaczenie CSRF tokenow
import MySQLdb
from Crypto.PublicKey import RSA
from Crypto import Random
from datetime import datetime

username = "maciej"
userpassword = "WApet1995"
databasename = "Inteligentny_zamek_db"
databaseaddres = "192.168.137.204"

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
            cursor.execute("SELECT PASSWORD FROM USERS WHERE login='%s'" % username)
            data = cursor.fetchone()[0]
            if (data == password):
                cursor.execute("UPDATE USERS SET TOKEN = '%s' WHERE LOGIN = '%s'" % (token, username))
                db.commit()

                return JsonResponse({"status": "ok", "token": token})
            else:
                return JsonResponse({"status": "ERROR PASSWORD", "token": "invalid"})
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
                record = [login, password, name, surname, '0']
                cursor.execute('INSERT INTO USERS (LOGIN,PASSWORD,NAME,SURNAME,IS_ADMIN) VALUES(%s,%s,%s,%s,%s)',
                               record)
                db.commit()
                return JsonResponse({"status": "REGISTER OK"})
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
            if (data == token):
                # aktualizacja tokena na pusty
                cursor.execute("UPDATE USERS SET TOKEN = '' WHERE LOGIN = '%s'" % (login))
                db.commit()
                return JsonResponse({"status": "logout"})
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

            if (token_from_DB == token):
                cursor.execute(
                    "SELECT * FROM LOCKS_KEYS WHERE ID_USER=(SELECT ID_USER FROM USERS WHERE LOGIN='%s')" % login)
                dict_all_certificate = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row
                                        in cursor.fetchall()]
                return JsonResponse({"data": dict_all_certificate})
            else:
                return JsonResponse({"status": "invalid"})
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
        desicion = request.POST.get('decision')
        certificate_id = request.POST.get('certificate_id')
        try:
            cursor = db.cursor()
            cursor.execute(
                "INSERT INTO `ACCESS_TO_LOCKS`(`ID_KEY`, `DATE`, `ACCESS`) VALUES (%s,[value-3],[value-4])" % (
                certificate_id, datetime.now().strftime('%Y-%m-%d %H:%M:%S')), desicion)
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
            if (token_from_DB == token):
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

            if (token_from_DB == token):
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
def api_generate_new_quest_certificate(request):
    if request.method == 'POST':
        login = request.POST.get('login')
        token = request.POST.get('token')
        lock_id = request.POST.get('lock_id')
        quest_name = request.POST.get('quest_name')
        quest_surname = request.POST.get('quest_surname')
        period = request.POST.get('periot')

        try:
            cursor = db.cursor()
            cursor.execute("SELECT TOKEN FROM USERS WHERE login='%s'" % login)
            token_from_DB = cursor.fetchone()[0]

            if (
                token_from_DB == token):  # TODO: jak zrobić by nie przydzielać uprawnień, których sam użytkownik nie ma?
                return JsonResponse({"status": "ok"})
            else:
                return JsonResponse({"status": "invalid"})
        except Exception:
            return JsonResponse({"status": "Invalid"})
