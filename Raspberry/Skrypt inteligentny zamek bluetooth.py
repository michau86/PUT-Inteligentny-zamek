import bluetooth
import lightblue
# we should know
target_name = "Galaxy S5 Neo"
file_to_send = "./image.jpg"
# we don't know yet
obex_port = None
target_address = None

print "poszukiwanie dostepnych urzadzen..."
nearby_devices = bluetooth.discover_devices()

for bdaddr in nearby_devices:
    print bluetooth.lookup_name( bdaddr )
    if target_name == bluetooth.lookup_name( bdaddr ):
        print "Znaleziono wybrane urzadzenie"
        target_address = bdaddr
        break
    
services = lightblue.findservices(target_address)
for service in services:
    if service[2] == "OBEX Object Push":
        obex_port = service[1]
        print "OK, service '", service[2], "' is in port", service[1], "!"
        break

print "Wysylam plik..."
try:
    lightblue.obex.sendfile( target_address, service[1], file_to_send )
    print "ukonczono!\n"
except:
    print "an error occurred while sending file\n"
