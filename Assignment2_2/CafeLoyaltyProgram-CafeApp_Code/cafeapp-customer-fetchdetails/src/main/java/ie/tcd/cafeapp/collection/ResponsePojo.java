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
	private String firstName;
	private String lastName;
	private Integer rewardPoints;
	private String username;
	
	private List<TransactionHistory> transactionHistory;
	
	private List<VoucherDetails> voucher;

}
