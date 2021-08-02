from django.urls import path
from django.conf.urls import url

from . import views

urlpatterns = [
    path('', views.home, name='home'),
    path('index', views.index, name = 'index'),
    path('<int:question_id>/', views.question, name='question'),
    path('<int:question_id>/essay<int:essay_id>/', views.essay, name='essay'),
    path('login', views.login, name='login'),
    path('register', views.register, name='resister'),
    path('logout', views.logout, name='logout'),
    path('teacher', views.teacher, name='teacher'),
    path('student', views.student, name='student'),
    path('sub', views.sub, name='sub'),
    path('english', views.english, name='English'),
    path('subject', views.subject, name='subject'),
    path('student_instructions', views.student_instructions, name='student_instructions'),
    path('teacher_instructions', views.teacher_instructions, name='teacher_instructions'),



]