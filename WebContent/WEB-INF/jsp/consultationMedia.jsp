<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:genericpage>
	<fieldset>
		<legend>
			<c:out value="${ fichier.titre }" />
			:
		</legend>
		<c:choose>
			<c:when test="${ fichier.isVideo() }">
				<video id="mediaPlayed" width="320" height="240" controls>
					<source
						src="./fichier/video/<c:out value="${ fichier.cheminfichier }"/>"
						type="video/mp4">
					<source
						src="./files/video/<c:out value="${ fichier.cheminfichier }"/>"
						type="video/ogg">
					Votre navigateur ne supporte pas la lecture des vidéos via html5.
				</video>
			</c:when>
			<c:when test="${ fichier.isMusique() }">
				<audio id="mediaPlayed" controls>
					<source
						src="./fichier/musique/<c:out value="${ fichier.cheminfichier }"/>"
						type="audio/ogg">
					<source
						src="./fichier/musique/<c:out value="${ fichier.cheminfichier }"/>"
						type="audio/mpeg">
					Votre navigateur ne supporte pas la lecture des musiques via html5.
				</audio>
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