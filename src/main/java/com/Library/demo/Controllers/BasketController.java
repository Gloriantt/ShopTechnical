package com.Library.demo.Controllers;

import com.Library.demo.Models.Basket;
import com.Library.demo.Models.Post;
import com.Library.demo.Models.User;
import com.Library.demo.Repos.BasketRepository;
import com.Library.demo.Repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BasketController {
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/basketmain")
    public String basketmain(Model model,@AuthenticationPrincipal User user){
        List<Basket> basket = basketRepository.findAll(user.getId());
       model.addAttribute("basket",basket);
        return "basketmain";
    }

    @GetMapping("/basket/{id}")
    public String basketADD(Model model,@AuthenticationPrincipal User user, @PathVariable(value = "id") long id){
        return "redirect:/blog";
    }
    @GetMapping("/basket/{id}/detail")
    public String bloguser(@PathVariable(value = "id") long id,@AuthenticationPrincipal User user, Model model) {
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blogdetailuser";
    }
    @PostMapping("/basket/{id}/detail")
    public String addbasketsave(@AuthenticationPrincipal User user, @PathVariable(value = "id") long id, Model model ) throws IOException {
        Post post = postRepository.findById(id).orElseThrow();
        int views= post.getViews()+1;
        post.setViews(views);
        postRepository.save(post);
        Basket basket = new Basket(user,post);
        basketRepository.save(basket);
        return "redirect:/blog";
    }
    @PostMapping("/basket/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id,@AuthenticationPrincipal User user, Model model) {
        Basket basket = basketRepository.findById(id).orElseThrow();
        basketRepository.delete(basket);

        return "redirect:/blog";
    }

}
