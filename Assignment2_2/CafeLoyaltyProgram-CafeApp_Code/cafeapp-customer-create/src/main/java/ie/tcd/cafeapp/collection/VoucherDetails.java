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
public class VoucherDetails {
	private String voucherCode;
	private String validTill;
	private Double voucherAmount;
}
