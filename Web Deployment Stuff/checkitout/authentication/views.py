from django.shortcuts import render, redirect
from django.http import HttpResponse
from django.contrib.auth import authenticate,login
from django.contrib.auth.forms import UserCreationForm
def index(request):
    return render(request, 'index.html')

def about(request):
    return render(request, 'about.html')

def studentLogin(request):
    return render(request, 'register.html')

def features(request):
    return render(request, 'features.html')

def register(request):
    if request.method == 'POST':
        form = UserCreationForm(request.POST)
        if form.is_valid():
            form.save()
        return redirect(request, '/index')
    else:
        form = UserCreationForm()

    return render(request,'register.html',{"form":form})

def home(request):
    return render(request,'home.html')