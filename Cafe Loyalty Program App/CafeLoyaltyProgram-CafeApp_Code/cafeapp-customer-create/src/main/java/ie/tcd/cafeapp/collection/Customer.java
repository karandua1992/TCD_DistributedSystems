package ie.tcd.cafeapp.collection;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Document(collection = "customer")
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer {
	@Id
	private String membershipId;
	private String firstName;
	private String lastName;
	private Integer rewardPoints;
	
	private LoginDetails loginCredentials;
	
	private List<TransactionHistory> transactionHistory;
	
	private List<VoucherDetails> voucher;

	private Session sessionDetails;

}
