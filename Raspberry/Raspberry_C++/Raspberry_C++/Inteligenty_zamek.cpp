#include <stdio.h>
#include <curl/curl.h>
#include <unistd.h>
#include <sys/socket.h>
#include <bluetooth/bluetooth.h>
#include <bluetooth/rfcomm.h>
#include <fstream>
#include <string>
#include <iostream>
#include <chrono>

#include "Servo.h"
#include "Certificate.h"

using namespace std;
int main(int argc, char **argv)
{
	struct sockaddr_rc loc_addr, rem_addr;
	char buf[1024];
	socklen_t opt;
	bdaddr_t my_bdaddr_any;
	int s, client, bytes_read;
	loc_addr = { 0 };
	rem_addr = { 0 };
	buf[1024] = { 0 };
	opt = sizeof(rem_addr);
	// allocate socket
	s = socket(AF_BLUETOOTH, SOCK_STREAM, BTPROTO_RFCOMM);
	// bind socket to port 1 of the first available
	// local bluetooth adapter
	loc_addr.rc_family = AF_BLUETOOTH;
	my_bdaddr_any = { 0 };
	loc_addr.rc_bdaddr = my_bdaddr_any;
	loc_addr.rc_channel = (uint8_t)1;
	bind(s, (struct sockaddr *)&loc_addr, sizeof(loc_addr));
	// put socket into listening mode
	listen(s, 1);
	ofstream out("log.log", ios::app);
	while (1)
	{
		// accept one connection
		client = accept(s, (struct sockaddr *)&rem_addr, &opt);
		ba2str(&rem_addr.rc_bdaddr, buf);
		time_t time_now = time(0);
		struct tm * now = localtime(&time_now);
		out << now->tm_year+1900<<"-"<<now->tm_mon + 1<<"-"<<now->tm_mday << " " << now->tm_hour << ":" << now->tm_min << ":" << now->tm_sec << ": accepted connection from "<< buf<< endl;
		fprintf(stderr, "accepted connection from %s\n", buf);
		memset(buf, 0, sizeof(buf));

		// read data from the client
		bytes_read = read(client, buf, sizeof(buf));
		if (bytes_read > 0) {
			printf("received [%s]\n", buf);
		}
		close(client);
	}
	out.close();
	close(s);
	return 0;
}