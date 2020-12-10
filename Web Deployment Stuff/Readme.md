1. Clone project and open webstuff in pycharm
2. Install django using pip in pycharm terminal or download django plugin
3. install postgres db and it's gui pgadmin4. ps. don't install latest version of pgadmin4
4. setup password for pgadmin4 and postgres
5. Create database in pgadmin4 , to create db right click on databases and create db with name "rectifica"
6. You won't find any tables under rectifica->schemas->tables option, for that you need to connect db and application
7. Go to settings.py in project and chnage user and password in databases dict to your configured value
8. Open pycharm terminal and give command "pip install psycopg2-binary"
9. python manage.py makemigrations
10. python manage.py migrate
11. Now you can find tables created under rectifica->schemas->tables option
Enjoy :)
