<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.model.Group" %>
<%@ page import="com.liferay.portal.model.Organization" %>
<%@ page import="com.liferay.portal.model.Role" %>
<%@ page import="com.liferay.portal.service.GroupLocalServiceUtil" %>
<%@ page import="com.liferay.portal.service.OrganizationLocalServiceUtil" %>
<%@ page import="com.liferay.portal.service.RoleLocalServiceUtil" %>
<%@ page import="java.util.List" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />