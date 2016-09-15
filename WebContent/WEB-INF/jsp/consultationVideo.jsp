<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:genericpage>
	<fieldset>
		<legend>
			<c:out value="${ media.titre }" /> :
		</legend>
		<video width="320" height="240" controls>
			<source src="./files/video/<c:out value="${ media.cheminfichier }"/>" type="video/mp4">
<%-- 			<source src="./files/video/<c:out value="${ media.cheminfichier }"/>" type="video/ogg"> --%>
			Your browser does not support the video tag.
		</video>
	</fieldset>
</t:genericpage>