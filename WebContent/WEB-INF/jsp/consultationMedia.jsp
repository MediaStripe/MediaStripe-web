<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:genericpage>
	<fieldset>
		<legend>
			<c:out value="${ fichier.titre }" />
			:
		</legend>
		<%-- *********************************************** --%>
		<%-- Affichage d'un player adapté au type de fichier --%>
		<%-- *********************************************** --%>
		<c:choose>
			<%-- ************* --%>
			<%-- Fichier vidéo --%>
			<%-- ************* --%>
			<c:when test="${ fichier.isVideo() }">
				<%-- Détection d'un film ou d'un épisode --%>
				<c:choose>
					<c:when test="${ fichier.isFilm() }">
						<c:set scope="page" var="pathExtension" value="film/" />
					</c:when>
					<c:when test="${ fichier.isEpisode() }">
						<c:set scope="page" var="pathExtension" value="episode/" />
					</c:when>
				</c:choose>
				<%-- Affichage du player vidéo --%>
				<video id="mediaPlayed" width="320" height="240" controls>
					<source
						src="./fichier/video/${ pathExtension }<c:out value="${ fichier.cheminfichier }"/>"
						type="video/mp4">
					<source
						src="./files/video/${ pathExtension }<c:out value="${ fichier.cheminfichier }"/>"
						type="video/ogg">
					Votre navigateur ne supporte pas la lecture des vidéos via html5.
				</video>
			</c:when>
			<%-- ************* --%>
			<%-- Fichier audio --%>
			<%-- ************* --%>
			<c:when test="${ fichier.isMusique() }">
				<audio id="mediaPlayed" controls>
					<source src="./fichier/musique/<c:out value="${ fichier.cheminfichier }"/>"
						type="audio/ogg">
					<source src="./fichier/musique/<c:out value="${ fichier.cheminfichier }"/>"
						type="audio/mpeg">
					Votre navigateur ne supporte pas la lecture des musiques via html5.
				</audio>
			</c:when>
			<%-- ************* --%>
			<%-- Fichier image --%>
			<%-- ************* --%>
			<c:when test="${ media.isPhoto() }">
				<img src="./fichier/photo/<c:out value="${ fichier.cheminfichier }"/>" alt="${ fichier.titre }"/>
			</c:when>
		</c:choose>
	</fieldset>
	<script type="text/javascript">
		window.onload = function() {
			setTimeout(function() {
				document.getElementById("mediaPlayed").play();
			}, 1000);
		}
	</script>
</t:genericpage>