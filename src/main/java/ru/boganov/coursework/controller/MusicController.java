package ru.boganov.coursework.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.boganov.coursework.entity.Music;
import ru.boganov.coursework.service.MusicService;

import java.util.List;

@Controller
@RequestMapping("/music")
public class MusicController {

    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/list")
    public String listMusic(Model model) {
        List<Music> allMusic = musicService.getAllMusic();
        model.addAttribute("musicList", allMusic);
        return "listMusic";
    }

    @GetMapping("/addForm")
    public String showAddMusicForm(Model model) {
        Music music = new Music();
        model.addAttribute("music", music);
        return "addMusic";
    }

    @PostMapping("/save")
    public String saveMusic(@ModelAttribute("music") Music music) {
        musicService.addMusic(music);
        return "redirect:/music/list";
    }

    @GetMapping("/updateForm")
    public String showUpdateMusicForm(@RequestParam("id") int id, Model model) {
        Music music = musicService.getMusicById(id);
        model.addAttribute("music", music);
        return "updateMusic";
    }

    @PostMapping("/update")
    public String updateMusic(@ModelAttribute("music") Music music) {
        musicService.updateMusic(music);
        return "redirect:/music/list";
    }

    @GetMapping("/delete")
    public String deleteMusic(@RequestParam("id") int id) {
        musicService.deleteMusicById(id);
        return "redirect:/music/list";
    }
}