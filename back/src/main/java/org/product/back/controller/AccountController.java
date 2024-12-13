package org.product.back.controller;

import org.product.back.auth.JwtTokenUtil;
import org.product.back.dto.UserDTO;
import org.product.back.entity.Account;
import org.product.back.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class AccountController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public AccountController(AccountService accountService, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/account")
    public ResponseEntity<String> createAccount(@Validated @RequestBody Account account) {
        if (accountService.findByEmail(account.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already in use!");
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.ok("Account " + createdAccount.getEmail() + " created successfully");
    }

    @PostMapping("/token")
    public ResponseEntity<String> authenticate(@RequestBody UserDTO userDTO) {
        Account storedAccount = accountService.findByEmail(userDTO.getEmail());
        if (storedAccount != null && passwordEncoder.matches(userDTO.getPassword(), storedAccount.getPassword())) {
            String token = jwtTokenUtil.generateToken(storedAccount.getEmail());
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(401).body("Invalid email or password!");
    }
}
