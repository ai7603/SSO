package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

public class MainController {
	@RequestMapping(value="/main",method=RequestMethod.GET)
	public ModelAndView main()
	{
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/main");
		return mv;
	}
}
