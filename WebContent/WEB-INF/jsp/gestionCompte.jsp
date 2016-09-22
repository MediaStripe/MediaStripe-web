<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
<t:controleUtilisateurConnecte />
		<fieldset>
			<legend>Mon espace personnel :</legend>
			Sur cet espace, je peux :
			<ul>
				<li><a href="./ListerMedias">Lister mes m&eacute;dias</a></li>
				<li><a href="./ModificationUtilisateur">Modifier mes informations personnelles</a></li>
				<li><a href="./ModificationMotdepasse">Modifier mon mot de passe</a></li>
				<li><a href="./ListerContacts">Lister mes contacts</a></li>
			</ul>
		</fieldset>
</t:genericpage>