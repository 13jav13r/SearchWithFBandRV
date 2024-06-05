package com.example.buttonnavigationviewlesson.adapter

import kotlin.properties.Delegates

class Product() {
    lateinit var image: String
    var itemBasePrice by Delegates.notNull<Int>()
    lateinit var moduleName: String

    constructor(image: String, moduleName: String, itemBasePrice: Int) : this() {
        this.image = image
        this.itemBasePrice = itemBasePrice
        this.moduleName = moduleName
    }
}
