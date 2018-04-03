package caca.servicio;

import org.springframework.stereotype.Service;

import caca.model.User;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements UserService {

}
