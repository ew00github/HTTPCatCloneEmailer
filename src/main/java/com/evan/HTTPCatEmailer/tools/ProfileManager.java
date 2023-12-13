package com.evan.HTTPCatEmailer.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ProfileManager {

    private final Environment environment;

    @Value("${spring.mail.brad.username}")
    private String usernameBrad;

    @Value("${spring.mail.evan.username}")
    private String usernameEvan;

    @Value("${spring.mail.username}")
    private String usernameDefault;

    @Value("${spring.mail.test.username}")
    private String usernameTest;

    public ProfileManager(Environment environment){
        this.environment = environment;
    }

    public String getActiveProfile(){
        return environment.getActiveProfiles()[0];
    }

    public String getActiveEmailUsername(){
        if("brad".equals(getActiveProfile())){
            return usernameBrad;
        } else if("evan".equals(getActiveProfile())){
            return usernameEvan;
        } else if ("test".equals(getActiveProfile())) {
            return usernameTest;
        } else{
            return usernameDefault;
        }
    }
}
