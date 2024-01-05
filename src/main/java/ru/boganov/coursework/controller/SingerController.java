package ru.boganov.coursework.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.boganov.coursework.entity.Singer;
import ru.boganov.coursework.service.SingerService;

import java.util.List;

@Controller
@RequestMapping("/singers")
public class SingerController {

    private final SingerService singerService;

    public SingerController(SingerService singerService) {
        this.singerService = singerService;
    }

    @GetMapping("/list")
    public String listSingers(Model model) {
        List<Singer> allSingers = singerService.getAllSingers();
        model.addAttribute("singerList", allSingers);
        return "listSingers";
    }

    @GetMapping("/addForm")
    public String showAddSingerForm(Model model) {
        Singer singer = new Singer();
        model.addAttribute("singer", singer);
        return "addSinger";
    }

    @PostMapping("/save")
    public String saveSinger(@ModelAttribute("singer") Singer singer) {
        singerService.addSinger(singer);
        return "redirect:/singers/list";
    }

    @GetMapping("/updateForm")
    public String showUpdateSingerForm(@RequestParam("id") int id, Model model) {
        Singer singer = singerService.getSingerById(id);
        model.addAttribute("singer", singer);
        return "updateSinger";
    }

    @PostMapping("/update")
    public String updateSinger(@ModelAttribute("singer") Singer singer) {
        singerService.updateSinger(singer);
        return "redirect:/singers/list";
    }

    @GetMapping("/delete")
    public String deleteSinger(@RequestParam("id") int id) {
        singerService.deleteSingerById(id);
        return "redirect:/singers/list";
    }
}