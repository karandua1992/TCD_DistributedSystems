package ie.tcd.cafeapp.collection;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponsePojo {
	
	private String responseMessage;
	private String membershipId;
	private Integer rewardPoints;
	private List<VoucherDetails> voucher;
	private String username;
	
}
