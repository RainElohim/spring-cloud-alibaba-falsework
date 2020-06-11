package com.gupaoedu.sca.service;

import com.gupaoedu.sca.dao.OrderDAO;
import com.gupaoedu.sca.dao.UserDemoDAO;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private UserDemoDAO userDemoDAO;



    public void insertOne(int userId) {

        orderDAO.insertUserOne(userId);

    }

    public void insertTwo(int userId) {
//        int a = 1/0;
        orderDAO.insertUserTwo(userId);

    }

    public void insertUser(String name) {

        userDemoDAO.insertUserDemo(name);

    }

    /**
     * 异常如果被try住 则不会回滚，所以不能放在controller层，因为controller层有拦截器做了try
     */
    @ShardingTransactionType(value = TransactionType.XA)
    @Transactional(rollbackFor = Exception.class)
    public void insert() {
        orderDAO.insertUserOne(333);
        orderDAO.insertUserTwo(666);
        userDemoDAO.insertUserDemo("name");
        int a = 1/0; //人为抛异常
    }
}
