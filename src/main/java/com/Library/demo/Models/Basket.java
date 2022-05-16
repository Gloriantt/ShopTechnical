package com.Library.demo.Models;

import javax.persistence.*;

@Entity
@Table(name = "Basket")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "posts_id")
    private Post order;


    public Basket(User author, Post order) {
        this.author = author;
        this.order = order;
    }

    public Post getOrder() {
        return order;
    }

    public void setOrder(Post order) {
        this.order = order;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }


    public Basket() {

    }
}
