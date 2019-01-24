<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="customer/register.do" modelAttribute="customer">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="socialProfiles" />
	<form:hidden path="boxes"  />
	<form:hidden path="userAccount.authorities" />
	<form:hidden path="ban" />	
	
	<form:hidden path="fixUpTasks" />
	<form:hidden path="score" />
	
	
	<fieldset>
	<legend align="left"><spring:message code="customer.edit.contact" /></legend>
	
		<form:label path="name">
			<spring:message code="customer.edit.label.name" />:
		</form:label>
		<form:input path="name"/>
		<form:errors cssClass="error" path="name" />		
	
		<br/>
		<br/>
	
		<form:label path="middleName">
			<spring:message code="customer.edit.label.middleName" />:
		</form:label>
		<form:input path="middleName"/>
		<form:errors cssClass="error" path="middleName" />		
		
		<br/>
		<br/>
		
		<form:label path="surname">
			<spring:message code="customer.edit.label.surname" />:
		</form:label>
		<form:input path="surname"/>
		<form:errors cssClass="error" path="surname" />		
		
		<br/>
		<br/>
		
		<form:label path="address">
			<spring:message code="customer.edit.label.address" />:
		</form:label>
		<form:input path="address"/>
		<form:errors cssClass="error" path="address" />		
		
		<br/>
		<br/>
	
		<form:label path="email">
			<spring:message code="customer.edit.label.email" />:
		</form:label>
		<form:input path="email" pattern="[A-z0-9]+@[A-z0-9.]+|[A-z0-9 ]+ <[A-z0-9]+@[A-z0-9.]+>"/>
		<form:errors cssClass="error" path="email" />	
		<br/>
		<br/>
		
		<form:label path="phoneNumber">
			<spring:message code="customer.edit.label.phoneNumber" />:
		</form:label>
		<form:input path="phoneNumber" onchange="check(this)" pattern="^(\d|\(|\)| |\+)+$"/>
		<form:errors cssClass="error" path="phoneNumber" />
		
		<script language='javascript' type='text/javascript'>
		
			var re = /^\+\d{1,3} \(\d{1,3}\) \d{4,}$/;
			var re2 = /^\+\d{1,3} \d{4,}$/;
			var re3 = /^\d{4,}$/;
		
		    function check(input) {
		    	var OK = re.exec(input.value);
		    	var OK2 = re2.exec(input.value);
		    	var OK3 = re3.exec(input.value);
		        if (!(OK || OK2 || OK3)) {
		            alert("<spring:message code="customer.confirm" />" );
		        }
		    }
		</script>		
		<br/>
		<br/>
	
		<form:label path="photoURL">
			<spring:message code="customer.edit.label.photoURL" />:
		</form:label>
		<form:input path="photoURL"/>
		<form:errors cssClass="error" path="photoURL" />
		
		<br/>
		<br/>

		
	</fieldset>
	<br/>
	<br/>

	<fieldset>
		<legend align="left"><spring:message code="customer.edit.useraccount" /></legend>
		<form:label path="userAccount.username">
			<spring:message code="customer.edit.label.username" />:
		</form:label>
		<form:input path="userAccount.username"/>
		<form:errors cssClass="error" path="userAccount.username" />
		
		<br/>
		<br/>
		
		<form:label path="userAccount.password">
			<spring:message code="customer.edit.label.password" />:
		</form:label>
		<form:password path="userAccount.password"/>
		<form:errors cssClass="error" path="userAccount.password" />
	</fieldset>
	<br/>
	<br/>
	<input type="submit" name="save" value="<spring:message code="customer.edit.save" />" />&nbsp;
	<input type="button" name="cancel" onclick="javascript: window.location.replace('welcome/index.do')"
			value="<spring:message code="customer.edit.cancel" />" />

</form:form>