package com.fxclub.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fxclub.account.model.Account;
import com.fxclub.account.service.AccountService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc; // имитация MVC клиента для выполнения HTTP запросов

    @Test
    public void testA_create() throws Exception {
        String content = "{\"id\":1}";
        Account account = new Account();
        account.setId(1L);
        account.setBalance(0);

        this.mockMvc
                .perform(post("/api/v1/account/create")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(content));
    }


    @Test
    public void testB_info() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String content = "{\"id\":1,\"balance\":0.0}";

        AccountInfoRequest request = new AccountInfoRequest();
        request.setId(1);

        this.mockMvc.perform(post("/api/v1/account/info")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.balance", is(0.0)))
                .andExpect(content().json(content));
    }


    @Test
    public void testC_deposit() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        AccountDepositRequest depositRequest = new AccountDepositRequest();
        depositRequest.setId(1);
        depositRequest.setAmount(100);

        this.mockMvc.perform(post("/api/v1/account/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(depositRequest)))
                .andExpect(status().isOk());
    }


    @Test
    public void testD_infoAfterDeposit() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String content = "{\"id\":1,\"balance\":100.0}";

        AccountInfoRequest request = new AccountInfoRequest();
        request.setId(1);

        this.mockMvc.perform(post("/api/v1/account/info")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(content));
    }


    @Test
    public void testE_withdraw() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        AccountDepositRequest depositRequest = new AccountDepositRequest();
        depositRequest.setId(1);
        depositRequest.setAmount(99);

        this.mockMvc.perform(post("/api/v1/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(depositRequest)))
                .andExpect(status().isOk());
    }


    @Test
    public void testF_infoAfterWithdraw() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        AccountInfoRequest request = new AccountInfoRequest();
        request.setId(1);

        this.mockMvc.perform(post("/api/v1/account/info")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.balance", is(1.0)));
    }
}