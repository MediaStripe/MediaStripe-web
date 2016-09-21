<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
<t:controleUtilisateurConnecte />
		<fieldset>
			<legend>Mon espace personnel :</legend>
			Sur cet espace, je peux :
			<ul>
				<li><a href="./ModificationUtilisateur">Modifier mes informations personnelles</a></li>
				<li><a href="./ModificationMotdepasse">Modifier mon mot de passe</a></li>
				<li><a href="./ListerContacts">Mes contacts</a></li>
			</ul>
		</fieldset>
</t:genericpage>