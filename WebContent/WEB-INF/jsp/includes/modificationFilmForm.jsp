<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
			<label for="realisateur">R&eacute;alisateur<t:required/> :</label>
			<input type="text" name="realisateur" id="realisateur" value="<c:out value="${ media.realisateur }" />" /><br/> 
			<span class="erreur"></span><br />