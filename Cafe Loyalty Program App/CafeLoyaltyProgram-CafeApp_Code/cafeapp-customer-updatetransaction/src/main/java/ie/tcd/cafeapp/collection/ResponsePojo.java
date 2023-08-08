package ie.tcd.cafeapp.collection;

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
	private Integer earnedRewardPoints;
	private Integer rewardPointBalance;
}
