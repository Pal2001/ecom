package com.pal.ecom.api.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pal.ecom.model.WebOrder;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    @WithUserDetails("UserA")
    public void testUserAAuthenticatedList() throws Exception{
        testAuthenticatedOrderListBelongsToUser("UserA");
    }

    @Test
    @WithUserDetails("UserB")
    public void testUserBAuthenticatedList() throws Exception{
        testAuthenticatedOrderListBelongsToUser("UserB");
    }

    private void testAuthenticatedOrderListBelongsToUser(String username) throws Exception{
        mvc.perform(get("/order")).andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    List<WebOrder> orders = new ObjectMapper().readValue(json, new TypeReference<List<WebOrder>>(){});
                    for(WebOrder order : orders){
                        Assertions.assertEquals(username, order.getUser().getUsername(), "Order list should only be orders belonging to the user.");

                    }
                });
    }

    @Test
    public void testUnauthenticatedOrderList() throws Exception{
        mvc.perform(get("/order")).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }
}
