package com.example.otp_test.controller;

import com.example.otp_test.Service.EmailClass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.util.Random;


@Controller
@RequestMapping(value = "/")
public class LoginController {

    @GetMapping(value = "")
    public String login(){
        return "login";
    }
  @PostMapping(value = "otp")
    public String Otp(@RequestParam String email, HttpSession httpSession){
       StringBuilder stringBuilder=new StringBuilder();
       ///Loop that creates 4 digit random number
        for (int i=1;i<=4;i++){
            stringBuilder.append(new Random().nextInt(10));
        }
        httpSession.setAttribute("otp",String.valueOf(stringBuilder));
        EmailClass emailClass=new EmailClass();
        emailClass.setSubjectAndMessage("OTP","Your OTP is ".concat(String.valueOf(stringBuilder)),email);
        return "redirect:otpPage";
  }
  @GetMapping(value = "otpPage")
  public String OtpPage(){
        return "otp";
  }

 @PostMapping(value = "checkOtp")
    public String checkOtp(@RequestParam String otp,HttpSession httpSession){
        if(otp.equals(String.valueOf(httpSession.getAttribute("otp")))){
            return "login";
        }
        return "otp";
 }

}
