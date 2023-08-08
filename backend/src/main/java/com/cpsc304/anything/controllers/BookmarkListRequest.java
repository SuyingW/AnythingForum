package com.cpsc304.anything.controllers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookmarkListRequest {
    public String listName;

    @JsonCreator
    BookmarkListRequest(@JsonProperty("listName") String listName) {
        this.listName = listName;
    }
}
