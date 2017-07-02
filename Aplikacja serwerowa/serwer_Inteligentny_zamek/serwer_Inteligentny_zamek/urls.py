from django.conf.urls import url
from . import views

urlpatterns = {
    url(r'^api/login/$', views.api_login, name='api_login'),  # logowanie dla admina z jednoczesnym pobraniem listy kluczy awaryjnych
    url(r'^api/register/$', views.api_register, name='api_register'),
    url(r'^api/logout/$', views.api_logout, name='api_logout'),
    url(r'^api/download/all_certifacate/$', views.api_download_all_certificate, name='api_download_all_certificate'),
    url(r'^api/download/all_locks/$', views.api_download_all_locks, name='api_download_all_locks'),
    url(r'^api/deactivation/$', views.api_deactivation, name='api_deactivation'),
    url(r'^api/request_new_certificate/$', views.api_request_new_certificate, name='api_request_new_certificate'),
    #url(r'^api/generate_new_quest_certificate/$', views.api_generate_new_quest_certificate, name='api_generate_new_quest_certificate'),
    url(r'^api/admin/history/$', views.api_admin_history, name='api_admin_history'),
    url(r'^api/admin/generate_new_certificate/$', views.api_admin_generate_new_certificate, name='api_admin_generate_new_certificate'),
    url(r'^api/admin/download/all_certificate/$', views.api_admin_download_all_certificate, name='api_admin_download_all_certificate'),
    url(r'^api/admin/deactivation/$', views.api_admin_deactivation, name='api_admin_deactivation'),
    # url(r'^api/admin/register_decision/$', views.api_admin_register_decision, name='api_admin_register_decision'),  # po zatwierdzeniu uzytkownika generowany jest klucz prywatny i przesyaany do uzytkownika
    url(r'^api/admin/cetificate_waiting/$', views.api_admin_cetificate_waiting, name='api_admin_cerificate_waiting'),
    # url(r'^api/admin/certificate_decision/$', views.api_admin_cetificate_decision, name='api_admin_certificate_decision'),
    url(r'^api/change_password/$', views.api_change_password, name='api_change_password'),
    url(r'^api/RPI/download/cerificate/$', views.api_RPI_download_cetificate, name='api_RPI_download_cetificate'),
    url(r'^api/RPI/access_decision/$', views.api_RPI_access_decision, name='api_RPI_access_decision'),
    url(r'^api/download/all_user/$', views.api_download_all_user, name='api_download_all_user'),
}
