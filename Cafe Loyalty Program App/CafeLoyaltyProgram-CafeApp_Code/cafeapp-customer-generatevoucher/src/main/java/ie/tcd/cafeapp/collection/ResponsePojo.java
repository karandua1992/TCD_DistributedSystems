package ie.tcd.cafeapp.collection;

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
	private Integer remainingRewardPoints;
	private VoucherDetails generatedVoucher;
	private String username;

}
