# coding=utf-8
from django.shortcuts import render
from django.contrib.auth import authenticate  # metoda autoryzacji
from django.http import JsonResponse  # zwracanie JSONow w odpowiedzi
import hashlib  # generowanie SHA384
from django.contrib.auth.decorators import login_required
from django.views.decorators.csrf import csrf_exempt  # wylaczenie CSRF tokenow
import MySQLdb

@csrf_exempt

def api_login(request):
    if request.method == 'POST':
        username = request.POST.get('username')
        password = request.POST.get('password')



        return JsonResponse({"status":"ok", "token":"sample token"})

@csrf_exempt
def api_register(request):
    if request.method == 'POST':
        login =request.POST.get('login')
        password=request.POST.get('password')
        name= request.POST.get('name')
        surname=request.POST.get('surname')
        print (login)
        # Open database connection
        db = MySQLdb.connect("localhost", "root", "1234", "inteligentny_zamek_db")

        # prepare a cursor object using cursor() method
        cursor = db.cursor()



        # execute SQL query using execute() method.
        cursor.execute("SELECT Login FROM users WHERE login='%s'" %login)

        # Fetch a single row using fetchone() method.
        data = cursor.fetchone()
        if(data != None):
            return JsonResponse({"status": "ERROR LOGIN"})
        else:


            try:
                #
                record = [login, password,name,surname,'0']
                cursor.execute("insert into users (LOGIN,PASSWORD,NAME,SURNAME,IS_ADMIN) values(%s,%s,%s,%s,%s)", record)
                db.commit()
                return JsonResponse({"status": "REGISTER OK"})
            except:
                db.rollback()
                return JsonResponse({"status": "ERROR"})
        db.close()