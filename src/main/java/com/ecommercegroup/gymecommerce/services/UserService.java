package com.ecommercegroup.gymecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommercegroup.gymecommerce.dto.UserDto;
import com.ecommercegroup.gymecommerce.entities.User;
import com.ecommercegroup.gymecommerce.repositories.UserRepository;
import com.ecommercegroup.gymecommerce.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public User save(UserDto userDto) {
		User user = fromDto(userDto);
		return userRepository.save(user);
	}
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Id de usuário não encontrado"));
	}
	
	public List<User> findByName(String userName) {
		List<User> listUsers = userRepository.findByName(userName);
		if (listUsers != null ) {
			return listUsers;
		}
		throw new ObjectNotFoundException("Nome de usuário não encontrado");
	}
	
	@Transactional
	public User update(User user) {
		User updateUser = userRepository.findById(user.getId()).get();
		updateData(updateUser, user);
		return userRepository.save(updateUser);
	}
	
	private void updateData(User updateUser, User user) {
		updateUser.setName(user.getName());
		updateUser.setEmail(user.getEmail());	
	}

	@Transactional
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}
	
	public User fromDto(UserDto userDto) {
		return new User(userDto.getId(), userDto.getName(), userDto.getPhone(), userDto.getEmail(), userDto.getBirthdate(), userDto.getCpf(), userDto.getPassword());
	}
}
