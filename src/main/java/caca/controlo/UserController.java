package caca.controlo;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import caca.model.User;
import caca.servicio.UserService;

@Controller
@RequestMapping("/caca")
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * Request mapping for user
	 */
	@RequestMapping(value = "users", method = RequestMethod.GET)
	public ModelAndView getUsersView() {
		ModelAndView mv = new ModelAndView("usersView");
		mv.addObject("usersModel", userService.findAll());
		// XXX:
		// https://stackoverflow.com/questions/11666472/pass-custom-object-to-arrays-aslist
		// User[] combinedPersonObjs = { new User(10L, "uno", "unop", "unoe"), new
		// User(11L, "dos", "dosp", "dose") };
		// mv.addObject("usersModel", Arrays.asList(combinedPersonObjs));
		return mv;
	}

	/**
	 * Rest web service
	 */
	@RequestMapping(value = "/usersList", method = RequestMethod.GET)
	public @ResponseBody List<User> getUsersRest() {
		/*
		 * User[] combinedPersonObjs = { new User(20L, "uno", "unop", "unoe"), new
		 * User(21L, "dos", "dosp", "dose") };
		 * 
		 * return Arrays.asList(combinedPersonObjs);
		 */
		return userService.findAll();
	}
}
