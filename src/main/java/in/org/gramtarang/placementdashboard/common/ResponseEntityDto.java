package in.org.gramtarang.placementdashboard.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseEntityDto<T> {
    private String message;
    private boolean status;
    private List<T> listOfData;
    private T data;
}
