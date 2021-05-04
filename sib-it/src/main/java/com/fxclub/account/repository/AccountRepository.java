package com.fxclub.account.repository;

import com.fxclub.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AccountRepository extends JpaRepository <Account , Long> {

}
