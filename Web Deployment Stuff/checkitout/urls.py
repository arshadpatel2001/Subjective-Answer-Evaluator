from django.contrib import admin
from django.urls import path, include

admin.site.site_headers = "Teacher Login"
admin.site.site_title = "Answer verifier"
admin.site.index_title = "Welcome to Answer Verifier"

urlpatterns = [
    path('admin/', admin.site.urls),
    path('', include('rectifica.urls')),




]
