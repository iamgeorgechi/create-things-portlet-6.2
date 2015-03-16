<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ include file="/html/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "Create Documents");

String tabNames = "Create Documents,Create Organizations,Create Pages,Create Roles,Create Sites,Create Users,Create Web Content Articles";
%>

<liferay-portlet:renderURL var="portletURL"/>

<liferay-ui:success key="success" message="Your request completed successfully." />
<liferay-ui:error key="mustBeSignedIn" message="You must be signed in as an administrator. Please sign in and try again." />
<!-- Create Organizations Errors-->
<liferay-ui:error key="numberOfOrganizationsError" message="Please enter the number of organizations you would like to create" />
<liferay-ui:error key="baseOrganizationNameError" message="Please enter the base name for the organizations (i.e. newOrg, org, testOrg)" />
<liferay-ui:error key="mustEnterNumberOrgs" message="You have entered invalid data for the number of organizations. Please enter an integer value and try again." />
<liferay-ui:error key="duplicateOrgName" message="One or more organizations already exist with the name you've entered. Please try again." />
<!-- Create Sites Errors-->
<liferay-ui:error key="numberOfSitesError" message="Please enter the number of sites you would like to create" />
<liferay-ui:error key="baseSiteNameError" message="Please enter the base name for the sites (i.e. newSite, site, testSite)" />
<liferay-ui:error key="mustEnterNumberSites" message="You have entered invalid data for the number of sites. Please enter an integer value and try again." />
<liferay-ui:error key="duplicateSiteName" message="One or more sites already exist with the name you've entered. Please try again." />
<!-- Create Pages Errors-->
<liferay-ui:error key="numberOfPagesError" message="Please enter the number of pages you would like to create" />
<liferay-ui:error key="basePageNameError" message="Please enter the base name for the pages (i.e. newPage, page, testPage)" />
<liferay-ui:error key="numberOfChildPagesError" message="Please enter the number of child pages you would like to create" />
<liferay-ui:error key="baseChildPageNameError" message="Please enter the base name for the child pages (i.e. newChildPage, childPage, testChildPage)" />
<liferay-ui:error key="mustEnterNumberPages" message="You have entered invalid data for the number of pages. Please enter an integer value and try again." />
<liferay-ui:error key="mustEnterNumberChildPages" message="You have entered invalid data for the number of child pages. Please enter an integer value and try again." />
<!-- Create Users Errors-->
<liferay-ui:error key="numberOfUsersError" message="Please enter the number of users you would like to create" />
<liferay-ui:error key="baseScreenNameError" message="Please enter the base screenName for the users (i.e. newUser, testUser, user)" />
<liferay-ui:error key="duplicateScreenName" message="One or more users already exist with name you've entered. Please try again." />
<liferay-ui:error key="noOrg" message="Cannot assign users to organization; organization does not exist. Please try again." />
<liferay-ui:error key="noGroup" message="Cannot assign users to group; group does not exist. Please try again." />
<liferay-ui:error key="noRole" message="Cannot assign users to role; role does not exist. Please try again." />
<liferay-ui:error key="mustEnterNumberUsers" message="You have entered invalid data for the number of users. Please enter an integer value and try again." />
<liferay-ui:error key="error" message="You have entered invalid data. Please try again." />
<!-- Create Articles Errors-->
<liferay-ui:error key="noMethod" message="This portion of the portlet will not work with your version of Liferay. Please download and deploy the correct version of this portlet." />
<liferay-ui:error key="numberOfArticlesError" message="Please enter the number of web content articles you would like to create" />
<liferay-ui:error key="baseArticleNameError" message="Please enter the base name for the web content articles (i.e. article, newArticle, webContent)" />
<liferay-ui:error key="mustEnterNumberArticles" message="You have entered invalid data for the number of web content articles. Please enter an integer value and try again." />
<liferay-ui:error key="noGroup" message="Cannot assign web content articles to group; group does not exist. Please try again." />
<!-- Create Documents Errors-->
<liferay-ui:error key="duplicateDocName" message="One or more documents already exist with the name you've entered. Please try again." />
<liferay-ui:error key="numberOfDocumentsError" message="Please enter the number of documents you would like to create" />
<liferay-ui:error key="baseDocumentNameError" message="Please enter the base name for the documents (i.e. doc, newDoc, testDoc)" />
<liferay-ui:error key="mustEnterNumberDocuments" message="You have entered invalid data for the number of documents. Please enter an integer value and try again." />
<liferay-ui:error key="noGroup" message="Cannot assign documents to group; group does not exist. Please try again." />
<!-- Create Roles Errors-->
<liferay-ui:error key="duplicateRoleName" message="One or more roles already exist with the name you've entered. Please try again." />
<liferay-ui:error key="numberOfRolesError" message="Please enter the number of roles you would like to create" />
<liferay-ui:error key="baseRoleNameError" message="Please enter the base name for the role (i.e. role, newRole, testRole)" />
<liferay-ui:error key="mustEnterNumberRoles" message="You have entered invalid data for the number of roles. Please enter an integer value and try again." />

