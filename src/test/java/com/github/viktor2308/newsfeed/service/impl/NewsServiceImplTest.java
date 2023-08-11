package com.github.viktor2308.newsfeed.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.viktor2308.newsfeed.repository.NewsRepository;


import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NewsServiceImpl.class})
@ExtendWith(SpringExtension.class)
class NewsServiceImplTest {

    @MockBean
    private NewsRepository newsRepository;

    @Autowired
    private NewsServiceImpl newsServiceImpl;

    @Test
    void testGetAllNews() {
        when(newsRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(newsServiceImpl.getAllNews().isEmpty());
        verify(newsRepository).findAll();
    }


}