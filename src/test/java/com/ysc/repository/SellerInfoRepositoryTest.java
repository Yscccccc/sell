package com.ysc.repository;

import com.ysc.dataobject.SellerInfo;
import com.ysc.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository repository;

    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("adc");

        SellerInfo result = repository.save(sellerInfo);
        assertNotNull(result);
    }


    @Test
    public void findByOpenid() {

        SellerInfo result = repository.findByOpenid("adc");
        assertEquals("adc", result.getOpenid());
    }
}