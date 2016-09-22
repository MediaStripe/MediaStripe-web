<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
					<tr>
						<td>
							<label for="realisateur">R&eacute;alisateur<t:required/> :</label>
						</td>
						<td>
							<input type="text" name="realisateur" id="realisateur" value="<c:out value="${ media.realisateur }" />" />
						</td>
					</tr>