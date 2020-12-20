from django.contrib import messages
from django.contrib.auth.models import User, auth
from django.shortcuts import render, redirect

is_staff = 'option2'


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
                user = User.objects.create_user(username=username, first_name=first_name, last_name=last_name,
                                                email=email, password=password2, is_staff=flag)
                user.set_password(request.POST['password2'])
                user.save()
                user.save()
                messages.info(request, 'User Created')
                print(is_staff)

                return redirect('login')
            else:
                messages.info(request, 'Phone Number must be of 10 digits only')

        else:
            messages.info(request, 'Confirm Password field not matching with password files')

    return render(request, 'register.html')


def login(request):
    if request.method == 'POST':
        username = request.POST.get('username')
        password = request.POST.get('password')
        user = auth.authenticate(request, username=username, password=password)
        if user is not None:
            auth.login(request, user)
            if user.is_staff == True:
                return render(request, 'teacher.html')
            elif user.is_staff == False:
                return redirect('http://127.0.0.1:8001/')
            else:
                print('Not working')
                return render(request, 'index.html')
        else:
            print('Invalid Credentials')

    else:
        print('NonGetGadbad')
    #
    # username = request.POST.get('username')
    # password = request.POST.get('password')

    return render(request, 'login.html')


def logout(request):
    auth.logout(request)
    return render(request, 'index.html')
