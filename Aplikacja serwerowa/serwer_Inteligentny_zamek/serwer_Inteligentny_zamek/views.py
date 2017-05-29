
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
from parse import *

username="root"
userpassword="1234"
databasename="inteligentny_zamek_db"
databaseaddres="localhost"

@csrf_exempt
def api_login(request):
    username = request.POST.get('username')
    password = request.POST.get('password')

    random_generator = Random.new().read
    key = RSA.generate(1024, random_generator).publickey().exportKey()
    token = ""

    for x in key.split("\n")[1:-1]:
                token += x
    # trzeba zapisac do BD

    db = MySQLdb.connect(databaseaddres, username, userpassword, databasename)
    # prepare a cursor object using cursor() method
    cursor = db.cursor()
    # execute SQL query using execute() method.

    cursor.execute("SELECT PASSWORD FROM users WHERE login='%s'" %username)
    data = cursor.fetchone()[0]
    print data
    print password
    if(data==password):

        cursor.execute("UPDATE users SET TOKEN = '%s' WHERE LOGIN = '%s'" % (token, username))
        data = cursor.fetchone()

        return JsonResponse({"status": "ok", "token": token})

    else:

        return JsonResponse({"status": "ERROR PASSWORD", "token": "invalid"})


@csrf_exempt
def api_register(request):
    if request.method == 'POST':
        login =request.POST.get('login')
        password=request.POST.get('password')
        name= request.POST.get('name')
        surname=request.POST.get('surname')
        # Open database connection
        db = MySQLdb.connect(databaseaddres, username, userpassword, databasename)

        # prepare a cursor object using cursor() method
        cursor = db.cursor()

        # execute SQL query using execute() method.
        cursor.execute("SELECT Login FROM users WHERE login='%s'" % login)

        # Fetch a single row using fetchone() method.
        data = cursor.fetchone()
        if(data != None):
            return JsonResponse({"status": "ERROR LOGIN"})
        else:
            #stworzyc skort hasla

            try:
                #
                record = [login, password,name,surname,'0']
                cursor.execute("insert into users (LOGIN,PASSWORD,NAME,SURNAME,IS_ADMIN) values(%s,%s,%s,%s,%s)", record)
                db.commit()
                return JsonResponse({"status": "REGISTER OK"})
            except:
                db.rollback()
                return JsonResponse({"status": "ERROR"})