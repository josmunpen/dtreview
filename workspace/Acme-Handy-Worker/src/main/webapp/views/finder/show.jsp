<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('HANDYWORKER')">

	<h1>
		<spring:message code="finder.show" />
	</h1>
	
	<p>
		<spring:message code="finder.keyWord" />
	</p>
	<jstl:out value="${finder.keyWord}"></jstl:out>
	
	<p>
		<spring:message code="finder.category" />
	</p>
	<jstl:out value="${finder.category}"></jstl:out>
	
	<p>
		<spring:message code="finder.minPrice" />
	</p>
	<jstl:out value="${finder.minPrice}"></jstl:out>
	
	<p>
		<spring:message code="finder.maxPrice" />
	</p>
	<jstl:out value="${finder.maxPrice}"></jstl:out>
	
	<p>
		<spring:message code="finder.startDate" />
	</p>
	<jstl:out value="${finder.startDate}"></jstl:out>
	
	<p>
	<spring:message code="finder.endDate" />
	</p>
	<jstl:out value="${finder.endDate}"></jstl:out>
	
	<p>
	<spring:message code="finder.warranty" />
	</p>
	<jstl:out value="${finder.warranty}"></jstl:out>	
	
	<p>
	<spring:message code="finder.results" />
	</p>
<!-- 	<jstl:forEach var="res" items="${finder.fixUpTasks}">
		<jstl:out value="${res}"/>
		<a href="finder/handyworker/edit.do?finderId=${finder.id}" ><spring:message code="finder.edit" /></a>
	</jstl:forEach>  -->
	
	<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="finder.fixUpTasks" requestURI="${requestURI}" id="row">
	
	<display:column property="ticker" titleKey="fixUpTask.ticker" />
	<display:column property="description" titleKey="fixUpTask.description" />
	<display:column property="maximumPrice" titleKey="fixUpTask.maximumPrice" />
	<display:column property="startDate" titleKey="fixUpTask.startDate" />
	<display:column property="endDate" titleKey="fixUpTask.endDate" />
	
	<display:column>
		<a href="fixUpTask/handyworker/show.do?fixUpTaskId=${fixUpTask.id}"> <spring:message
					code="fixUpTask.show.link" />
		</a>
	</display:column>

</display:table>
	
	<p>
	<a href="finder/handyworker/edit.do?finderId=${finder.id}" ><spring:message code="finder.edit" /></a>
	</p>	
	
	<input type="button" name="back" onclick="javascript: window.location.replace('welcome/index.do')"
		value="<spring:message code="finder.back" />" />
	
</security:authorize>