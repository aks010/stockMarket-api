package com.stockmarket.company.auth;

import com.stockmarket.company.entity.MyUser;
import com.stockmarket.company.exceptions.BadRequestException;
import com.stockmarket.company.exceptions.InternalServerError;
import com.stockmarket.company.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    MyUserRepository myUserRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public Collection<? extends GrantedAuthority> getAuthorities() {

        MyUser user = new MyUser() ;

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();


        authorities.add(new SimpleGrantedAuthority(user.getRole()));


        return authorities;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> queryMyUser = myUserRepository.findByUsername(username);

        if (!queryMyUser.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        //non dto code below	//return new org.springframework.security.core.userdetails.User(user.getname(), user.getpassword(),
        //	new ArrayList<>());
        return new MyUserDetails(queryMyUser.get()) {
        };//you have to implement userdetails if you dont want to use dto
    }

    // implement without dto	public MyUser save(UserDto user) {
    public MyUser save(MyUser myUser) {
        try {
            Optional<MyUser> queryMyUser = myUserRepository.findByEmail(myUser.getEmail());
            if (queryMyUser.isPresent()) {
                System.out.println(queryMyUser.get().toString());
                throw new BadRequestException("Provided email is already in use!");
            }
            Optional<MyUser> queryByUsername = myUserRepository.findByUsername(myUser.getUsername());
            if (queryByUsername.isPresent()) {
                throw new BadRequestException("Provided Username is already in use!");
            }

            String username = myUser.getUsername();

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
//            sendEmailForConfirmation(username, myUser.getEmail());

            MyUser newUser = new MyUser();
            newUser.setUsername(myUser.getUsername());
            newUser.setPassword(bcryptEncoder.encode(myUser.getPassword()));
            newUser.setEmail(myUser.getEmail());
            newUser.setRole(myUser.getRole());
            newUser.setMobile(myUser.getMobile());
            newUser.setAdmin(myUser.getAdmin());
            newUser.setConfirmed(myUser.getConfirmed());
            newUser = myUserRepository.save(newUser);

            // ToDo: EMAIL with other smtp service provider
            return newUser;
        }
        catch (BadRequestException e) {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new InternalServerError(e.getMessage());
        }
    }


    private void sendEmailForConfirmation (String username, String recEmail) throws AddressException, MessagingException {

System.out.println("PREPARING TO SEND EMAIL!!");
        final String email = "ashish.iitr017@gmail.com";
        final String password = "";

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
                        return new javax.mail.PasswordAuthentication(email, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sftrainerram@gmail.com"));

            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recEmail)
            );
            message.setSubject("User confirmation email");
            //     message.setText("Dear Mail Crawler,"
            //           + "\n\n Please do not spam my email!");
            message.setContent(
                    "<h1><a href =\"http://127.0.0.1:8081/confirmuser/"+username+"/\"> Click to confirm </a></h1>",
                    "text/html");
            Transport.send(message);

            System.out.println("Mail Sent");

        } catch (MessagingException e) {
            e.printStackTrace();
            throw e;
        }

    }

}
