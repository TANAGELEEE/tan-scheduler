/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.controller;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.tanagelee.tanscheduler.common.BaseJunit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * EngineControllerTest
 *
 * @author liyunjun
 * @date 2022/3/14 10:35
 */
@SpringBootTest
public class EngineControllerTest extends BaseJunit {

    @Test
    public void test() throws Exception {
        mockMvc.perform(get("/engine/convert").param("xx", ""))
                .andDo(print()).andReturn().getResponse().getContentAsString();
    }
}