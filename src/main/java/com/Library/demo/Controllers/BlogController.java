package com.Library.demo.Controllers;

import com.Library.demo.Models.Post;
import com.Library.demo.Models.User;
import com.Library.demo.Repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class BlogController {
    @Autowired
    private PostRepository postRepository;
    @Value("${upload.path}")
    private String uploadPath;


    @GetMapping("/blog")
    public String blogMain(Model model,@AuthenticationPrincipal User user) {
        if(user.getId()== 999) {
            Iterable<Post> posts = postRepository.findAll();
            model.addAttribute("posts", posts);
            return "blog-main";
        }else {
            Iterable<Post> posts = postRepository.findAll();
            model.addAttribute("posts", posts);
            return "blog-main-user";
        }

    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model,@AuthenticationPrincipal User user) {
        if(user.getId()==999){
        return "blog-add";}else {
            return "redirect:/blog";
        }
    }

    @GetMapping("/blogsearch")
    public String blogsearch(Model model,@RequestParam String title,@AuthenticationPrincipal User user){
        List<Post> posts = postRepository.SearchName(title);
        if (posts == null){
            return "redirect:/blog";
        }else {
            model.addAttribute("posts",posts);
            return "blogsearch";
        }
    }
    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam("file")MultipartFile file, Model model,
                              @RequestParam String title, @RequestParam String anons,
                              @RequestParam String brand, @RequestParam String price,
                              @RequestParam String full_text) throws IOException {
        Post post = new Post(title, anons, brand, price, full_text);
        if (file != null) {
            File uploadDir=new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile =UUID.randomUUID().toString();
            String resultFileName= uuidFile +"."+ file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/"+resultFileName));
            post.setFile(resultFileName);
        }
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model,@AuthenticationPrincipal User user) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        if(user.getId()==999){
            return "blog-details";
        }else{
            return "blogdetailuser";
        }

    }


    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, @RequestParam String brand, @RequestParam String price, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setBrand(brand);
        post.setPrice(price);
        post.setFull_text(full_text);
        postRepository.save(post);

        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);

        return "redirect:/blog";
    }
}
