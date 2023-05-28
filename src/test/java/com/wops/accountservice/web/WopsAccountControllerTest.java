package com.wops.accountservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wops.accountservice.config.WopsAccountPropertiesConfig;
import com.wops.accountservice.domain.WopsAccount;
import com.wops.accountservice.domain.WopsAccountAlreadyExistsException;
import com.wops.accountservice.domain.WopsAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Alexandre AMEVOR
 */

@WebMvcTest(WopsAccountController.class)
class WopsAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private WopsAccountService accountService;
    @MockBean
    private WopsAccountPropertiesConfig propertiesConfig;

    @Test
    void whenCreateAccountWithoutNumberThenShouldReturn400() throws Exception {
        WopsAccount wopsAccount = new WopsAccount(null, new Date(), true, false);
        given(accountService.createWopsAccount(wopsAccount)).willReturn(wopsAccount);
        mockMvc.perform(post("/wopsAccounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wopsAccount)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateAccountWithExistingNumberThenShouldThrowAccountAlreadyExistsException() throws Exception {
        given(accountService.generateAccountNumber()).willReturn(UUID.randomUUID().toString());
        MvcResult mvcResult = mockMvc.perform(get("/wopsAccounts/generateNumber")).andReturn();

        WopsAccount account1 = new WopsAccount(mvcResult.getResponse().getContentAsString(), new Date(), true, false);
        given(accountService.createWopsAccount(account1)).willReturn(account1);
        mockMvc.perform(post("/wopsAccounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account1)));

        WopsAccount account2 = new WopsAccount(mvcResult.getResponse().getContentAsString(), new Date(), true, false);
        given(accountService.createWopsAccount(account2)).willThrow(WopsAccountAlreadyExistsException.class);
        mockMvc.perform(post("/wopsAccounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account2)))
                .andExpect(status().isUnprocessableEntity());
    }
}