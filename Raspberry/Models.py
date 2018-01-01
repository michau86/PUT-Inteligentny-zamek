class Certificate:
    def __init__(self, isactual=None, ispernament=0, date_from=None, date_to=None, lock_key=None, monday=[], tuesday=[],
                 wednesday=[], thursday=[], friday=[], saturday=[], sunday=[]):
        self.isactual = isactual
        self.ispernament = ispernament
        self.date_from = date_from
        self.date_to = date_to
        self.lock_key = lock_key
        self.access_table = []
        self.access_table.append(monday)
        self.access_table.append(tuesday)
        self.access_table.append(wednesday)
        self.access_table.append(thursday)
        self.access_table.append(friday)
        self.access_table.append(saturday)
        self.access_table.append(sunday)

    def __getitem__(self, item):
        return self.access_table


class CertificatePKI:
    def __init__(self, publickey=None, signature_algorithm_identifier="RSA", validitiy_period=None, version=None,
                 issuer_name=None, hash_algorithm="SHA-256", serial_number=None, user_name=None):
        self.publickey = publickey
        self.signature_algorithm_identifier = signature_algorithm_identifier
        self.validitiy_period = validitiy_period
        self.version = version
        self.issuer_name = issuer_name
        self.hash_algorithm = hash_algorithm
        self.serial_number = serial_number
        self.user_name = user_name
