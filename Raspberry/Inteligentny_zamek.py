from bluetooth import *
import time
from Crypto.PublicKey import RSA
from Crypto import Random
import requests
import json
from datetime import datetime

import Servo

def Compare(certificat1, certificat2):
    if True:
        return True
    else:
        return False

if __name__ == '__main__':
	while True:
		try:
			server_sock = BluetoothSocket(RFCOMM)
			server_sock.bind(("", PORT_ANY))
			server_sock.listen(1)
			
			port = server_sock.getsockname()[1]
			
			uuid = "fa87c0d0-afac-11de-8a39-0800200c9a66"
			
			advertise_service(server_sock, "BluetoothChatSecure",
							service_id=uuid,
							service_classes=[uuid, SERIAL_PORT_CLASS],
							profiles=[SERIAL_PORT_PROFILE])
			
			random_generator = Random.new().read
			key = RSA.generate(1024, random_generator)
			
			public_key = key.publickey()
			
			with open("rsa.pub", "w") as pub_file:
				pub_file.write(public_key.exportKey())
			
			with open("rsa.pvt", "w") as pvt_file:
				pvt_file.write(key.exportKey())
			
			servo = Servo.Servo()
			
			url = 'http://192.168.137.232:8000/api/login/'
			print "Waiting for connection on RFCOMM channel %d" % port
			while True:
				try:
					client_sock, client_info = server_sock.accept()
					with open("log.log", "a") as log:
						log.write(datetime.now().strftime('%Y-%m-%d %H:%M:%S') + ":\tAccepted connection from " + str(client_info[0]) + "\n")
					
					data = client_sock.recv(1024)
			
					with open("log.log", "a") as log:
						log.write(datetime.now().strftime('%Y-%m-%d %H:%M:%S') + ":\tReceived data: " + data + " from " + str(client_info[0]) + "\n")
			
					r = requests.post(url, data={'username': 'aaa', 'password': 'password'})
					
					with open('rsa.pub', 'r') as pub_file:
						pub_key = RSA.importKey(pub_file.read())
					client_sock.send(r.json()['token'])
			
					certificat1 = None
					certificat2 = None
					
					if Compare(certificat1, certificat2):
						with open("log.log", "a") as log:
							log.write(datetime.now().strftime('%Y-%m-%d %H:%M:%S') + ":\tAccess to lock" + "\n")
						servo.Open()
						time.sleep(10)
						servo.Close()
					else:
						with open("log.log", "a") as log:
							log.write(datetime.now().strftime('%Y-%m-%d %H:%M:%S') + ":\tAccess denied to lock" + "\n")
			
					client_sock.close()
			
				except IOError:
					pass
		
		except KeyboardInterrupt:
				print "disconnected"
				servo.Destroy()
				server_sock.close()
				print "all done"
				break
		
		except Exception:
				servo.Destroy()
				server_sock.close()
				client_sock.close()
				with open("log.log", "a") as log:
					log.write(datetime.now().strftime('%Y-%m-%d %H:%M:%S') + "\tError for unknow reson" + "\n")
				print "Error for unknow reson"
				break
