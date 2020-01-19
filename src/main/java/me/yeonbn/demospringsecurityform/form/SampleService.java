package me.yeonbn.demospringsecurityform.form;

import me.yeonbn.demospringsecurityform.account.Account;
import me.yeonbn.demospringsecurityform.account.AccountContext;
import org.springframework.stereotype.Service;

@Service
public class SampleService {
    
    public void dashboard() {
        Account account = AccountContext.getAccount();
        System.out.println("===========================");
        System.out.println(account.getUserName());
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        boolean authenticated = authentication.isAuthenticated();
    }
}
