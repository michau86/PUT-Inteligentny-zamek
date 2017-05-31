class Certificate:
    def __init__(self, isactual=None, ispernament=0, date_from=None, date_to=None, lock_key=None, monday = [], tuesday = [], wednesday = [], thursday = [], friday= [], saturday = [], sunday = []):
        self.isactual = isactual
        self.ispernament = ispernament
        self.date_from = date_from
        self.date_to = date_to
        self.lock_key = lock_key
        self.access_table[0] = monday
        self.access_table[1] = tuesday
        self.access_table[2] = wednesday
        self.access_table[3] = thursday
        self.access_table[4] = friday
        self.access_table[5] = saturday
        self.access_table[6] = sunday
