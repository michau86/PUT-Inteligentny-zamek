# coding=utf-8
from random import randint

from django.shortcuts import render
from django.contrib.auth import authenticate  # metoda autoryzacji
from django.http import JsonResponse  # zwracanie JSONow w odpowiedzi
import hashlib  # generowanie SHA384
from django.contrib.auth.decorators import login_required
from django.views.decorators.csrf import csrf_exempt  # wylaczenie CSRF tokenow
import MySQLdb
from Crypto.PublicKey import RSA
from Crypto import Random


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

        return JsonResponse({"status":"ok", "token":token})

@csrf_exempt
def api_register(request):
    if request.method == 'POST':
        login =request.POST.get('login')
        password=request.POST.get('password')
        name= request.POST.get('name')
        surname=request.POST.get('surname')
        # Open database connection
        db = MySQLdb.connect("localhost", "testuser", "test123", "TESTDB")

        # prepare a cursor object using cursor() method
        cursor = db.cursor()

        # execute SQL query using execute() method.
        cursor.execute("SELECT Login FROM users WHERE login=%s" % login)

        # Fetch a single row using fetchone() method.
        data = cursor.fetchone()
        if(data != None):
            return JsonResponse({"status": "ERROR LOGIN"})
        else:
            #stworzyc skort hasla

            try:
                #
                hash_object = hashlib.sha384(password)
                passwordhash=hash_object.hexdigest()
                record = [login, passwordhash,name,surname,'0']
                cursor.execute("insert into users (LOGIN,PASSWORD,NAME,SURNAME,IS_ADMIN) values(%s,%s,%s,%s,%s)", record)
                db.commit()
                return JsonResponse({"status": "REGISTER OK"})
            except:
                db.rollback()
                return JsonResponse({"status": "ERROR"})
