# Create your models here.
from django.db import models


class Student(models.Model):

    first_name = models.CharField(max_length=100)
    last_name = models.CharField(max_length=100)
    username = models.CharField(max_length=100)
    email = models.EmailField()
    phone_number = models.IntegerField()

    # img = models.ImageField(upload_to='Profile')
