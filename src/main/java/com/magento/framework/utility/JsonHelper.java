package com.magento.framework.utility;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

	public static String readJson(String fileName) {
		Path path = Paths.get(fileName);
		StringBuffer sb = new StringBuffer();
		try (Scanner scanner = new Scanner(path)) {
			while (scanner.hasNextLine()) {
				sb.append(scanner.nextLine().trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(sb);
	}

	public static void main(String[] args) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("key1", "value1");
			jsonObject.put("key2", "value2");
			jsonObject.put("key3", "value3");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		createJsonFile("1A.json", jsonObject);
	}

	public static void createJsonFile(String fileName, JSONObject jsonObject) {
		try {
			FileWriter file = new FileWriter(fileName);
			file.write(jsonObject.toString());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getJsonFromMap(Map<Object, Object> map) {
		String json_string = "";
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			json_string = objectMapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json_string;
	}

	public static JSONObject finalizeBody(String js, Map<Object, Object> map) {
		try {
			if (js.charAt(0) == '[')
				js = js.substring(1, js.length() - 1);
			JSONObject partsData = new JSONObject(js);
			Iterator<String> iterator = partsData.keys();
			while (iterator.hasNext()) {
				String result = iterator.next();
				if (!partsData.get(result).equals("")) {
					if (partsData.getString(result).charAt(0) == '{' || (partsData.getString(result).charAt(0) == '['
							&& partsData.getString(result).charAt(1) == '{')) {
						if (partsData.getString(result).charAt(0) == '[')
							partsData.put(result, new JSONArray(
									"[" + finalizeBody(partsData.get(result).toString(), map).toString() + "]"));
						else
							partsData.put(result, finalizeBody(partsData.get(result).toString(), map));
					} else {
						for (@SuppressWarnings("rawtypes")
						Map.Entry me : map.entrySet()) {
							if (me.getKey().equals(result)) {
								if (partsData.getString(result).charAt(0) == '[')
									partsData.put(result, new JSONArray("[" + me.getValue() + "]"));
								else
									partsData.put(result, me.getValue());
							}
						}
						if (partsData.get(result).equals("%s")) {
							iterator.remove();
						}
					}
				}
			}
			return partsData;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}