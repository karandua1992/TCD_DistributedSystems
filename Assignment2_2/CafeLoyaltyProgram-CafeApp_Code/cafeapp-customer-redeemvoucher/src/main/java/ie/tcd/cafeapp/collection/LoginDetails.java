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
public class LoginDetails {
	
	private String username;
	private String password;

}
