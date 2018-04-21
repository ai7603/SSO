package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cas.server.Constants;
import domain.Mapping;
import domain.ServiceTicket;
import domain.SessionStorage;
import domain.User;

public class SSOController extends BaseController{
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(String LOCAL_SERVICE, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(Constants.CAS_TGS)) {
					// 为简化设计TGS=ST
					String CAS_TGS = cookie.getValue();
					String CAS_ST = CAS_TGS;
					ServiceTicket st = (ServiceTicket) this.getFirst(
							"from ServiceTicket where st=?", CAS_ST);
					if (st != null) {
						mv.setView(new RedirectView(LOCAL_SERVICE + "?"
								+ Constants.CAS_ST + "=" + CAS_ST + "&"
								+ Constants.LOCAL_SERVICE + "=" + LOCAL_SERVICE));
						return mv;
					}
					// 有cookie
				}
			}
		}
		mv.addObject(Constants.LOCAL_SERVICE, LOCAL_SERVICE);
		mv.setViewName("/login");
		return mv;
	}
	
	@Transactional
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(String id, String pwd, String LOCAL_SERVICE,
			HttpSession session, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		User user = (User) this.getFirst("from User where id=? and pwd=?", id,
				pwd);
		if (user != null) {
			session.setAttribute("user", user);
			String CAS_ST = user.getId() + System.nanoTime();
			// 为简化设计TGS=ST
			Cookie cookie = new Cookie(Constants.CAS_TGS, CAS_ST);
			cookie.setMaxAge(-1);
			response.addCookie(cookie);
			ServiceTicket st = new ServiceTicket();
			st.setSt(CAS_ST);
			st.setUser(user);
			this.save(st);
			if (LOCAL_SERVICE != null && !LOCAL_SERVICE.equals("")) {

				mv.setView(new RedirectView(LOCAL_SERVICE + "?"
						+ Constants.CAS_ST + "=" + CAS_ST + "&"
						+ Constants.LOCAL_SERVICE + "=" + LOCAL_SERVICE));
			} else
				mv.setViewName("redirect:/main.do");
		} else {
			mv.setViewName("/login");
		}
		return mv;
	}

	@Transactional
	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public void getUser(String CAS_ST, String host, String app,
			String LOCAL_SERVICE, String sessionId, HttpServletResponse response)
			throws IOException {
		SessionStorage sessionStorage = new SessionStorage();
		sessionStorage.setLocalService(LOCAL_SERVICE);
		sessionStorage.setSt(CAS_ST);
		sessionStorage.setSessionId(sessionId);
		this.save(sessionStorage);
		ServiceTicket st = (ServiceTicket) this.getFirst(
				"from ServiceTicket where st=?", CAS_ST);
		Mapping mapping = (Mapping) this.getFirst(
				"from Mapping where host=? and app=? and casUser=?", host, app,
				st.getUser());
		response.getWriter().println(mapping.getLocalUser());
	}

	@Transactional
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		ModelAndView mv = new ModelAndView();
		session.invalidate();
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(Constants.CAS_TGS)) {
					String CAS_TGS = cookie.getValue();
					String CAS_ST = CAS_TGS;
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					List<SessionStorage> list = this.find(
							"from SessionStorage where st=?", CAS_ST);
					try {
						for (SessionStorage item : list) {
							URL url = new URL(item.getLocalService()
									+ "?logout=true&sessionId="
									+ item.getSessionId());
							BufferedReader reader = new BufferedReader(
									new InputStreamReader(url.openStream()));
							String line = reader.readLine();
							reader.close();
						}
					} catch (Exception e) {
						e.printStackTrace();

					}
					this.bulkUpdate("delete from SessionStorage where st=?",
							CAS_ST);
					this.bulkUpdate("delete from ServiceTicket where st=?",
							CAS_ST);
				}
			}
		}

		mv.setViewName("redirect:/login.do");
		return mv;
	}
}
