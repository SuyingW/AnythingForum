package com.cpsc304.anything.controllers;

import com.cpsc304.anything.Models.Collection;
import com.cpsc304.anything.Models.Post;
import com.cpsc304.anything.Models.User;
import com.cpsc304.anything.Models.Writer;
import com.cpsc304.anything.Models.Count;
import com.cpsc304.anything.Models.*;
import com.cpsc304.anything.database.DatabaseConnectionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:9000")
@RestController
public class AnythingController {
    @Autowired private DatabaseConnectionHandler handler;

    @GetMapping("/")
    public Map<String, String> test() {
        handler.getTableNames();
        return Collections.singletonMap("success", "true");
    }

    @PostMapping("/login")
    public Map<String, String> userLogin(@RequestBody UserRequest user) {
        return Collections.singletonMap("username", handler.userLogin(user.email, user.password));
    }

    @PostMapping("/registration")
    public Map<String, String> userRegistration(@RequestBody User user) {
        return Collections.singletonMap("username", handler.userRegistration(user.userID, user.email, user.userName, user.userPassword));
    }

    @GetMapping("/users")
    public Map<String, User[]> userList() {
        return Collections.singletonMap("users", handler.userList());
    }

    @GetMapping("/posts")
    public Map<String, Post[]> postList() {
        return Collections.singletonMap("posts", handler.postList());
    }

    @GetMapping("/post/{postID}")
    public Map<String, Post> getPost(@PathVariable("postID") int postID) {
        System.out.println(postID);
        return Collections.singletonMap("post", handler.getPost(postID));
    }

    @PostMapping("/writer")
    public Map<String, String> beWriter(@RequestBody Writer writer) {
        return Collections.singletonMap("alias", handler.beWriter(writer.userID, writer.alias));
    }

    @GetMapping("/collections")
    public Map<String, Collection[]> collectionList() {return Collections.singletonMap("collections", handler.collectionList());}

    @GetMapping("/users/{userID}/bookmarkLists")
    public Map<String, BookmarkList[]> getBookmarkLists(@PathVariable("userID") int userID) {
        return Collections.singletonMap("bookmarkLists", handler.getBookmarkLists(userID));
    }

    @PostMapping("/users/{userID}/bookmarkLists")
    public Map<String, Boolean> addBookmarkList(@RequestBody BookmarkListRequest body, @PathVariable("userID") int userID) {
        System.out.println(body.listName);
        return Collections.singletonMap("success", handler.addBookmarkList(userID, body.listName));
    }

    @GetMapping("/bookmarkList/{userID}/{listID}/posts")
    public Map<String, Post[]> getPostsInBookmarkList(@PathVariable("userID") int userID, @PathVariable("listID") int listID) {
        return Collections.singletonMap("posts", handler.getPostsInBookmarkList(userID, listID));
    }

    @GetMapping("/collections/{collectionID}/posts")
    public Map<String, Post[]> postListInColl(@PathVariable("collectionID") int collectionID) {
        return Collections.singletonMap("posts", handler.postInColl(collectionID));
    }

    @GetMapping("/groupBy")
    public Map<String, Count[]> groupBy() {
        return Collections.singletonMap("counts", handler.groupBy());
    }

    @GetMapping("/filteredPosts/{categoryID}")
    public Map<String, Post[]> filterPost(@PathVariable("categoryID") int categoryID) {
        return Collections.singletonMap("posts", handler.filterPost(categoryID));
    }

}
