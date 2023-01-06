package com.sparta.jpahibernate.dao.concretes;

import com.sparta.jpahibernate.dao.interfaces.UserDAO;
import com.sparta.jpahibernate.dto.EmployeeDTO;
import com.sparta.jpahibernate.dto.UserDTO;
import com.sparta.jpahibernate.entities.Employee;
import com.sparta.jpahibernate.entities.User;
import com.sparta.jpahibernate.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserDAOImpl implements UserDAO {
    @Autowired
    private final UserRepository userRepo;

    @Autowired
    public UserDAOImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public long count() {
        return userRepo.count();
    }

    @Override
    public void delete(UserDTO dto) {
        Optional<User> optEmp = userRepo.findById(dto.getId());
        optEmp.ifPresent(userRepo::delete);
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepo.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(new UserDTO(user.getId(), user.getEmail(), user.getPassword(),
                    user.getKey(), user.getLastUpdate(), user.getLevel()));
        }
        return userDTOS;
    }

    @Override
    public void save(UserDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setKey(dto.getKey());
        user.setLastUpdate(Instant.now());
        if(dto.getLevel() != null) {
            user.setLevel(dto.getLevel());
        }
        userRepo.save(user);
    }

    @Override
    public void createUser(UserDTO dto) {
        Optional<User> optUser = userRepo.findById(dto.getId());
        if(optUser.isEmpty()) {
            User user = new User();
            user.setId(dto.getId());
            user.setEmail(String.valueOf(user.getEmail().matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")));
            user.setPassword(String.valueOf(dto.getPassword().matches("[0-9]+.*[a-zA-Z]")));
            user.setKey(dto.getKey());
            user.setLastUpdate(Instant.now());
            user.setLevel(dto.getLevel());
            userRepo.save(user);
            //this.save(dto);
        }
//        User newUser = new User();
//        newUser.setId(Long.valueOf(0));
//        newUser.setEmail(String.valueOf(newUser.getEmail().matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")));
//        newUser.setPassword(String.valueOf(newUser.getPassword().matches("[0-9]+.*[a-zA-Z]")));
//        newUser.setLastUpdate(Instant.now());
//        System.out.println(newUser);
//        User result = userRepo.save(newUser);
//        System.out.println(result);
    }

    @Override
    public void updateUser(long id, UserDTO newInfo) {
        Optional<User> optUser = userRepo.findById(id);
        if (optUser.isPresent()) {
            User userFound = optUser.get();
            userFound.setId((long)0);
            userFound.setEmail(newInfo.getEmail());
            userFound.setPassword(newInfo.getPassword());
            userFound.setKey(newInfo.getKey());
            userFound.setLastUpdate(Instant.now());
            userFound.setLevel(newInfo.getLevel());
            userRepo.save(userFound);
        }
    }
    @Override
    public Optional<UserDTO> findById(long id) {
        Optional<User> result = userRepo.findById((long) 0);
        if (result.isPresent()) {
            User user = result.get();
            System.out.println(user);
        }
        return null;
    }

    public UserDTO findByKey(String key) {
        Optional<User> result = userRepo.findByKey(key);
        if(result.isPresent()) {
            User user = result.get();
            UserDTO userDto = new UserDTO(user.getId(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getKey(),
                    user.getLastUpdate(),
                    user.getLevel());
            return userDto;
        }
        return null;
    }

    public Optional<UserDTO> findByEmail(String email) {
        Optional<User> result = userRepo.findByEmail(email);
        if(result.isPresent()) {
            User user = result.get();
            UserDTO userDto = new UserDTO(user.getId(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getKey(),
                    user.getLastUpdate(),
                    user.getLevel());
            return Optional.of(userDto);
        }
        return Optional.empty();
    }

    public boolean isBasic(String apiKey) {
        Optional<User> userOpt = userRepo.findByKey(apiKey);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getLevel().equalsIgnoreCase("basic");
        }
        return false;
    }

    public boolean isUpdate(String apiKey) {
        Optional<User> userOpt = userRepo.findByKey(apiKey);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getLevel().equalsIgnoreCase("update");
        }
        return false;
    }

    public boolean isAdmin(String apiKey) {
        Optional<User> userOpt = userRepo.findByKey(apiKey);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getLevel().equalsIgnoreCase("admin");
        }
        return false;
    }



    @Override
    public String getEmail(UserDTO dto) {
        return dto.getEmail();
    }

    @Override
    public String getPassword(UserDTO dto) {
        return dto.getPassword();
    }

    @Override
    public String getKey(UserDTO dto) {
        return dto.getKey();
    }

    @Override
    public String getLevel(UserDTO dto) {
        return dto.getLevel();
    }

    @Override
    public String toString() {
        return "UserDAOImpl{" +
                "userRepo=" + userRepo +
                '}';
    }
}
