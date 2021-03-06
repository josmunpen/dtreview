<%@page import="domain.Warranty"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('ADMIN')">

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="warranties" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<!-- Attributes -->
	
	<display:column property="title" titleKey="warranty.title" sortable="true" />

	<spring:message code="warranty.terms" var="termsHeader" />
	<display:column property="terms" title="${termsHeader}" sortable="false" />

	<spring:message code="warranty.applicableLaws" var="lawsHeader" />
	<display:column property="applicableLaws" title="${lawsHeader}" sortable="false" />

	<spring:message code="warranty.finalMode" var="finalModeHeader" />
	<display:column property="finalMode" title="${finalModeHeader}"	sortable="false" />


		<display:column>	
		<jstl:if test="${row.finalMode == false}">
				<a href="warranty/administrator/edit.do?warrantyId=${row.id}">
				<spring:message code="warranty.edit.link" />
				</a>
		</jstl:if>
		</display:column>
		
</display:table>

<div>
	<a href="warranty/administrator/create.do"> <spring:message
			code="warranty.list.create" />
	</a>
</div>
</security:authorize>