package com.ssafy.happyhouse.model.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.mapper.InterestMapMapper;

@Service
public class InterestMapServiceImpl implements InterestMapService {

	final String SERVICE_KEY = "pK%2B5v%2BJKLaoeG%2FpUU1QVku5WJ7JKwySWiCvRbpo4StK4pBIuKHVrxEuibjXNZGTBVqhZeeoS8fzCbBRy7uCOCg%3D%3D";
	@Autowired
	private SqlSession SqlSession;

	@Override
	public List<HouseInfoDto> getCafe(String gugunCode) throws Exception {
		return SqlSession.getMapper(InterestMapMapper.class).getCafe(gugunCode);
	}

	@Override
	public List<HouseInfoDto> getFastFood(String gugunCode) throws Exception {
		return SqlSession.getMapper(InterestMapMapper.class).getFastFood(gugunCode);
	}

	@Override
	public List<HouseInfoDto> getSchool(String gugunCode) throws Exception {
		return SqlSession.getMapper(InterestMapMapper.class).getSchool(gugunCode);
	}

	@Override
	public List<HouseInfoDto> getCorona(String gugunCode) throws Exception {
		return SqlSession.getMapper(InterestMapMapper.class).getCorona(gugunCode);
	}

	@Override
	public List<HouseInfoDto> getHospital(String gugunCode) throws Exception {
		List<HouseInfoDto> list = SqlSession.getMapper(InterestMapMapper.class).getHospital(gugunCode);

		for (HouseInfoDto houseInfoDto : list) {
			JSONObject json = new JSONObject(getCoordination(houseInfoDto.getJibun()));
			JSONArray jsonDocuments = (JSONArray) json.get("documents");
			if (jsonDocuments.length() != 0) {
				JSONObject j = (JSONObject) jsonDocuments.get(0);
				houseInfoDto.setLat(((String) j.get("y")).trim());
				houseInfoDto.setLng(((String) j.get("x")).trim());
			}
		}

		return list;
	}

	private String getCoordination(String address) throws Exception {

		String encodeAddress = ""; // 한글 주소는 encoding 해서 날려야 함
		try {
			encodeAddress = URLEncoder.encode(address, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json?query=" + encodeAddress;
		String auth = "KakaoAK " + "a1b504204202aefc63a532b62e1629aa";

		URL url = new URL(apiUrl);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization", auth);

		BufferedReader br;

		int responseCode = conn.getResponseCode();
		if (responseCode == 200) { // 호출 OK
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		} else { // 에러
			br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
		}

		String jsonString = new String();
		String stringLine;
		while ((stringLine = br.readLine()) != null) {
			jsonString += stringLine;
		}

		return jsonString;
	}

}
