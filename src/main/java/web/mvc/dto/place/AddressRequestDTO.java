package web.mvc.dto.place;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDTO {
    private String keyword;
    private int currentPage;
    private int countPerPage;
    private String resultType;
}
