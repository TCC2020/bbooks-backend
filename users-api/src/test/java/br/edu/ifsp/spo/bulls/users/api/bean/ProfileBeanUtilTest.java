package br.edu.ifsp.spo.bulls.users.api.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.edu.ifsp.spo.bulls.common.api.domain.Profile;
import br.edu.ifsp.spo.bulls.common.api.domain.User;
import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProfileBeanUtilTest {

	@Autowired
	private ProfileBeanUtil bean;
	
	@Test
	void testToUser() {
		ProfileTO profileTO = new ProfileTO(1, "nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998");
		
		Profile profileResultado = bean.toProfile(profileTO);
		
		assertEquals(profileTO.getBirthDate(), profileResultado.getBirthDate());
		assertEquals(profileTO.getCity(), profileResultado.getCity());
		assertEquals(profileTO.getCountry(), profileResultado.getCountry());
		assertEquals(profileTO.getName(), profileResultado.getName());
		assertEquals(profileTO.getLastName(), profileResultado.getLastName());
		assertEquals(profileTO.getId(), profileResultado.getId());
		assertEquals(profileTO.getState(), profileResultado.getState());
		
	}
	
	@Test
	void testToUserTO() {
	Profile profile = new Profile(1, "nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998", new User());

		ProfileTO profileTO = bean.toProfileTO(profile);
		
		assertEquals(profile.getBirthDate(), profileTO.getBirthDate());
		assertEquals(profile.getCity(), profileTO.getCity() );
		assertEquals(profile.getCountry(), profileTO.getCountry());
		assertEquals(profileTO.getName(), profileTO.getName());
		assertEquals(profileTO.getLastName(), profileTO.getLastName());
		assertEquals(profile.getId(), profileTO.getId());
		assertEquals(profile.getState(), profileTO.getState());
	}
}
