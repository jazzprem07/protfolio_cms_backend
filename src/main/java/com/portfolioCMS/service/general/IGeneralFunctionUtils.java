/**
 *
 * Author: Sathish K (000464)
 * Date: 01-Jul-2025
 *
 * Copyright (c) 2025 Caplin Point Laboratories Limited. All rights reserved.
 *
 */

package com.portfolioCMS.service.general;

import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Date;
import java.time.LocalDate;

public interface IGeneralFunctionUtils {

	int StringToIntConvertion(String in);
	long StringToLongConvertion(String in);
	String ObjectToStringConvertion(Object in);
	int ObjectToIntConvertion(Object in);
	long ObjectToLongConvertion(Object in);
	boolean ObjectToBooleanConvertion(Object in);
	LocalDate ObjectToLocalDateConvertion(Object in);

	String ConcatUserNameAndEmpId(String name, String empId);

	long convertMinutesToMillis(int minutes);
	long convertHoursToMillis(int hours);
	long GetExpiryTime(String value);
	int GetNewIdInt(GeneratedKeyHolder keyHolder);
	long GetNewIdLong(GeneratedKeyHolder keyHolder);
	String GetCurrentDateForEmail();
	String GetLocalHostIp();
	String convertToJson(Object object);
	String GetCurrentDateTimeForAttachmentFileName();
	String GetCurrentDateTimeForProfile();
	String GetAuditTitleForDownload(String labelName, String titleName);
	String capitalizeWords(String input);
	int GetCurrentYear();
	Date StringToSqlDateConverstion(String value);
	String decodeBase64(String encodedString);
	String GetAlphanumericRandomValue(int length);
	String convertToIndianCurrency(int amount, boolean addSymbol);
	String getModifiedTableName(String tableName);
	String RemoveAllSpaceAndReplaceUnderscore(String name);
	boolean isSafeSelectQuery(String query);
	boolean IsMysqlDriver();
}
