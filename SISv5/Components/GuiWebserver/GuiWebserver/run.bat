npm run dev
python manage.py makemigrations workforce
python manage.py migrate
python manage.py runserver

./env/Scripts/activate.ps1
deactivate

net start MongoDB
net stop MongoDB

docker-compose up
docker-compose down
docker exec -it [container-id] bash
docker image ls
docker system prune

docker exec -it b30a1649f4b8 bash