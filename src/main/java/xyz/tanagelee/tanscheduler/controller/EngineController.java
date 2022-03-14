/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tanagelee.tanscheduler.engine.EngineServiceDecorator;
import xyz.tanagelee.tanscheduler.vo.TaskVO;

/**
 * EngineController
 *
 * @author liyunjun
 * @date 2022/3/14 9:38
 */
@RestController
@Slf4j
@RequestMapping(value = "/engine")
public class EngineController {
    @Autowired
    private EngineServiceDecorator engineService;

    @GetMapping("/convert")
    public void convert() {
        TaskVO taskVO = new TaskVO();
        try {
            engineService.execute(taskVO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}