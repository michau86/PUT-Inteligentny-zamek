import numpy as np
import cv2
import Person
import time
import requests
import json
import argparse

cnt_up   = 0
cnt_down = 0
server_address = 'http://192.168.137.1:8080/'
url_people_counter = server_address + 'api/RPI/people_counter/'
mac = "B8:27:EB:FC:73:A2"

ap = argparse.ArgumentParser()
ap.add_argument("-ip", "--ipcamera", help="ip camera use")
args = vars(ap.parse_args())
if args.get("ipcamera", None) is None:
    width_camera = 1280
    height_camera = 960
    from picamera.array import PiRGBArray
    from picamera import PiCamera
    cap = PiCamera(resolution=(width_camera, height_camera))
    cap.iso = 800
    cap.led = False
    rawCapture = PiRGBArray(cap, size=(width_camera, height_camera))
    cap.rotation = 180
    cap.brightness = 55
    cap.framerate = 24
    cap.color_effects = None
    cap.exposure_mode = 'auto'
    cap.shutter_speed = 6000000
    time.sleep(0.25)
    cap.start_preview()
    time.sleep(2)
else:
    cap = cv2.VideoCapture("rtsp://admin:admin@192.168.130.140:554/cam/realmonitor?channel=1&subtype=0&unicast=true&proto=Onvif")
    time.sleep(0.25)
    width_camera = cap.get(3)
    height_camera = cap.get(4)
    
frameArea = height_camera*width_camera
areaTH = frameArea/250

line_up = int(2*(height_camera/5))
line_down   = int(3*(height_camera/5))

up_limit =   int(1*(height_camera/5))
down_limit = int(4*(height_camera/5))

pt1 =  (0, line_down)
pt2 =  (int(width_camera), line_down)

pt3 =  (0, line_up)
pt4 =  (int(width_camera), line_up)

fgbg = cv2.BackgroundSubtractorMOG2()

kernelOp = np.ones((3,3),np.uint8)
kernelOp2 = np.ones((5,5),np.uint8)
kernelCl = np.ones((11,11),np.uint8)

font = cv2.FONT_HERSHEY_SIMPLEX
persons = []
max_p_age = 5
pid = 1
cnt_now = 0
while(1):
    if args.get("ipcamera", None) is None:
            cap.capture(rawCapture, format='bgr', use_video_port=True)
            frame = rawCapture.array
            rawCapture.truncate(0)
    else:
            (ret, frame) = cap.read()

    for i in persons:
        i.age_one()
    
    fgmask = fgbg.apply(frame)

    try:
        ret,imBin = cv2.threshold(fgmask,200,255,cv2.THRESH_BINARY)
        mask = cv2.morphologyEx(imBin, cv2.MORPH_OPEN, kernelOp)
        mask = cv2.morphologyEx(mask, cv2.MORPH_CLOSE, kernelCl)
    except:
        break
    
    contours0, hierarchy = cv2.findContours(mask,cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)
    for cnt in contours0:
        area = cv2.contourArea(cnt)
        if area > areaTH:
            M = cv2.moments(cnt)
            cx = int(M['m10']/M['m00'])
            cy = int(M['m01']/M['m00'])
            x,y,width_camera,height_camera = cv2.boundingRect(cnt)

            new = True
            if cy in range(up_limit,down_limit):
                for i in persons:
                    if abs(cx-i.getX()) <= width_camera and abs(cy-i.getY()) <= height_camera:
                        new = False
                        i.updateCoords(cx,cy)
                        if i.going_UP(line_down,line_up) == True:
                            cnt_up += 1;
                            print "ID:",i.getId(),'person in at',time.strftime("%c")
                        elif i.going_DOWN(line_down,line_up) == True:
                            cnt_down += 1;
                            print "ID:",i.getId(),'person out at',time.strftime("%c")
                        break
                    if i.getState() == '1':
                        if i.getDir() == 'down' and i.getY() > down_limit:
                            i.setDone()
                        elif i.getDir() == 'up' and i.getY() < up_limit:
                            i.setDone()
                    if i.timedOut():
                        index = persons.index(i)
                        persons.pop(index)
                        del i
                if new == True:
                    p = Person.MyPerson(pid,cx,cy, max_p_age)
                    persons.append(p)
                    pid += 1
                    
            #cv2.circle(frame,(cx,cy), 5, (0,0,255), -1)
            #img = cv2.rectangle(frame,(x,y),(x+width_camera,y+height_camera),(0,255,0),2)
    cnt_old = cnt_now
    cnt_now = cnt_up - cnt_down
    if not cnt_old == cnt_now:
        print cnt_now
        request = requests.post(url_people_counter, data={'counter': cnt_now,'mac': mac})
    '''for i in persons:
        cv2.putText(frame, str(i.getId()),(i.getX(),i.getY()),font,0.8,i.getRGB(),1)
        
    str_up = 'IN: '+ str(cnt_up)
    str_down = 'OUT: '+ str(cnt_down)
    cv2.line(frame, (0, line_down), pt2, (0, 0, 255), 2) #red line
    cv2.line(frame, (0, line_up), pt4, (250, 0, 1), 2) #blue line
    cv2.putText(frame, str_up ,(10,40),font,0.5,(255,255,255),2)
    cv2.putText(frame, str_up ,(10,40),font,0.5,(0,0,255),1)
    cv2.putText(frame, str_down ,(10,90),font,0.5,(255,255,255),2)
    cv2.putText(frame, str_down ,(10,90),font,0.5,(255,0,0),1)

    cv2.imshow('Frame',frame)    
    
    k = cv2.waitKey(30) & 0xff
    if k == 27:
        break'''

if args.get("ipcamera", None) is None:
    cap.stop_preview()
else:
    cap.release()
#cv2.destroyAllWindows()