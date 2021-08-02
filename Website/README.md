# Rectifica - A Subjective Answer Evaluator
To connect to postgresql
1) Install postgresql (do not install pgadmin from postgres installer).
2) Install pgadmin.
3) Create a database named 'rectifica' in postgres using pgadmin.
4) Configure your username and password in website/mysite/settings.py in database section.
5) pip install psycopg2
6) python manage.py migrate --run-syncdb
7) python manage.py loaddata <path to datadump.json here without angular brackets> eg: python manage.py loaddata datadump.json
8) Refresh the database, tables should be created now.
