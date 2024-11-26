# BDDMEEFProjet

Cette branche est présente pour essayer de régler les soucis de connexion entre le programme et une base de donnée Oracle. pour celà il y a la présence de Maven qui permet d'obtenir une dépendance nécessaire pour la connexion avec Oracle. Pour pouvoir exécuter le code il faut dans "projetbd" écrire la commande suivante :

mvn exec:java -Dexec.mainClass="com.AL2000"

Pour pouvoir modifier le compte oracle connecté par l'utilisateur il faut aller dans BaseDAO.java et modifier le String USER et PASSWORD
