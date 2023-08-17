package com.github.viktor2308.newsfeed.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.viktor2308.newsfeed.dto.CategoryDto;
import com.github.viktor2308.newsfeed.dto.CreateNewsDto;
import com.github.viktor2308.newsfeed.dto.NewsDto;
import com.github.viktor2308.newsfeed.dto.UpdateNewsDto;
import com.github.viktor2308.newsfeed.service.NewsService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {NewsController.class})
@ExtendWith(SpringExtension.class)
class NewsControllerTest {

    @Autowired
    private NewsController newsController;

    @MockBean
    private NewsService newsService;

    @Test
    void testGetAllNews() throws Exception {
        when(newsService.getAllNews()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/news");
        MockMvcBuilders.standaloneSetup(newsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testCreateNews() throws Exception {
        when(newsService.createNews(Mockito.any()))
                .thenReturn(new NewsDto(1L, "Dr", "Text", "Category", "Jan 1, 2020 8:00am GMT+0100"));

        CreateNewsDto createNewsDto = new CreateNewsDto();
        createNewsDto.setCategory("Category");
        createNewsDto.setText("Text");
        createNewsDto.setTitle("Dr");
        String content = (new ObjectMapper()).writeValueAsString(createNewsDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(newsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"newsId\":1,\"title\":\"Dr\",\"text\":\"Text\",\"category\":\"Category\",\"created\":\"Jan 1, 2020 8:00am"
                                        + " GMT+0100\"}"));
    }

    @Test
    void testCreateNews2() throws Exception {
        when(newsService.createNews(Mockito.any()))
                .thenReturn(new NewsDto(1L, "Dr", "Text", "Category", "Jan 1, 2020 8:00am GMT+0100"));

        CreateNewsDto createNewsDto = new CreateNewsDto();
        createNewsDto.setCategory("Request create new News: {},{}");
        createNewsDto.setText("Text");
        createNewsDto.setTitle("Dr");
        String content = (new ObjectMapper()).writeValueAsString(createNewsDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(newsController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testUpdateNews2() throws Exception {
        when(newsService.updateNews(anyLong(), Mockito.any()))
                .thenReturn(new NewsDto(1L, "Dr", "Text", "Category", "Jan 1, 2020 8:00am GMT+0100"));

        UpdateNewsDto updateNewsDto = new UpdateNewsDto();
        updateNewsDto.setText("Text");
        updateNewsDto.setTitle("Prof");
        String content = (new ObjectMapper()).writeValueAsString(updateNewsDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/news/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(newsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"newsId\":1,\"title\":\"Dr\",\"text\":\"Text\",\"category\":\"Category\",\"created\":\"Jan 1, 2020 8:00am"
                                        + " GMT+0100\"}"));
    }

    @Test
    void testUpdateNewsCategory() throws Exception {
        when(newsService.updateNewsCategory(anyLong(), Mockito.any()))
                .thenReturn(new NewsDto(1L, "Dr", "Text", "Category", "Jan 1, 2020 8:00am GMT+0100"));

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategory("Category");
        String content = (new ObjectMapper()).writeValueAsString(categoryDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/news/{id}/category", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(newsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"newsId\":1,\"title\":\"Dr\",\"text\":\"Text\",\"category\":\"Category\",\"created\":\"Jan 1, 2020 8:00am"
                                        + " GMT+0100\"}"));
    }

    @Test
    void testDeleteNews() throws Exception {
        when(newsService.deleteNews(anyLong())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/news/{id}", 1L);
        MockMvcBuilders.standaloneSetup(newsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteNews2() throws Exception {
        when(newsService.deleteNews(anyLong())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/news/{id}", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(newsController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testSearchNews() throws Exception {
        when(newsService.searchNews(Mockito.any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/news/search");
        MockMvcBuilders.standaloneSetup(newsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testUpdateNews() throws Exception {
        UpdateNewsDto updateNewsDto = new UpdateNewsDto();
        updateNewsDto.setText("Text");
        updateNewsDto.setTitle("Dr");
        String content = (new ObjectMapper()).writeValueAsString(updateNewsDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/news/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(newsController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}

