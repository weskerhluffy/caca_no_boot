package caca.dao;

import caca.model.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {

	User findByLogin(String login);
}
