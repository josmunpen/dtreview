<%@page import="domain.Category"%>
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
	name="customisations" requestURI="${requestURI}" id="row">


 	<display:column property="id" titleKey="customisation.id" />

	<display:column>	
			<a href="customisation/administrator/edit.do?customisationId=${row.id}">
			<spring:message code="customisation.edit.link" />
			</a>

	</display:column>
				
</display:table>
</security:authorize>