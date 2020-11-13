from django.shortcuts import render,redirect
from django.contrib.auth.models import User,auth

# Create your views here.
def index(request):
    return render(request, 'index.html')

def student(request):
    if request.method == 'POST':
        first_name = request.POST.get('First_Name')
        last_name = request.POST.get('Last_Name')
        email = request.POST.get('Email_Address')
        password = request.POST.get('Password')
        password1 = request.POST.get('Confirm_Password')
        username = request.POST.get('Username')

        if password==password1:
            if User.objects.filter(username=username).exists():
                print('Username Taken')
                return render(request, 'student.html')
            elif User.objects.filter(email=email).exists():
                print('Email Taken')
                return render(request, 'student.html')
            else:
                user = User.objects.create_user(username=username, password=password, email=email, first_name=first_name, last_name=last_name)
                user.save();
                print('User Created')
                return redirect(request, '/')
        else:
            print('Password Not matching')

    else:
        return render(request, 'student.html')