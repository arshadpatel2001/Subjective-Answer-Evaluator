from django.shortcuts import render, redirect
from django.contrib.auth.models import User
from django.contrib import messages

# Create your views here.
def index(request):
    return render(request, 'index.html', {'teacher': 5})


def register(request):
    if request.method == 'POST':
        first_name = request.POST['first_name']
        last_name = request.POST['last_name']
        username = request.POST['username']
        email = request.POST['email']
        password1 = request.POST['password1']
        password2 = request.POST['password2']
        phone_number = request.POST['phone_number']
        is_staff = request.POST['is_staff']
        if is_staff == 'option1':
            flag = True
        else:
            flag = False
        if password1 == password2:
            if len(phone_number) == 10:
                user = User.objects.create_user(username=username, first_name=first_name, last_name=last_name, email=email, password=password2, is_staff = flag)
                user.save()
                messages.info(request, 'User Created')
                return redirect('/')
            else:
                messages.info(request, 'Phone Number must be of 10 digits only')

        else:
            messages.info(request, 'Confirm Password field not matching with password files')

    return render(request, 'register.html')


def login(request):
    if request.method == 'POST':
        pass
    else:
        return render(request, 'register.html')
    return redirect(request, '/')
