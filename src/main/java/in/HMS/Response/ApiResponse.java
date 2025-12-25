package in.HMS.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private String status;   // success / failure
    private String message;  // readable message
    private T data;          // actual response data
}
