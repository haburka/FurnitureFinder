package com.robb.furniturefinder;

class Item {
    String name;
    String description;
    int photoId;

    Item(String name, String desc, int photoId) {
        this.name = name;
        this.description = desc;
        this.photoId = photoId;
    }
}