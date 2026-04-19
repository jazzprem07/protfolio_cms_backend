/**
 * 
 * Author: Sathish K (000464)
 * Date: 01-Jul-2025
 * 
 * Copyright (c) 2025 Caplin Point Laboratories Limited. All rights reserved.
 *
 */

package com.portfolioCMS.service.general.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.portfolioCMS.exceptions.CustomInternalServerException;
import com.portfolioCMS.service.general.IGeneralFunctionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GeneralFunctionUtils implements IGeneralFunctionUtils {

	private final Logger logger = LoggerFactory.getLogger(GeneralFunctionUtils.class);
	private ObjectMapper objectMapper;

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final SecureRandom random = new SecureRandom();

	@Value("${spring.datasource.driver-class-name}")
	String driverName;

	public GeneralFunctionUtils(ObjectMapper objectMapper) {
		super();
		this.objectMapper = objectMapper;
	}

	// Conversion Section
	@Override
	public int StringToIntConvertion(String in) {
		if (Optional.ofNullable(in).isPresent() && !in.isEmpty()) {
			return Integer.parseInt(in.trim());
		}
		return 0;
	}

	@Override
	public long StringToLongConvertion(String in) {
		if (Optional.ofNullable(in).isPresent() && !in.isEmpty()) {
			return Long.parseLong(in.trim());
		}
		return 0;
	}

	@Override
	public String ObjectToStringConvertion(Object in) {
		if (Optional.ofNullable(in).isPresent()) {
			return String.valueOf(in.toString());
		}
		return "";
	}

	@Override
	public int ObjectToIntConvertion(Object in) {
		if (Optional.ofNullable(in).isPresent()) {
			return StringToIntConvertion(in.toString());
		}
		return 0;
	}

	@Override
	public long ObjectToLongConvertion(Object in) {
		if (Optional.ofNullable(in).isPresent()) {
			return StringToLongConvertion(in.toString());
		}
		return 0;
	}

	@Override
	public boolean ObjectToBooleanConvertion(Object in) {
//		System.out.println(in);
		if (Optional.ofNullable(in).isPresent()) {
			if (in instanceof String) {
				return Boolean.parseBoolean(in.toString());
			}
			if (in instanceof Boolean) {
				return (boolean) in;
			}
		}
		return false;
	}

	@Override
	public LocalDate ObjectToLocalDateConvertion(Object in) {
		if (Optional.ofNullable(in).isPresent()) {
			if (in instanceof String dateString && !dateString.isEmpty()) {
				return LocalDate.parse(dateString);
			}

			if (in instanceof Date inDate) {
				return LocalDate.parse(inDate.toString());
			}
		}
		return null;
	}

	@Override
	public String ConcatUserNameAndEmpId(String name, String empId) {
		StringBuilder sb = new StringBuilder();
		return sb.append(name).append(" (").append(empId).append(")").toString();
	}

	// Convert minutes to milliseconds
	@Override
	public long convertMinutesToMillis(int minutes) {
		return minutes * 60 * 1000L;
	}

	// Convert hours to milliseconds
	@Override
	public long convertHoursToMillis(int hours) {
		return hours * 60 * 60 * 1000L;
	}

	@Override
	public long GetExpiryTime(String value) {
		try {
			if (value != null && !value.isEmpty()) {
				String[] split = value.split("\\|");
				if (split.length == 2) {
					int time = ObjectToIntConvertion(split[0]);
					String type = split[1];
					return GetExpiryTimeValue(type, time);
				}
			}
		} catch (Exception e) {
			logger.error("LOGGER_ERROR", e.getMessage());
			throw new CustomInternalServerException(e.getMessage());
		}
		return 30 * 60 * 1000L;
	}

	private long GetExpiryTimeValue(String type, int time) {
		if (type != null && !type.isEmpty()) {
			type = type.trim().toUpperCase();
			if (type.equals("MIN")) {
				return convertMinutesToMillis(time);
			}
			if (type.equals("HRS")) {
				return convertHoursToMillis(time);
			}
		}
		return 30 * 60 * 1000L;
	}

	@Override
	public int GetNewIdInt(GeneratedKeyHolder keyHolder) {
		int newId = 0;
		List<Map<String, Object>> generatedKeys = keyHolder.getKeyList();
		if (!generatedKeys.isEmpty()) {
			newId = ObjectToIntConvertion(generatedKeys.get(0).get("GENERATED_KEY"));
		}
		return newId;
	}

	@Override
	public long GetNewIdLong(GeneratedKeyHolder keyHolder) {
		long newId = 0;
		List<Map<String, Object>> generatedKeys = keyHolder.getKeyList();
		if (!generatedKeys.isEmpty()) {
			newId = ObjectToLongConvertion(generatedKeys.get(0).get("id"));
		}
		return newId;
	}

	@Override
	public String GetCurrentDateForEmail() {
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
		StringBuilder sb = new StringBuilder();
		sb.append("(").append(currentDate.format(formatter)).append(")");
		return sb.toString();
	}

	@Override
	public String GetLocalHostIp() {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			return inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "localhost";
		}
	}

	@Override
	public String convertToJson(Object object) {
		try {
			objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CASE);
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			logger.error("LOGGER_ERROR", e.getMessage());
			return null;
		}
	}

	@Override
	public String GetCurrentDateTimeForAttachmentFileName() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		return currentDateTime.format(formatter);
	}

	@Override
	public String GetCurrentDateTimeForProfile() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return currentDateTime.format(formatter);
	}

	@Override
	public String GetAuditTitleForDownload(String labelName, String titleName) {
		StringJoiner str = new StringJoiner(" ");
		str.add("Audit Trail for").add(labelName).add("-").add(titleName);
		return str.toString().toUpperCase();
	}


	@Override
	public String capitalizeWords(String input) {
		if (input == null || input.isEmpty())
			return input;

		return Arrays.stream(input.split("\\s+"))
				.map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
				.collect(Collectors.joining(" "));
	}

	@Override
	public int GetCurrentYear() {
		return Year.now().getValue();
	}

	@Override
	public Date StringToSqlDateConverstion(String value) {
		if (value != null) {
			return Date.valueOf(value);
		}
		return null;
	}

	@Override
	public String decodeBase64(String encodedString) {
		// Step 1: Decode the Base64 string into bytes
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);

		// Step 2: Convert the decoded bytes to their ASCII format
		StringBuilder asciiString = new StringBuilder();
		for (byte b : decodedBytes) {
			// Append each byte's ASCII value
			asciiString.append((int) b).append(" ");
		}

		// Return the ASCII representation
		return asciiString.toString().trim();
	}

	@Override
	public String GetAlphanumericRandomValue(int length) {
		StringBuilder token = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(CHARACTERS.length());
			token.append(CHARACTERS.charAt(index));
		}
		return token.toString();

	}

	@Override
	public String convertToIndianCurrency(int amount, boolean addSymbol) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');

		String pattern = "##,##,##0";
		DecimalFormat formatter = new DecimalFormat(pattern, symbols);

		String formattedAmount = formatter.format(amount);

		if (addSymbol) {
			return "₹ " + formattedAmount;
		} else {
			return formattedAmount;
		}
	}

	@Override
	public String getModifiedTableName(String tableName) {
		if(tableName != null && !tableName.isEmpty()) {
			return "ETL_" + tableName.replaceAll("\\s+", "_").toUpperCase();
		}
		return null;
	}

	@Override
	public String RemoveAllSpaceAndReplaceUnderscore(String name) {
		if(name != null && !name.isEmpty()) {
			return name.replaceAll("\\s+", "_").toLowerCase();
		}
		return null;
	}

	@Override
	public boolean isSafeSelectQuery(String sql) {
		String trimmed = sql.trim().replaceAll("\\s+", " ");
		return trimmed.toUpperCase().startsWith("SELECT") && !isUnsafeQuery(trimmed);
	}

	private boolean isUnsafeQuery(String sql) {
		String queryUpper = sql.toUpperCase();
		return queryUpper.contains("INSERT") ||
				queryUpper.contains("UPDATE") ||
				queryUpper.contains("DELETE") ||
				queryUpper.contains("ALTER") ||
				queryUpper.contains("DROP");
	}

	@Override
	public boolean IsMysqlDriver() {
		if(driverName.contains("mysql")) {
			return true;
		}
		return false;
	}
}