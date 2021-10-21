import numpy as np
from django.shortcuts import get_object_or_404, render, redirect
from django.http import HttpResponseRedirect, HttpResponse
from django.urls import reverse
from django.contrib import messages
from django.contrib.auth.models import User, auth
from django.shortcuts import render, redirect

from .models import Question, Essay
from .forms import AnswerForm

from .utils.model import *
from .utils.helpers import *

import os

current_path = os.path.abspath(os.path.dirname(__file__))


# Create your views here.
def home(request):
    print("Inside grader home")
    return render(request, 'index.html', {'teacher': 5})


def index(request):
    questions_list = Question.objects.order_by('set')
    context = {
        'questions_list': questions_list,
    }
    return render(request, 'grader/index.html', context)


def essay(request, question_id, essay_id):
    essay = get_object_or_404(Essay, pk=essay_id)
    context = {
        "essay": essay,
    }
    return render(request, 'grader/essay.html', context)


def question(request, question_id):
    question = get_object_or_404(Question, pk=question_id)
    if request.method == 'POST':
        # create a form instance and populate it with data from the request:
        form = AnswerForm(request.POST)
        if form.is_valid():

            content = form.cleaned_data.get('answer')

            if len(content) > 20:
                num_features = 300
                model = word2vec.KeyedVectors.load_word2vec_format(
                    os.path.join(current_path, "deep_learning_files/word2vec.bin"), binary=True)
                clean_test_essays = []
                clean_test_essays.append(essay_to_wordlist(content, remove_stopwords=True))
                testDataVecs = getAvgFeatureVecs(clean_test_essays, model, num_features)
                testDataVecs = np.array(testDataVecs)
                testDataVecs = np.reshape(testDataVecs, (testDataVecs.shape[0], 1, testDataVecs.shape[1]))

                lstm_model = get_model()
                lstm_model.load_weights(os.path.join(current_path, "deep_learning_files/final_lstm.h5"))
                preds = lstm_model.predict(testDataVecs)

                if math.isnan(preds):
                    preds = 0
                else:
                    preds = np.around(preds)

                if preds < 0:
                    preds = 0
                if preds > question.max_score:
                    preds = question.max_score
            else:
                preds = 0

            K.clear_session()
            essay = Essay.objects.create(
                content=content,
                question=question,
                score=preds
            )
        return redirect('essay', question_id=question.set, essay_id=essay.id)
    else:
        form = AnswerForm()

    context = {
        "question": question,
        "form": form,
    }
    return render(request, 'grader/question.html', context)


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
            print("Password checked")
            if len(phone_number) == 10:
                print("Phone number chcked")
                user = User.objects.create_user(username=username, first_name=first_name, last_name=last_name,
                                                email=email, password=password2, is_staff=flag)
                user.set_password(request.POST['password2'])
                user.save()
                user.save()
                print(is_staff)

                return redirect('login')
            else:
                messages.info(request, 'Phone Number must be of 10 digits only')


        else:
            messages.error(request, 'Confirm Password field not matching with password files')
        print("Not entered in login")

    return render(request, 'register.html')


def login(request):
    if request.method == 'POST':
        username = request.POST.get('username')
        password = request.POST.get('password')
        user = auth.authenticate(request, username=username, password=password)
        if user is not None:
            auth.login(request, user)
            if user.is_staff:
                return render(request, 'index.html')
            elif not user.is_staff:
                return render(request, 'index.html')
            else:
                print('Not working')
                return render(request, 'index.html')
        else:
            print('Invalid Credentials')

    else:
        print('Not entered in login')
    #
    # username = request.POST.get('username')
    # password = request.POST.get('password')

    return render(request, 'login.html')


def logout(request):
    auth.logout(request)
    return render(request, 'index.html')


def teacher(request):
    if request.user.is_authenticated:
        if request.user.is_staff:
            return render(request, 'teacher.html')
        else:
            return render(request, 'login.html')
    else:
        return render(request, 'login.html')


def student(request):
    if request.user.is_authenticated:
        if request.user.is_staff:  # if teacher then again login
            return render(request, 'login.html')
        else:
            return redirect('index')
    else:
        return render(request, 'login.html')


def student_instructions(request):
    return render(request, 'Student_Instructions.html')


def teacher_instructions(request):
    return render(request, 'Teacher_Instructions.html')


def sub(request):
    if request.user.is_authenticated:
        if request.user.is_staff:  # if teacher then again login
            return render(request, 'login.html')
        else:
            return render(request, "sub.html")
    else:
        return render(request, 'login.html')


def english(request):
    if request.user.is_authenticated:
        if request.user.is_staff:
            return render(request, 'login.html')
        else:
            return redirect('index')
    else:
        return render(request, 'login.html')


def subject(request):
    if request.user.is_authenticated:
        if request.user.is_staff:
            return render(request, 'login.html')
        else:
            return render(request, 'subjects.html')
    else:
        return render(request, 'login.html')



#---------------------------------------------
import os
import time
import json

from django.http.response import JsonResponse
from django.contrib.auth import get_user_model
from django.contrib.auth.decorators import login_required

from django.shortcuts import render


from agora_key.RtcTokenBuilder import RtcTokenBuilder,Role_Attendee
from pusher import Pusher


# Instantiate a Pusher Client
# pusher_client = Pusher(app_id=os.environ.get('PUSHER_APP_ID'),
#                        key=os.environ.get('PUSHER_KEY'),
#                        secret=os.environ.get('PUSHER_SECRET'),
#                        ssl=True,
#                        cluster=os.environ.get('PUSHER_CLUSTER')
#                        )

pusher_client = Pusher(app_id='1279596',
                       key='3ec3b4018b4ba217a984',
                       secret='188be52ee3c41821252e',
                       ssl=True,
                       cluster='ap2'
                       )



def agora_index(request):
    if request.user.is_authenticated:

        User = get_user_model()
        all_users = User.objects.exclude(id=request.user.id).only('id', 'username')
        return render(request, 'agora/index.html', {'allUsers': all_users})
    else:
        return redirect('login')



def pusher_auth(request):
    payload = pusher_client.authenticate(
        channel=request.POST['channel_name'],
        socket_id=request.POST['socket_id'],
        custom_data={
            'user_id': request.user.id,
            'user_info': {
                'id': request.user.id,
                'name': request.user.username
            }
        })
    return JsonResponse(payload)


def generate_agora_token(request):
    # appID = os.environ.get('AGORA_APP_ID')
    # appCertificate = os.environ.get('AGORA_APP_CERTIFICATE')
    appID ='9b85d76264a44259966263071babed65'
    appCertificate = 'faa65948b6f94567b1b3380bf42ffae0'
    channelName = json.loads(request.body.decode(
        'utf-8'))['channelName']
    userAccount = request.user.username
    expireTimeInSeconds = 3600
    currentTimestamp = int(time.time())
    privilegeExpiredTs = currentTimestamp + expireTimeInSeconds

    token = RtcTokenBuilder.buildTokenWithAccount(
        appID, appCertificate, channelName, userAccount, Role_Attendee, privilegeExpiredTs)

    return JsonResponse({'token': token, 'appID': appID})


def call_user(request):
    body = json.loads(request.body.decode('utf-8'))

    user_to_call = body['user_to_call']
    channel_name = body['channel_name']
    caller = request.user.id

    pusher_client.trigger(
        'presence-online-channel',
        'make-agora-call',
        {
            'userToCall': user_to_call,
            'channelName': channel_name,
            'from': caller
        }
    )
    return JsonResponse({'message': 'call has been placed'})
