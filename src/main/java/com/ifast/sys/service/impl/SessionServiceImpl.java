package com.ifast.sys.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifast.sys.domain.UserDO;
import com.ifast.sys.domain.UserOnline;
import com.ifast.sys.service.SessionService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public class SessionServiceImpl implements SessionService {
	@Autowired
	private SessionDAO sessionDAO;

	@Override
	public List<UserOnline> list(String name) {
		List<UserOnline> list = new ArrayList<>();
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		for (Session session : sessions) {
			if(session==null) continue;
			UserOnline userOnline = new UserOnline();
			UserDO userDO = new UserDO();
			SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
			if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
				continue;
			} else {
				principalCollection = (SimplePrincipalCollection) session
						.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
				userDO = (UserDO) principalCollection.getPrimaryPrincipal();
				userOnline.setUsername(userDO.getUsername());
			}
			userOnline.setId((String) session.getId());
			userOnline.setHost(session.getHost());
			userOnline.setStartTimestamp(session.getStartTimestamp());
			userOnline.setLastAccessTime(session.getLastAccessTime());
			userOnline.setTimeout(session.getTimeout());
			if(StringUtils.isBlank(name) || userOnline.getUsername().contains(name)) list.add(userOnline);
		}
		return list;
	}

	@Override
	public Collection<Session> sessionList() {
		return sessionDAO.getActiveSessions();
	}

	@Override
	public boolean forceLogout(String sessionId) {
		Session session = sessionDAO.readSession(sessionId);
		sessionDAO.delete(session);
		return true;
	}
}
