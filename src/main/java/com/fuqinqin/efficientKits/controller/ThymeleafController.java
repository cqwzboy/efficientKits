package com.fuqinqin.efficientKits.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fuqinqin on 2018/10/8.
 */
@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {

    @GetMapping("/test/{lang}")
    public String test(HttpSession session, Model model, @PathVariable("lang") String lang){
        model.addAttribute("content", "Hello <font size='10px' color='red'>thymeleaf</font>!");

        List<Person> persons = new ArrayList<>();
        persons.add(new Person(1, "zhangsan"));
        persons.add(new Person(2, "lisi"));
        persons.add(new Person(3, "wangwu"));
        persons.add(new Person(4, "zhaoliu"));
        persons.add(new Person(5, "heqi"));
        model.addAttribute("persons", persons);

        model.addAttribute("date", new Date());

        session.setAttribute("user", new Person(6, "wangba"));

        model.addAttribute("flag", "0");

        model.addAttribute("welcome", "zh".equals(lang)?"index.welcome":"index.home."+lang);

        return "index";
    }

    @Data
    @AllArgsConstructor
    @ToString
    public static class Person{
        private int id;
        private String name;
    }

}
