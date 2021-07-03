package tools.mtsuite.core.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tools.mtsuite.core.common.dao.IUserDao;
import tools.mtsuite.core.common.model.User;
import tools.mtsuite.core.core.context.Mapeador;
import tools.mtsuite.core.core.dto.*;
import tools.mtsuite.core.core.excepctions.BadRequestException;

import javax.transaction.Transactional;

@Service
@Transactional
public class CommonService {

	@Autowired
	private Mapeador mapeador;

	@Autowired
	private IUserDao iUserDao;

	/**
	 * Return login user
	 * @return
	 */
	public String getCurrentLoginUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (String) auth.getPrincipal();
	}

	/**
	 * Return user from DB
	 * @return
	 */
	public User getCurrentUser() {
		try {
			return  iUserDao.findByUsername(this.getCurrentLoginUser());
		}catch (Exception e){
			try {
				Thread.sleep(5 * 1000);
				return  iUserDao.findByUsername(this.getCurrentLoginUser());
			}catch (Exception ee){
				try {
					Thread.sleep(5 * 1000);
					return  iUserDao.findByUsername(this.getCurrentLoginUser());
				}catch (Exception e3e){
					throw new BadRequestException("004","user not yet synced");
				}
			}
		}
	}
}