<liferay-ui:tabs
	names="<%= tabNames %>"
	url="<%= portletURL.toString() %>"
/>

<%
if (tabs1.equals("Create Organizations")) {
%>
	<portlet:actionURL var="createOrganizationsURL">
		<portlet:param name="mvcPath" value="/html/view.jsp" />
	</portlet:actionURL>

	<h4>This portlet creates organizations with minimal information.</h4><br />

	Example: if you enter the values "3" and "org" the portlet will create three regular organizations: org1, org2, and org3.<br /><br />

	*You must be signed in as an administrator in order to create organizations<br />
	*The counter always starts at 1<br />
	*The organization type is "Regular Organization"<br /><br />

	<h5>Creating Large Batches of Organizations</h5>
	*If the number of organizations is large (over 100), go to <i>Control Panel -> Server Administration -> Log Levels -> Add Category</i>, and add "com.liferay.custom" and set to "INFO" to track progress (batches of 10%)<br />
	*It may take some time (even for the logs to show) to create a large number of organizations, and the page will hang until the process is complete; you can query the database if you are uncertain of the progress<br /><br /><br />

	<%
	String numberOfOrganizationsLabel = "Enter the number of organizations you would like to create";
	String baseOrganizationNameLabel = "Enter the base name for the organizations";
	%>

	<aui:form action="<%= createOrganizationsURL %>" method="post">
		<aui:input name="companyId" type="hidden" value="<%= company.getCompanyId() %>" />
		<aui:input name="tab" type="hidden" value="<%= tabs1 %>" />
		<aui:input name="numberOfOrganizations" label="<%= numberOfOrganizationsLabel %>" /><br />
		<aui:input name="baseOrganizationName" label="<%= baseOrganizationNameLabel %>" /><br />

		<aui:button type="submit" />
	</aui:form>
<%
}
%>

<%
if (tabs1.equals("Create Sites")) {
%>
	<portlet:actionURL var="createSitesURL">
		<portlet:param name="mvcPath" value="/html/view.jsp" />
	</portlet:actionURL>

	<h4>This portlet creates sites with minimal information.</h4><br />

	Example: if you enter the values "3" and "site" the portlet will create three blank sites: site1, site2, and site3.<br /><br />

	*You must be signed in as an administrator in order to create sites<br />
	*The counter always starts at 1<br />
	*The site type is "Blank Site"<br /><br />

	<h5>Creating Large Batches of Sites</h5>
	*If the number of sites is large (over 100), go to <i>Control Panel -> Server Administration -> Log Levels -> Add Category</i>, and add "com.liferay.custom" and set to "INFO" to track progress (batches of 10%)<br />
	*It may take some time (even for the logs to show) to create a large number of sites, and the page will hang until the process is complete; you can query the database if you are uncertain of the progress<br /><br /><br />

	<%
	String numberOfSitesLabel = "Enter the number of sites you would like to create";
	String baseSiteNameLabel = "Enter the base name for the sites";
	%>

	<aui:form action="<%= createSitesURL %>" method="post">
		<aui:input name="companyId" type="hidden" value="<%= company.getCompanyId() %>" />
		<aui:input name="tab" type="hidden" value="<%= tabs1 %>" />
		<aui:input name="numberOfSites" label="<%= numberOfSitesLabel %>" /><br />
		<aui:input name="baseSiteName" label="<%= baseSiteNameLabel %>" /><br />

		<aui:button type="submit" />
	</aui:form>

<%
}
%>

