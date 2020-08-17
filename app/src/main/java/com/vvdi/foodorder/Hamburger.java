package com.vvdi.foodorder;

import java.util.ArrayList;

public class Hamburger
{
    private String _id;
    private int _count;
    private IngridientsH ingridientsH;


    public Hamburger()
    {
    }

    public void setIngridientsH(IngridientsH ingridientsH) { this.ingridientsH = ingridientsH; }

    public void set_id(String _id) { this._id = _id; }
    public void set_count(int _count) { this._count = _count; }

    public String get_id() { return _id; }
    public int get_count() { return _count; }
}
