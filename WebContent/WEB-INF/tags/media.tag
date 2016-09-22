<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="media" required="true"
	type="com.imie.entities.Media" description="Média à afficher"%>
<%@ attribute name="showPublieur" required="false"
	description="Booléen indiquand s'il faut afficher le nom du publieur"%>
<p class="ligneMedia">
	<a href="./ConsultationMedia?media=<c:out value="${ media.id }"/>"class="ligneMediaLien">
		<%-- Définition de l'icone du média --%> 
		<c:choose>
			<c:when test="${ media.isPhoto() }">
				<c:set scope="page" var="icone" value="icone_photo.png" />
			</c:when>
			<c:when test="${ media.isMusique() }">
				<c:set scope="page" var="icone" value="icone_musique.png" />
			</c:when>
			<c:when test="${ media.isVideo() }">
				<c:set scope="page" var="icone" value="icone_video.png" />
			</c:when>
		</c:choose>
		<img src="./inc/images/<c:out value="${ icone }" />" class="mediaIcon" />
		<c:out value="${media.titre }" /><br />
		<c:if test="${ showPublieur }">
			<a href="./ConsultationProfil?utilisateur=<c:out value="${ media.publieur.id }"/>" class="publieur">
				<c:out value="${ media.publieur.nom } ${ media.publieur.prenom }" />
			</a>
		</c:if>
	</a>
</p>