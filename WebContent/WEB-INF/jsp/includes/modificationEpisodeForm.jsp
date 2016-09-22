<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
					<tr>
						<td>
							<label for="numeroEpisode">Num&eacute;ro de l'&eacute;pisode<t:required/> :</label>
						</td>
						<td>
							<input type="text" name="numeroEpisode" id="numeroEpisode" value="<c:out value="${ media.numero }" />" />
						</td>
					</tr>
					<tr>
						<td>
				 			<label for="serieEpisode">S&eacute;rie de l'&eacute;pisode<t:required/> :</label>
						</td>
						<td>
							<input type="text" name="serieEpisode" id="serieEpisode" value="<c:out value="${ media.serie }" />" />
						</td>
					</tr>