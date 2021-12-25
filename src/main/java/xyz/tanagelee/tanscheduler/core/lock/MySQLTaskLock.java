/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.core.lock;

import lombok.extern.slf4j.Slf4j;
import xyz.tanagelee.tanscheduler.common.context.SpringContextHolder;
import xyz.tanagelee.tanscheduler.common.exception.SystemException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MySQLTaskLock
 *
 * @author liyunjun
 * @date 2021/12/25 18:24
 */
@Slf4j
public class MySQLTaskLock extends TaskLock {

    private Connection connection;

    private boolean autoCommit;

    public MySQLTaskLock(String resource) {
        super(resource);
        DataSource dataSource = SpringContextHolder.getBean("mysqlDataSource", DataSource.class);
        try {
            this.connection = dataSource.getConnection();
            this.autoCommit = this.connection.getAutoCommit();
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            log.error("构建MySQL分布式锁失败", e);
            if (null != this.connection) {
                try {
                    this.connection.setAutoCommit(autoCommit);
                } catch (SQLException e1) {
                    log.error("", e);
                }
                try {
                    this.connection.close();
                } catch (SQLException e1) {
                    log.error("", e);
                }
            }
            throw new SystemException(e);
        }
    }

    @Override
    public void acquire() {
        acquire(-1L);
    }

    @Override
    public void acquire(long timeout) {
        String sql = String
                .format("select * from task_schedule_lock where lock_name = '%s' for update", resource);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            if (timeout > 0L) {
                ps.setQueryTimeout((int) (timeout / 1000));
            }
            rs = ps.executeQuery();
        } catch (SQLException e) {
            throw new SystemException(e);
        } finally {
            if (null != rs) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.error("", e);
                }
            }
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.error("", e);
                }
            }
        }
    }

    @Override
    public void release() {
        if (null != connection) {
            try {
                connection.commit();
            } catch (SQLException e) {
                throw new SystemException(e);
            } finally {
                try {
                    connection.setAutoCommit(autoCommit);
                } catch (SQLException e) {
                    log.error("", e);
                }
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("", e);
                }
            }
        }
    }
}