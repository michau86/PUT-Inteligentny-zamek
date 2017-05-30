class Certificate:
    def __init__(self, isactual=None, ispernament=0, date_from=None, date_to=None, lock_key=None, monday = [], tuesday = [], wednesday = [], thursday = [], friday= [], saturday = [], sunday = []):
        self.isactual = isactual
        self.ispernament = ispernament
        self.date_from = date_from
        self.date_to = date_to
        self.lock_key = lock_key
        self.monday = monday
        self.tuesday = tuesday
        self.wednesday = wednesday
        self.thursday = thursday
        self.friday = friday
        self.saturday = saturday
        self.sunday = sunday
