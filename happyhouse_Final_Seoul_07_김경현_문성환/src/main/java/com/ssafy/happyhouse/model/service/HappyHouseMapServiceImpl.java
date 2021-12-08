package com.ssafy.happyhouse.model.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.SidoGugunCodeDto;
import com.ssafy.happyhouse.model.mapper.HouseMapMapper;

@Service
public class HappyHouseMapServiceImpl implements HappyHouseMapService {

//	final String SERVICE_KEY = "5QaPpa8kkge2Dl4XoYO54r8EPZ8Kg%2BPGsJtjuF36TSTQZf3eryNQP6f4sElP%2BcPANncLbM6FkGAubmuXrhGkDA%3D%3D";
	final String SERVICE_KEY = "pK%2B5v%2BJKLaoeG%2FpUU1QVku5WJ7JKwySWiCvRbpo4StK4pBIuKHVrxEuibjXNZGTBVqhZeeoS8fzCbBRy7uCOCg%3D%3D";
	@Autowired
	private SqlSession SqlSession;

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return SqlSession.getMapper(HouseMapMapper.class).getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return SqlSession.getMapper(HouseMapMapper.class).getGugunInSido(sido);
	}

	@Override
	public List<HouseInfoDto> getDongInGugun(String gugun) throws Exception {
		return SqlSession.getMapper(HouseMapMapper.class).getDongInGugun(gugun);
	}

	// tag값의 정보를 가져오는 메소드
	private static String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue == null)
			return null;
		return nValue.getNodeValue();
	}

	// 주소를 입력하면 위,경도를 반환하는 메소드
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

	@Override
	public List<HouseInfoDto> getAlliRentInDong(String dongName, String gugunCode) throws Exception {
		String urlStr = "	http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcRHRent?"
				+ "serviceKey=" + SERVICE_KEY + "&LAWD_CD=" + gugunCode + "&DEAL_YMD=202110";

		// XML 파싱
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(urlStr);

		// root tag
		doc.getDocumentElement().normalize();
//		System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // Root element: response

		// 파싱할 tag로 리스트 만듬
		NodeList nList = doc.getElementsByTagName("item");
		System.out.println("파싱할 리스트 수 : " + nList.getLength());

		// 반환할 리스트 생성
		List<HouseInfoDto> list = new ArrayList<HouseInfoDto>();

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				// DB의 dongCode가 10자리 이므로 파싱한 데이터에서 시군구코드+읍면동코드를 합친 것과 비교
				if (dongName.equals(getTagValue("법정동", eElement).trim())) {
					System.out.println("!");
					HouseInfoDto houseInfoDto = new HouseInfoDto();
					houseInfoDto.setName(getTagValue("연립다세대", eElement).trim());
					houseInfoDto.setDongName(getTagValue("법정동", eElement).trim());
					houseInfoDto.setBuildYear(Integer.parseInt(getTagValue("건축년도", eElement)));
					houseInfoDto.setDealYear((getTagValue("년", eElement)).trim());
					houseInfoDto.setDealMonth((getTagValue("월", eElement)).trim());
					houseInfoDto.setDealDay((getTagValue("일", eElement)).trim());
					houseInfoDto.setJibun(getTagValue("지번", eElement).trim());
					houseInfoDto.setDeposit(getTagValue("보증금액", eElement).trim());
					houseInfoDto.setMonthly(getTagValue("월세금액", eElement).trim());
					houseInfoDto.setArea(getTagValue("전용면적", eElement).trim());
					houseInfoDto.setFloor(getTagValue("층", eElement).trim());

					String address = houseInfoDto.getDongName() + " " + houseInfoDto.getJibun() + " "
							+ houseInfoDto.getName();

					JSONObject json = new JSONObject(getCoordination(address));
					JSONArray jsonDocuments = (JSONArray) json.get("documents");
					if (jsonDocuments.length() != 0) {
						JSONObject j = (JSONObject) jsonDocuments.get(0);
						houseInfoDto.setLat(((String) j.get("y")).trim());
						houseInfoDto.setLng(((String) j.get("x")).trim());
					}
					list.add(houseInfoDto);

//				System.out.println(eElement.getTextContent());
				}
			} // for end
		} // if end
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
		}

		return list;
	}

	@Override
	public List<HouseInfoDto> getOfficeDealInDong(String dongName, String gugunCode) throws Exception {
		String urlStr = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcOffiTrade?"
				+ "serviceKey=" + SERVICE_KEY + "&LAWD_CD=" + gugunCode + "&DEAL_YMD=202110";

		// XML 파싱
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(urlStr);

		// root tag
		doc.getDocumentElement().normalize();
//		System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // Root element: response

		// 파싱할 tag로 리스트 만듬
		NodeList nList = doc.getElementsByTagName("item");
		System.out.println("파싱할 리스트 수 : " + nList.getLength());

		// 반환할 리스트 생성
		List<HouseInfoDto> list = new ArrayList<HouseInfoDto>();

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				// DB의 dongCode가 10자리 이므로 파싱한 데이터에서 시군구코드+읍면동코드를 합친 것과 비교
				if (dongName.equals(getTagValue("법정동", eElement).trim())) {
					System.out.println("!");
					HouseInfoDto houseInfoDto = new HouseInfoDto();
					houseInfoDto.setName(getTagValue("단지", eElement).trim());
					houseInfoDto.setDongName(getTagValue("법정동", eElement).trim());
					houseInfoDto.setBuildYear(Integer.parseInt(getTagValue("건축년도", eElement)));
					houseInfoDto.setDealYear((getTagValue("년", eElement)).trim());
					houseInfoDto.setDealMonth((getTagValue("월", eElement)).trim());
					houseInfoDto.setDealDay((getTagValue("일", eElement)).trim());
					houseInfoDto.setJibun(getTagValue("지번", eElement).trim());
					houseInfoDto.setPrice(getTagValue("거래금액", eElement).trim());
					houseInfoDto.setArea(getTagValue("전용면적", eElement).trim());
					houseInfoDto.setFloor(getTagValue("층", eElement).trim());

					String address = houseInfoDto.getDongName() + " " + houseInfoDto.getJibun() + " "
							+ houseInfoDto.getName();

					JSONObject json = new JSONObject(getCoordination(address));
					JSONArray jsonDocuments = (JSONArray) json.get("documents");
					if (jsonDocuments.length() != 0) {
						JSONObject j = (JSONObject) jsonDocuments.get(0);
						houseInfoDto.setLat(((String) j.get("y")).trim());
						houseInfoDto.setLng(((String) j.get("x")).trim());
					}
					list.add(houseInfoDto);

//				System.out.println(eElement.getTextContent());
				}
			} // for end
		} // if end
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
		}

		return list;
	}

	@Override
	public List<HouseInfoDto> getOfficeRentInDong(String dongName, String gugunCode) throws Exception {
		String urlStr = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcOffiRent?"
				+ "serviceKey=" + SERVICE_KEY + "&LAWD_CD=" + gugunCode + "&DEAL_YMD=202110";

		// XML 파싱
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(urlStr);

		// root tag
		doc.getDocumentElement().normalize();
//		System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // Root element: response

		// 파싱할 tag로 리스트 만듬
		NodeList nList = doc.getElementsByTagName("item");
		System.out.println("파싱할 리스트 수 : " + nList.getLength());

		// 반환할 리스트 생성
		List<HouseInfoDto> list = new ArrayList<HouseInfoDto>();

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				// DB의 dongCode가 10자리 이므로 파싱한 데이터에서 시군구코드+읍면동코드를 합친 것과 비교
				if (dongName.equals(getTagValue("법정동", eElement).trim())) {
					System.out.println("!");
					HouseInfoDto houseInfoDto = new HouseInfoDto();
					houseInfoDto.setName(getTagValue("단지", eElement).trim());
					houseInfoDto.setDongName(getTagValue("법정동", eElement).trim());
					houseInfoDto.setBuildYear(Integer.parseInt(getTagValue("건축년도", eElement)));
					houseInfoDto.setDealYear((getTagValue("년", eElement)).trim());
					houseInfoDto.setDealMonth((getTagValue("월", eElement)).trim());
					houseInfoDto.setDealDay((getTagValue("일", eElement)).trim());
					houseInfoDto.setJibun(getTagValue("지번", eElement).trim());
					houseInfoDto.setDeposit(getTagValue("보증금", eElement).trim());
					houseInfoDto.setMonthly(getTagValue("월세", eElement).trim());
					houseInfoDto.setArea(getTagValue("전용면적", eElement).trim());
					houseInfoDto.setFloor(getTagValue("층", eElement).trim());

					String address = houseInfoDto.getDongName() + " " + houseInfoDto.getJibun() + " "
							+ houseInfoDto.getName();

					JSONObject json = new JSONObject(getCoordination(address));
					JSONArray jsonDocuments = (JSONArray) json.get("documents");
					if (jsonDocuments.length() != 0) {
						JSONObject j = (JSONObject) jsonDocuments.get(0);
						houseInfoDto.setLat(((String) j.get("y")).trim());
						houseInfoDto.setLng(((String) j.get("x")).trim());
					}
					list.add(houseInfoDto);

//				System.out.println(eElement.getTextContent());
				}
			} // for end
		} // if end
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
		}

		return list;
	}

	@Override
	public List<HouseInfoDto> getAptDealInDong(String dongName, String gugunCode) throws Exception {
		String urlStr = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?"
				+ "serviceKey=" + SERVICE_KEY + "&LAWD_CD=" + gugunCode + "&DEAL_YMD=202110&pageNo=1&numOfRows=100";

		// XML 파싱
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(urlStr);

		// root tag
		doc.getDocumentElement().normalize();
//		System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // Root element: response

		// 파싱할 tag로 리스트 만듬
		NodeList nList = doc.getElementsByTagName("item");
		System.out.println("파싱할 리스트 수 : " + nList.getLength());

		// 반환할 리스트 생성
		List<HouseInfoDto> list = new ArrayList<HouseInfoDto>();

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				// DB의 dongCode가 10자리 이므로 파싱한 데이터에서 시군구코드+읍면동코드를 합친 것과 비교
				if (dongName.equals(getTagValue("법정동", eElement).trim())) {
					System.out.println("!");
					HouseInfoDto houseInfoDto = new HouseInfoDto();
					houseInfoDto.setName(getTagValue("아파트", eElement).trim());
					houseInfoDto.setDongName(getTagValue("법정동", eElement).trim());
					houseInfoDto.setBuildYear(Integer.parseInt(getTagValue("건축년도", eElement)));
					houseInfoDto.setDealYear((getTagValue("년", eElement)).trim());
					houseInfoDto.setDealMonth((getTagValue("월", eElement)).trim());
					houseInfoDto.setDealDay((getTagValue("일", eElement)).trim());
					houseInfoDto.setJibun(getTagValue("지번", eElement).trim());
					houseInfoDto.setPrice(getTagValue("거래금액", eElement).trim());
					houseInfoDto.setArea(getTagValue("전용면적", eElement).trim());
					houseInfoDto.setFloor(getTagValue("층", eElement).trim());

					String address = houseInfoDto.getDongName() + " " + houseInfoDto.getJibun() + " "
							+ houseInfoDto.getName();

					JSONObject json = new JSONObject(getCoordination(address));
					JSONArray jsonDocuments = (JSONArray) json.get("documents");
					if (jsonDocuments.length() != 0) {
						JSONObject j = (JSONObject) jsonDocuments.get(0);
						houseInfoDto.setLat(((String) j.get("y")).trim());
						houseInfoDto.setLng(((String) j.get("x")).trim());
					}
					list.add(houseInfoDto);

//				System.out.println(eElement.getTextContent());
				}
			} // for end
		} // if end
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
		}

		return list;
	}

	@Override
	public List<HouseInfoDto> getAlliDealInDong(String dongName, String gugunCode) throws Exception {
		String urlStr = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcRHTrade?"
				+ "serviceKey=" + SERVICE_KEY + "&LAWD_CD=" + gugunCode + "&DEAL_YMD=202110";

		// XML 파싱
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(urlStr);

		// root tag
		doc.getDocumentElement().normalize();
//		System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // Root element: response

		// 파싱할 tag로 리스트 만듬
		NodeList nList = doc.getElementsByTagName("item");
		System.out.println("파싱할 리스트 수 : " + nList.getLength());

		// 반환할 리스트 생성
		List<HouseInfoDto> list = new ArrayList<HouseInfoDto>();

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				// DB의 dongCode가 10자리 이므로 파싱한 데이터에서 시군구코드+읍면동코드를 합친 것과 비교
				if (dongName.equals(getTagValue("법정동", eElement).trim())) {
					System.out.println("!");
					HouseInfoDto houseInfoDto = new HouseInfoDto();
					houseInfoDto.setName(getTagValue("연립다세대", eElement).trim());
					houseInfoDto.setDongName(getTagValue("법정동", eElement).trim());
					houseInfoDto.setBuildYear(Integer.parseInt(getTagValue("건축년도", eElement)));
					houseInfoDto.setDealYear((getTagValue("년", eElement)).trim());
					houseInfoDto.setDealMonth((getTagValue("월", eElement)).trim());
					houseInfoDto.setDealDay((getTagValue("일", eElement)).trim());
					houseInfoDto.setJibun(getTagValue("지번", eElement).trim());
					houseInfoDto.setPrice(getTagValue("거래금액", eElement).trim());
					houseInfoDto.setArea(getTagValue("전용면적", eElement).trim());
					houseInfoDto.setFloor(getTagValue("층", eElement).trim());

					String address = houseInfoDto.getDongName() + " " + houseInfoDto.getJibun() + " "
							+ houseInfoDto.getName();

					JSONObject json = new JSONObject(getCoordination(address));
					JSONArray jsonDocuments = (JSONArray) json.get("documents");
					if (jsonDocuments.length() != 0) {
						JSONObject j = (JSONObject) jsonDocuments.get(0);
						houseInfoDto.setLat(((String) j.get("y")).trim());
						houseInfoDto.setLng(((String) j.get("x")).trim());
					}
					list.add(houseInfoDto);

//				System.out.println(eElement.getTextContent());
				}
			} // for end
		} // if end
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
		}

		return list;
	}

	@Override
	public List<HouseInfoDto> getAptRentInDong(String dongName, String gugunCode) throws Exception {
		String urlStr = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?"
				+ "serviceKey=" + SERVICE_KEY + "&LAWD_CD=" + gugunCode + "&DEAL_YMD=202110";

		// XML 파싱
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(urlStr);

		// root tag
		doc.getDocumentElement().normalize();
//		System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // Root element: response

		// 파싱할 tag로 리스트 만듬
		NodeList nList = doc.getElementsByTagName("item");
		System.out.println("파싱할 리스트 수 : " + nList.getLength());

		// 반환할 리스트 생성
		List<HouseInfoDto> list = new ArrayList<HouseInfoDto>();

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				// DB의 dongCode가 10자리 이므로 파싱한 데이터에서 시군구코드+읍면동코드를 합친 것과 비교
				if (dongName.equals(getTagValue("법정동", eElement).trim())) {
					System.out.println("!");
					HouseInfoDto houseInfoDto = new HouseInfoDto();
					houseInfoDto.setName(getTagValue("아파트", eElement).trim());
					houseInfoDto.setDongName(getTagValue("법정동", eElement).trim());
					houseInfoDto.setBuildYear(Integer.parseInt(getTagValue("건축년도", eElement)));
					houseInfoDto.setDealYear((getTagValue("년", eElement)).trim());
					houseInfoDto.setDealMonth((getTagValue("월", eElement)).trim());
					houseInfoDto.setDealDay((getTagValue("일", eElement)).trim());
					houseInfoDto.setJibun(getTagValue("지번", eElement).trim());
					houseInfoDto.setDeposit(getTagValue("보증금액", eElement).trim());
					houseInfoDto.setMonthly(getTagValue("월세금액", eElement).trim());
					houseInfoDto.setArea(getTagValue("전용면적", eElement).trim());
					houseInfoDto.setFloor(getTagValue("층", eElement).trim());

					String address = houseInfoDto.getDongName() + " " + houseInfoDto.getJibun() + " "
							+ houseInfoDto.getName();

					JSONObject json = new JSONObject(getCoordination(address));
					JSONArray jsonDocuments = (JSONArray) json.get("documents");
					if (jsonDocuments.length() != 0) {
						JSONObject j = (JSONObject) jsonDocuments.get(0);
						houseInfoDto.setLat(((String) j.get("y")).trim());
						houseInfoDto.setLng(((String) j.get("x")).trim());
					}
					list.add(houseInfoDto);

//				System.out.println(eElement.getTextContent());
				}
			} // for end
		} // if end
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
		}

		return list;
	}

}
