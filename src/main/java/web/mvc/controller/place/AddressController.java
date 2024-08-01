package web.mvc.controller.place;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.mvc.dto.place.AddressRequestDTO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
@Slf4j
public class AddressController {

    @Value("${address.key}")
    private String addressKey;

    @PostMapping(value = "/find")
    public void getAddrApi(@RequestBody AddressRequestDTO addressRequest, HttpServletResponse response) throws Exception {
        // 요청 변수 추출
        String currentPage = String.valueOf(addressRequest.getCurrentPage());
        String countPerPage = String.valueOf(addressRequest.getCountPerPage());
        String resultType = addressRequest.getResultType();
        String keyword = addressRequest.getKeyword();
        // OPEN API 호출 URL 정보 설정
        String apiUrl = "https://business.juso.go.kr/addrlink/addrLinkApi.do?currentPage=" + currentPage +
                "&countPerPage=" + countPerPage +
                "&keyword=" + URLEncoder.encode(keyword, "UTF-8") +
                "&confmKey=" + addressKey +
                "&resultType=" + resultType;

        URL url = new URL(apiUrl);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String tempStr;
        while ((tempStr = br.readLine()) != null) {
            sb.append(tempStr);
        }
        br.close();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(sb.toString());
    }
}
