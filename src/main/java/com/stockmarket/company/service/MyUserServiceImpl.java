package com.stockmarket.company.service;

import com.stockmarket.company.entity.MyUser;
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

//    @Override
//    public MyUser newUser(MyUser myUser) {
//        try {
//            Optional<MyUser> queryMyUser = myUserRepository.findByEmail(myUser.getEmail());
//            if (queryMyUser.isPresent()) {
//                System.out.println(queryMyUser.get().toString());
//                throw new BadRequestException("Provided email is already in use!");
//            }
//
//            String email = myUser.getEmail();
//
//            if(myUser.getUsername().equals("")) {
//                throw new BadRequestException("User Name cannot be empty!");
//            }
//            if(myUser.getEmail().equals("")) {
//                throw new BadRequestException("Email Address cannot be empty!");
//            }
//            if(myUser.getPassword().equals("")) {
//                throw new BadRequestException("Password cannot be empty!");
//            }
//            if(myUser.getRole().equals("")) {
//                throw new BadRequestException("Role cannot be empty!");
//            }
//
//            myUser.setConfirmed(false);
//            String userRole = myUser.getRole();
//            if(userRole.equals("admin"))
//                myUser.setAdmin(true);
//            else myUser.setAdmin(false);
//            sendEmailForConfirmation(email);
//            MyUser newUser = myUserRepository.save(myUser);
//
//            // ToDo: EMAIL with other smtp service provider
//            return newUser;
//        }
//        catch (BadRequestException e) {
//            throw e;
//        }
//        catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new InternalServerError(e.getMessage());
//        }
//
//    }

//    @Override
//    public MyUser loginMyUser(MyUser myUser) {
//        try {
//            Optional<MyUser> queryMyUser = myUserRepository.findByEmail(myUser.getEmail());
//            if (!queryMyUser.isPresent()) {
//                System.out.println(myUser.getEmail());
//                throw new BadRequestException("Invalid Credentials");
//            }
//            MyUser myUserFromDb = queryMyUser.get();
//            // todo: bcyryting, jwt
//            String dbPassword = myUserFromDb.getPassword();
//            String password = myUser.getPassword();
//
//            if(!password.equals(dbPassword)) {
//                throw new BadRequestException("Invalid Credentials!");
//            }
//
//            return queryMyUser.get();
//        }
//        catch (RecordNotFoundException e) {
//            throw e;
//        }
//        catch(BadRequestException e) {
//            throw e;
//        }
//        catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new InternalServerError("Something went wrong!!");
//        }
//    }

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
    public MyUser getUser(Long myUserId) {
        try {
            Optional<MyUser> queryMyUser = myUserRepository.findById(myUserId);
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
    public MyUser updateUser(Long myUserId, MyUser userUpdate) {
        try {
            Optional<MyUser> queryMyUser = myUserRepository.findById(myUserId);
            if (!queryMyUser.isPresent()) {
                throw new RecordNotFoundException();
            }
            MyUser myUser = queryMyUser.get();
            myUser.setAdmin(userUpdate.getAdmin());
            myUser.setConfirmed(userUpdate.getConfirmed());
            myUser.setUsername(userUpdate.getUsername());
            myUser.setMobile(userUpdate.getMobile());
            // TODO: CHECK UNIQUE EMAIL throw Bad Request
            // TODO: NEW EMAIL CONFIRMATION
            // TODO: update Role and Confirmed conditions
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
