package com.stockmarket.company.service;

import com.stockmarket.company.entity.MyUser;
import com.stockmarket.company.exceptions.BadRequestException;
import com.stockmarket.company.exceptions.InternalServerError;
import com.stockmarket.company.exceptions.RecordNotFoundException;
import com.stockmarket.company.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserServiceImpl implements MyUserService {
    @Autowired
    private MyUserRepository myUserRepository;

    @Override
    public Page<MyUser> listUsers(Pageable pageable) {
        return myUserRepository.findAll(pageable);
    }

    @Override
    public String confirmUser(String username) {
        try {
            Optional<MyUser> queryMyUser = myUserRepository.findByUsername(username);
            if (!queryMyUser.isPresent()) {
                throw new RecordNotFoundException();
            }
            MyUser myUser = queryMyUser.get();
            myUser.setConfirmed(true);
            myUserRepository.save(myUser);
            return "Email Id Verified!!";
        }
        catch (RecordNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!!");
        }
    }

    @Override
    public MyUser getUser(String username) {
        try {
            Optional<MyUser> queryMyUser = myUserRepository.findByUsername(username);
            if (!queryMyUser.isPresent()) {
                throw new RecordNotFoundException();
            }
            return queryMyUser.get();
        }
        catch (RecordNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!!");
        }
    }

    @Override
    public MyUser updateUser(String username, MyUser userUpdate) {
        try {
            Optional<MyUser> queryMyUser = myUserRepository.findByUsername(username);
            if (!queryMyUser.isPresent()) {
                throw new RecordNotFoundException();
            }
            MyUser myUser = queryMyUser.get();
            myUser.setAdmin(userUpdate.getAdmin());
            myUser.setConfirmed(userUpdate.getConfirmed());

            String updateUserName = userUpdate.getUsername();
            if(!updateUserName.equals(myUser.getUsername())) {
                Optional<MyUser> queryMyUserName = myUserRepository.findByUsername(updateUserName);
                if (queryMyUserName.isPresent()) {
                    throw new BadRequestException("Requested Username is already in use!");
                }
            }
            myUser.setUsername(userUpdate.getUsername());
            myUser.setMobile(userUpdate.getMobile());

            String updateEmail = userUpdate.getEmail();
            if(!updateEmail.equals(myUser.getEmail())) {
                Optional<MyUser> queryMyUserName = myUserRepository.findByEmail(updateEmail);
                if (queryMyUserName.isPresent()) {
                    throw new BadRequestException("Requested Email is already in use!");
                }
            }
            myUser.setEmail(userUpdate.getEmail());
            myUser.setPassword(userUpdate.getPassword());
            myUser.setRole(userUpdate.getRole());
            myUserRepository.save(myUser);
            return queryMyUser.get();
        }
        catch (RecordNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!!");
        }
    }



    @Override
    public void removeUser(Long myUserId) {
        try {
            Optional<MyUser> queryMyUser = myUserRepository.findById(myUserId);
            if (!queryMyUser.isPresent()) {
                throw new RecordNotFoundException();
            }
            myUserRepository.deleteById(myUserId);
        }
        catch (RecordNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!!");
        }
    }
}
