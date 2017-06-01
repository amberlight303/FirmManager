package com.amberlight.firmmanager.test;

import com.amberlight.firmmanager.model.Post;
import com.amberlight.firmmanager.service.FirmManagerService;
import com.amberlight.firmmanager.service.UserService;
import com.amberlight.firmmanager.web.PostController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.hamcrest.Matchers.*;


/**
 * Standalone test of {@link PostController} with {@link MockitoJUnitRunner}.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class StandalonePostControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FirmManagerService firmManagerService;

    @Mock
    private UserService userService;

    @InjectMocks
    private PostController postController;

    private Post postDummy = new Post();

    @Before
    public void setup() {
        postDummy.setId(5);
        postDummy.setTitle("hi");
        postDummy.setContent("hello");
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(postController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void initAddPostFormTest() throws Exception{
        mockMvc.perform(get("/admin/posts/addPost"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/createOrUpdatePost"))
                .andExpect(model().attributeExists("post"));
    }

    @Test
    public void initEditPostFormTest() throws Exception{
        when(this.firmManagerService.findPostById(5)).thenReturn(postDummy);
        mockMvc.perform(get("/admin/posts/{postId}/editPost",5))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/createOrUpdatePost"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attribute("post",
                        allOf(
                                hasProperty("id",equalTo(5)),
                                hasProperty("title",equalTo("hi")),
                                hasProperty("content",equalTo("hello"))
                        )));
    }
}

