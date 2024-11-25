package com.accord.listener;

import com.accord.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;


/**
 * User : Tanvir Ahmed
 * Date: 11/21/2024.
 */

public class AccountItemReadListener implements ItemReadListener<Account> {

public static final Logger logger = LoggerFactory.getLogger(AccountItemReadListener.class);

@Override
public void beforeRead() {
        logger.info("Reading a new Account Record");
        }

@Override
public void afterRead(Account input) {
        logger.info("New Account record read : " + input);
        }

@Override
public void onReadError(Exception e) {
        logger.error("Error in reading the Account record : " + e);
        }
}
