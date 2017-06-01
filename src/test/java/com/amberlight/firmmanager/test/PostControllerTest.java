package com.amberlight.firmmanager.test;

import com.amberlight.firmmanager.model.Employee;
import com.amberlight.firmmanager.model.Post;
import com.amberlight.firmmanager.model.Role;
import com.amberlight.firmmanager.model.User;
import com.amberlight.firmmanager.repository.UserDao;
import com.amberlight.firmmanager.service.*;
import com.amberlight.firmmanager.web.ControllerAdvice;
import com.amberlight.firmmanager.web.PostController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;

import java.lang.instrument.Instrumentation;
import java.util.HashSet;
import java.util.Set;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

/**
 * Created by amberlight on 30.05.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//file:src/main/webapp/WEB-INF/appconfig-root.xml
@ContextConfiguration(locations = {"classpath:testSpringAppconfig/test-appconfig-root.xml"})
public class PostControllerTest {

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;


    @Autowired
    private FirmManagerService firmManagerService;// = Mockito.mock(FirmManagerService.class);


  //  @InjectMocks
    private PostController postController;


  //  @Mock
    private UserServiceImpl userService = Mockito.mock(UserServiceImpl.class);

    private UserDao userDao = Mockito.mock(UserDao.class);

    private SecurityService securityService = Mockito.mock(SecurityService.class);

    private Authentication auth = Mockito.mock(Authentication.class);

  //  @InjectMocks
  //  private ControllerAdvice controllerAdvice;// = Mockito.mock(ControllerAdvice.class);


    private UserDetailsServiceImpl userDetailsServiceImpl = Mockito.mock(UserDetailsServiceImpl.class);

  //  @Mock
  //  private EntityManager entityManager;

    private User user = new User();

    private Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

    private SecurityServiceImpl securityServiceImpl= Mockito.mock(SecurityServiceImpl.class);

    private Post post = new Post();


    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(springSecurity());
        this.mockMvc = builder.build();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setId(1);
        user.setFirstName("admin");
        user.setLastName("admin");
        Role role = new Role();
        role.setId(2);
        role.setName("ROLE_ADMIN");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        user.setEmployee(null);
        MockitoAnnotations.initMocks(this);
        for (Role role1 : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role1.getName()));
        }


        post.setId(88888);
        post.setContent("someContent");
        post.setContentPreview("someContentPreview");
        post.setTitle("someTitle");

    }


    @WithMockUser(username = "admin", password = "admin",roles = {"ADMIN"})
   // @WithUserDetails("admin")
    @Test
    public void initAddPostFormTest () throws Exception{
       Mockito.when(auth.getName()).thenReturn("admin");
     //   Mockito.when(userService.findByUsername(anyString())).thenReturn(user);
       Mockito.when(userService.findUserByUserNameFetchEmployee(anyString())).thenReturn(user);
    //   Mockito.when(controllerAdvice.getCurrentUser()).thenReturn(user);
        Mockito.when(securityService.findLoggedInUsername()).thenReturn("admin");
        Mockito.when(userDetailsServiceImpl.loadUserByUsername(anyString()))
                .thenReturn(new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities));
/*
        mockMvc.perform(get("/admin/posts/addPost"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/createOrUpdatePost"))
                .andExpect(forwardedUrl("/WEB-INF/views/posts/createOrUpdatePost.jsp"))
                .andExpect(model().attributeExists("post"));
                //.andExpect(model().attribute("post", hasItem()
*/
        Mockito.when(firmManagerService.findPostById(5)).thenReturn(post);
        mockMvc.perform(get("/admin/posts/{postId}/editPost", 5))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/createOrUpdatePost"))
                .andExpect(forwardedUrl("/WEB-INF/views/posts/createOrUpdatePost.jsp"))
                .andExpect(model().attributeExists("post")).andExpect(model().attribute("post",
                allOf(
                        hasProperty("id",equalTo(88888)),
                        hasProperty("title",equalTo("someTitle")),
                        hasProperty("content",equalTo("someContent"))
                )
        ));

    }




}
