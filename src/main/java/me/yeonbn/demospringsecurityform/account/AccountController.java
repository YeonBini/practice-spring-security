package me.yeonbn.demospringsecurityform.account;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account/{role}/{userName}/{password}")
    public Account createAccount(@PathVariable String role, @PathVariable String userName, @PathVariable String password) {
        Account account = new Account.Builder().userName(userName).password(password).role(role).build();
        return accountService.createNew(account);
    }
}
