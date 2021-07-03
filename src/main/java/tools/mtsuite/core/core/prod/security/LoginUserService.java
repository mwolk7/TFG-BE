package tools.mtsuite.core.core.prod.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tools.mtsuite.core.common.model.User;


@Service("userDetailsService")
public class LoginUserService implements UserDetailsService {

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;
		try {
			 user = null;
			if (user == null) {
				throw new UsernameNotFoundException("Invalid username or password.");
			}
		} catch (Exception e) {
			throw new UsernameNotFoundException("Invalid username or password.");
 		}
		List<GrantedAuthority> setAuths = new ArrayList<GrantedAuthority>();

		// Build user's authorities

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), setAuths);

	}


}
