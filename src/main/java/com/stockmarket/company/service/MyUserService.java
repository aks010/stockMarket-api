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

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Properties;

@Service
public class MyUserService implements IMyUserService {
    @Autowired
    private MyUserRepository myUserRepository;

    @Override
    public Page<MyUser> listUsers(Pageable pageable) {
        return myUserRepository.findAll(pageable);
    }

    @Override
    public MyUser newUser(MyUser myUser) {
        try {
            Optional<MyUser> queryMyUser = myUserRepository.findByEmail(myUser.getEmail());
            if (queryMyUser.isPresent()) {
                System.out.println(queryMyUser.get().toString());
                throw new BadRequestException("Provided email is already in use!");
            }

            String email = myUser.getEmail();

            if(myUser.getUsername().equals("")) {
                throw new BadRequestException("User Name cannot be empty!");
            }
            if(myUser.getEmail().equals("")) {
                throw new BadRequestException("Email Address cannot be empty!");
            }
            if(myUser.getPassword().equals("")) {
                throw new BadRequestException("Password cannot be empty!");
            }
            if(myUser.getRole().equals("")) {
                throw new BadRequestException("Role cannot be empty!");
            }

            myUser.setConfirmed(false);
            String userRole = myUser.getRole();
            if(userRole.equals("admin"))
                myUser.setAdmin(true);
            else myUser.setAdmin(false);
            sendEmailForConfirmation(email);
            MyUser newUser = myUserRepository.save(myUser);

            // ToDo: EMAIL with other smtp service provider
            return newUser;
        }
        catch (BadRequestException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError(e.getMessage());
        }

    }

    private void sendEmailForConfirmation (String email) throws AddressException, MessagingException {


            final String username = "ashish.iitr017@gmail.com";
            final String password = "Ashish_3787";

            Properties prop = new Properties();

            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "465");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.starttls.required", "true");
            prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


        Session session = Session.getInstance(prop,
                    new javax.mail.Authenticator() {
                        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                            return new javax.mail.PasswordAuthentication(username, password);
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("sftrainerram@gmail.com"));

                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(email)
                );
                message.setSubject("User confirmation email");
                //     message.setText("Dear Mail Crawler,"
                //           + "\n\n Please do not spam my email!");
                message.setContent(
                        "<h1><a href =\"http://127.0.0.1:8080/confirmuser/"+email+"/\"> Click to confirm </a></h1>",
                        "text/html");
                Transport.send(message);

                System.out.println("Mail Sent");

            } catch (MessagingException e) {
                e.printStackTrace();
                throw e;
            }

    }

    @Override
    public MyUser loginMyUser(MyUser myUser) {
        try {
            Optional<MyUser> queryMyUser = myUserRepository.findByEmail(myUser.getEmail());
            if (!queryMyUser.isPresent()) {
                System.out.println(myUser.getEmail());
                throw new BadRequestException("Invalid Credentials");
            }
            MyUser myUserFromDb = queryMyUser.get();
            // todo: bcyryting, jwt
            String dbPassword = myUserFromDb.getPassword();
            String password = myUser.getPassword();

            if(!password.equals(dbPassword)) {
                throw new BadRequestException("Invalid Credentials!");
            }

            return queryMyUser.get();
        }
        catch (RecordNotFoundException e) {
            throw e;
        }
        catch(BadRequestException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerError("Something went wrong!!");
        }
    }

    @Override
    public String confirmUser(String email) {
        try {
            Optional<MyUser> queryMyUser = myUserRepository.findByEmail(email);
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
