# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
#
# Also note: You'll have to insert the output of 'django-admin sqlcustom [app_label]'
# into your database.
from __future__ import unicode_literals

from django.db import models


class AccessToLocks(models.Model):
    id = models.AutoField(db_column='ID')  # Field name made lowercase.
    id_key = models.IntegerField(db_column='ID_KEY')  # Field name made lowercase.
    date = models.DateTimeField(db_column='DATE')  # Field name made lowercase.
    access = models.IntegerField(db_column='ACCESS')  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'access_to_locks'
        unique_together = (('ID', 'ID_KEY'),)


class AuthGroup(models.Model):
    name = models.CharField(unique=True, max_length=80)

    class Meta:
        managed = False
        db_table = 'auth_group'


class AuthGroupPermissions(models.Model):
    group_id = models.IntegerField()
    permission_id = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'auth_group_permissions'
        unique_together = (('group_id', 'permission_id'),)


class AuthPermission(models.Model):
    name = models.CharField(max_length=255)
    content_type = models.ForeignKey('DjangoContentType')
    codename = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'auth_permission'
        unique_together = (('content_type_id', 'codename'),)


class AuthUser(models.Model):
    password = models.CharField(max_length=128)
    last_login = models.DateTimeField(blank=True, null=True)
    is_superuser = models.IntegerField()
    username = models.CharField(unique=True, max_length=150)
    first_name = models.CharField(max_length=30)
    last_name = models.CharField(max_length=30)
    email = models.CharField(max_length=254)
    is_staff = models.IntegerField()
    is_active = models.IntegerField()
    date_joined = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'auth_user'


class AuthUserGroups(models.Model):
    id = models.IntegerField(primary_key=True)
    user_id = models.IntegerField()
    group_id = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'auth_user_groups'
        unique_together = (('user_id', 'group_id'),)


class AuthUserUserPermissions(models.Model):
    id = models.IntegerField(primary_key=True)
    user_id = models.IntegerField()
    permission_id = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'auth_user_user_permissions'
        unique_together = (('user_id', 'permission_id'),)


class DjangoAdminLog(models.Model):
    id = models.IntegerField(primary_key=True)
    action_time = models.DateTimeField()
    object_id = models.TextField(blank=True, null=True)
    object_repr = models.CharField(max_length=200)
    action_flag = models.SmallIntegerField()
    change_message = models.TextField()
    content_type_id = models.IntegerField(blank=True, null=True)
    user_id = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'django_admin_log'


class DjangoContentType(models.Model):
    app_label = models.CharField(max_length=100)
    model = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'django_content_type'
        unique_together = (('app_label', 'model'),)


class DjangoMigrations(models.Model):
    id = models.IntegerField(primary_key=True)
    app = models.CharField(max_length=255)
    name = models.CharField(max_length=255)
    applied = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_migrations'


class DjangoSession(models.Model):
    session_key = models.CharField(primary_key=True, max_length=40)
    session_data = models.TextField()
    expire_date = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_session'


class DjangoSite(models.Model):
    domain = models.CharField(max_length=100)
    name = models.CharField(max_length=50)

    class Meta:
        managed = False
        db_table = 'django_site'


class Locks(models.Model):
    id_lock = models.AutoField(db_column='ID_LOCK', primary_key=True)  # Field name made lowercase.
    name = models.CharField(db_column='NAME', unique=True, max_length=30)  # Field name made lowercase.
    localization = models.TextField(db_column='LOCALIZATION', blank=True, null=True)  # Field name made lowercase.
    mac_address = models.CharField(db_column='MAC_ADDRESS', max_length=17)  # Field name made lowercase.
    admin_key = models.TextField(db_column='ADMIN_KEY')  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'locks'


class LocksKeys(models.Model):
    id_key = models.AutoField(db_column='ID_KEY', primary_key=True)  # Field name made lowercase.
    id_lock = models.IntegerField(db_column='ID_LOCK')  # Field name made lowercase.
    id_user = models.IntegerField(db_column='ID_USER')  # Field name made lowercase.
    lock_key = models.CharField(db_column='LOCK_KEY', unique=True, max_length=255)  # Field name made lowercase.
    from_date = models.DateTimeField(db_column='FROM_DATE')  # Field name made lowercase.
    to_date = models.DateTimeField(db_column='TO_DATE')  # Field name made lowercase.
    isactual = models.DateTimeField(db_column='ISACTUAL', blank=True, null=True)  # Field name made lowercase.
    monday = models.CharField(db_column='MONDAY', max_length=10, blank=True, null=True)  # Field name made lowercase.
    tuesday = models.CharField(db_column='TUESDAY', max_length=10, blank=True, null=True)  # Field name made lowercase.
    wednesday = models.CharField(db_column='WEDNESDAY', max_length=10, blank=True, null=True)  # Field name made lowercase.
    thursday = models.CharField(db_column='THURSDAY', max_length=10, blank=True, null=True)  # Field name made lowercase.
    friday = models.CharField(db_column='FRIDAY', max_length=10, blank=True, null=True)  # Field name made lowercase.
    saturday = models.CharField(db_column='SATURDAY', max_length=10, blank=True, null=True)  # Field name made lowercase.
    sunday = models.CharField(db_column='SUNDAY', max_length=10, blank=True, null=True)  # Field name made lowercase.
    is_pernament = models.IntegerField(db_column='IS_PERNAMENT')  # Field name made lowercase.
    name = models.CharField(db_column='NAME', max_length=30)  # Field name made lowercase.
    surname = models.CharField(db_column='SURNAME', max_length=50)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'locks_keys'


class Users(models.Model):
    id_user = models.AutoField(db_column='ID_USER', primary_key=True)  # Field name made lowercase.
    login = models.CharField(db_column='LOGIN', unique=True, max_length=50)  # Field name made lowercase.
    password = models.CharField(db_column='PASSWORD', max_length=64)  # Field name made lowercase.
    name = models.CharField(db_column='NAME', max_length=30)  # Field name made lowercase.
    surname = models.CharField(db_column='SURNAME', max_length=50)  # Field name made lowercase.
    token = models.CharField(db_column='TOKEN', max_length=300, blank=True, null=True)  # Field name made lowercase.
    isactivated = models.IntegerField(db_column='ISACTIVATED')  # Field name made lowercase.
    is_admin = models.IntegerField(db_column='IS_ADMIN')  # Field name made lowercase.
    public_key = models.CharField(db_column='PUBLIC_KEY', unique=True, max_length=300, blank=True, null=True)  # Field name made lowercase.
    serial_number = models.CharField(db_column='Serial_number', max_length=300, blank=True, null=True)  # Field name made lowercase.
    validitiy_period = models.DateTimeField(db_column='Validitiy_period', blank=True, null=True)  # Field name made lowercase.
    version = models.IntegerField(db_column='Version')  # Field name made lowercase.
    signature_algorithm_identifier = models.CharField(db_column='Signature_Algorithm_Identifier', max_length=30)  # Field name made lowercase.
    hash_algorithm = models.CharField(db_column='Hash_Algorithm', max_length=30)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'users'


class WaitLocksKeys(models.Model):
    id_key = models.AutoField(db_column='ID_KEY', primary_key=True)  # Field name made lowercase.
    id_lock = models.IntegerField(db_column='ID_LOCK')  # Field name made lowercase.
    id_user = models.IntegerField(db_column='ID_USER')  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'wait_locks_keys'
