package com.amberlight.firmmanager.test;

import com.amberlight.firmmanager.model.Post;
import com.amberlight.firmmanager.service.*;
import com.amberlight.firmmanager.web.PostController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

/**
 *
 * Test of {@link PostController} with {@link SpringJUnit4ClassRunner}.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:testSpringAppconfig/test-appconfig-root.xml"})
public class PostControllerTest {

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private FirmManagerService firmManagerService;

    private Post post = new Post();

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(springSecurity());
        this.mockMvc = builder.build();
        post.setId(88888);
        post.setContent("someContent");
        post.setContentPreview("someContentPreview");
        post.setTitle("someTitle");

    }

//    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void initAddPostFormTest () throws Exception{
        mockMvc.perform(get("/admin/posts/addPost"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/createOrUpdatePost"))
                .andExpect(forwardedUrl("/WEB-INF/views/posts/createOrUpdatePost.jsp"))
                .andExpect(model().attributeExists("post"));
    }

    @Test
    public void initEditPostFormTest() throws Exception{
        Mockito.when(firmManagerService.findPostById(5)).thenReturn(post);
        mockMvc.perform(get("/admin/posts/{postId}/editPost", 5))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/createOrUpdatePost"))
                .andExpect(forwardedUrl("/WEB-INF/views/posts/createOrUpdatePost.jsp"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attribute("post",
                allOf(
                        hasProperty("id",equalTo(88888)),
                        hasProperty("title",equalTo("someTitle")),
                        hasProperty("content",equalTo("someContent"))
                )
        ));
    }


}
