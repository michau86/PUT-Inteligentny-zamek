#pragma once
#include <ctime>
#include <string>
#include <vector>

class Certificate
{
private:
	bool isactual;
	bool ispernament;
	std::tm date_from;
	std::tm date_to;
	std::string lock_key;
	std::vector<std::tm> access_table[7];
public:
	Certificate(bool isactual, bool ispernament, std::string lock_key, std::tm date_from, std::tm date_to, std::vector<std::tm> monday,
		std::vector<std::tm> tuesday, std::vector<std::tm> wednesday, std::vector<std::tm> thursday, std::vector<std::tm> friday,
		std::vector<std::tm> saturday, std::vector<std::tm> sunday) : isactual(isactual), ispernament(ispernament), lock_key(lock_key), date_from(date_from), date_to(date_to)
	{
		access_table[0] = monday;
		access_table[1] = tuesday;
		access_table[2] = wednesday;
		access_table[3] = thursday;
		access_table[4] = friday;
		access_table[5] = saturday;
		access_table[6] = sunday;
	}
	bool Get_isactual() { return isactual; }
	bool Get_ispernament() { return ispernament; }
	std::tm Get_date_from() { return date_from; }
	std::tm Get_date_to() { return date_to; }
	std::string Get_lock_key() { return lock_key; }
	std::vector<std::tm>* Get_access_table() { return access_table; }
};