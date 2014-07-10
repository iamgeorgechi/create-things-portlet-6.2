package com.liferay.custom;

import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.DuplicateOrganizationException;
import com.liferay.portal.DuplicateUserScreenNameException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.OrganizationServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author georgechi
 */
public class CreateAllTheThingsPortlet extends MVCPortlet {
	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		String tab = actionRequest.getParameter("tab");

		if (tab.equals("Create Organizations")) {
			createOrganizations(actionRequest, actionResponse);
		}
		else if (tab.equals("Create Sites")) {
			createSites(actionRequest, actionResponse);
		}
		else if (tab.equals("Create Pages")) {
			createPages(actionRequest, actionResponse);
		}
		else if (tab.equals("Create Users")) {
			createUsers(actionRequest, actionResponse);
		}
		else if (tab.equals("Create Web Content Articles")) {
			createWebContentArticles(actionRequest, actionResponse);
		}
		else if (tab.equals("Create Documents")) {
			createDocuments(actionRequest, actionResponse);
		}

		actionResponse.setRenderParameter("tabs1", tab);
		super.processAction(actionRequest, actionResponse);
	}

	private void createOrganizations(ActionRequest actionRequest, ActionResponse actionResponse) {

		String numberOfOrganizations = actionRequest.getParameter("numberOfOrganizations");
		String baseOrganizationName = actionRequest.getParameter("baseOrganizationName");

		double loader = 10;

		try {
			if (Validator.isNotNull(numberOfOrganizations) && Validator.isNotNull(baseOrganizationName)) {
				ServiceContext serviceContext = ServiceContextFactory.getInstance(
					Organization.class.getName(), actionRequest);

				_log.info("Starting to create " + numberOfOrganizations + " organizations");

				for (int i = 1; i <= Integer.parseInt(numberOfOrganizations); i++) {
					if (Integer.parseInt(numberOfOrganizations) >= 100) {
						if (i == (int) (Double.parseDouble(numberOfOrganizations) * (loader / 100))) {
							_log.info("Creating organizations..." + (int) loader + "% done");
							loader = loader + 10;
						}
					}

					StringBundler organizationName = new StringBundler(2);
					organizationName.append(baseOrganizationName);
					organizationName.append(i);

					OrganizationServiceUtil.addOrganization(
						0, //parentOrganizationId
						organizationName.toString(), //name
						"regular-organization", //type
						false, //recursable
						0, //regionId
						0, //countryId
						12017, //statusId
						StringPool.BLANK, //comments
						false, //site
						Collections.<Address>emptyList(), //addresses
						Collections.<EmailAddress>emptyList(),//emailAddresses
						Collections.<OrgLabor>emptyList(),//orgLabors
						Collections.<Phone>emptyList(),//phones
						Collections.<Website>emptyList(), //websites
						serviceContext); //serviceContext

					SessionMessages.add(actionRequest, "success");
				}
				_log.info("Finished creating " + numberOfOrganizations + " organizations");
			}
			else {
				if (Validator.isNull(numberOfOrganizations)) {
					SessionErrors.add(actionRequest, "numberOfOrganizationsError");
				}

				if (Validator.isNull(baseOrganizationName)) {
					SessionErrors.add(actionRequest, "baseOrganizationNameError");
				}
			}
		}
		catch (DuplicateOrganizationException e) {
			SessionErrors.add(actionRequest, "duplicateOrgName");
		}
		catch (NumberFormatException e) {
			SessionErrors.add(actionRequest, "mustEnterNumberOrgs");
		}
		catch (PrincipalException e) {
			SessionErrors.add(actionRequest, "mustBeSignedIn");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createSites(ActionRequest actionRequest, ActionResponse actionResponse) {

		String numberOfSites = actionRequest.getParameter("numberOfSites");
		String baseSiteName = actionRequest.getParameter("baseSiteName");

		double loader = 10;

		try {
			if (Validator.isNotNull(numberOfSites) && Validator.isNotNull(baseSiteName)) {
				ServiceContext serviceContext = ServiceContextFactory.getInstance(
					Group.class.getName(), actionRequest);

				_log.info("Starting to create " + numberOfSites + " sites");

				for (int i = 1; i <= Integer.parseInt(numberOfSites); i++) {
					if (Integer.parseInt(numberOfSites) >= 100) {
						if (i == (int) (Double.parseDouble(numberOfSites) * (loader / 100))) {
							_log.info("Creating sites..." + (int) loader + "% done");
							loader = loader + 10;
						}
					}

					StringBundler siteName = new StringBundler(2);
					siteName.append(baseSiteName);
					siteName.append(i);

					GroupServiceUtil.addGroup(
						siteName.toString(), //name
						StringPool.BLANK, //description
						1, //type
						StringPool.BLANK, //friendlyURL
						true, //site
						true, //active
						serviceContext); //serviceContext

					SessionMessages.add(actionRequest, "success");
				}
				_log.info("Finished creating " + numberOfSites + " sites");
			}
			else {
				if (Validator.isNull(numberOfSites)) {
					SessionErrors.add(actionRequest, "numberOfSitesError");
				}

				if (Validator.isNull(baseSiteName)) {
					SessionErrors.add(actionRequest, "baseSiteNameError");
				}
			}
		}
		catch (PrincipalException e) {
			SessionErrors.add(actionRequest, "mustBeSignedIn");
		}
		catch (DuplicateGroupException e) {
			SessionErrors.add(actionRequest, "duplicateSiteName");
		}
		catch (NumberFormatException e) {
			SessionErrors.add(actionRequest, "mustEnterNumberSites");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createPages(ActionRequest actionRequest, ActionResponse actionResponse) {

		String numberOfPages = actionRequest.getParameter("numberOfPages");
		String basePage = actionRequest.getParameter("basePage");
		String defaultGroupId = actionRequest.getParameter("groupId");
		String languageId = actionRequest.getParameter("languageId");
		String groupDescriptiveName = actionRequest.getParameter("group");

		Locale defaultLocale = LocaleUtil.fromLanguageId(languageId);

		Locale[] locales = LanguageUtil.getAvailableLocales();

		Long groupId = null;

		double loader = 10;

		try {
			if (Validator.isNotNull(numberOfPages) && Validator.isNotNull(basePage)) {

				if (groupDescriptiveName.equals("(None)")) {
					groupId = Long.parseLong(defaultGroupId);
				}
				else {
					for (Group targetGroup : GroupLocalServiceUtil.getGroups(QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {
						if (groupDescriptiveName.equals(targetGroup.getDescriptiveName())) {
							groupId = targetGroup.getGroupId();
							break;
						}
					}
				}

				_log.info("Starting to create " + numberOfPages + " pages");

				ServiceContext serviceContext = ServiceContextFactory.getInstance(
					Layout.class.getName(), actionRequest);

				for (int i = 1; i <= Integer.parseInt(numberOfPages); i++) {
					if (Integer.parseInt(numberOfPages) >= 100) {
						if (i == (int) (Double.parseDouble(numberOfPages) * (loader / 100))) {
							_log.info("Creating pages..." + (int) loader + "% done");
							loader = loader + 10;
						}
					}

					StringBundler name = new StringBundler(2);
					name.append(basePage);
					name.append(i);

					Map<Locale, String> titleMap = new HashMap<Locale, String>();
					titleMap.put(defaultLocale, StringPool.BLANK);

					Map<Locale, String> nameMap = new HashMap<Locale, String>();
					nameMap.put(defaultLocale, name.toString());

					Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
					descriptionMap.put(defaultLocale, StringPool.BLANK);

					Map<Locale, String> keywordsMap = new HashMap<Locale, String>();
					keywordsMap.put(defaultLocale, StringPool.BLANK);

					Map<Locale, String> robotsMap = new HashMap<Locale, String>();
					robotsMap.put(defaultLocale, StringPool.BLANK);

					LayoutServiceUtil.addLayout(
						groupId, //groupId
						false, //privateLayout
						0, //parentLayoutId
						nameMap, //nameMap
						titleMap, //titleMap
						descriptionMap, //descriptionMap
						keywordsMap, //keywordsMap
						robotsMap, //robotsMap
						"portlet", //type
						false, //hidden
						StringPool.BLANK, //friendlyURL
						serviceContext); //serviceContext

					SessionMessages.add(actionRequest, "success");
				}
				_log.info("Finished creating " + numberOfPages + " pages");
			}
			else {
				if (Validator.isNull(numberOfPages)) {
					SessionErrors.add(actionRequest, "numberOfPagesError");
				}

				if (Validator.isNull(basePage)) {
					SessionErrors.add(actionRequest, "basePageNameError");
				}
			}
		}
		catch (NoSuchGroupException e) {
			SessionErrors.add(actionRequest, "noGroup");
		}
		catch (NumberFormatException e) {
			SessionErrors.add(actionRequest, "mustEnterNumberPages");
		}
		catch (PrincipalException e) {
			SessionErrors.add(actionRequest, "mustBeSignedIn");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createUsers(ActionRequest actionRequest, ActionResponse actionResponse) {

		String companyIdString = actionRequest.getParameter("companyId");
		String numberOfUsers = actionRequest.getParameter("numberOfUsers");
		String baseScreenName = actionRequest.getParameter("baseScreenName");
		String organizationName = actionRequest.getParameter("organization");
		String groupDescriptiveName = actionRequest.getParameter("group");
		String roleName = actionRequest.getParameter("role");

		long companyId = Long.parseLong(companyIdString);

		Organization organization = null;
		long[] organizationIds = new long[0];
		Group group = null;
		long[] groupIds = null;
		Role role = null;
		long[] roleIds = null;

		double loader = 10;

		try {
			if (!organizationName.equals("(None)")) {
				organization = OrganizationLocalServiceUtil.getOrganization(companyId, organizationName);
				organizationIds = new long[]{organization.getOrganizationId()};
			}

			if (!groupDescriptiveName.equals("(None)")) {
				for (Group targetGroup : GroupLocalServiceUtil.getGroups(QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {
					if (groupDescriptiveName.equals(targetGroup.getDescriptiveName())) {
						group = targetGroup;
						break;
					}
				}
				groupIds = new long[]{group.getGroupId()};
			}

			if (!roleName.equals("(None)")) {
				role = RoleLocalServiceUtil.getRole(companyId, roleName);
				roleIds = new long[]{role.getRoleId()};
			}

			if (Validator.isNotNull(numberOfUsers) && Validator.isNotNull(baseScreenName)) {
				ServiceContext serviceContext = ServiceContextFactory.getInstance(User.class.getName(), actionRequest);

				_log.info("Starting to create " + numberOfUsers + " users");

				for (int i = 1; i <= Integer.parseInt(numberOfUsers); i++) {
					if (Integer.parseInt(numberOfUsers) >= 100) {
						if (i == (int)(Double.parseDouble(numberOfUsers) * (loader / 100))) {
							_log.info("Creating users..." + (int)loader + "% done");
							loader = loader + 10;
						}
					}

					StringBundler screenName = new StringBundler(2);
					screenName.append(baseScreenName);
					screenName.append(i);

					StringBundler emailAddress = new StringBundler(2);
					emailAddress.append(screenName);
					emailAddress.append("@liferay.com");

					UserServiceUtil.addUser(
						companyId, //companyId
						false, //autopassword
						"test", //password1
						"test", //password2
						false, //autoscreenname
						screenName.toString(), //screenname
						emailAddress.toString(), //emailAddress
						0, //facebookId
						StringPool.BLANK, //openId
						LocaleUtil.getDefault(), //locale
						baseScreenName, //firstName
						StringPool.BLANK, //middleName
						String.valueOf(i), //lastName
						0, //prefixId
						0, //suffixId
						true, //male
						0, //birthdayDay
						1, //birthdayMonth
						1970, //birthdayYear
						StringPool.BLANK, //jobTitle
						groupIds, //groupIds
						organizationIds, //organizationIds
						roleIds, //roleIds
						null, //usergroupIds
						Collections.<Address>emptyList(), //addresses
						Collections.<EmailAddress>emptyList(), //emailAddresses
						Collections.<Phone>emptyList(), //phones
						Collections.<Website>emptyList(), //websites
						Collections.<AnnouncementsDelivery>emptyList(), //announcementsDelivers
						false, //sendEmail
						serviceContext); //serviceContext

					SessionMessages.add(actionRequest, "success");
				}
				_log.info("Finished creating " + numberOfUsers + " users");
			}
			else {
				if (Validator.isNull(numberOfUsers)) {
					SessionErrors.add(actionRequest, "numberOfUsersError");
				}

				if (Validator.isNull(baseScreenName)) {
					SessionErrors.add(actionRequest, "baseScreenNameError");
				}
			}
		}
		catch (DuplicateUserScreenNameException e) {
			SessionErrors.add(actionRequest, "duplicateScreenName");
		}
		catch (NoSuchOrganizationException e) {
			SessionErrors.add(actionRequest, "noOrg");
		}
		catch (NoSuchGroupException e) {
			SessionErrors.add(actionRequest, "noGroup");
		}
		catch (NoSuchRoleException e) {
			SessionErrors.add(actionRequest, "noRole");
		}
		catch (PrincipalException e) {
			SessionErrors.add(actionRequest, "mustBeSignedIn");
		}
		catch (NumberFormatException e) {
			SessionErrors.add(actionRequest, "mustEnterNumberUsers");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createWebContentArticles(ActionRequest actionRequest, ActionResponse actionResponse) {

		String numberOfArticles = actionRequest.getParameter("numberOfArticles");
		String baseArticle = actionRequest.getParameter("baseArticle");
		String defaultGroupId = actionRequest.getParameter("groupId");
		String languageId = actionRequest.getParameter("languageId");
		String groupDescriptiveName = actionRequest.getParameter("group");

		Long groupId = null;

		double loader = 10;

		try {
			if (Validator.isNotNull(numberOfArticles) && Validator.isNotNull(baseArticle)) {

				if (groupDescriptiveName.equals("(None)")) {
					groupId = Long.parseLong(defaultGroupId);
				}
				else {
					for (Group targetGroup : GroupLocalServiceUtil.getGroups(QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {
						if (groupDescriptiveName.equals(targetGroup.getDescriptiveName())) {
							groupId = targetGroup.getGroupId();
							break;
						}
					}
				}

				_log.info("Starting to create " + numberOfArticles + " web content articles");

				ServiceContext serviceContext = ServiceContextFactory.getInstance(
					JournalArticle.class.getName(), actionRequest);

				Locale defaultLocale = LocaleUtil.fromLanguageId(languageId);

				Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
				descriptionMap.put(defaultLocale, StringPool.BLANK);

				for (int i = 1; i <= Integer.parseInt(numberOfArticles); i++) {
					if (Integer.parseInt(numberOfArticles) >= 100) {
						if (i == (int) (Double.parseDouble(numberOfArticles) * (loader / 100))) {
							_log.info("Creating documents..." + (int) loader + "% done");
							loader = loader + 10;
						}
					}

					StringBundler title = new StringBundler(2);
					title.append(baseArticle);
					title.append(i);

					Map<Locale, String> titleMap = new HashMap<Locale, String>();
					titleMap.put(defaultLocale, title.toString());

					StringBundler content = new StringBundler(4);
					content.append("<?xml version='1.0' encoding='UTF-8'?>");
					content.append("<root available-locales=\"en_US\" default-locale=\"en_US\">");
					content.append("<static-content language-id=\"en_US\">");
					content.append("<![CDATA[");
					content.append(baseArticle);
					content.append("]]>");
					content.append("</static-content></root>");

					JournalArticleServiceUtil.addArticle(
						groupId, //groupId
						0, //folderId
						0, //classnameId
						0, //classpk
						StringPool.BLANK, //articleId
						true, //autoArticleId
						titleMap, //titleMap
						descriptionMap, //descriptionMap
						content.toString(), //content
						"general", //type
						StringPool.BLANK, //structureId
						StringPool.BLANK, //templateId
						null, //layoutUuid
						1, //displayDateMonth
						1, //displayDateDay
						2014, //displayDateYear
						1, //displayDateHour
						30, //displayDateMinute
						0, //expirationDateMonth
						0, //expirationDateDay
						0, //expirationDateYear
						0, //expirationDateHour
						0, //expirationDateMinute
						true, //neverExpire
						0, //reviewDateMonth
						0, //reviewDateDay
						0, //reviewDateYear
						0, //reviewDateHour
						0, //reviewDateMinute
						true, //neverReview
						true, //indexable
						false, //smallImage
						StringPool.BLANK, //smallImageURL
						null, //small file
						Collections.EMPTY_MAP, //images
						StringPool.BLANK, //articleURL
						serviceContext); //serviceContext

					SessionMessages.add(actionRequest, "success");
				}
				_log.info("Finished creating " + numberOfArticles + " web content articles");
			}
			else {
				if (Validator.isNull(numberOfArticles)) {
					SessionErrors.add(actionRequest, "numberOfArticlesError");
				}

				if (Validator.isNull(baseArticle)) {
					SessionErrors.add(actionRequest, "baseArticleNameError");
				}
			}
		}
		catch (NoSuchMethodError e) {
			SessionErrors.add(actionRequest, "noMethod");
		}
		catch (NoSuchGroupException e) {
			SessionErrors.add(actionRequest, "noGroup");
		}
		catch (NumberFormatException e) {
			SessionErrors.add(actionRequest, "mustEnterNumberArticles");
		}
		catch (PrincipalException e) {
			SessionErrors.add(actionRequest, "mustBeSignedIn");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createDocuments(ActionRequest actionRequest, ActionResponse actionResponse) {

		String numberOfDocuments = actionRequest.getParameter("numberOfDocuments");
		String baseDocument = actionRequest.getParameter("baseDocument");
		String defaultGroupId = actionRequest.getParameter("groupId");
		String groupDescriptiveName = actionRequest.getParameter("group");

		Long groupId = null;

		double loader = 10;

		try {
			if (Validator.isNotNull(numberOfDocuments) && Validator.isNotNull(baseDocument)) {

				if (groupDescriptiveName.equals("(None)")) {
					groupId = Long.parseLong(defaultGroupId);
				}
				else {
					for (Group targetGroup : GroupLocalServiceUtil.getGroups(QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {
						if (groupDescriptiveName.equals(targetGroup.getDescriptiveName())) {
							groupId = targetGroup.getGroupId();
							break;
						}
					}
				}

				_log.info("Starting to create " + numberOfDocuments + " documents");

				ServiceContext serviceContext = ServiceContextFactory.getInstance(
					DLFileEntry.class.getName(), actionRequest);

				for (int i = 1; i <= Integer.parseInt(numberOfDocuments); i++) {
					if (Integer.parseInt(numberOfDocuments) >= 100) {
						if (i == (int) (Double.parseDouble(numberOfDocuments) * (loader / 100))) {
							_log.info("Creating documents..." + (int) loader + "% done");
							loader = loader + 10;
						}
					}

					StringBundler title = new StringBundler(2);
					title.append(baseDocument);
					title.append(i);

					StringBundler sourceFileName = new StringBundler(2);
					sourceFileName.append(title.toString());
					sourceFileName.append(".txt");

					DLAppServiceUtil.addFileEntry(
						groupId, //repositoryId
						0, //folderId
						sourceFileName.toString(), //sourceFileName
						"application/octet-stream", //contentType
						title.toString(), //title
						title.toString(), //description
						StringPool.BLANK, //changeLog
						null, //inputStream
						0, //size
						serviceContext); //serviceContext

					SessionMessages.add(actionRequest, "success");
				}
				_log.info("Finished creating " + numberOfDocuments + " documents");
			}
			else {
				if (Validator.isNull(numberOfDocuments)) {
					SessionErrors.add(actionRequest, "numberOfDocumentsError");
				}

				if (Validator.isNull(baseDocument)) {
					SessionErrors.add(actionRequest, "baseDocumentNameError");
				}
			}
		}
		catch (DuplicateFileException e) {
			SessionErrors.add(actionRequest, "duplicateDocName");
		}
		catch (NoSuchGroupException e) {
			SessionErrors.add(actionRequest, "noGroup");
		}
		catch (NumberFormatException e) {
			SessionErrors.add(actionRequest, "mustEnterNumberDocuments");
		}
		catch (PrincipalException e) {
			SessionErrors.add(actionRequest, "mustBeSignedIn");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CreateAllTheThingsPortlet.class);
}