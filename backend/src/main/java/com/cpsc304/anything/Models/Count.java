package com.cpsc304.anything.Models;

public class Count {

    // count the number of posts in each category

    public String categoryName;
    public int count;

    public Count(String categoryName, int count) {
       this.count = count;
       this.categoryName = categoryName;
    }

}
