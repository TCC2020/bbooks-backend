package br.edu.ifsp.spo.bulls.usersApi.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.dto.ProfileTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProfileBeanUtilTest {

	@Autowired
	ProfileBeanUtil bean;
	
	@Test
	void testToUser() {
		ProfileTO profileTO = new ProfileTO(1, "nome completo", "pais", "sao paulo", "SP", "10/10/1998", new User());
		
		Profile profileResultado = bean.toProfile(profileTO);
		
		assertEquals(profileTO.getBirthDate(), profileResultado.getBirthDate());
		assertEquals(profileTO.getCity(), profileResultado.getCity());
		assertEquals(profileTO.getCountry(), profileResultado.getCountry());
		assertEquals(profileTO.getFullName(), profileResultado.getFullName());
		assertEquals(profileTO.getId(), profileResultado.getId());
		assertEquals(profileTO.getState(), profileResultado.getState());
		assertEquals(profileTO.getUser(), profileResultado.getUser());
		
	}
	
	@Test
	void testToUserTO() {
	Profile profile = new Profile(1, "nome completo", "pais", "sao paulo", "SP", "10/10/1998", new User());

		ProfileTO profileTO = bean.toProfileTO(profile);
		
		assertEquals(profile.getBirthDate(), profileTO.getBirthDate());
		assertEquals(profile.getCity(), profileTO.getCity() );
		assertEquals(profile.getCountry(), profileTO.getCountry());
		assertEquals(profile.getFullName(), profileTO.getFullName());
		assertEquals(profile.getId(), profileTO.getId());
		assertEquals(profile.getState(), profileTO.getState());
		assertEquals(profile.getUser(),profileTO.getUser());
	}
	
	@Test
	void toHashUserTO() {
		Profile profile0 = new Profile(1, "nome completo1", "pais1", "sao paulo1", "SP1", "10/10/19981", new User());
		Profile profile1 = new Profile(2, "nome completo2", "pais2", "sao paulo2", "SP2", "10/10/19982", new User());

		HashSet<Profile> listaProfiles = new HashSet<Profile>();
		listaProfiles.add(profile0);
		listaProfiles.add(profile1);
		
		HashSet<ProfileTO> listaTOusers = bean.toProfileTO(listaProfiles);
		
		ProfileTO profile0TO = null, profile1TO = null;
		
		int i = 0 ;
		 for (ProfileTO userTO: listaTOusers ) {
			 switch(i) {
			 case 0:
				 profile0TO = userTO;
				 break;
			 case 1:
				 profile1TO = userTO;
				 break;
			 default:
			 }
			 i++;
		 }
		 
		assertEquals(profile0.getBirthDate(), profile0TO.getBirthDate());
		assertEquals(profile0.getCity(), profile0TO.getCity() );
		assertEquals(profile0.getCountry(), profile0TO.getCountry());
		assertEquals(profile0.getFullName(), profile0TO.getFullName());
		assertEquals(profile0.getId(), profile0TO.getId());
		assertEquals(profile0.getState(), profile0TO.getState());
		assertEquals(profile0.getUser(),profile0TO.getUser());
	 
		assertEquals(profile1.getBirthDate(), profile1TO.getBirthDate());
		assertEquals(profile1.getCity(), profile1TO.getCity() );
		assertEquals(profile1.getCountry(), profile1TO.getCountry());
		assertEquals(profile1.getFullName(), profile1TO.getFullName());
		assertEquals(profile1.getId(), profile1TO.getId());
		assertEquals(profile1.getState(), profile1TO.getState());
		assertEquals(profile1.getUser(),profile1TO.getUser());

		
	}
}
