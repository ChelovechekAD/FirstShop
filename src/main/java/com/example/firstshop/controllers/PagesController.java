package com.example.firstshop.controllers;

import com.example.firstshop.database.Bricks;
import com.example.firstshop.repository.BricksRepos;
import com.example.firstshop.database.Timber;
import com.example.firstshop.repository.TimberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class PagesController {
    @Autowired TimberRepository timberRepository;
    @Autowired BricksRepos bricksRepos;
    @GetMapping(path = "/")
    public String mainPage(Model model){
        return "index";
    }
    @GetMapping(path = "/catalog")
    public String catalog(Model model){
        return "catalog";
    }
    @GetMapping(path = "/catalog/{type}")
    public String selectCategory(@PathVariable(value = "type") String type, Model model){
        if(type.equals("timber")){
            Iterable<Timber> list = timberRepository.findAll();
            model.addAttribute("list", list);
        }else if(type.equals("bricks")){
            Iterable<Bricks> list = bricksRepos.findAll();
            model.addAttribute("list", list);
        }

        model.addAttribute("type", type);
        return "catalog";
    }
    @GetMapping(path = "/catalog/{type}/{id}")
    public String showFullDescription(@PathVariable(value = "type") String type, @PathVariable(value = "id") Long id, Model model){
        if (type.equals("timber")){
            Optional<Timber> prod = timberRepository.findById(id);
            ArrayList<Timber> out = new ArrayList<>();
            prod.ifPresent(out::add);
            model.addAttribute("prod", out);
        } else if (type.equals("bricks")){
            Optional<Bricks> prod = bricksRepos.findById(id);
            ArrayList<Bricks> out = new ArrayList<>();
            prod.ifPresent(out::add);
            model.addAttribute("prod", out);
        }
        model.addAttribute("type", type);

        return "catalog";
    }
    @GetMapping(path = "/test")
    public @ResponseBody Iterable<Timber> getAll(){return timberRepository.findAll();}
    @GetMapping(path = "/auth")
    public String authPath(Model model){
        model.addAttribute("title", "Авторизация");
        return "auth_reg";
    }


}
