package com.spinningnoodle.communitymanager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = CommunityManagerApplication.class, loader = SpringBootContextLoader.class)
@SpringBootTest()
public abstract class AbstractDefs
{
    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mvc;
    protected MvcResult result;
    protected Document document;

    /**
     * Call the url with a GET method and store the MvcResult and Document in state.
     *
     * @param url URL Path
     * @throws Exception If a call is unable to be made
     */
    protected void executeGet(String url) throws Exception
    {
        result = mvc.perform(get(url)).andDo(print()).andReturn();
        document = Jsoup.parse(result.getResponse().getContentAsString());
    }

    /**
     * Call the url with a POST method and store the MvcResult and Document in state.
     *
     * @param url URL Path
     * @throws Exception If a call is unable to be made
     */
    protected void executePost(String url, String content) throws Exception {
        result = mvc.perform(post(url).content(content).param("meetupKey", "2")).andDo(print()).andReturn();
        document = Jsoup.parse(result.getResponse().getContentAsString());
    }
}