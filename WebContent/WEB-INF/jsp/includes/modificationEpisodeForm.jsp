<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
			<label for="numeroEpisode">Num&eacute;ro de l'&eacute;pisode<t:required/> :</label>
 			<input type="text" name="numeroEpisode" id="numeroEpisode" value="<c:out value="${ media.numero }" />" /><br/>
 			<span class="erreur"></span><br />
 			<label for="serieEpisode">S&eacute;rie de l'&eacute;pisode<t:required/> :</label>
 			<input type="text" name="serieEpisode" id="serieEpisode" value="<c:out value="${ media.serie }" />" /><br/>
 			<span class="erreur"></span><br />