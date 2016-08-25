#!/bin/bash

#*************************************************************************
# Script de création de l'arborescence des dossiers du projet 
# MediaStripe 
#*************************************************************************

#****************************
# Fonctions
#****************************

#************************************************
# \brief Contrôle le code retour de la dernière
#		commande exécutée.
#
# \param $1 Le code retour de la dernière
#		commande exécutée.
# \param $2 Le message à afficher si la commande
#		s'est bien exécutée.
# \param $3 Le message à afficher si la commande
#		ne s'est pas bien exécutée.
#************************************************
function checkCommand
{
	# Contrôle des paramètres de la fonction
	if [ $# -ne 3 ]
	then
		echo "Erreur : La fonction $0 prend 3 paramètres. (Nombre de paramètres : $#)"
		exit 1
	fi
	
	if [ $1 -eq 0 ]
	then
		echo $1
	else
		echo $2
		exit 1
	fi
}

#****************************
# Programme principal
#****************************
echo 'Création des répertoires...'
sudo mkdir -p /opt/MediaStripe/files/{videos/{films,episodes},musiques,photos,tmp}
checkCommand $? 'Répertoires créés' 'Une erreur est survenue lors de la création des répertoires'

echo 'Modification des droits des répertoires créés...'
user=`whoami`
sudo chown $user:$user -R /opt/MediaStripe/files/
checkCommand $? 'Droits modifiés' 'Une erreur est survenue lors de la modification des droits des répertoires'

exit 0
