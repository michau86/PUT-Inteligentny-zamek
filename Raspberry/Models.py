class Certificat:
    def __init__(self, name=None, surname=None, isactual=None, ispernament=0, date_from=None, date_to=None, key=None,
                 user=None, access_table=None):
        self.name = name
        self.surname = surname
        self.isactual = isactual
        self.ispernament = ispernament
        self.date_from = date_from
        self.date_to = date_to
        self.key = key
        self.user = user
        self.access_table = access_table


class User:
    def __init__(self, login=None, password=None, public_key=None, name=None, surname=None, isadmin=0):
        self.login = login
        self.password = password
        self.public_key = public_key
        self.name = name
        self.surname = surname
        self.isadmin = isadmin
