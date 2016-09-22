<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
				<div id="rechercher">
					<form action="./Rechercher" method="POST">
						<div id="categories">
							<p style="width: 140px;">
								<input type="checkbox" id="toutcocher" checked /> 
								<label for="toutcocher">Tout décocher</label>
							</p>
							<p>
								<input type="checkbox" name="photo" id="photo" value="photo" class="filtreCategorie" checked /> 
								<label for="photo">Photos</label>
							</p>
							<p>
								<input type="checkbox" name="musique" id="musique" value="musique" class="filtreCategorie" checked /> 
								<label for="musique">Musiques</label>
							</p>
							<p>
								<input type="checkbox" name="video" id="video" value="video" class="filtreCategorie" checked /> 
								<label for="video">Vid&eacute;os</label>
							</p>
							<p>
								<input type="checkbox" name="utilisateur" id="utilisateur" value="utilisateur" class="filtreCategorie" checked /> 
								<label for="utilisateur">Utilisateurs</label>
							</p>
						</div>
						<input type="text" name="criteres" id="criteres" required />
						<input type="submit" value="Rechercher" />
					</form>
				</div>