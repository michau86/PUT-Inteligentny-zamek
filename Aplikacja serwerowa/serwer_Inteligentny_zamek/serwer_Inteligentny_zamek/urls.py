

from django.conf.urls import url
from django.contrib import admin
from . import views
urlpatterns = {
    url(r'^api/login/$', views.api_login, name='api_login'),
    url(r'^api/register/$', views.api_register, name='api_register'),
}
