package ie.tcd.cafeapp.collection;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponsePojo {
	
	private String responseMessage;
	private String membershipId;
	private List<TransactionHistory> transactionHistory;
	private String username;
}
