package de.soflimo.sgr.web;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.soflimo.sgr.SgrBadmintonApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SgrBadmintonApplication.class)
@WebAppConfiguration
@Ignore
public class MockMvcWebTests {

  @Autowired
  WebApplicationContext webContext;
  private MockMvc mockMvc;

  @Before
  public void setupMockMvc() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(webContext)
        .apply(springSecurity())
        .build();
  }
  
  @Test
  public void homePage_unauthenticatedUser() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().is3xxRedirection())
        .andExpect(header().string("Location", "http://localhost/login.html"));
  }
  
//  @Test
    //  @WithUserDetails("craig")
    //  @WithMockUser(username="craig", password="password", roles="READER")
    //  public void homePage_authenticatedUser() throws Exception {
    //    Reader expectedReader = new Reader();
    //    expectedReader.setUsername("craig");
    //    expectedReader.setPassword("password");
    //    expectedReader.setFullname("Craig Walls");
    //
    //    mockMvc.perform(get("/"))
    //        .andExpect(status().isOk())
    //        .andExpect(view().name("readingList"))
    //        .andExpect(model().attribute("reader",
    //                           samePropertyValuesAs(expectedReader)))
    //        .andExpect(model().attribute("books", hasSize(0)))
    //        .andExpect(model().attribute("amazonID", "habuma-20"));
    //  }

}
