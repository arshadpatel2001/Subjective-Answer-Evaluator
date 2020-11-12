from django.urls import path
from . import views

urlpatterns = [
    path('', views.index, name='Answer Checker'),
    path('about', views.about, name='About'),

    path('register',views.register,name='Sign Up')

]
