package com.ysc.service.impl;

import com.ysc.dataobject.ProductInfo;
import com.ysc.enums.ProductStatusEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("123456");
        assertEquals("123456", productInfo.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        assertNotEquals(0, productInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        //System.out.println(productInfoPage.getTotalElements());
        assertNotEquals(0,productInfoPage.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("饭");
        productInfo.setProductPrice(new BigDecimal(4.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("可以吃的饭");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);

        ProductInfo result = productService.save(productInfo);
        assertNotNull(productInfo);
    }

    @Test
    public void onSale() {
        ProductInfo productInfo = productService.onSale("123456");
        assertEquals(ProductStatusEnum.UP, productInfo.getProductStatusEnum());
    }

    @Test
    public void offSale() {
        ProductInfo productInfo = productService.offSale("123456");
        assertEquals(ProductStatusEnum.DOWN, productInfo.getProductStatusEnum());
    }
}