<%
if (tabs1.equals("Create Pages")) {
%>
	<portlet:actionURL var="createPagesURL">
	<portlet:param name="mvcPath" value="/html/view.jsp" />
	</portlet:actionURL>

	<h4>This portlet creates pages with minimal information.</h4><br />

	Example: if you enter the values "3" and "page" the portlet will create three pages: page1, page2, and page3.<br /><br />

	*You must be signed in as an administrator in order to create pages<br />
	*The counter always starts at 1<br />
	*If no site is selected, the default site will be liferay.com<br /><br />

	<h5>Creating Large Batches of Pages</h5>
	*If the number of pages is large (over 100), go to <i>Control Panel -> Server Administration -> Log Levels -> Add Category</i>, and add "com.liferay.custom" and set to "INFO" to track progress (batches of 10%)<br />
	*It may take some time (even for the logs to show) to create a large number of pages, and the page will hang until the process is complete; you can query the database if you are uncertain of the progress<br /><br /><br />

	<%
	String numberOfPagesLabel= "Enter the number of pages you would like to create";
	String basePageLabel= "Enter the base page name (i.e. newPage, page, testPage)";
	String groupLabel = "(OPTIONAL) Select a site to assign the pages to";

	String numberOfChildPagesLabel= "(OPTIONAL) Enter the number of child pages you would like to create";
	String baseChildPageLabel= "(OPTIONAL) Enter the base child page name (i.e. newChildPage, childPage, testChildPage)";

	List<Group> groups = GroupLocalServiceUtil.getGroups(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	String defaultOption = "(None)";
	%>

	<aui:form action="<%= createPagesURL %>" method="post">
	<aui:input name="companyId" type="hidden" value="<%= company.getCompanyId() %>" />
	<aui:input name="groupId" type="hidden" value="<%= themeDisplay.getScopeGroupId() %>" />
	<aui:input name="languageId" type="hidden" value="<%= user.getLanguageId() %>" />
	<aui:input name="tab" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="numberOfPages" label="<%= numberOfPagesLabel %>" /><br />
	<aui:input name="basePage" label="<%= basePageLabel %>" /><br />

	<aui:select name="group" label="<%= groupLabel %>" >
	<aui:option label="<%= defaultOption %>" />
	<%
	for (Group group : groups) {
		if (group.isSite() && !group.getDescriptiveName().equals("Control Panel")) {
	%>
			<aui:option label="<%= group.getDescriptiveName() %>" />
	<%
		}
	}
	%>
	</aui:select><br />

	<aui:input name="numberOfChildPages" label="<%= numberOfChildPagesLabel %>" /><br />
	<aui:input name="baseChildPage" label="<%= baseChildPageLabel %>" /><br />

	<aui:button type="submit" />
	</aui:form>
	<%
}
%>

<%
if (tabs1.equals("Create Users")) {
%>
	<portlet:actionURL var="createUsersURL">
		<portlet:param name="mvcPath" value="/html/view.jsp" />
	</portlet:actionURL>

	<h4>This portlet creates users with minimal information.</h4><br />

	Example: if you enter the values "3" and "newUser" the portlet will create three users: newUser1, newUser2, and newUser3.<br /><br />

	*You must be signed in as an administrator in order to create users<br />
	*The counter always starts at 1<br />
	*Email addresses will be the base screenName + "@liferay.com"<br />
	*Passwords are set to "test"<br />
	*Users' first names will be the base screenName you input<br />
	*Users' last names will be the counter<br /><br />

	<h5>Creating Large Batches of Users</h5>
	*If the number of users is large (over 100), go to <i>Control Panel -> Server Administration -> Log Levels -> Add Category</i>, and add "com.liferay.custom" and set to "INFO" to track progress (batches of 10%)<br />
	*It may take some time (even for the logs to show) to create a large number of users, and the page will hang until the process is complete; you can query the database if you are uncertain of the progress<br /><br /><br />

	<%
	List<Organization> organizations = OrganizationLocalServiceUtil.getOrganizations(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	List<Group> groups = GroupLocalServiceUtil.getGroups(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	List<Role> roles = RoleLocalServiceUtil.getRoles(company.getCompanyId());

	String numberOfUsersLabel= "Enter the number of users you would like to create";
	String baseScreenNameLabel= "Enter the base screenName for the users (i.e. newUser, testUser, user)";
	String organizationLabel = "(OPTIONAL) Select an organization to assign the users to";
	String groupLabel = "(OPTIONAL) Select a site to assign the users to";
	String roleLabel = "(OPTIONAL) Select a role to assign the users to";
	String roleHelpMessage = "Organization and site roles cannot be assigned unless users are assigned to an organization or site.";

	String defaultOption = "(None)";
	%>

	<aui:form action="<%= createUsersURL %>" method="post">
		<aui:input name="companyId" type="hidden" value="<%= company.getCompanyId() %>" />
		<aui:input name="tab" type="hidden" value="<%= tabs1 %>" />
		<aui:input name="numberOfUsers" label="<%= numberOfUsersLabel %>" /><br />
		<aui:input name="baseScreenName" label="<%= baseScreenNameLabel %>" /><br />

		<aui:select name="organization" label="<%= organizationLabel %>" >
			<aui:option label="<%= defaultOption %>" />
			<%
			for (Organization organization : organizations) {
			%>
				<aui:option label="<%= organization.getName() %>" />
			<%
			}
			%>
		</aui:select><br />

		<aui:select name="group" label="<%= groupLabel %>" >
			<aui:option label="<%= defaultOption %>" />
			<%
			for (Group group : groups) {
				if (group.isSite() && !group.getDescriptiveName().equals("Control Panel")) {
			%>
					<aui:option label="<%= group.getDescriptiveName() %>" />
			<%
				}
			}
			%>
		</aui:select><br />

		<aui:select name="role" label="<%= roleLabel %>" helpMessage="<%= roleHelpMessage %>" >
			<aui:option label="<%= defaultOption %>" />
			<%
			for (Role role : roles) {
			%>
				<aui:option label="<%= role.getDescriptiveName() %>" />
			<%
			}
			%>
		</aui:select><br />

		<aui:button type="submit" />
	</aui:form>
<%
}
%>

<%
if (tabs1.equals("Create Web Content Articles")) {
%>
	<portlet:actionURL var="createArticlesURL">
		<portlet:param name="mvcPath" value="/html/view.jsp" />
	</portlet:actionURL>

	<h4>This portlet creates web content articles with minimal information.</h4><br />

	Example: if you enter the values "3" and "webContent" the portlet will create three web content articles: webContent1, webContent2, and webContent3.<br /><br />

	*You must be signed in as an administrator in order to create web content articles<br />
	*The counter always starts at 1<br />
	*If no site is selected, the default site will be liferay.com<br /><br />

	<h5>Creating Large Batches of Web Content Articles</h5>
	*If the number of web content articles is large (over 100), go to <i>Control Panel -> Server Administration -> Log Levels -> Add Category</i>, and add "com.liferay.custom" and set to "INFO" to track progress (batches of 10%)<br />
	*It may take some time (even for the logs to show) to create a large number of web content articles, and the page will hang until the process is complete; you can query the database if you are uncertain of the progress<br /><br /><br />

	<%
	String numberOfArticlesLabel= "Enter the number of web content articles you would like to create";
	String baseArticleLabel= "Enter the base web content article name (i.e. article, newArticle, webContent)";
	String groupLabel = "(OPTIONAL) Select a site to assign the web content articles to";

	List<Group> groups = GroupLocalServiceUtil.getGroups(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	String defaultOption = "(None)";
	%>

	<aui:form action="<%= createArticlesURL %>" method="post">
		<aui:input name="companyId" type="hidden" value="<%= company.getCompanyId() %>" />
		<aui:input name="groupId" type="hidden" value="<%= themeDisplay.getScopeGroupId() %>" />
		<aui:input name="languageId" type="hidden" value="<%= user.getLanguageId() %>" />
		<aui:input name="tab" type="hidden" value="<%= tabs1 %>" />
		<aui:input name="numberOfArticles" label="<%= numberOfArticlesLabel %>" /><br />
		<aui:input name="baseArticle" label="<%= baseArticleLabel %>" /><br />

		<aui:select name="group" label="<%= groupLabel %>" >
			<aui:option label="<%= defaultOption %>" />
			<%
			for (Group group : groups) {
				if (group.isSite() && !group.getDescriptiveName().equals("Control Panel")) {
			%>
					<aui:option label="<%= group.getDescriptiveName() %>" />
			<%
				}
			}
			%>
		</aui:select><br />

		<aui:button type="submit" />
	</aui:form>
<%
}
%>

<%
if (tabs1.equals("Create Documents")) {
%>
	<portlet:actionURL var="createDocumentsURL">
		<portlet:param name="mvcPath" value="/html/view.jsp" />
	</portlet:actionURL>

	<h4>This portlet creates documents with minimal information.</h4><br />

	Example: if you enter the values "3" and "doc" the portlet will create three documents: doc1, doc2, and doc3.<br /><br />

	*You must be signed in as an administrator in order to create documents<br />
	*The counter always starts at 1<br />
	*If no site is selected, the default site will be liferay.com<br /><br />

	<h5>Creating Large Batches of Documents</h5>
	*If the number of documents is large (over 100), go to <i>Control Panel -> Server Administration -> Log Levels -> Add Category</i>, and add "com.liferay.custom" and set to "INFO" to track progress (batches of 10%)<br />
	*It may take some time (even for the logs to show) to create a large number of documents, and the page will hang until the process is complete; you can query the database if you are uncertain of the progress<br /><br /><br />

	<%
	String numberOfDocumentsLabel= "Enter the number of documents you would like to create";
	String baseDocumentLabel= "Enter the base document name (i.e. doc, newDoc, testDoc)";
	String groupLabel = "(OPTIONAL) Select a site to assign the documents to";

	List<Group> groups = GroupLocalServiceUtil.getGroups(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	String defaultOption = "(None)";
	%>

	<aui:form action="<%= createDocumentsURL %>" method="post">
		<aui:input name="companyId" type="hidden" value="<%= company.getCompanyId() %>" />
		<aui:input name="groupId" type="hidden" value="<%= themeDisplay.getScopeGroupId() %>" />
		<aui:input name="tab" type="hidden" value="<%= tabs1 %>" />
		<aui:input name="numberOfDocuments" label="<%= numberOfDocumentsLabel %>" /><br />
		<aui:input name="baseDocument" label="<%= baseDocumentLabel %>" /><br />

		<aui:select name="group" label="<%= groupLabel %>" >
			<aui:option label="<%= defaultOption %>" />
			<%
			for (Group group : groups) {
				if (group.isSite() && !group.getDescriptiveName().equals("Control Panel")) {
			%>
					<aui:option label="<%= group.getDescriptiveName() %>" />
			<%
				}
			}
			%>
		</aui:select><br />

		<aui:button type="submit" />
	</aui:form>
<%
}
%>

<%
if (tabs1.equals("Create Roles")) {
%>
	<portlet:actionURL var="createRolesURL">
		<portlet:param name="mvcPath" value="/html/view.jsp" />
	</portlet:actionURL>

	<h4>This portlet creates roles with minimal information.</h4><br />

	Example: if you enter the values "3" and "role" the portlet will create three roles: role1, role2, and role3.<br /><br />

	*You must be signed in as an administrator in order to create roles<br />
	*The counter always starts at 1<br />
	*If no role type is selected, the default type will be "Regular Role"<br /><br />


	<h5>Creating Large Batches of Roles</h5>
	*If the number of roles is large (over 100), go to <i>Control Panel -> Server Administration -> Log Levels -> Add Category</i>, and add "com.liferay.custom" and set to "INFO" to track progress (batches of 10%)<br />
	*It may take some time (even for the logs to show) to create a large number of roles, and the page will hang until the process is complete; you can query the database if you are uncertain of the progress<br /><br /><br />

	<%
	String numberOfRolesLabel= "Enter the number of roles you would like to create";
	String baseRoleLabel= "Enter the base role name (i.e. role, newRole, testRole)";
	String roleLabel = "(OPTIONAL) Select the type of Role";
	String defaultOption = "(None)";
	%>

	<aui:form action="<%= createRolesURL %>" method="post">
		<aui:input name="numberOfRoles" label="<%= numberOfRolesLabel %>" /><br />
		<aui:input name="baseRole" label="<%= baseRoleLabel %>" /><br />
		<aui:input name="languageId" type="hidden" value="<%= user.getLanguageId() %>" />
		<aui:input name="tab" type="hidden" value="<%= tabs1 %>" />

		<aui:select name="roleType" label="<%= roleLabel %>" >
			<aui:option label="<%= defaultOption %>" />
			<aui:option label="Regular Role" value="regular" />
			<aui:option label="Organization Role" value="organization" />
			<aui:option label="Site Role" value="site" />
		</aui:select><br />

		<aui:button type="submit" />
	</aui:form>
<%
}
%>