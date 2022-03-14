/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.common;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * BaseJunit
 *
 * @author liyunjun
 * @date 2022/3/14 10:31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ComponentScan(basePackages = {"xyz.tanagelee.tanscheduler"})
public class BaseJunit {
    @Autowired
    WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;

    /**
     * 初始化SpringmvcController类测试环境
     */
    @Before
    public void setup() {
        //加载web容器上下文
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
}