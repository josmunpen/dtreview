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
<form:form action="customisation/administrator/edit.do" modelAttribute="customisation">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:hidden path="negativeWords"/>
	<form:hidden path="positiveWords"/>

	<form:label path="systemName">
		<spring:message code="customisation.systemName" />:
	</form:label>
	<form:input path="systemName" />
	<form:errors cssClass="error" path="systemName" />
	<br />
	
	<form:label path="bannerURL">
		<spring:message code="customisation.bannerURL" />:
	</form:label>
	<form:input path="bannerURL" />
	<form:errors cssClass="error" path="bannerURL" />
	<br />
	
	<form:label path="welcomeMessage">
		<spring:message code="customisation.welcomeMessage" />:
	</form:label>
	<form:input path="welcomeMessage" />
	<form:errors cssClass="error" path="welcomeMessage" />
	<br />
	
	<form:label path="VATPercentage">
		<spring:message code="customisation.VATPercentage" />:
	</form:label>
	<form:input path="VATPercentage" />
	<form:errors cssClass="error" path="VATPercentage" />
	<br />

	<form:label path="phoneNumberCountryCode">
	<spring:message code="customisation.phoneNumberCountryCode" />:
	</form:label>
	<form:input path="phoneNumberCountryCode" />
	<form:errors cssClass="error" path="phoneNumberCountryCode" />
	<br />
		
	<form:label path="finderDuration">
	<spring:message code="customisation.finderDuration" />:
	</form:label>
	<form:input path="finderDuration" />
	<form:errors cssClass="error" path="finderDuration" />
	<br />
		
	<form:label path="resultsNumber">
	<spring:message code="customisation.resultsNumber" />:
	</form:label>
	<form:input path="resultsNumber" />
	<form:errors cssClass="error" path="resultsNumber" />
	<br />
	
	<spring:message code="configuration.edit.CCardsMakes" />:
	<br/>
	<input id="cCM" name="cCM" value="${CCMakesCommas}"/> 
	<jstl:set var="cCM" value="cCM"/>
	<br />
	
	<spring:message code="configuration.edit.spamWords" />:
	<br/>
	<input id="spamWords" name="spamWords" value="${spamCommas}"/> 
	<jstl:set var="spamWords" value="spamWords"/>
	<br />
	
	
	<br />	
	<br />	
	

	<input type="submit" name="save"
		value="<spring:message code="customisation.save" />" />&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="category.cancel" />"
		onclick="javascript: relativeRedir('customisation/administrator/list.do');" />
	<br />


</form:form>
</security:authorize>