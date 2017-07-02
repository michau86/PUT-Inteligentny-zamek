from bluetooth import *
import time
from Crypto.PublicKey import RSA
from Crypto.Hash import SHA256
import base64
from Crypto.Signature import PKCS1_v1_5
from Crypto import Random
import requests
import json
from datetime import datetime
from datetime import date

import Servo
import Models

server_address = 'http://192.168.137.1:8080/'
url_download_certificate = server_address + 'api/RPI/download/cerificate/'
url_access_decision = server_address + 'api/RPI/access_decision/'

def Check_access(certificate):
    if certificate.isactual is None:
        if certificate.ispernament == "1":
            return True
        else:
            if datetime.strptime(certificate.date_from, '%Y-%m-%dT%H:%M:%S') < datetime.now() < datetime.strptime(certificate.date_to, '%Y-%m-%dT%H:%M:%S'):
                try:
                    day_access = certificate.access_table[date.today().weekday()].split(";")
                except Exception:
                    day_access = []
                now = int(datetime.now().strftime('%H'))
                if len(day_access) > 0:
                    for x in day_access:
                        x = x.split("-")
                        try:
                            if int(x[0]) <= int(now) < int(x[1]):
                                return True
                        except Exception:
                            continue
    return False


if __name__ == '__main__':
    while True:
        try:
            with open('/sys/class/bluetooth/hci0/address') as mac:
                mac = mac.read().split("\n")[0]
            with open("log.log", "a") as log:
                log.write(datetime.now().strftime('%Y-%m-%d %H:%M:%S') + "\tStart working" + "\n")
            server_sock = BluetoothSocket(RFCOMM)
            server_sock.bind(("", PORT_ANY))
            server_sock.listen(1)

            port = server_sock.getsockname()[1]

            uuid = "fa87c0d0-afac-11de-8a39-0800200c9a66"

            advertise_service(server_sock, "BluetoothChatSecure",
                              service_id=uuid,
                              service_classes=[uuid, SERIAL_PORT_CLASS],
                              profiles=[SERIAL_PORT_PROFILE])

            servo = Servo.Servo()
            print "Waiting for connection on RFCOMM channel %d" % port
            while True:
                try:
                    client_sock, client_info = server_sock.accept()
                    with open("log.log", "a") as log:
                        log.write(datetime.now().strftime('%Y-%m-%d %H:%M:%S') + ":\tAccepted connection from " + str(
                            client_info[0]) + "\n")

                    data = client_sock.recv(1024)
                    try:
                        id_certificate = data.split(";")[0]
                        login = data.split(";")[1]
                        signature_lock_key = data.split(";")[2]
                        data = True
                    except Exception:
                        data = False

                    with open("log.log", "a") as log:
                        log.write(datetime.now().strftime('%Y-%m-%d %H:%M:%S') + ":\tReceived data: " + str(
                            data) + " from " + str(client_info[0]) + "\n")

                    if data:
                        request = requests.post(url_download_certificate,
                                                data={'mac': mac, 'certificate_id': id_certificate, 'login': login})
                        if request.json()['data'] == 'invalid':
                            with open("log.log", "a") as log:
                                log.write(
                                    datetime.now().strftime(
                                        '%Y-%m-%d %H:%M:%S') + ":\tAccess denied to lock: received data false" + "\n")
                            request1 = requests.post(url_access_decision,
                                                    data={'certificate_id': id_certificate,
                                                          'desicion': "0"})
                            client_sock.send("Access denied")
                        else:
                            certificate = Models.Certificate(isactual=request.json()['data'][0]['ISACTUAL'],
                                                             ispernament=request.json()['data'][0]['IS_PERNAMENT'],
                                                             date_from=request.json()['data'][0]['FROM_DATE'],
                                                             date_to=request.json()['data'][0]['TO_DATE'],
                                                             lock_key=request.json()['data'][0]['LOCK_KEY'],
                                                             monday=request.json()['data'][0]['MONDAY'],
                                                             tuesday=request.json()['data'][0]['TUESDAY'],
                                                             wednesday=request.json()['data'][0]['WEDNESDAY'],
                                                             thursday=request.json()['data'][0]['THURSDAY'],
                                                             friday=request.json()['data'][0]['FRIDAY'],
                                                             saturday=request.json()['data'][0]['SATURDAY'],
                                                             sunday=request.json()['data'][0]['SUNDAY'])
                            if request.json()['public_key'][0] is None:
                                with open("log.log", "a") as log:
                                    log.write(datetime.now().strftime(
                                        '%Y-%m-%d %H:%M:%S') + ":\tAccess denied to lock : public key false" + "\n")
                                request = requests.post(url_access_decision,
                                                        data={'certificate_id': id_certificate,
                                                              'desicion': "0"})
                                client_sock.send("Access denied")
                            else:
                                key = '-----BEGIN PUBLIC KEY-----\n' + request.json()['public_key'][0] + '\n-----END PUBLIC KEY-----'
                                public_key = RSA.importKey(key)
                                verifier = PKCS1_v1_5.new(public_key)
                                if verifier.verify(SHA256.new(certificate.lock_key), base64.standard_b64decode(signature_lock_key)):
                                    if Check_access(certificate):
                                        with open("log.log", "a") as log:
                                            log.write(datetime.now().strftime(
                                                '%Y-%m-%d %H:%M:%S') + ":\tAccess granted to lock" + "\n")
                                        request1 = requests.post(url_access_decision,
                                                                data={'certificate_id': id_certificate,
                                                                      'desicion': "1"})
                                        client_sock.send("Access granted")
                                        servo.Open()
                                        time.sleep(10)
                                        servo.Close()
                                    else:
                                        with open("log.log", "a") as log:
                                            log.write(datetime.now().strftime(
                                                '%Y-%m-%d %H:%M:%S') + ":\tAccess denied to lock : certificate false" + "\n")
                                        request1 = requests.post(url_access_decision,
                                                                data={'certificate_id': id_certificate,
                                                                      'desicion': "0"})
                                        client_sock.send("Access denied")
                                else:
                                    with open("log.log", "a") as log:
                                        log.write(datetime.now().strftime(
                                            '%Y-%m-%d %H:%M:%S') + ":\tAccess denied to lock : signature false" + "\n")
                                    request1 = requests.post(url_access_decision,
                                                            data={'certificate_id': id_certificate,
                                                                  'desicion': "0"})
                                    client_sock.send("Access denied")

                    else:
                        request1 = requests.post(url_access_decision,
                                                  data={'certificate_id': id_certificate,'desicion': "0"})
                        client_sock.send("Access denied")

                    client_sock.close()

                except IOError:
                    pass

        except KeyboardInterrupt:
            print "disconnected"
            servo.Destroy()
            server_sock.close()
            print "all done"
            with open("log.log", "a") as log:
                log.write(datetime.now().strftime('%Y-%m-%d %H:%M:%S') + "\tExit with KeyboardInterrupt" + "\n")
            break

        '''except Exception:
                servo.Destroy()
                server_sock.close()
                client_sock.close()
                with open("log.log", "a") as log:
                    log.write(datetime.now().strftime('%Y-%m-%d %H:%M:%S') + "\tError for unknow reson" + "\n")
                print "Error for unknow reson"
                break'''
