package com.library.DaoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.library.Dao.UserDao;
import com.library.Entity.User;
import com.library.Utility.UserResponse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public UserResponse saveUser(User user) throws Exception {
		if (!isUserExist(user.getUserName())) {
			entityManager.persist(user);
			return new UserResponse.Builder()
	                .setMessage("User registered successfully!")
	                .setSuccess(true)
	                .setUser(user)
	                .build();
		} else {
			return new UserResponse.Builder()
	                .setMessage("User with username '" + user.getUserName() + "' already exists.")
	                .setSuccess(false)
	                .build();
		}

	}

	@Override
	public User loginUser(String userName, String password) {
		try {
			String query = "SELECT u FROM User u WHERE u.userName = :userName AND u.password = :password";
			TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
			typedQuery.setParameter("userName", userName);
			typedQuery.setParameter("password", password);
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public boolean isUserExist(String userName) {
		try {
			String query = "SELECT u FROM User u WHERE u.userName = :userName";
			TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
			typedQuery.setParameter("userName", userName);

			List<User> users = typedQuery.getResultList();
			if(users.size() == 0) {
				return false;
			}
			return true;

		} catch (NoResultException e) {
			return true;
		}
	}

}
